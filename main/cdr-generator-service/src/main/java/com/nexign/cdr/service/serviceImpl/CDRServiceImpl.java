package com.nexign.cdr.service.serviceImpl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

import com.nexign.cdr.entity.Subscriber;
import com.nexign.cdr.entity.CDR;
import com.nexign.cdr.enums.CallType;
import com.nexign.cdr.repository.SubscriberRepository;
import com.nexign.cdr.repository.CDRRepositoryFile;
import com.nexign.cdr.repository.CDRRepositoryDB;
import com.nexign.cdr.service.CDRService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * Implementation of {@link CDRService} that generate CDRs, 
 * saves them in DB and in files and sends them to Kafka
 */
@Slf4j
@Service
public class CDRServiceImpl implements CDRService {

    private final CDRRepositoryDB cdrRepositoryDB;
    private final CDRRepositoryFile cdrRepositoryFile;
    private final SubscriberRepository subscriberRepository;
    private final KafkaTemplate<String, byte[]> kafkaTemplate;
    @Value("${kafka.topics.cdr}")
    private final String KAFKA_TOPIC;

    private final int NUM_THREADS = 10;
    private final ExecutorService executorService = Executors.newFixedThreadPool(NUM_THREADS);

    private final Random random = new Random();

    private final int year = LocalDateTime.now().getYear();
    private final LocalDateTime currentYear = LocalDateTime.of(year, 1, 1, 0, 0);

    private final long rightBound = Year.isLeap(year) ? 60 * 60 * 24 * 366L : 60 * 60 * 24 * 365L;
    private final AtomicLong leftBound = new AtomicLong(0L);

    private final int MAX_CALL_DELAY = 60 * 60 * 24 * 7;
    private final int MAX_CALL_TIME = 60 * 5;

    private final int RECORDS_IN_FILE = 10;

    @Autowired
    public CDRServiceImpl(CDRRepositoryDB cdrRepositoryDB,
            CDRRepositoryFile cdrRepositoryFile,
            SubscriberRepository subscriberRepository,
            KafkaTemplate<String, byte[]> kafkaTemplate) {
        this.cdrRepositoryDB = cdrRepositoryDB;
        this.cdrRepositoryFile = cdrRepositoryFile;
        this.subscriberRepository = subscriberRepository;
        this.kafkaTemplate = kafkaTemplate;
        this.KAFKA_TOPIC = "cdr-topic";
    }

    @Override
    public void generateCDR() {
        try {
            deleteFolder(Paths.get("./CDRFiles/"));
            cdrRepositoryDB.deleteAll();
            log.info("Directory deleted successfully.");
        } catch (IOException e) {
            log.info("Failed to delete the directory.");
            e.printStackTrace();
        }

        List<Subscriber> subscribers = subscriberRepository.findAll();
        int subscribersSize = subscribers.size();

        BlockingQueue<CDR> queue = new PriorityBlockingQueue<>(RECORDS_IN_FILE, Comparator.comparing(CDR::getStartTime));

        if (subscribersSize == 0) {
            throw new IllegalArgumentException("There are no subscribers in the system");
        }

        log.info("Generating CDR...");

        for (int index = 0; index < NUM_THREADS; index++) {
            executorService.submit(() -> {
                while (leftBound.get() < rightBound) {
                    long currentLeftBound = leftBound.getAndAdd(random.nextInt(MAX_CALL_DELAY));

                    int firstSubscriberId = random.nextInt(subscribersSize);
                    int secondSubscriberId;
                    do {
                        secondSubscriberId = random.nextInt(subscribersSize);
                    } while (firstSubscriberId == secondSubscriberId);

                    Subscriber firstSubscriber = subscribers.get(firstSubscriberId);
                    Subscriber secondSubscriber = subscribers.get(secondSubscriberId);

                    LocalDateTime startTime = currentYear.plusSeconds(currentLeftBound);
                    LocalDateTime endTime = startTime.plusSeconds(random.nextInt(MAX_CALL_TIME));

                    CDR cdr1 = CDR.builder()
                            .callType(CallType.getByCode("01"))
                            .subscriberServed(firstSubscriber)
                            .subscriberConnected(secondSubscriber)
                            .startTime(startTime)
                            .endTime(endTime)
                            .build();

                    CDR cdr2 = CDR.builder()
                            .callType(CallType.getByCode("02"))
                            .subscriberServed(secondSubscriber)
                            .subscriberConnected(firstSubscriber)
                            .startTime(startTime)
                            .endTime(endTime)
                            .build();

                    try {
                        queue.put(cdr1);
                        queue.put(cdr2);

                        synchronized (queue) {
                            if (queue.size() >= RECORDS_IN_FILE) {
                                saveCDR(queue);
                            }
                        }
                    } catch (InterruptedException | IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        }

        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
        }

        if (!queue.isEmpty()) {
            try {
                saveCDR(queue);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        log.info("CDR generation finished");
    }

    public void saveCDR(BlockingQueue<CDR> queue) throws IOException {
        List<CDR> queueList = new ArrayList<>(RECORDS_IN_FILE);
        queue.drainTo(queueList, RECORDS_IN_FILE);
        File file = cdrRepositoryFile.saveToFile(queueList);
        cdrRepositoryDB.saveAll(queueList);
        byte[] fileData = Files.readAllBytes(file.toPath());
        kafkaTemplate.send(KAFKA_TOPIC, fileData);
    }

    public static void deleteFolder(Path folder) throws IOException {
        Files.walk(folder)
             .sorted((a, b) -> b.compareTo(a))
             .forEach(path -> {
                 try {
                     Files.delete(path);
                 } catch (IOException e) {
                     e.printStackTrace();
                 }
             });
    }
}

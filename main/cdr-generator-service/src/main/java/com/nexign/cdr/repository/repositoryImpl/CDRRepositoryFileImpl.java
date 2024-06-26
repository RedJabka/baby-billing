package com.nexign.cdr.repository.repositoryImpl;

import com.nexign.cdr.entity.CDR;
import com.nexign.cdr.repository.CDRRepositoryFile;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.nio.file.Paths;
import java.nio.file.Files;

/**
 * Implementation of {@link CDRRepositoryFile} that work with files
 */
@Repository
public class CDRRepositoryFileImpl implements CDRRepositoryFile {

    @Override
    public File saveToFile(List<CDR> cdrList) throws IOException {
        String fileName = getFileName();

        try (FileWriter writer = new FileWriter(fileName)) {
            for (CDR cdr : cdrList) {
                String cdrRecord = cdr.getCallType().getCode() + "," +
                        cdr.getSubscriberServed().getMsisdn() + ", " +
                        cdr.getSubscriberConnected().getMsisdn() + ", " +
                        cdr.getStartTime().toEpochSecond(ZoneOffset.UTC) + ", " +
                        cdr.getEndTime().toEpochSecond(ZoneOffset.UTC) + "\n";

                writer.write(cdrRecord);
            }
        }

        return new File(fileName);
    }

    private String getFileName() throws IOException {

        String directory = "./CDRFiles/";
        Files.createDirectories(Paths.get(directory));

        return directory + LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli() + ".txt";
    }

}

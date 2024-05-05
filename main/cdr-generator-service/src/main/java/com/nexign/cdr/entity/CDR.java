package com.nexign.cdr.entity;

import java.time.LocalDateTime;

import com.nexign.cdr.enums.CallType;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "transactions")
public class CDR {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private Long id;

    private CallType callType;

    @ManyToOne
    @JoinColumn(name = "subscriber_served_id", nullable = false)
    private Subscriber subscriberServed;

    @ManyToOne
    @JoinColumn(name = "subscriber_connected_id", nullable = false)
    private Subscriber subscriberConnected;

    private LocalDateTime startTime;
    private LocalDateTime endTime;
}

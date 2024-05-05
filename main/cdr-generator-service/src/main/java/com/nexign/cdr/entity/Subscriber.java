package com.nexign.cdr.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "subscribers")
public class Subscriber {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subscriber_id")
    private Long id;

    private String msisdn;

//    @OneToMany(mappedBy = "subscriberServedId", fetch = FetchType.EAGER)
//    List<CDR> cdrListServed = new ArrayList<>();
//
//    @OneToMany(mappedBy = "subscriberConnectedId", fetch = FetchType.EAGER)
//    List<CDR> cdrListConnected = new ArrayList<>();


    public Subscriber(String phoneNumber) {
        this.msisdn = phoneNumber;
    }
}

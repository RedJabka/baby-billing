package com.nexign.hrs.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nexign.hrs.domain.entity.ClientRemainingResource;

public interface ClientRemainingResourceRepository extends JpaRepository<ClientRemainingResource, Long> {
    
}

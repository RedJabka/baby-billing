package com.nexign.hrs.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nexign.hrs.domain.entity.IncludedResource;

public interface IncludedResourceRepository extends JpaRepository<IncludedResource, Long> {
    
}

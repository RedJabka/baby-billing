package com.nexign.cdr.repository;

import com.nexign.cdr.entity.CDR;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CDRRepositoryDB extends JpaRepository<CDR, Long> {
}

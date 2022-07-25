package com.eteration.simplebanking.services;

import com.eteration.simplebanking.model.Account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceRepository extends JpaRepository<Account, String> {
    
}

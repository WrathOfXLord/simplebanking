package com.eteration.simplebanking.services;

import java.util.List;
import com.eteration.simplebanking.model.Account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    private ServiceRepository serviceRepository;

    public List<Account> getAccounts() {
        return serviceRepository.findAll();
    }

    public void saveAccount(Account acc) {
        serviceRepository.save(acc);
    }

    public Account findAccount(String string) {
        return serviceRepository.findById(string).orElseThrow(() -> 
            new IllegalStateException(String.format("Account %s was not found !", string)));
    }
}

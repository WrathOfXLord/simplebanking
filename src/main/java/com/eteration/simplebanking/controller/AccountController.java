package com.eteration.simplebanking.controller;

import java.util.List;

import com.eteration.simplebanking.model.Account;
import com.eteration.simplebanking.model.DepositTransaction;
import com.eteration.simplebanking.model.InsufficientBalanceException;
import com.eteration.simplebanking.model.Transaction;
import com.eteration.simplebanking.model.WithdrawalTransaction;
import com.eteration.simplebanking.services.AccountService;
import com.eteration.simplebanking.services.TransactionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
// import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


// This class is a place holder you can change the complete implementation
@RestController
@RequestMapping("/account/v1")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @Autowired
    private TransactionRepository transactionRepository;

    @GetMapping("/{string}")
    // or
    // @GetMapping("/accounts")
    // public ResponseEntity<Account> getAccount(@RequestParam("string") String string)
    // localhost:8080/accounts/{string}
    public ResponseEntity<Account> getAccount(@PathVariable(value = "string") String string) {
        Account account = accountService.findAccount(string);
        return new ResponseEntity<Account>(account, HttpStatus.OK);
    }

    @PostMapping
    public void saveAccount(@RequestBody Account acc) {
        accountService.saveAccount(acc);
    }

    @GetMapping
    public List<Account> getAccounts() {
        return accountService.getAccounts();
    }

    public Transaction saveTransaction(String accountNumber, Transaction transaction) throws InsufficientBalanceException {
        Account account = this.getAccount(accountNumber).getBody();
        Transaction savedTransaction = transactionRepository.save(transaction);
        account.post(savedTransaction);
        accountService.saveAccount(account);
        return savedTransaction;
    }

    @Transactional
    @PostMapping("/credit/{accountNumber}")
    public ResponseEntity<TransactionStatus> credit(@PathVariable String accountNumber, @RequestBody DepositTransaction depositTransaction) throws InsufficientBalanceException {
        Transaction savedTransaction = saveTransaction(accountNumber, depositTransaction);
        return ResponseEntity.ok().body(new TransactionStatus(savedTransaction));
    }

    @Transactional
    @PostMapping("/debit/{accountNumber}")
    public ResponseEntity<TransactionStatus> debit(@PathVariable String accountNumber, @RequestBody WithdrawalTransaction withdrawalTransaction) throws InsufficientBalanceException {
        Transaction savedTransaction = saveTransaction(accountNumber, withdrawalTransaction);
        return ResponseEntity.ok().body(new TransactionStatus(savedTransaction));
    }

}
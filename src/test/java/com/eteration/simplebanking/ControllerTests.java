package com.eteration.simplebanking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
// import static org.mockito.Mockito.doReturn;
// import static org.mockito.Mockito.times;
// import static org.mockito.Mockito.verify;
// import static org.mockito.Mockito.when;

import java.time.Duration;

import com.eteration.simplebanking.controller.AccountController;
import com.eteration.simplebanking.controller.TransactionStatus;
import com.eteration.simplebanking.model.Account;
import com.eteration.simplebanking.model.DepositTransaction;
import com.eteration.simplebanking.model.InsufficientBalanceException;
import com.eteration.simplebanking.model.WithdrawalTransaction;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.Mockito;
// import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ContextConfiguration
// @AutoConfigureMockMvc
class ControllerTests  {

    // @Mock
    @Autowired
    private AccountController controller;
   
    @Test
    public void givenId_Credit_thenReturnJson()
    throws Exception {
        
        Account account = new Account("Kerem Karaca", "17892");
        controller.saveAccount(account);
        assertEquals(controller.getAccount("17892").getBody().getAccountNumber(), account.getAccountNumber());
        ResponseEntity<TransactionStatus> result = controller.credit( "17892", new DepositTransaction(1000.0));
        assertEquals("OK", result.getBody().getStatus());
    }

    @Test
    public void givenId_CreditAndThenDebit_thenReturnJson()
    throws Exception {
        
        Account account = new Account("Kerem Karaca", "17892");
        controller.saveAccount(account);
        assertEquals(controller.getAccount("17892").getBody().getAccountNumber(), account.getAccountNumber());
        ResponseEntity<TransactionStatus> result = controller.credit( "17892", new DepositTransaction(1000.0));
        ResponseEntity<TransactionStatus> result2 = controller.debit( "17892", new WithdrawalTransaction(50.0));
        assertEquals("OK", result.getBody().getStatus());
        assertEquals("OK", result2.getBody().getStatus());
        System.out.println("balance: " + controller.getAccount("17892"));
        assertEquals(950.0, controller.getAccount("17892")
                                                .getBody()
                                                .getBalance(), 0.001);
    }

    @Test
    public void givenId_CreditAndThenDebitMoreGetException_thenReturnJson()
    throws Exception {
        Assertions.assertThrows( InsufficientBalanceException.class, () -> {
            Account account = new Account("Kerem Karaca", "17892");
            controller.saveAccount(account);
            assertEquals(controller.getAccount("17892").getBody().getAccountNumber(), account.getAccountNumber());
            ResponseEntity<TransactionStatus> result = controller.credit( "17892", new DepositTransaction(1000.0));
            assertEquals("OK", result.getBody().getStatus());
            assertEquals(1000.0, controller.getAccount("17892")
                                                    .getBody()
                                                    .getBalance(),0.001);
            // verify(controller, times(1)).getAccount("17892").getBody();
            controller.debit( "17892", new WithdrawalTransaction(5000.0));
        });
    }

    @Test
    public void givenId_GetAccount_thenReturnJson()
    throws Exception {
        
        Account account = new Account("Kerem Karaca", "17892");
        controller.saveAccount(account);
        assertEquals(controller.getAccount("17892").getBody().getAccountNumber(), account.getAccountNumber());
        ResponseEntity<Account> result = controller.getAccount("17892");
        assertEquals(account.getAccountNumber(), result.getBody().getAccountNumber());
        assertEquals(account.getOwner(), result.getBody().getOwner());
        assertEquals(account.getBalance(), result.getBody().getBalance());
        // creation date might differ for a few microseconds
        // got difference as millisecond
        long dateDifference = Duration.between(account.getCreateDate(), result.getBody().getCreateDate()).toMillis();
        assertTrue(dateDifference < 10);
    }

}

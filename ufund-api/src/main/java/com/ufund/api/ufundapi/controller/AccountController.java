package com.ufund.api.ufundapi.controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ufund.api.ufundapi.model.Account;
import com.ufund.api.ufundapi.persistence.AccountDAO;

@RestController
@RequestMapping("accounts")
public class AccountController {



    private static class ErrorResponse {
        public String message;
        public ErrorResponse(String message) { this.message = message; }
    }

    private static final Logger LOG = Logger.getLogger(AccountController.class.getName());
    private AccountDAO accountDAO;

    /**
     * Creates a REST API controller to reponds to requests
     * 
     * @param accountDao The {@link AccountDAO Need Data Access Object} to perform CRUD operations
     * <br>
     * This dependency is injected by the Spring Framework
     */
    public AccountController(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    @PostMapping("")
    public ResponseEntity<Account> createAccount(@RequestBody Account account) {
        LOG.info("POST /accounts " + account);
        
        try {
            Account newAccount = accountDAO.createAccount(account);
            if (newAccount != null)
                return new ResponseEntity<Account>(newAccount, HttpStatus.CREATED);
            else{
                LOG.log(Level.WARNING, "Invalid arguments");
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("")
    public ResponseEntity<Account> incrementNeed(@RequestParam String accountName, @RequestParam String UUID, @RequestParam Boolean increment){
        LOG.info("POST /accounts" + UUID + "/" + increment);

        try{
            Account account = accountDAO.updateNeed(UUID, accountName, increment);
            return new ResponseEntity<Account>(account, HttpStatus.OK);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    


}

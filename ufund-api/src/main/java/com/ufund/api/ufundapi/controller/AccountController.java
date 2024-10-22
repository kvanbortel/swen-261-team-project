package com.ufund.api.ufundapi.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ufund.api.ufundapi.model.Account;
import com.ufund.api.ufundapi.model.BasketNeed;
import com.ufund.api.ufundapi.model.Need;
import com.ufund.api.ufundapi.persistence.AccountDAO;
import com.ufund.api.ufundapi.persistence.NeedDAO;

@RestController
@RequestMapping("accounts")
public class AccountController {

    private static final Logger LOG = Logger.getLogger(AccountController.class.getName());
    private AccountDAO accountDAO;

    /**
     * Creates a REST API controller to reponds to requests
     * 
     * @param accountDao The {@link AccountDAO Need Data Access Object} to perform CRUD operations
     * 
     * This dependency is injected by the Spring Framework
     */
    public AccountController(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    @PostMapping("")
    public ResponseEntity<Account> createAccount(@RequestBody String name) {
        LOG.info("POST /accounts " + name);
        
        try {
            Account newAccount = accountDAO.createAccount(name);
            if (newAccount != null) {
                return new ResponseEntity<Account>(newAccount, HttpStatus.CREATED);
            }
            return new ResponseEntity<Account>(this.accountDAO.getAccount(name), HttpStatus.OK);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Updates the quantity of a specified need in an account.
     * 
     * @param accountName the name of the account 
     * @param need the basketNeed to be updated
     * @param increment int the quantity of the need to be changed
     * @return 200 OK if the update was successful
     * 404 NOT FOUND if the account does not exist
     * 500 INTERNAL SERVER ERROR otherwise
     */
    @PutMapping("/{accountName}/needs/{amount}")
    public ResponseEntity<BasketNeed> updateNeed(@PathVariable String accountName, @PathVariable int amount, @RequestBody Need need) {
    LOG.info("PUT /accounts/" + accountName + "/needs/" + amount);
        try {
            BasketNeed found = accountDAO.updateNeed(accountName, need, amount);
            if (found == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } 
            return new ResponseEntity<BasketNeed>(found, HttpStatus.OK);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Checkout the need basket for a given user.
     * 
     * @param accountName the name of the account 
     * @return 200 OK if the checkout was successful
     * 404 NOT FOUND if the account does not exist
     * 500 INTERNAL SERVER ERROR otherwise
     */
    @PutMapping("/{accountName}/checkout")
    public ResponseEntity<Boolean> checkout(@PathVariable String accountName) {
    LOG.info("PUT /accounts/" + accountName + "/checkout");
        try {
            boolean success = accountDAO.checkout(accountName);
            if (!success) {
                return new ResponseEntity<Boolean>(false, HttpStatus.NOT_FOUND);
            } 
            return new ResponseEntity<Boolean>(true, HttpStatus.OK);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to the GET request for all {@linkplain BasketNeed needs} in a specific account
     * 
     * @return ResponseEntity with array of {@link BasketNeed need} objects (may be empty) and
     * HTTP status of OK<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("/{accountName}/needs")
    public ResponseEntity<ArrayList<BasketNeed>> getNeeds(@PathVariable String accountName) {
        LOG.info("GET /accounts/" + accountName + "/needs");
        try {
            Account account = accountDAO.getAccount(accountName);
            ArrayList<BasketNeed> needs = accountDAO.getNeeds(accountName);
            return new ResponseEntity<ArrayList<BasketNeed>>(needs, HttpStatus.OK);
        }
        catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
    


package com.ufund.api.ufundapi.controller;

import com.ufund.api.ufundapi.model.Account;
import com.ufund.api.ufundapi.model.BasketNeed;
import com.ufund.api.ufundapi.model.Need;
import com.ufund.api.ufundapi.model.ProfileInfo;
import com.ufund.api.ufundapi.persistence.AccountDAO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("accounts")
public class AccountController {

    private static final Logger LOG = Logger.getLogger(AccountController.class.getName());
    private final AccountDAO accountDAO;

    /**
     * Creates a REST API controller to respond to requests
     * 
     * @param accountDAO The {@link AccountDAO Need Data Access Object} to perform CRUD operations
     * 
     * This dependency is injected by the Spring Framework
     */
    public AccountController(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    @PostMapping("")
    public ResponseEntity<Account> createAccount(@RequestBody String name) {
        LOG.info("POST /accounts " + name);

        String usernameRegex = "^[a-zA-Z][a-zA-Z0-9]{0,12}$";
        if (!name.matches(usernameRegex)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            Account newAccount = accountDAO.createAccount(name);
            if (newAccount != null) {
                return new ResponseEntity<>(newAccount, HttpStatus.CREATED);
            }
            return new ResponseEntity<>(this.accountDAO.getAccount(name), HttpStatus.OK);
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
     * @param amount the quantity of Needs
     * @param need the basketNeed to be updated
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
            return new ResponseEntity<>(found, HttpStatus.OK);
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
                return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
            } 
            return new ResponseEntity<>(true, HttpStatus.OK);
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
            ArrayList<BasketNeed> needs = accountDAO.getNeeds(accountName);
            return new ResponseEntity<>(needs, HttpStatus.OK);
        }
        catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to the GET request for a {@linkplain ProfileInfo info} in a specific account
     *
     * @param accountName the name of the account to retrieve
     *
     * @return ResponseEntity a {@link ProfileInfo info} object (may be empty) and
     * HTTP status of OK<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("/{accountName}/profileInfo")
    public ResponseEntity<ProfileInfo> getProfileInfo(@PathVariable String accountName) {
        LOG.info("GET /accounts/" + accountName + "/profileInfo");
        try {
            ProfileInfo profileInfo = accountDAO.getProfileInfo(accountName);
            if (profileInfo == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<ProfileInfo>(profileInfo, HttpStatus.OK);
        }
        catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Update the ProfileInfo for a specified account
     * @param accountName the name of the user to update info for
     * @param profileInfo the info to update the user with
     * @return 200 OK if the update was successful
     * 404 NOT FOUND if the account does not exist
     * 500 INTERNAL SERVER ERROR otherwise
     */
    @PutMapping("/{accountName}/profileInfo")
    public ResponseEntity<ProfileInfo> updateProfileInfo(@PathVariable String accountName, @RequestBody ProfileInfo profileInfo) {
        LOG.info("PUT /accounts/" + accountName + "/profileInfo");

        try {
            ProfileInfo updatedProfileInfo = accountDAO.updateProfileInfo(accountName, profileInfo);

            if (updatedProfileInfo == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Account not found
            }

            return new ResponseEntity<>(updatedProfileInfo, HttpStatus.OK); // Successfully updated
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // Handle internal errors
        }
    }
}
    


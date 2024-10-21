package com.ufund.api.ufundapi.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ufund.api.ufundapi.model.Account;
import com.ufund.api.ufundapi.model.Basket;
import com.ufund.api.ufundapi.model.BasketNeed;
import com.ufund.api.ufundapi.model.Need;
import com.ufund.api.ufundapi.persistence.AccountDAO;

/**
 * Test the Account Controller class
 * 
 * @author Max Klot
 */
@Tag("Controller-tier")
public class AccountControllerTest {
    private AccountController accountController;
    private AccountDAO mockAccountDAO;

    // Local test data variables
    private static final String TEST_NAME = "AccountA1";
    private static final Need TEST_NEED = new Need("123456", "NeedA1", "Need for testing", 0, 1, 0);
    
    private static BasketNeed[] basketNeedArray = {(new BasketNeed(TEST_NEED, 1))};
    private static final ArrayList<BasketNeed> basketNeeds = new ArrayList<>(Arrays.asList(basketNeedArray));
    private static final Basket TEST_BASKET = new Basket();

    /**
     * Before each test, create a new Accoount Controller object and inject
     * a mock Account DAO
     */
    @BeforeEach
    public void setupAccountController() {
        mockAccountDAO = mock(AccountDAO.class);
        accountController = new AccountController(mockAccountDAO);
    }

    // Test that a valid createAccount (with no provided basket param) succeeds and returns the account
    @Test
    public void testCreateAccountNoBasket() throws IOException {
        // Setup
        Account account = new Account(TEST_NAME);

        when(mockAccountDAO.createAccount(TEST_NAME)).thenReturn(account);

        // Invoke
        ResponseEntity<Account> response = accountController.createAccount(TEST_NAME);

        // Analyze
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(account, response.getBody());
    }

    // Test that a valid createAccount (with a provided basket param) succeeds and returns the account
    @Test
    public void testCreateAccountBasket() throws IOException {
        // Setup
        Account account = new Account(TEST_NAME, TEST_BASKET);

        when(mockAccountDAO.createAccount(TEST_NAME)).thenReturn(account);

        // Invoke
        ResponseEntity<Account> response = accountController.createAccount(TEST_NAME);

        // Analyze
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(account, response.getBody());
    }

    //test that creating an existing account will do nothing and return an HTTP 200 
    @Test
    public void testCreateExistingAccount() throws IOException {
        // Setup
        Account account = new Account(TEST_NAME, TEST_BASKET);

        when(mockAccountDAO.getAccount(TEST_NAME)).thenReturn(account);

        // Invoke
        ResponseEntity<Account> response = accountController.createAccount(TEST_NAME);

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(account, response.getBody());
    }

    // Test that if Need#createNeed throws IOException, the server responds with HTTP 500
    @Test
    public void testCreateAccountHandleException() throws Exception {
        
        doThrow(new IOException()).when(mockAccountDAO).createAccount(TEST_NAME);

        // Invoke
        var response = accountController.createAccount(TEST_NAME);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    // Test that incrementing a need works 
    @Test
    public void testIncrementNeedTrue() throws IOException {
        // Setup
        BasketNeed updatedNeed = new BasketNeed(TEST_NEED, 2);

        when(mockAccountDAO.updateNeed(TEST_NAME, TEST_NEED, 1)).thenReturn(updatedNeed);

        // Invoke
        ResponseEntity<BasketNeed> response = accountController.updateNeed(TEST_NAME, 1, TEST_NEED);

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedNeed, response.getBody());
    }


    // Test that decrementing a need works
    @Test
    public void testIncrementNeedFalse() throws IOException {
        // Setup
        BasketNeed updatedNeed = new BasketNeed(TEST_NEED, 2);

        when(mockAccountDAO.updateNeed(TEST_NAME, TEST_NEED, -1)).thenReturn(updatedNeed);

        // Invoke
        ResponseEntity<BasketNeed> response = accountController.updateNeed(TEST_NAME, -1, TEST_NEED);

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedNeed, response.getBody());
    }

    // Test that if a need is not found, the server responds with HTTP 404
    @Test
    public void testIncrementNeedNotFound() throws Exception {
        
        Account account = new Account(TEST_NAME, (TEST_BASKET));
        when((mockAccountDAO).updateNeed(TEST_NAME, TEST_NEED, 1)).thenReturn(null);

        // Invoke
        var response = accountController.updateNeed(TEST_NAME, 1, TEST_NEED);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    // Test that if incrementNeed throws IOException, the server responds with HTTP 500
    @Test
    public void testIncrementNeedHandleError() throws Exception {
        
        doThrow(new IOException()).when(mockAccountDAO).updateNeed(TEST_NAME, TEST_NEED, 1);

        // Invoke
        var response = accountController.updateNeed(TEST_NAME, 1, TEST_NEED);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
    
    // Test that a valid checkout succeeds
    @Test
    public void testCheckout() throws IOException {
        // Setup
        when(mockAccountDAO.checkout(TEST_NAME)).thenReturn(true);

        // Invoke
        var response = accountController.checkout(TEST_NAME);

        // Analyze.to
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    // Test that an invalid checkout throws a 404
    @Test
    public void testCheckoutFail() throws IOException {
        // Setup
        when(mockAccountDAO.checkout(TEST_NAME)).thenReturn(false);

        // Invoke
        var response = accountController.checkout(TEST_NAME);

        // Analyze.to
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    // Test that an invalid checkout throws a 404
    @Test
    public void testCheckoutHandleError() throws IOException {
        // Setup
        doThrow(new IOException()).when(mockAccountDAO).checkout(TEST_NAME);
        // Invoke
        var response = accountController.checkout(TEST_NAME);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    // Test that a valid getNeed succeeds and returns all needs
    @Test
    public void testGetNeeds() throws IOException {
        // Setup
        Account account = new Account(TEST_NAME, (TEST_BASKET));
        when(mockAccountDAO.getAccount(TEST_NAME)).thenReturn(account);

        // Invoke
        var response = accountController.getNeeds(TEST_NAME);

        // Analyze.to
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(TEST_BASKET.getNeeds(), response.getBody());
    }

    // Test that if getNeeds throws IOException, the server responds with HTTP 500
    @Test
    public void testGetNeedsHandleException() throws Exception {
        
        doThrow(new IOException()).when(mockAccountDAO).getNeeds(TEST_NAME);

        // Invoke
        var response = accountController.getNeeds(TEST_NAME);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}

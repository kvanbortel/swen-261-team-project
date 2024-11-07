package com.ufund.api.ufundapi.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ufund.api.ufundapi.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

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
    private static final String TEST_PASSWORD_HASH = "thisisnotahash";
    private static final Need TEST_NEED = new Need("123456", "NeedA1", "Need for testing", 0, 1, 0);
    private static final ProfileInfo TEST_PROFILE_INFO = new ProfileInfo();
    private static final Level TEST_LEVEL = Level.NOOB;

    private static BasketNeed[] basketNeedArray = {(new BasketNeed(TEST_NEED, 1))};
    private static final ArrayList<BasketNeed> basketNeeds = new ArrayList<>(Arrays.asList(basketNeedArray));
    private static final Basket TEST_BASKET = new Basket();
    String TEST_IMG =  "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png";

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
        Account account = new Account(TEST_NAME, TEST_PASSWORD_HASH);

        when(mockAccountDAO.createAccount(TEST_NAME, TEST_PASSWORD_HASH)).thenReturn(account);

        // Invoke
        AccountRequest accountRequest = new AccountRequest(TEST_NAME, TEST_PASSWORD_HASH);
        ResponseEntity<Account> response = accountController.createAccount(accountRequest);

        // Analyze
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(account, response.getBody());
    }

    // Test that a valid createAccount (with a provided basket param) succeeds and returns the account
    @Test
    public void testCreateAccountBasket() throws IOException {
        // Setup
        Account account = new Account(TEST_NAME, TEST_BASKET, TEST_PROFILE_INFO, TEST_LEVEL, TEST_PASSWORD_HASH, TEST_IMG);

        when(mockAccountDAO.createAccount(TEST_NAME, TEST_PASSWORD_HASH)).thenReturn(account);

        // Invoke
        AccountRequest accountRequest = new AccountRequest(TEST_NAME, TEST_PASSWORD_HASH);
        ResponseEntity<Account> response = accountController.createAccount(accountRequest);

        // Analyze
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(account, response.getBody());
    }

    //test that creating an existing account will do nothing and return an HTTP 200 
    @Test
    public void testCreateExistingAccount() throws IOException {
        // Setup
        Account account = new Account(TEST_NAME, TEST_BASKET, TEST_PROFILE_INFO, TEST_LEVEL, TEST_PASSWORD_HASH, TEST_IMG);

        when(mockAccountDAO.getAccount(TEST_NAME)).thenReturn(account);

        // Invoke
        AccountRequest accountRequest = new AccountRequest(TEST_NAME, TEST_PASSWORD_HASH);
        ResponseEntity<Account> response = accountController.createAccount(accountRequest);

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(account, response.getBody());
    }

    // Test that if Need#createNeed throws IOException, the server responds with HTTP 500
    @Test
    public void testCreateAccountHandleException() throws Exception {
        
        doThrow(new IOException()).when(mockAccountDAO).createAccount(TEST_NAME, TEST_PASSWORD_HASH);

        // Invoke
        AccountRequest accountRequest = new AccountRequest(TEST_NAME, TEST_PASSWORD_HASH);
        ResponseEntity<Account> response = accountController.createAccount(accountRequest);

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
        assertEquals(true, response.getBody());
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
        assertEquals(false, response.getBody());
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
        Account account = new Account(TEST_NAME, (TEST_BASKET), TEST_PROFILE_INFO, TEST_LEVEL, TEST_PASSWORD_HASH, TEST_IMG);
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

    //test getting an account that does exist 
    @Test
    public void testGetAccountTrue() throws IOException {
        // Setup
        
        Account account = new Account(TEST_NAME, (TEST_BASKET), TEST_PROFILE_INFO, TEST_LEVEL, TEST_PASSWORD_HASH, TEST_IMG);
        when(mockAccountDAO.getAccount(TEST_NAME)).thenReturn(account);

        // Invoke
        var response = accountController.getAccount(TEST_NAME);

        // Analyze.to
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(account, response.getBody());
    }

    //test http response when getting an account that doesnt exist 
    @Test
    public void testGetAccountNotFound() throws IOException {
        // Setup
        when(mockAccountDAO.getAccount(TEST_NAME)).thenReturn(null);

        // Invoke
        var response = accountController.getAccount(TEST_NAME);

        // Analyze.to
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(null, response.getBody());
    }

    //test handling exception when getting accounts
    @Test
    public void testGetAccountHandleException() throws IOException {
        // Setup
        doThrow(new IOException()).when(mockAccountDAO).getAccount(TEST_NAME);

        // Invoke
        
        ResponseEntity<Account> response = accountController.getAccount(TEST_NAME);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    //test sucessful image upload
    @Test
    public void testUploadImageSuccess() throws IOException {
        // Setup

        String path = "../ufund-ui/angular/src/assets/Paws&Claws.png";
        
        byte[] img = Files.readAllBytes(Paths.get(path));
        when(mockAccountDAO.addImage(TEST_NAME, img)).thenReturn(path);

        // Invoke
        MultipartFile result = new MockMultipartFile("image", new FileInputStream(new File(path)));
        var response = accountController.handleImageUpload(TEST_NAME, result);

        // Analyze.to
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(path, response.getBody());
    }

    //test unsucessful image upload
    @Test
    public void testUploadImageFail() throws IOException {
        // Setup

        String path = "../ufund-ui/angular/src/assets/Paws&Claws.png";
        
        byte[] img = Files.readAllBytes(Paths.get(path));
        when(mockAccountDAO.addImage(TEST_NAME, img)).thenReturn(null);

        // Invoke
        MultipartFile result = new MockMultipartFile("image", new FileInputStream(new File(path)));
        var response = accountController.handleImageUpload(TEST_NAME, result);

        // Analyze.to
        assertEquals(HttpStatus.UNSUPPORTED_MEDIA_TYPE, response.getStatusCode());
    }

    //test throw database error
    @Test
    public void testUploadImageException() throws IOException {
        // Setup

        String path = "../ufund-ui/angular/src/assets/Paws&Claws.png";
        
        byte[] img = Files.readAllBytes(Paths.get(path));

        doThrow(new IOException()).when(mockAccountDAO).addImage(TEST_NAME, img);

        // Invoke
        MultipartFile result = new MockMultipartFile("image", new FileInputStream(new File(path)));
        var response = accountController.handleImageUpload(TEST_NAME, result);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    // Successfully get ProfileInfo when account exists
    @Test
    void testGetProfileInfo_AccountExists() throws IOException {
        when(mockAccountDAO.getProfileInfo(TEST_NAME)).thenReturn(TEST_PROFILE_INFO);

        ResponseEntity<ProfileInfo> response = accountController.getProfileInfo(TEST_NAME);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(TEST_PROFILE_INFO, response.getBody());
    }

    // NOT_FOUND when getting ProfileInfo of account that doesn't exist
    @Test
    void testGetProfileInfo_NonExistentAccount() throws IOException {
        when(mockAccountDAO.getProfileInfo("NonExistent")).thenReturn(null);

        ResponseEntity<ProfileInfo> response = accountController.getProfileInfo("NonExistent");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    // test server error when getting ProfileInfo
    @Test
    void testGetProfileInfo_InternalServerError() throws IOException {
        when(mockAccountDAO.getProfileInfo(TEST_NAME)).thenThrow(new IOException());

        ResponseEntity<ProfileInfo> response = accountController.getProfileInfo(TEST_NAME);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
    }

    // test successful ProfileInfo update
    @Test
    void testUpdateProfileInfo_SuccessfulUpdate() throws IOException {
        when(mockAccountDAO.updateProfileInfo(TEST_NAME, TEST_PROFILE_INFO)).thenReturn(TEST_PROFILE_INFO);

        ResponseEntity<ProfileInfo> response = accountController.updateProfileInfo(TEST_NAME, TEST_PROFILE_INFO);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(TEST_PROFILE_INFO, response.getBody());
    }

    // test update ProfileInfo when account doesn't exist
    @Test
    void testUpdateProfileInfo_NonExistentAccount() throws IOException {
        when(mockAccountDAO.updateProfileInfo(TEST_NAME, TEST_PROFILE_INFO)).thenReturn(null);

        ResponseEntity<ProfileInfo> response = accountController.updateProfileInfo(TEST_NAME, TEST_PROFILE_INFO);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    // test update ProfileInfo when internal server error
    @Test
    void testUpdateProfileInfo_InternalServerError() throws IOException {
        when(mockAccountDAO.updateProfileInfo(TEST_NAME, TEST_PROFILE_INFO)).thenThrow(new IOException());

        ResponseEntity<ProfileInfo> response = accountController.updateProfileInfo(TEST_NAME, TEST_PROFILE_INFO);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
    }
}

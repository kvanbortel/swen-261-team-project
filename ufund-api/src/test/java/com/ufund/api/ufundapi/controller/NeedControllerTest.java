package com.ufund.api.ufundapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ufund.api.ufundapi.model.Need;
import com.ufund.api.ufundapi.persistence.NeedDAO;

/**
 * Test the Need Controller class
 * 
 * @author SWEN Faculty
 */
@Tag("Controller-tier")
public class NeedControllerTest {
    private NeedController needController;
    private NeedDAO mockNeedDAO;

    // Local test data variables
    private static final String TEST_ID = "MOCKID-0";
    private static final String TEST_NAME = "NeedA1";
    private static final String TEST_DESCRIPTION = "Descr1";
    private static final double TEST_DEMAND_RATING = 3.0;
    private static final int TEST_QUANTITY = 2;
    private static final double TEST_COST = 6.5;

    /**
     * Before each test, create a new NeedController object and inject
     * a mock Need DAO
     */
    @BeforeEach
    public void setupNeedController() {
        mockNeedDAO = mock(NeedDAO.class);
        needController = new NeedController(mockNeedDAO);
    }

    // Test that a valid createNeed succeeds and returns the need
    @Test
    public void testCreateNeed() throws IOException {
        // Setup
        Need need = new Need(TEST_ID, TEST_NAME, TEST_DESCRIPTION, TEST_DEMAND_RATING, TEST_QUANTITY, TEST_COST);

        when(mockNeedDAO.createNeed(need)).thenReturn(need);

        // Invoke
        ResponseEntity<Need> response = needController.createNeed(need);

        // Analyze
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(need, response.getBody());
    }

    // Test that an invalid createNeed fails for bad arguments
    @Test
    public void testCreateInvalidNeed() throws IOException {
        // Setup
        Need need = new Need(TEST_ID, TEST_NAME, TEST_DESCRIPTION, -1, TEST_QUANTITY, TEST_COST);

        when(mockNeedDAO.createNeed(need)).thenReturn(null);

        // Invoke
        ResponseEntity<Need> response = needController.createNeed(need);

        // Analyze
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    // Test that if Need#createNeed throws IOException, the server responds with HTTP 500
    @Test
    public void testCreateNeedHandleException() throws Exception {
        // Setup
        Need need = new Need(TEST_ID, TEST_NAME, TEST_DESCRIPTION, -1, TEST_QUANTITY, TEST_COST);

        doThrow(new IOException()).when(mockNeedDAO).createNeed(need);

        // Invoke
        var response = needController.createNeed(need);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    // Test getNeed()

    // Test that a valid getNeed succeeds and returns the need
    @Test
    public void testGetNeed() throws IOException {
        // Setup
        Need need = new Need(TEST_ID, TEST_NAME, TEST_DESCRIPTION, 1.0, 5, 15.0);
        when(mockNeedDAO.getNeed(TEST_ID)).thenReturn(need);

        // Invoke
        var response = needController.getNeed(TEST_ID);

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(need, response.getBody());
    }

    // Test that getNeed of nonexistent id fails with HTTP 404
    @Test
    public void testGetNeedNotFound() throws Exception {
        // Setup
        when(mockNeedDAO.getNeed(TEST_ID)).thenReturn(null);

        // Invoke
        var response = needController.getNeed(TEST_ID);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    // Test that if Need#getNeed throws IOException, the server responds with HTTP 500
    @Test
    public void testGetNeedHandleException() throws Exception {
        // Setup
        doThrow(new IOException()).when(mockNeedDAO).getNeed(TEST_ID);

        // Invoke
        var response = needController.getNeed(TEST_ID);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    // Test deleteNeed()

    // Test that a valid deleteNeed succeeds and returns true
    @Test
    public void testDeleteNeed() throws IOException {
        // Setup
        when(mockNeedDAO.deleteNeed(TEST_ID)).thenReturn(true);

        // Invoke
        var response = needController.deleteNeed(TEST_ID);

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    // Test that deleteNeed of nonexistent id fails with HTTP 404
    @Test
    public void testDeleteNeedNotFound() throws Exception {
        // Setup
        when(mockNeedDAO.deleteNeed(TEST_ID)).thenReturn(false);

        // Invoke
        var response = needController.deleteNeed(TEST_ID);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    // Test that if Need#deleteNeed throws IOException, the server responds with HTTP 500
    @Test
    public void testDeleteNeedHandleException() throws Exception {
        // Setup
        doThrow(new IOException()).when(mockNeedDAO).deleteNeed(TEST_ID);

        // Invoke
        var response = needController.deleteNeed(TEST_ID);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    // Test updateNeed()

    // Test that a valid updateNeed succeeds and returns the updated need
    @Test
    public void testUpdateNeed() throws IOException {
        // Setup
        Need updatedNeed = new Need(TEST_ID, "UpdatedNeedA1", "UpdatedDescr1", 5.0, 8, 230.0);

        when(mockNeedDAO.updateNeed(updatedNeed)).thenReturn(updatedNeed);

        // Invoke
        var response = needController.updateNeed(updatedNeed);

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedNeed, response.getBody());
    }

    // Test that updateNeed of nonexistent id fails with HTTP 404
    @Test
    public void testUpdateNeedNotFound() throws Exception {
        // Setup
        Need updatedNeed = new Need("MOCKID-1", "UpdatedNeedA1", "UpdatedDescr1", 5.0, 8, 230.0);

        when(mockNeedDAO.updateNeed(updatedNeed)).thenReturn(null);

        // Invoke
        var response = needController.updateNeed(updatedNeed);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    // Test that if Need#updateNeed throws IOException, the server responds with HTTP 500
    @Test
    public void testUpdateNeedHandleException() throws Exception {
        // Setup
        Need updatedNeed = new Need(TEST_ID, "UpdatedNeedA1", "UpdatedDescr1", 5.0, 8, 230.0);

        doThrow(new IOException()).when(mockNeedDAO).updateNeed(updatedNeed);

        // Invoke
        var response = needController.updateNeed(updatedNeed);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    // Test getNeeds()

    // Test that a valid getNeeds succeeds and returns all needs
    @Test
    public void testGetNeeds() throws IOException {
        // Setup
        Need[] needs = {
            new Need(TEST_ID, TEST_NAME, TEST_DESCRIPTION, 1.0, 2, 5.0),
            new Need("MOCKID-1", "NeedA2", "Descr2", 1.0, 3, 7.0),
        };

        when(mockNeedDAO.getNeeds()).thenReturn(needs);

        // Invoke
        ResponseEntity<Need[]> response = needController.getNeeds();

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(needs, response.getBody());
    }

    // Test that when no products exist, a valid getNeeds succeeds and returns an empty list
    @Test
    public void testGetNeedsEmpty() throws IOException {
        // Setup
        Need[] needs = {};
        when(mockNeedDAO.getNeeds()).thenReturn(needs);

        // Invoke
        ResponseEntity<Need[]> response = needController.getNeeds();

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(needs, response.getBody());
    }

    // Test that if Need#getNeeds throws IOException, the server responds with HTTP 500
    @Test
    public void testGetNeedsHandleException() throws IOException {
        // Setup
        doThrow(new IOException()).when(mockNeedDAO).getNeeds();

        // Invoke
        ResponseEntity<Need[]> response = needController.getNeeds();

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
    
    // Test searchNeeds()

    // Test that searching for a given text returns only Needs with that text
    @Test
    public void testSearchNeeds() throws IOException { // searchNeeds may throw IOException
        // Setup
        String searchString = "B";
        Need[] needs = new Need[3];
        needs[0] = new Need("MOCKID-0", "NeedA1", "Descr1", 3.9, 7, 12.1);
        needs[1] = new Need("MOCKID-1", "NeedB1", "Descr2", 2.2, 4, 10.5);

        when(mockNeedDAO.searchNeeds(searchString)).thenReturn(needs);

        // Invoke
        ResponseEntity<Need[]> response = needController.searchNeeds(searchString);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(needs,response.getBody());
    }

    // Test that searching for needs with the given text IN A DIFFERENT CAPITALIZATION gives Needs with that text
    @Test
    public void testSearchNeedsLowercase() throws IOException { // searchNeeds may throw IOException
        // Setup
        String searchString = "b";
        Need[] needs = new Need[3];
        needs[0] = new Need("MOCKID-0", "NeedA1", "Descr1", 3.9, 7, 12.1);
        needs[1] = new Need("MOCKID-1", "NeedB1", "Descr2", 2.2, 4, 10.5);
        needs[2] = new Need("MOCKID-2", "Needb1", "Descr2", 2.2, 4, 10.5);

        when(mockNeedDAO.searchNeeds(searchString)).thenReturn(needs);

        // Invoke
        ResponseEntity<Need[]> response = needController.searchNeeds(searchString);

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(needs ,response.getBody());
    }

    // Test that if Need#searchNeeds throws IOException, the server responds with HTTP 500
    @Test
    public void testSearchNeedsHandleException() throws Exception { // createNeed may throw IOException
        // Setup
        String searchString = "A";

        doThrow(new IOException()).when(mockNeedDAO).searchNeeds(searchString);

        // Invoke
        var response = needController.searchNeeds(searchString);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}

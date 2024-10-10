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
        Need need = new Need("MOCKID-0", "NeedA1", "Descr1", 3.0, 2, 6.5);

        when(mockNeedDAO.createNeed(need)).thenReturn(need);

        // Invoke
        ResponseEntity<Need> response = needController.createNeed(need);

        // Analyze
        assertEquals(HttpStatus.CREATED,response.getStatusCode());
        assertEquals(need,response.getBody());
    }

    // Test that an invalid createNeed fails for bad arguments
    @Test
    public void testCreateInvalidNeed() throws IOException { // getNeed may throw IOException
        // Setup
        Need need = new Need("MOCKID-0", "NeedA1", "Descr1", -1, 2, 6.5);

        when(mockNeedDAO.createNeed(need)).thenReturn(null);

        // Invoke
        ResponseEntity<Need> response = needController.createNeed(need);

        // Analyze
        assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode());
    }

    // Test that if Need#createNeed throws IOException, the server responds with HTTP 500
    @Test
    public void testCreateNeedHandleException() throws Exception { // createNeed may throw IOException
        // Setup
        Need need = new Need("MOCKID-0", "NeedA1", "Descr1", -1, 2, 6.5);

        // When createNeed is called on the mock NeedDAO, throw an IOException
        doThrow(new IOException()).when(mockNeedDAO).createNeed(need);

        // Invoke
        var response = needController.createNeed(need);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    // Test getNeed()

    // Test that a valid getNeed succeeds and returns the need
    @Test
    public void testGetNeed() throws IOException { // getNeed may throw IOException
        // Setup
        String id = "MOCKID-0";
        Need need = new Need("MOCKID-0", "NeedA1", "Descr1", 1.0, 5, 15.0);
        // When getNeed is called with the id, return the need above
        when(mockNeedDAO.getNeed(id)).thenReturn(need);

        // Invoke
        var response = needController.getNeed(id);

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(need, response.getBody());
    }

    // Test that getNeed of nonexistent id fails with HTTP 404
    @Test
    public void testGetNeedNotFound() throws Exception { // getNeed may throw IOException
        // Setup
        String id = "MOCKID-0";
        // When getNeed is called with the id, the mock NeedDAO will return null, simulating not need found
        when(mockNeedDAO.getNeed(id)).thenReturn(null);

        // Invoke
        var response = needController.getNeed(id);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    // Test that if Need#getNeed throws IOException, the server responds with HTTP 500
    @Test
    public void testGetNeedHandleException() throws Exception { // getNeed may throw IOException
        // Setup
        String id = "MOCKID-0";
        // When getNeed is called on the mock NeedDAO, throw an IOException
        doThrow(new IOException()).when(mockNeedDAO).getNeed(id);

        // Invoke
        var response = needController.getNeed(id);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    // Test deleteNeed()

    // Test that a valid deleteNeed succeeds and returns true
    @Test
    public void testDeleteNeed() throws IOException { // deleteNeed may throw IOException
        // Setup
        String id = "MOCKID-0";
        // When getNeed is called with the id, retrun true that the need is deleted
        when(mockNeedDAO.deleteNeed(id)).thenReturn(true);

        // Invoke
        var response = needController.deleteNeed(id);

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    // Test that deleteNeed of nonexistent id fails with HTTP 404
    @Test
    public void testDeleteNeedNotFound() throws Exception { // getNeed may throw IOException
        // Setup
        String id = "MOCKID-0";
        // When deleteNeed is called with the id, the mock NeedDAO will return null, simulating not need found
        when(mockNeedDAO.deleteNeed(id)).thenReturn(false);

        // Invoke
        var response = needController.deleteNeed(id);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    // Test that if Need#deleteNeed throws IOException, the server responds with HTTP 500
    @Test
    public void testDeleteNeedHandleException() throws Exception { // deleteNeed may throw IOException
        // Setup
        String id = "MOCKID-0";
        // When getNeed is called on the mock NeedDAO, throw an IOException
        doThrow(new IOException()).when(mockNeedDAO).deleteNeed(id);

        // Invoke
        var response = needController.deleteNeed(id);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    // Test updateNeed()

    // Test that a valid updateNeed succeeds and returns the updated need
    @Test
    public void testUpdateNeed() throws IOException { // updateNeed may throw IOException
        // Setup
        String id = "MOCKID-0";
        Need need = new Need(id, "NeedA1", "Descr1", 1.0, 5, 15.0);
        Need updatedNeed = new Need(id, "UpdatedNeedA1", "UpdatedDescr1", 5.0, 8, 230.0);

        // When updateNeed is called with the id, return the need above
        when(mockNeedDAO.updateNeed(updatedNeed)).thenReturn(updatedNeed);

        // Invoke
        var response = needController.updateNeed(updatedNeed);

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedNeed, response.getBody());
    }

    // Test that updateNeed of nonexistent id fails with HTTP 404
    @Test
    public void testUpdateNeedNotFound() throws Exception { // updateNeed may throw IOException
        // Setup
        String id = "MOCKID-0";
        String id1 = "MOCKID-1";
        Need need = new Need(id, "NeedA1", "Descr1", 1.0, 5, 15.0);
        Need updatedNeed = new Need(id1, "UpdatedNeedA1", "UpdatedDescr1", 5.0, 8, 230.0);

        // When updateNeed is called with the id, the mock NeedDAO will return null, simulating not need found
        when(mockNeedDAO.updateNeed(updatedNeed)).thenReturn(null);

        // Invoke
        var response = needController.updateNeed(need);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    // Test that if Need#updateNeed throws IOException, the server responds with HTTP 500
    @Test
    public void testUpdateNeedHandleException() throws Exception { // updateNeed may throw IOException
        // Setup
        String id = "MOCKID-0";
        Need updatedNeed = new Need(id, "UpdatedNeedA1", "UpdatedDescr1", 5.0, 8, 230.0);

        // When getNeed is called on the mock NeedDAO, throw an IOException
        doThrow(new IOException()).when(mockNeedDAO).updateNeed(updatedNeed);

        // Invoke
        var response = needController.updateNeed(updatedNeed);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    // Test getNeeds()

    // Test that a valid getNeeds succeeds and returns all needs
    @Test
    public void testGetNeeds() throws IOException { // getNeeds may throw IOException
        // Setup
        Need[] needs = new Need[] {
            new Need("MOCKID-0", "NeedA1", "Descr1", 1.0, 2, 5.0),
            new Need("MOCKID-1", "NeedA2", "Descr2", 1.0, 3, 7.0),
        };
        // When getNeeds is called return the needs created above
        when(mockNeedDAO.getNeeds()).thenReturn(needs);

        // Invoke
        ResponseEntity<Need[]> response = needController.getNeeds();

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(needs, response.getBody());
    }

    // Test that when no products exist, a valid getNeeds succeeds and returns an empty list
    @Test
    public void testGetNeedsEmpty() throws IOException { // getNeeds may throw IOException
        // Setup
        Need[] needs = {};
        // When getNeeds is called return the needs created above
        when(mockNeedDAO.getNeeds()).thenReturn(needs);

        // Invoke
        ResponseEntity<Need[]> response = needController.getNeeds();

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(needs, response.getBody());
    }
    
    // Test that if Need#getNeeds throws IOException, the server responds with HTTP 500
    @Test
    public void testGetNeedsHandleException() throws IOException { // getNeeds may throw IOException
        // Setup
        // When getNeeds is called on the mock NeedDAO, throw an IOException
        doThrow(new IOException()).when(mockNeedDAO).getNeeds();

        // Invoke
        ResponseEntity<Need[]> response = needController.getNeeds();

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
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
        // When findNeeds is called with the search string, return the two
        /// needs above
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
        // When findNeeds is called with the search string, return the two
        /// needs above
        when(mockNeedDAO.searchNeeds(searchString)).thenReturn(needs);

        // Invoke
        ResponseEntity<Need[]> response = needController.searchNeeds(searchString);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(needs,response.getBody());
    }

        // Test that if Need#getNeed throws IOException, the server responds with HTTP 500
        @Test
        public void testSearchNeedHandleException() throws Exception { // createNeed may throw IOException

            // When getNeed is called on the mock NeedDAO, throw an IOException
            doThrow(new IOException()).when(mockNeedDAO).searchNeeds("searching");
    
            // Invoke
            var response = needController.searchNeeds("searching");
    
            // Analyze
            assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
        }
}

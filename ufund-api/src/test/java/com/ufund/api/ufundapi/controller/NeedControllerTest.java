package com.ufund.api.ufundapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.apache.tomcat.util.http.fileupload.impl.IOFileUploadException;
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

    // Test getNeed()

    // Test that a valid getNeed succeeds and returns the need
    @Test
    public void testGetNeed() throws IOException { // getNeed may throw IOException
        // Setup
        String id = "MOCKID-0";
        Need need = new Need("MOCKID-0", "NeedA1", "Descr1", 1.0);
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
    public void testGetNeedNotFound() throws Exception { // createNeed may throw IOException
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
    public void testGetNeedHandleException() throws Exception { // createNeed may throw IOException
        // Setup
        String id = "MOCKID-0";
        // When getNeed is called on the mock NeedDAO, throw an IOException
        doThrow(new IOException()).when(mockNeedDAO).getNeed(id);

        // Invoke
        var response = needController.getNeed(id);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    // Test getNeeds()

    // Test that a valid getNeeds succeeds and returns all needs
    @Test
    public void testGetNeeds() throws IOException { // getNeeds may throw IOException
        // Setup
        Need[] needs = new Need[] {
            new Need("MOCKID-0", "NeedA1", "Descr1", 1.0),
            new Need("MOCKID-1", "NeedA2", "Descr2", 1.0),
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
}

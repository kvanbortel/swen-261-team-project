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

    @Test
    public void testGetNeed() throws IOException { // getNeed may throw IOException
        // Setup
        String id = "MOCKID-0";
        Need need = new Need("MOCKID-0", "NeedA1", "Descr1", 1.0);
        // When getNeed is called with the id, return the need above
        when(mockNeedDAO.getNeed(id)).thenReturn(need);

        // Invoke
        ResponseEntity<Need> response = needController.getNeed(id);

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(need, response.getBody());
    }

    @Test
    public void testGetNeedNotFound() throws Exception { // createNeed may throw IOException
        // Setup
        String id = "MOCKID-0";
        // When getNeed is called with the id, the mock NeedDAO will return null, simulating not need found
        when(mockNeedDAO.getNeed(id)).thenReturn(null);

        // Invoke
        ResponseEntity<Need> response = needController.getNeed(id);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testGetNeedHandleException() throws Exception { // createNeed may throw IOException
        // Setup
        String id = "MOCKID-0";
        // When getNeed is called on the mock NeedDAO, throw an IOException
        doThrow(new IOException()).when(mockNeedDAO).getNeed(id);

        // Invoke
        ResponseEntity<Need> response = needController.getNeed(id);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }
}

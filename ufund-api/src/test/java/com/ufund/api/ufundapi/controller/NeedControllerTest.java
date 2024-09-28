package com.ufund.api.ufundapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    public void testGetNeed() throws IOException {  // getNeed may throw IOException
        // Setup
        Need need = new Need("MOCKID-0","NeedName", "NeedDescription", 10.3);
        // When the same id is passed in, our mock Need DAO will return the Need object
        when(mockNeedDAO.getNeed(need.getId())).thenReturn(need);

        // Invoke
        ResponseEntity<Need> response = needController.getNeed(need.getId());

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(need, response.getBody());
    }
}
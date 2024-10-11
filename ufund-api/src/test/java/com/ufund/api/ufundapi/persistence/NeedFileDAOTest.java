package com.ufund.api.ufundapi.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufund.api.ufundapi.model.Need;

/**
 * Test the Need File DAO class
 * 
 * @author SWEN Faculty
 */
@Tag("Persistence-tier")
public class NeedFileDAOTest {
    NeedFileDAO needFileDAO;
    Need[] testNeeds;
    ObjectMapper mockObjectMapper;

    /**
     * Before each test, we will create and inject a Mock Object Mapper to
     * isolate the tests from the underlying file
     * @throws IOException
     */
    @BeforeEach
    public void setupNeedFileDAO() throws IOException {
        mockObjectMapper = mock(ObjectMapper.class);
        testNeeds = new Need[3];
        testNeeds[0] = new Need("MOCKID-0","NeedA1", "Description1", 0, 3, 12.5);
        testNeeds[1] = new Need("MOCKID-1","NeedB1", "Description2", 2.9, 10, 5.4);
        testNeeds[2] = new Need("MOCKID-2","NeedB2", "Description3", 1.3, 6, 12.32);

        // When the object mapper is supposed to read from the file
        // the mock object mapper will return the need array above
        when(mockObjectMapper
            .readValue(new File("doesnt_matter.txt"),Need[].class))
                .thenReturn(testNeeds);
        needFileDAO = new NeedFileDAO("doesnt_matter.txt",mockObjectMapper);
    }

    @Test
    public void testGetNeed() throws IOException {
        // Invoke
        Need need = needFileDAO.getNeed("MOCKID-0");

        // Analyze
        assertEquals(need, testNeeds[0]);
    }

    @Test
    public void testGetNeedNotFound() throws IOException {
        // Invoke
        Need need = needFileDAO.getNeed("NotAnId");

        // Analyze
        assertEquals(need, null);
    }

    @Test
    public void testCreateNeed() throws IOException {
        // Setup
        Need newNeed = new Need("MOCKID-10","NeedA0", "Description0", 10, 5, 15.0);

        // Invoke
        Need returnedNeed = needFileDAO.createNeed(newNeed);
        Need fetchedNeed = needFileDAO.getNeed(returnedNeed.getId()); // we know that getNeed works

        // Analyze
        assertEquals(fetchedNeed, returnedNeed);
    }

    @Test
    public void testCreateNeedInvalid() throws IOException {
        // Setup
        Need needInvalidDemandLow = new Need("MOCKID-10","NeedX0", "Description0", 104.3, 5, 15.0);
        Need needInvalidDemandHigh = new Need("MOCKID-10","NeedX0", "Description0", -4.4, 5, 15.0);
        Need needInvalidQuantity = new Need("MOCKID-10","NeedX1", "Description1", 10.2, -5, 15.0);
        Need needInvalidCost = new Need("MOCKID-10","NeedX2", "Description2", 10.2, 5, -15.0);

        // Invoke
        Need returnedNeed0 = needFileDAO.createNeed(needInvalidDemandLow);
        Need returnedNeed1 = needFileDAO.createNeed(needInvalidDemandHigh);
        Need returnedNeed2 = needFileDAO.createNeed(needInvalidQuantity);
        Need returnedNeed3 = needFileDAO.createNeed(needInvalidCost);

        // Analyze
        assertEquals(returnedNeed0, null);
        assertEquals(returnedNeed1, null);
        assertEquals(returnedNeed2, null);
        assertEquals(returnedNeed3, null);
    }

    @Test
    public void testDeleteNeed() throws IOException {
        // Invoke
        boolean response = needFileDAO.deleteNeed("MOCKID-0");

        // Analyze
        assertEquals(response, true);
    }

    @Test
    public void testDeleteNeedNotFound() throws IOException {
        // Invoke
        boolean response = needFileDAO.deleteNeed("NotAnId");

        // Analyze
        assertEquals(response, false);
    }

    @Test
    public void testUpdateNeed() throws IOException {
        // Setup
        Need updatedNeed = new Need("MOCKID-0","Up-NeedA1", "Up-Description1", 1, 12, 53.0);

        // Invoke
        Need need = needFileDAO.updateNeed(updatedNeed);

        // Analyze
        assertEquals(need, updatedNeed);
    }

    @Test
    public void testUpdateNeedNotFound() throws IOException {
        // Setup
        Need updatedNeed = new Need("NOT AN ID","Up-NeedA1", "Up-Description1", 1, 12, 53.0);

        // Invoke
        Need need = needFileDAO.updateNeed(updatedNeed);

        // Analyze
        assertEquals(need, null);
    }

    @Test
    public void testGetNeeds() throws IOException {
        // Invoke
        Need[] needs = needFileDAO.getNeeds();

        // Analyze
        assertEquals(needs.length, testNeeds.length);
        for (int i = 0; i < testNeeds.length; ++i)
            assertEquals(needs[i], testNeeds[i]);
    }

    @Test
    public void testGetNeedsEmpty() throws IOException {
        // Invoke
        Need[] needs = {};

        // Analyze
        assertEquals(needs.length, 0);
    }

    @Test
    public void testGetNeedsSearch() {
        // Invoke
        Need[] needs = needFileDAO.searchNeeds("B");

        // Analyze
        assertEquals(needs.length,2);
        assertEquals(needs[0],testNeeds[1]);
        assertEquals(needs[1],testNeeds[2]);
    }
}

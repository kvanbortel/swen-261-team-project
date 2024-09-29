package com.ufund.api.ufundapi.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;

import org.apache.tomcat.util.http.fileupload.impl.IOFileUploadException;
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
        testNeeds[0] = new Need("MOCKID-0","NeedA1", "Description1", 0);
        testNeeds[1] = new Need("MOCKID-1","NeedB1", "Description2", 2.9);
        testNeeds[2] = new Need("MOCKID-2","NeedB2", "Description3", 1.3);

        // When the object mapper is supposed to read from the file
        // the mock object mapper will return the hero array above
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
}

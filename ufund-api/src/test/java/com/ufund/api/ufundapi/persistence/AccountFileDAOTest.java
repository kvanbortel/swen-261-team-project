package com.ufund.api.ufundapi.persistence;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufund.api.ufundapi.model.Account;
import com.ufund.api.ufundapi.model.AdminInfo;
import com.ufund.api.ufundapi.model.Basket;
import com.ufund.api.ufundapi.model.BasketNeed;
import com.ufund.api.ufundapi.model.Need;
import com.ufund.api.ufundapi.model.ProfileInfo;
import com.ufund.api.ufundapi.model.Region;

/**
 * 
 */
@Tag("Persistence-tier")
public class AccountFileDAOTest {
    private static final ProfileInfo TEST_PROFILE_INFO = new ProfileInfo();

    AccountFileDAO accountFileDAO;
    Account[] testAccounts;
    Basket[] testBaskets;
    Need[] testNeeds;
    BasketNeed[] testBasketNeeds;
    ProfileInfo[] testProfileInfos;
    ObjectMapper mockObjectMapper;
    NeedFileDAO mockNeedDAO;

    String path = "../ufund-ui/angular/src/assets/Paws&Claws.png";

    /**
     * Before each test, we will create and inject a Mock Object Mapper to
     * isolate the tests from the underlying file
     * @throws IOException
     */
    @BeforeEach
    public void setupAccountFileDAO() throws IOException {
        mockObjectMapper = mock(ObjectMapper.class);

        testNeeds = new Need[3];
        testNeeds[0] = new Need("MOCKID-0","NeedA1", "Description1", 0, 3, 12.5);
        testNeeds[1] = new Need("MOCKID-1","NeedB1", "Description2", 2.9, 10, 5.4);
        testNeeds[2] = new Need("MOCKID-2","NeedB2", "Description3", 1.3, 6, 12.32);

        String TEST_IMG =  "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png";

        testBasketNeeds = new BasketNeed[5];
        testBasketNeeds[0] = new BasketNeed(testNeeds[0], 1);
        testBasketNeeds[1] = new BasketNeed(testNeeds[1], 1);
        testBasketNeeds[2] = new BasketNeed(testNeeds[2], 1);
        testBasketNeeds[3] = new BasketNeed(testNeeds[1], 3);
        testBasketNeeds[4] = new BasketNeed(testNeeds[2], 6);

        testBaskets = new Basket[4];        

        testBaskets[0] = new Basket(); // empty

        testBaskets[1] = new Basket(); // need0: 1, need3: 3
        testBaskets[1].addNeed(testNeeds[0]);
        testBaskets[1].addNeed(testNeeds[1]);
        testBaskets[1].addNeed(testNeeds[1]);
        testBaskets[1].addNeed(testNeeds[1]);

        testBaskets[2] = new Basket(); // need4: 6
        for (int i=0; i<6; i++) {
            testBaskets[2].addNeed(testNeeds[2]);
        }

        testBaskets[3] = new Basket(); // need0: 1
        testBaskets[3].addNeed(testNeeds[0]);

        testProfileInfos = new ProfileInfo[2];
        testProfileInfos[0] = new ProfileInfo(null, "pronouns", "my-alias", "bio", Region.CENTRAL_NEW_YORK, "(123) 123-1233", "me@me.com", "111-11-1111", "Private");
        testProfileInfos[1] = new ProfileInfo(null, "he/him", "alias", "bio", Region.CENTRAL_NEW_YORK, "(123) 123-1233", "me@me.com", "111-11-1111", "Private");

        // accounts baskets match the related index in testBaskets
        testAccounts = new Account[5];
        testAccounts[0] = new Account("Max", "pass");
        testAccounts[1] = new Account("Kayla", testBaskets[1], TEST_PROFILE_INFO, "pass", TEST_IMG);
        testAccounts[2] = new Account("Jonah", testBaskets[2], TEST_PROFILE_INFO, "pass", TEST_IMG);
        testAccounts[3] = new Account("Ryan", testBaskets[3], TEST_PROFILE_INFO, "pass", TEST_IMG);
        testAccounts[4] = new Account("KaylaInfo", testBaskets[1], testProfileInfos[0], "pass", TEST_IMG);

        // When the object mapper is supposed to read from the file
        // the mock object mapper will return the need array above
        when(mockObjectMapper
            .readValue(new File("doesnt_matter.txt"),Account[].class))
                .thenReturn(testAccounts);

        mockNeedDAO = mock(NeedFileDAO.class);

        for(Need n: testNeeds){
            when(mockNeedDAO.getNeed(n.getId())).thenReturn(n);
        }

        accountFileDAO = new AccountFileDAO("doesnt_matter.txt", mockObjectMapper, mockNeedDAO);
    }

    /**
     * Tests getting an account
     * @throws IOException
     */
    @Test
    public void testGetAccount() throws IOException {
        // Invoke
        Account acc = accountFileDAO.getAccount("Max");

        // Analyze
        assertEquals(testAccounts[0], acc);
    }

    /**
     * Tests getting needs for an Account
     * @throws IOException
     */
    @Test
    public void testGetNeeds() throws IOException {
        // Invoke
        List<BasketNeed> needs = accountFileDAO.getNeeds("Kayla");

        assertEquals(testBaskets[1].getNeeds(), needs);
    }

    /**
     * Tests getting needs for an account that doesn't exist
     * @throws IOException
     */
    @Test
    public void testGetNeedsNullAccount() throws IOException {
        // Invoke
        List<BasketNeed> needs = accountFileDAO.getNeeds("idx");

        assertNull(needs);
    }

    /**
     * Tests removing a null Need
     * @throws IOException
     */
    @Test
    public void testRemoveNullNeed() throws IOException {
        // Setup

        when(mockNeedDAO.getNeed(testNeeds[0].getId())).thenReturn(null);

        // Invoke

        List<BasketNeed> needs = accountFileDAO.getNeeds("Kayla");

        assertTrue(needs.size() == 1);
    }

    /**
     * Tests the FileDAO returning a decreased need
     * @throws IOException
     */
    @Test
    public void testDropDecreasedNeed() throws IOException {
        // Setup
        Need decreasedNeed = new Need("MOCKID-1","NeedB1", "Description2", 2.9, 1, 5.4);

        when(mockNeedDAO.getNeed("MOCKID-1")).thenReturn(decreasedNeed);

        // Invoke

        List<BasketNeed> needs = accountFileDAO.getNeeds("Kayla");

        for(BasketNeed n: needs){
            if(n.getNeed().getId() == decreasedNeed.getId()){
                assertEquals(decreasedNeed, n.getNeed());
                assertEquals(n.getQuantity(), 1);
            }
        }
    }

    /**
     * tests the checkout functionality
     * @throws IOException
     */
    @Test 
    public void testCheckout() throws IOException {
        // Setup to fufill all of one need
        testBaskets[1].updateNeed(testNeeds[0], 3 - testBaskets[1].getBasketNeed(testNeeds[0]).getQuantity());
        
        when(mockNeedDAO.getNeed(testNeeds[0].getId())).thenReturn(testNeeds[0]);
        when(mockNeedDAO.deleteNeed(testNeeds[0].getId())).thenReturn(true);

        // Invoke

        boolean checked = accountFileDAO.checkout("Kayla");

        List<BasketNeed> needs = accountFileDAO.getNeeds("Kayla");

        assertTrue(checked);
        assertEquals(needs.size(), 0);
    }

    /**
     * tests checking out for a null account
     * @throws IOException
     */
    @Test 
    public void testCheckoutNullAccount() throws IOException {
        // Invoke

        boolean checkedout = accountFileDAO.checkout("idx");

        assertTrue(!checkedout);
    }

    /**
     * Tests checkout for a need with a low quantity (checks out to become zero quantity)
     * @throws IOException
     */
    @Test 
    public void testCheckoutLowNeed() throws IOException {
        // Setup 
        testBaskets[1].updateNeed(testNeeds[0], 5 - testBaskets[1].getBasketNeed(testNeeds[0]).getQuantity());

        when(mockNeedDAO.getNeed(testNeeds[0].getId())).thenReturn(testNeeds[0]);
        when(mockNeedDAO.deleteNeed(testNeeds[0].getId())).thenReturn(true);

        // Invoke

        boolean checkedout = accountFileDAO.checkout("Kayla");

        assertTrue(!checkedout);
    }

    /**
     * checks out a null need
     * @throws IOException
     */
    @Test 
    public void testCheckoutNullNeed() throws IOException {
        // Setup 
        when(mockNeedDAO.getNeed(testNeeds[0].getId())).thenReturn(null);

        // Invoke

        boolean checkedout = accountFileDAO.checkout("Kayla");

        assertTrue(!checkedout);
    }

    /**
     * tests creating an account
     * @throws IOException
     */
    @Test
    public void testCreateAccount() throws IOException {
        Account created = accountFileDAO.createAccount("Bichael", "pass");
        Account gotten = accountFileDAO.getAccount("Bichael");

        assertEquals(created, gotten);
    }

    /**
     * Tests attempting to create an already existing account
     * @throws IOException
     */
    @Test
    public void testCreateExistingAccount() throws IOException {
        Account created = accountFileDAO.createAccount("Max", "pass");

        assertNull(created);
    }

    /**
     * tests adding a need to an account's basket
     * @throws IOException
     */
    @Test
    public void testAddNeed() throws IOException {
        BasketNeed ryanNeed = accountFileDAO.updateNeed("Ryan", testNeeds[0], 1);

        assertEquals(ryanNeed.getQuantity(), 2);
    }

    /**
     * tests removing a need from a basket
     * @throws IOException
     */
    @Test
    public void testRemoveNeed() throws IOException {
        BasketNeed kaylaNeed = accountFileDAO.updateNeed("Kayla", testNeeds[1], -1);

        assertEquals(2, kaylaNeed.getQuantity());
    }

    /**
     * tests updating a need when the account doesn't exist
     * @throws IOException
     */
    @Test
    public void testUpdateNeedForFakeAccount() throws IOException {
        assertNull(accountFileDAO.updateNeed("FAKE", testNeeds[1], 1));
    }

    @Test
    public void testAddImage() throws IOException {//failing
        byte[] img = Files.readAllBytes(Paths.get(path));

        assertEquals("http://res.cloudinary.com/dc5ifh1f7/image/upload/v1730819252/Kayla.png", accountFileDAO.addImage("Kayla", img));
    }

    @Test
    public void testAddImageFakeAccount() throws IOException {
        byte[] img = Files.readAllBytes(Paths.get(path));

        assertNull(accountFileDAO.addImage("FAKE", img));
    }

    /**
     * tests that checkout updates an account's moneyFunded and needsFunded
     * @throws IOException
     */
    @Test 
    public void testCheckoutUpdatesFundingData() throws IOException {
        Account account = accountFileDAO.getAccount("Kayla");

        double expectedMoney = testBaskets[1].getCost();
        int expectedCount = testBaskets[1].getNeedCount();

        boolean checked = accountFileDAO.checkout("Kayla");

        assertEquals(expectedMoney, account.getMoneyFunded());
        assertEquals(expectedCount, account.getNeedsFunded());

    }

    /**
     * tests getting all accounts in order of rank
     * @throws IOException
     */
    @Test
    public void testGetRanks() throws IOException {
        Account kayla = accountFileDAO.getAccount("Kayla");
        Account max = accountFileDAO.getAccount("Max");
        Account jonah = accountFileDAO.getAccount("Jonah");
        Account ryan = accountFileDAO.getAccount("Ryan");
        Account kaylainfo = accountFileDAO.getAccount("KaylaInfo");

        kayla.addMoneyFunded(12); 
        max.addMoneyFunded(2); 
        jonah.addMoneyFunded(19); 
        ryan.addMoneyFunded(7);
        kaylainfo.addMoneyFunded(5);

        List<Account> expected = new ArrayList<>();
        expected.add(jonah); // rank 1
        expected.add(kayla);
        expected.add(ryan);
        expected.add(kaylainfo);
        expected.add(max); // max doesn't wanna save the world

        assertEquals(expected, accountFileDAO.getRankList());
    }

    // Successfully retrieve a valid account's profileInfo
    @Test
    public void testGetProfileInfo_ValidAccount() throws IOException {
        Account kaylaInfo = accountFileDAO.getAccount("KaylaInfo");
        ProfileInfo expectedInfo = testProfileInfos[0];
        ProfileInfo result = accountFileDAO.getProfileInfo("KaylaInfo");

        assertEquals(expectedInfo, result);
    }
    /**
     * asserts that the user with isGod = true is loaded on startup
     */
    @Test
    public void testGodLoad() throws IOException {
        // make max god
        testAccounts[0].setIsGod(true);

        accountFileDAO = new AccountFileDAO("doesnt_matter.txt",mockObjectMapper, mockNeedDAO);

        Account max = accountFileDAO.getAccount("Max");
        Account god = accountFileDAO.getGod();

        assertEquals(max, god);
    }    

    /**
     * Tests if get and set god are working
     * @throws IOException
     */
    @Test
    public void testGetSetGod() throws IOException {
        Account kayla = accountFileDAO.getAccount("Kayla");
        Account max = accountFileDAO.getAccount("Max");

        accountFileDAO.setGod(kayla);

        // max is no longer god
        // kayla is god
        assertFalse(max.getIsGod());
        assertTrue(kayla.getIsGod());
    }


    // Exception when trying to retrieve profileInfo with null account name
    @Test
    public void testGetProfileInfo_NullAccountName() throws IOException {
        assertThrows(IllegalArgumentException.class, () -> accountFileDAO.getProfileInfo(null));
    }

    // Successfully update ProfileInfo of a valid account
    @Test
    public void testUpdateProfileInfo_ValidAccount() throws IOException {
        Account kaylaInfo = accountFileDAO.getAccount("KaylaInfo");
        ProfileInfo newProfileInfo = testProfileInfos[1];

        ProfileInfo result = accountFileDAO.updateProfileInfo("KaylaInfo", newProfileInfo);

        assertEquals(newProfileInfo, result);
        // verify(kaylaInfo.getProfileInfo()).updateProfileInfo(newProfileInfo);
        //verify(accountFileDAO, times(1)).save();
    }

    // Exception if updating profileInfo with null account
    @Test
    public void testUpdateProfileInfo_NullAccount() throws IOException {
        ProfileInfo newProfileInfo = testProfileInfos[1];
        assertThrows(IllegalArgumentException.class, () -> accountFileDAO.updateProfileInfo(null, new ProfileInfo()));
    }

    // Exception if updating profileInfo with null info
    @Test
    public void testUpdateProfileInfo_NullInfo() throws IOException {
        Account kaylaInfo = accountFileDAO.getAccount("KaylaInfo");
        assertThrows(IllegalArgumentException.class, () -> accountFileDAO.updateProfileInfo("KaylaInfo", null));
    }

    // Null if updating profileInfo of nonexistent account
    @Test
    public void testUpdateProfileInfo_NonExistentAccount() throws IOException {
        ProfileInfo newProfileInfo = testProfileInfos[1];
        assertNull(accountFileDAO.updateProfileInfo("nonExistentAccount", newProfileInfo));
    }

    /**
     * checks that a new god is assigned on checkout
     * @throws IOException
     */
    @Test
    public void testCheckoutSwapGod() throws IOException {

        Account max = accountFileDAO.getAccount("Max");
        Account kayla = accountFileDAO.getAccount("Kayla");

        when(mockNeedDAO.isEmpty()).thenReturn(true);
        accountFileDAO.checkout("Kayla");


        assertEquals(kayla, accountFileDAO.getGod());
    }

    /**
     * Checks that god does not change when there are still needs left after checkout
     * @throws IOException
     */
    @Test
    public void testCheckoutNoSwapGod() throws IOException {
        when(mockNeedDAO.isEmpty()).thenReturn(false);
        accountFileDAO.checkout("Kayla");

        assertNull(accountFileDAO.getGod());
    }

    //test for no god
    @Test
    void testLoadNoGod() throws IOException {
        assertNull(accountFileDAO.getGod());
    }    

    //test for getting old god, null
    @Test
    void testSetGodOldIsNull() throws IOException {
        Account kayla = accountFileDAO.getAccount("Kayla");
        accountFileDAO.setGod(kayla);

        assertEquals(kayla, accountFileDAO.getGod());
    }

    //test for getting old god, not null
    @Test
    void testSetGodOldNotNull() throws IOException {
        Account max = accountFileDAO.getAccount("Max");
        Account kayla = accountFileDAO.getAccount("Kayla");
        accountFileDAO.setGod(max);
        accountFileDAO.setGod(kayla);

        assertEquals(kayla, accountFileDAO.getGod());
        assertFalse(max.getIsGod());
    }

    

    // Successfully getting AdminInfo
    @Test
    public void testGetAdminInfo() throws IOException {
        int userNumber = 0;
        int needsFunded = 0;
        int moneyFunded = 0;
        Instant lastFundedInstant = Instant.MIN;
        Map<Region, Integer> regions = new EnumMap<>(Region.class);
        Map<Region, Double> fundedByRegion = new EnumMap<>(Region.class);

        for (Region region : Region.values()) {
            regions.put(region, 0);
            fundedByRegion.put(region, 0.0);
        }

        testAccounts[0].setLastCheckoutInstant(Instant.parse("2023-01-01T10:15:30Z"));
        testAccounts[1].setLastCheckoutInstant(Instant.parse("2023-02-01T10:15:30Z")); // Latest
        testAccounts[2].setLastCheckoutInstant(null);

        for(Account acc : testAccounts){
            userNumber++;
            needsFunded += acc.getNeedsFunded();
            moneyFunded += acc.getMoneyFunded();
            if(acc.getLastCheckoutInstant() != null){
                if(lastFundedInstant.isBefore(acc.getLastCheckoutInstant())){
                    lastFundedInstant = acc.getLastCheckoutInstant();
                }
            }
            Region region = acc.getProfileInfo().getRegion();
            regions.put(region, regions.get(region) + 1);
            fundedByRegion.put(region, fundedByRegion.get(region) + acc.getMoneyFunded());
        }

        AdminInfo result = accountFileDAO.getUserStats();

        AdminInfo expected = new AdminInfo(userNumber, needsFunded, moneyFunded, lastFundedInstant, regions, fundedByRegion);

        assertEquals(expected, result);
    }
    //test for getting a rank successfully
    @Test
    public void testgetRankSuccessful() throws InterruptedException, IOException{

        // make new accountFileDAO so we have just the new 4 accounts
        when(mockObjectMapper
            .readValue(new File("doesnt_matter.txt"),Account[].class))
                .thenReturn(new Account[0]);
        accountFileDAO = new AccountFileDAO("doesnt_matter.txt", mockObjectMapper, mockNeedDAO);

        accountFileDAO.createAccount("account1", "PASS");
        TimeUnit.MILLISECONDS.sleep(10);
        accountFileDAO.createAccount("account2", "PASS");
        TimeUnit.MILLISECONDS.sleep(10);
        accountFileDAO.createAccount("account3", "PASS");
        TimeUnit.MILLISECONDS.sleep(10);
        accountFileDAO.createAccount("account4", "PASS");

        // ordered by time because there are no needs or money funded

        assertEquals(3, accountFileDAO.getRank("account3"));
    }
}

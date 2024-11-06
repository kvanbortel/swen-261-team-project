package com.ufund.api.ufundapi.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import java.time.Instant;

/**
 * Class for testing the Account model class
 *
 * @author Max Klot
 */
@Tag("Model-tier")
public class AccountTest {
    private static final String TEST_NAME = "Max";
    private static final Basket TEST_BASKET = new Basket();
    private static final String TEST_PASSWORD_HASH = "thisisnotahash";
    private static final ProfileInfo TEST_PROFILE_INFO = new ProfileInfo();
    private static final Level TEST_LEVEL = Level.NOOB;
    
    private Account account;

    @BeforeEach
    public void setUp() {
        account = new Account(TEST_NAME, (TEST_BASKET), TEST_PROFILE_INFO, TEST_LEVEL, TEST_PASSWORD_HASH);
    }

    //Test for creating an account with no basket parameter
    @Test
    public void testConstructorNoBasket() {
        Account newAccount =  new Account(TEST_NAME, TEST_PASSWORD_HASH);
        assertEquals(TEST_NAME, account.getName());
        assertEquals(new Basket(), account.getBasket());
    }

    //Test for creating an account with basket parameter
    @Test
    public void testConstructorBasket() {
        Account newAccount =  new Account(TEST_NAME, (TEST_BASKET), TEST_PROFILE_INFO, TEST_LEVEL, TEST_PASSWORD_HASH);
        assertEquals(TEST_NAME, account.getName());
        assertEquals(TEST_BASKET, account.getBasket());
    }

    //test for getting name of account
    @Test
    public void testgetName() {
        assertEquals(TEST_NAME, account.getName());
    }

    //test for getting basket of an account 
    @Test
    public void testgetBasket() {
        assertEquals(TEST_BASKET, account.getBasket());
    }

    //test for true equality between two accounts
    @Test
    public void testAccountEqualTrue() {
        Account account1 = new Account(TEST_NAME, (TEST_BASKET), TEST_PROFILE_INFO, TEST_LEVEL, TEST_PASSWORD_HASH);
        Account account2 = new Account(TEST_NAME, (TEST_BASKET), TEST_PROFILE_INFO, TEST_LEVEL, TEST_PASSWORD_HASH);
        assertEquals(account1, account2);
    }

    //test for false equality between two accounts depending on name
    @Test
    public void testAccountEqualFalseName() {
        Account account1 = new Account(TEST_NAME, (TEST_BASKET), TEST_PROFILE_INFO, TEST_LEVEL, TEST_PASSWORD_HASH);
        Account account2 = new Account("Different Name", (TEST_BASKET), TEST_PROFILE_INFO, TEST_LEVEL, TEST_PASSWORD_HASH);
        assertNotEquals(account1, account2);
    }

    //test for false equality between two accounts depending on basket
    @Test
    public void testAccountEqualFalseBasket() {
        Account account1 = new Account(TEST_NAME, (TEST_BASKET), TEST_PROFILE_INFO, TEST_LEVEL, TEST_PASSWORD_HASH);
        BasketNeed[] needs = {new BasketNeed(new Need("hello", "hello", "hello", 10, 10, 0), 0)};
        Account account2 = new Account(TEST_NAME, new Basket(Arrays.asList(needs)), TEST_PROFILE_INFO, TEST_LEVEL, TEST_PASSWORD_HASH);
        assertNotEquals(account1, account2);
    }

    //test for false equality between an account and an object
    @Test
    public void testAccountEqualObjNotNeed() {
        Account account1 = new Account(TEST_NAME, (TEST_BASKET), TEST_PROFILE_INFO, TEST_LEVEL, TEST_PASSWORD_HASH);
        Object account2 = null;
        assertNotEquals(account1, account2);
    }

    /**
     * Gets the money funded for an account
     */
    @Test 
    public void testGetMoneyFunded() {
        Account account = new Account("NAME", "PASS");

        double amount = account.getMoneyFunded();

        assertEquals(0.0, amount);
    }

    /**
     * Test getting number of needs funded
     */
    @Test 
    public void testGetNeedsFunded() {
        Account account = new Account("NAME", "PASS");

        int quantity = account.getNeedsFunded();

        assertEquals(0.0, quantity);
    }

    /**
     * tests adding money to moneyFunded
     */
    @Test
    public void testAddMoneyFunded() {
        Account account = new Account("NAME", "PASS");

        double addMe = 38.28;

        double newAmount = account.addMoneyFunded(addMe);

        assertEquals(addMe, newAmount);
    }

    /** tests adding a number of needs funded */
    @Test
    public void testAddNeedsFunded() {
        Account account = new Account("NAME", "PASS");

        int addMe = 7;

        int newQuantity = account.addNeedsFunded(addMe);

        assertEquals(addMe, newQuantity);
    }

    /** tests ordering accounts based on rank */
    @Test
    public void testOrderAccounts() {
        Account account1 = new Account("account1", "PASS");
        Account account2 = new Account("account2", "PASS");
        List<Account> accounts= new ArrayList<Account>();
        accounts.add(account2);
        accounts.add(account1);

        List<Account> expected= new ArrayList<Account>();
        expected.add(account1);
        expected.add(account2);

        account1.addMoneyFunded(100);
        account2.addMoneyFunded(15);

        Collections.sort(accounts);

        assertEquals(expected, accounts);
    }

    /** tests comparing a rank that is GT another */
    @Test
    public void testCompareGT() {
        Account account1 = new Account("account1", "pass");
        Account account2 = new Account("account2", "pass");

        account1.addMoneyFunded(1);
        account2.addMoneyFunded(15);
        
        assertEquals(1, account1.compareTo(account2));
    }

    /** tests comparing 2 ranks */
    @Test
    public void testCompareEQ() {
        Account account1 = new Account("account", "PASS");
        Account account2 = new Account("account", "PASS");
        // names, needsFunded, and moneyFundeda are the same

        Instant instant = Instant.EPOCH;
        account1.setLastCheckoutInstant(instant);
        account2.setLastCheckoutInstant(instant);
        
        assertEquals(0, account1.compareTo(account2));
    }

    /** tests comparing 2 ranks */
    @Test
    public void testCompareLT() {
        Account account1 = new Account("account1", "PASS");
        Account account2 = new Account("account2", "PASS");

        account1.addMoneyFunded(100);
        account2.addMoneyFunded(15);
        
        assertEquals(-1, account1.compareTo(account2));
    }

    /** tests comparing 2 ranks with the same moneyFunded */
    @Test
    public void testCompareNeedTiebreakLT() {
        Account account1 = new Account("account1", "PASS");
        Account account2 = new Account("account2", "PASS");

        account1.addMoneyFunded(15);
        account2.addMoneyFunded(15);

        account1.addNeedsFunded(3);
        account2.addNeedsFunded(2);

        assertEquals(-1, account1.compareTo(account2));
    }
    
    /** tests comparing 2 ranks with the same moneyFunded */
    @Test
    public void testCompareNeedTiebreakGT() {
        Account account1 = new Account("account1", "PASS");
        Account account2 = new Account("account2", "PASS");

        account1.addMoneyFunded(15);
        account2.addMoneyFunded(15);

        account1.addNeedsFunded(1);
        account2.addNeedsFunded(2);

        assertEquals(1, account1.compareTo(account2));
    }

    /** tests comparing 2 ranks with the same moneyFunded and needsFunded */
    @Test 
    public void testCompareInstantTiebreakLT() throws InterruptedException {
        Account account1 = new Account("account1", "PASS");
        TimeUnit.MILLISECONDS.sleep(10);
        Account account2 = new Account("account2", "PASS");
        // needsFunded and moneyFunded = 0

        assertEquals(-1, account1.compareTo(account2));
    }

    /** tests comparing 2 ranks with the same moneyFunded and needsFunded */
    @Test 
    public void testCompareInstantTiebreakGT() throws InterruptedException {
        Account account1 = new Account("xaccount1", "PASS");
        TimeUnit.MILLISECONDS.sleep(10);
        Account account2 = new Account("account2", "PASS");
        // needsFunded and moneyFunded = 0

        assertEquals(1, account2.compareTo(account1), account1.toString() + "\n" + account2.toString());
    }

    /** tests creating a string for account */
    @Test
    public void testToString() {
        Account account = new Account("ACCOUNT_NAME", "PASS");

        assertEquals("Account(ACCOUNT_NAME)", account.toString());
    }

    /** tests getting the rank of an account */
    @Test
    public void testRank3() throws InterruptedException {
        Account account1 = new Account("account1", "PASS");
        TimeUnit.MILLISECONDS.sleep(10);
        Account account2 = new Account("account2", "PASS");
        TimeUnit.MILLISECONDS.sleep(10);
        Account account3 = new Account("account3", "PASS");
        TimeUnit.MILLISECONDS.sleep(10);
        Account account4 = new Account("account4", "PASS");

        // ordered by time because there are no needs or money funded

        List<Account> accounts = new ArrayList<>();
        accounts.add(account1);
        accounts.add(account2);
        accounts.add(account3);
        accounts.add(account4);
        assertEquals(3, account3.getRank(accounts));

    }

    /** tests setting the last checkout instant */
    @Test
    public void testSetLast() {
        Instant instant = Instant.EPOCH;

        Account account = new Account("ACCOUNT", "PASS");

        account.setLastCheckoutInstant(instant);

        assertEquals(instant, account.getLastCheckoutInstant());
    }

    /** tests that setting lastCheckoutInstant works when nothing is passed */
    @Test 
    void testSetLastAuto() throws InterruptedException{
        Account account = new Account("ACCOUNT", "PASS");
        Instant before = Instant.now();
        
        TimeUnit.MILLISECONDS.sleep(10);

        account.setLastCheckoutInstant();

        TimeUnit.MILLISECONDS.sleep(10);

        Instant after = Instant.now();

        boolean isBetween = before.compareTo(account.getLastCheckoutInstant()) < 0
            && after.compareTo(account.getLastCheckoutInstant()) > 0;

        assertTrue(isBetween);
    }
}

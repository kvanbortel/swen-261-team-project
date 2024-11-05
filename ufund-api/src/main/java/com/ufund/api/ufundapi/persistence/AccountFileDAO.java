package com.ufund.api.ufundapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufund.api.ufundapi.model.Account;
import com.ufund.api.ufundapi.model.Basket;
import com.ufund.api.ufundapi.model.BasketNeed;
import com.ufund.api.ufundapi.model.Need;

import java.time.*;

@Repository
public class AccountFileDAO implements AccountDAO {

    // Use the logger from HERO for message handling?
    private static final Logger LOG = Logger.getLogger(AccountFileDAO.class.getName());

    // Local cashe of needs
    Map<String, Account> accounts;

    // Convert between jsons and java objects
    private ObjectMapper objectMapper;

    // File name to read/write from
    private String filename;

    // Need DAO to keep the needs in sync with the database and check out
    private final NeedDAO needDAO;

    /**
     * Creates an Account File Data Access Object
     * 
     * @param filename     Filename to read from and write to
     * @param objectMapper Provides JSON Object to/from Java Object serialization
     *                     and deserialization
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    public AccountFileDAO(@Value("${accounts.file}") String filename, ObjectMapper objectMapper, NeedDAO needDAO)
            throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        this.needDAO = needDAO;
        load(); // load the needs from the file
    }

    /**
     * Generates an array of {@linkplain Account accounts} from the tree map
     * 
     * @return The array of {@link Account accounts}, may be empty
     */
    private Account[] getAccountsArray(String containsText) { // if containsText == null, no filter
        ArrayList<Account> accountArrayList = new ArrayList<>();

        for (Account account : accounts.values()) {
            accountArrayList.add(account);
            /*
             * This branch is for searching for accounts. It is currently unreachable
             * So it is being commented out but left in case we want to use it later.
             * if (containsText == null || account.getName().contains(containsText)) {
             * accountArrayList.add(account);
             * }
             */
        }

        Account[] accountArray = new Account[accountArrayList.size()];
        accountArrayList.toArray(accountArray);
        return accountArray;
    }

    /**
     * Generates an array of {@linkplain Account accounts} from the tree map
     * 
     * @return The array of {@link Account accounts}, may be empty
     */
    private Account[] getAccountsArray() {
        return getAccountsArray(null);
    }

    /**
     * Loads {@linkplain Account accounts} from the JSON file into the map
     * <br>
     * 
     * @return true if the file was read successfully
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    private boolean load() throws IOException {
        accounts = new TreeMap<>();

        // Deserializes the JSON objects from the file into an array of needs
        // readValue will throw an IOException if there's an issue with the file
        // or reading from the file
        Account[] accountArray = objectMapper.readValue(new File(filename), Account[].class);

        // Add each need to the tree map
        for (Account account : accountArray) {
            accounts.put(account.getName(), account);
        }

        return true;
    }

    /**
     ** {@inheritDoc}
     */
    public Account createAccount(String name, String passwordHash) throws IOException {
        if (accounts.containsKey(name)) {
            return null;
        }
        Account newAccount = new Account(name, passwordHash);
        accounts.put(name, newAccount);
        save(); // may throw an IOException
        return newAccount;
    }

    /**
     * Saves the {@linkplain Account accounts} from the map into the file as an
     * array of JSON objects
     * 
     * @return true if the {@link Account accounts} were written successfully
     * 
     * @throws IOException when file cannot be accessed or written to
     */
    private boolean save() throws IOException {
        Account[] accountsArray = getAccountsArray();

        // Serializes the Java Objects to JSON objects into the file
        // writeValue will thrown an IOException if there is an issue
        // with the file or reading from the file
        objectMapper.writeValue(new File(filename), accountsArray);
        return true;
    }

    /**
     ** {@inheritDoc}
     */
    @Override
    public BasketNeed updateNeed(String account, Need need, int amount) throws IOException {

        Account accountObj = accounts.get(account);
        if (accountObj != null) {
            accountObj.getBasket().updateNeed(need, amount);
            save();
            return accountObj.getBasket().getBasketNeed(need);
        } else {
            return null;
        }

    }

    /**
     ** {@inheritDoc}
     */
    @Override
    public boolean checkout(String account) throws IOException {

        Account accountObj = accounts.get(account);
        if (accountObj != null) {
            Basket basket = accountObj.getBasket();

            List<BasketNeed> needs = new ArrayList<>(basket.getNeeds());

            // check that checkout is still valid
            for(BasketNeed bNeed: needs){
                
                Need newNeed = needDAO.getNeed(bNeed.getNeed().getId());
                if(newNeed == null){
                    return false;
                }
                int newQuantity = newNeed.getQuantity() - bNeed.getQuantity();
                if(newQuantity < 0){
                    // can't checkout this need - CHECKOUT FAILS
                    return false;
                }
            }

            // checkout should always succeed past this
            // update funding data for users
            // this MUST happen before the basket is changed
            accountObj.addMoneyFunded(basket.getCost());
            accountObj.addNeedsFunded(basket.getNeedCount());
            accountObj.setLastCheckoutInstant(Instant.now());
            for (BasketNeed bNeed : needs) {
                Need newNeed = needDAO.getNeed(bNeed.getNeed().getId());
                int newQuantity = newNeed.getQuantity() - bNeed.getQuantity();
                if(newQuantity == 0){
                    needDAO.deleteNeed(newNeed.getId());
                }
                else{
                    // update the remaining quantity of the need
                    newNeed.setQuantity(newQuantity);
                    needDAO.updateNeed(newNeed);
                }
                basket.updateNeed(bNeed.getNeed(), -bNeed.getQuantity());
            }
            save();
            return true;
        } else {
            return false;
        }

    }

    /**
     ** {@inheritDoc}
     */
    @Override
    public ArrayList<BasketNeed> getNeeds(String account) throws IOException {

        Account accountObj = accounts.get(account);
        if(accountObj == null){
            return null;
        }
        
        Basket basket = accountObj.getBasket();

        List<BasketNeed> needs = new ArrayList<>(basket.getNeeds());

        // make sure the needs are up-to-date with the database
        for (BasketNeed bNeed : needs) {
            Need newNeed = needDAO.getNeed(bNeed.getNeed().getId());
            if(newNeed == null){
                // if the need is no longer in the database, remove it from the basket
                basket.updateNeed(bNeed.getNeed(), -bNeed.getQuantity());
            }
            else{
                basket.setNeed(newNeed);
                if(newNeed.getQuantity() <= basket.getBasketNeed(newNeed).getQuantity()){
                    // if the quantity has been decreased below the amount in our basket, drop by the difference
                    basket.updateNeed(newNeed, newNeed.getQuantity() - basket.getBasketNeed(newNeed).getQuantity());
                }
            }
        }

        return basket.getNeeds();
    }

    /**
     ** {@inheritDoc}
     */
    public Account getAccount(String accountName) throws IOException {
        return accounts.get(accountName); // Return the Account object or null if not found
    }

    /**
     * @inheritdoc
     */
    public List<Account> getRankList() {
        List<Account> accountList = new ArrayList<>(this.accounts.values());
        // sort it
        Collections.sort(accountList);
        return accountList;
    }

}

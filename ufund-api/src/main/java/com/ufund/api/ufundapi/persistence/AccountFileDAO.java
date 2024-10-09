package com.ufund.api.ufundapi.persistence;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufund.api.ufundapi.model.Account;
import com.ufund.api.ufundapi.model.Need;

@Component
public class AccountFileDAO implements AccountDAO{

    private static final Logger LOG = Logger.getLogger(AccountFileDAO.class.getName());

    // Local cashe of needs
    Map<String, Account> accounts;

    // Convert between jsons and java objects 
    private ObjectMapper objectMapper;

    // File name to read/write from
    private String filename;

    public AccountFileDAO(@Value("${accounts.file}") String filename, ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();  // load the needs from the file
    }
    
    private Account[] getAccountsArray(String containsText) { // if containsText == null, no filter
        ArrayList<Account> accountArrayList = new ArrayList<>();

        for (Account account : accounts.values()) {
            if (containsText == null || account.getName().contains(containsText)) {
                accountArrayList.add(account);
            }
        }

        Account[] accountArray = new Account[accountArrayList.size()];
        accountArrayList.toArray(accountArray);
        return accountArray;
    }
    
    private Account[] getAccountsArray() {
        return getAccountsArray(null);
    }
    

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

    public Account createAccount(Account account) throws IOException{
        
        if( accounts.containsKey(account.getName())){

            //TODO: error handling for creating an account with a name that already exists
        }
        Account newAccount = new Account(account.getName());
        accounts.put(newAccount.getName(), account);
        save(); // may throw an IOException
        System.out.println(newAccount);
        return newAccount;
    }

    private boolean save() throws IOException {
        Account[] accountsArray = getAccountsArray();

        // Serializes the Java Objects to JSON objects into the file
        // writeValue will thrown an IOException if there is an issue
        // with the file or reading from the file
        objectMapper.writeValue(new File(filename), accountsArray);
        return true;
    }

    @Override
    public boolean updateNeed(String id) throws IOException{

        return false;
    }

    @Override
    public ArrayList<Need> getNeeds() throws IOException{

        return new ArrayList<Need>();
    }


}

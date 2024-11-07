package com.ufund.api.ufundapi.persistence;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.ufund.api.ufundapi.model.*;

public interface AccountDAO{

    /**
     * Retrieves all {@linkplain BasketNeed Needs}
     * 
     * @param account {String account} Account name 
     * 
     * @return An array of {@link BasketNeed Need} objects, may be empty
     * 
     * @throws IOException if an issue with underlying storage
     */
    ArrayList<BasketNeed> getNeeds(String account) throws IOException;

     /**
     * Updates a {@linkplain Basket basket} with a {@linkplain BasketNeed BasketNeed
     * }
     * @param account {String account} Account name 
     * 
     * @param need {Need need} Basket ID
     * 
     * @param amount {int amount} the quantity to change the need by
     * 
     * @return the {@link BasketNeed} if the need was updated 
     * 
     * null if Account with the given id does not exist
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    public BasketNeed updateNeed(String account, Need need, int amount) throws IOException;

    /**
     * Checks out all the needs in a user's basket, reducing their quantities
     * 
     * @param account {String account} Account name 
     * 
     * @return true if the {@link Account account} was updated 
     * <br>
     * false if Account with the given id does not exist
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    public boolean checkout(String account) throws IOException;

    /**
     * Creates and saves a {@linkplain Account account}
     * 
     * @param Account {@linkplain Account account} object to be created and saved
     * <br>
     * The id of the Account object is ignored and a new uniqe id is assigned
     *
     * @return new {@link Account account} if successful, false otherwise 
     * 
     * @throws IOException if an issue with underlying storage
     */
    public Account createAccount(String name, String passwordHash) throws IOException;

    /**
     * Retrieves an account by its name.
     *
     * @param accountName the name of the account to retrieve
     * @return the Account object if found, or null if the account does not exist
     * @throws IOException if there is an error reading from the data source
     */
    Account getAccount(String accountName) throws IOException;

    /**
     * Add a profile picture to an account
     *
     * @param accountName the name of the account to retrieve
     * @param img the file to be stored as an image
     * @return the link to the new image
     * @throws IOException if there is an error reading from the data source
     */
    String addImage(String accountName, byte[] img) throws IOException;

    /**
     * Accounts may not always have the most up to date rank,
     * But all ranks will have been defined at the same time
     * There is no accessor for a rank list because you should ALWAYS
     * update the rank before accessing it
     * 
     * @return List<Account> of lists starting with Rank1, then Rank2, and so on
     *         Use `list.indexOf(account) + 1` to find the rank of a specific account
     */
    public List<Account> getRankList();
    /**
      * Retrieves all {@linkplain ProfileInfo} profile information
      *
      * @param account {String account} Account name
      *
      * @return A {@link ProfileInfo Info} object, may be empty
      *
      * @throws IOException if an issue with underlying storage
      */
    ProfileInfo getProfileInfo(String account) throws IOException;

    /**
     * Updates a user's {@linkplain ProfileInfo profileInfo} with given data
     *
     * @param account {String account} Account name
     *
     * @param info {ProfileInfo info} profile info data
     *
     * @return the {@link ProfileInfo} if the profileInfo was updated
     * null if the Account with the given id does not exist
     *
     * @throws IOException if underlying storage cannot be accessed
     */
    ProfileInfo updateProfileInfo(String account, ProfileInfo info) throws IOException;

    /**
     * Retrieve a {@linkplain AdminInfo} object that contains user demographic information for the admins
     *
     * @return the {@link AdminInfo} the information of the users
     *
     * @throws IOException if underlying storage cannot be accessed
     */
    AdminInfo getUserStats() throws IOException;
}
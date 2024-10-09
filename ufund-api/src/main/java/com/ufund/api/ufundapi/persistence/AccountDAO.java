package com.ufund.api.ufundapi.persistence;
import java.io.IOException;
import java.util.ArrayList;

import com.ufund.api.ufundapi.model.Account;
import com.ufund.api.ufundapi.model.Need;

public interface AccountDAO{

    /**
     * Retrieves all {@linkplain Need Needs}
     * 
     * @return An array of {@link Need Need} objects, may be empty
     * 
     * @throws IOException if an issue with underlying storage
     */
    ArrayList<Need> getNeeds() throws IOException;

     /**
     * Updates a {@linkplain Account account} with the given id
     * 
     * @param account {@link Account account} Account object 
     * 
     * @return true if the {@link Account account} was deleted
     * <br>
     * false if Account with the given id does not exist
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    boolean updateNeed(Account account) throws IOException;

    /**
     * Creates and saves a {@linkplain Account account}
     * 
     * @param Need {@linkplain Account account} object to be created and saved
     * <br>
     * The id of the Account object is ignored and a new uniqe id is assigned
     *
     * @return new {@link Account account} if successful, false otherwise 
     * 
     * @throws IOException if an issue with underlying storage
     */
    public Account createAccount(Account account) throws IOException;

}
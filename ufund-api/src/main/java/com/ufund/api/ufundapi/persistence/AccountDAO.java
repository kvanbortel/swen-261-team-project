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
     * Deletes a {@linkplain Need Need} with the given id
     * 
     * @param id The id of the {@link Need Need}
     * 
     * @return true if the {@link Need Need} was deleted
     * <br>
     * false if Need with the given id does not exist
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    boolean updateNeed(String id) throws IOException;

    public Account createAccount(Account account) throws IOException;

}
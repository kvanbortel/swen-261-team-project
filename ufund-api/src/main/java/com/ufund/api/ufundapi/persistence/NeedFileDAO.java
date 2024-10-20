package com.ufund.api.ufundapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;
import java.util.logging.Logger;

import org.apache.logging.log4j.util.PropertySource.Comparator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufund.api.ufundapi.model.Need;

/**
 * Implement the NeedDAO to store needs within a JSON database
 * Modified from the HeroFileDAO by RIT SWEN Faculty
 * 
 * {@literal @}Component Spring annotation instantiates a single instance of this
 * class and injects the instance into other classes as needed
 */
@Component
public class NeedFileDAO implements NeedDAO{
    // Use the logger from HERO for message handling? 
    private static final Logger LOG = Logger.getLogger(NeedFileDAO.class.getName());

    // Local cashe of needs
    Map<String, Need> needs;

    // Convert between jsons and java objects 
    private ObjectMapper objectMapper;

    // File name to read/write from
    private String filename;

    /**
     * Creates a Need File Data Access Object
     * 
     * @param filename Filename to read from and write to
     * @param objectMapper Provides JSON Object to/from Java Object serialization and deserialization
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    public NeedFileDAO(@Value("${needs.file}") String filename, ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();  // load the needs from the file
    }

    /**
     * Generates an array of {@linkplain Need needs} from the tree map
     * 
     * @return  The array of {@link Need needs}, may be empty
     */
    private Need[] getNeedsArray() {
        return getNeedsArray(null);
    }

    /**
     * Generates an array of {@linkplain Need needs} from the tree map for any
     * {@linkplain Need needs} that contains the text specified by containsText
     * <br>
     * If containsText is null, the array contains all of the {@linkplain Need needs}
     * in the tree map
     * 
     * @return  The array of {@link Need needs}, may be empty
     */
    private Need[] getNeedsArray(String containsText) { // if containsText == null, no filter
        ArrayList<Need> needArrayList = new ArrayList<>();

        for (Need need : needs.values()) {
            if (containsText == null || need.getName().toLowerCase().contains(containsText.toLowerCase())) {
                needArrayList.add(need);
            }
        }

        // reverse sort the needs by demand
        needArrayList.sort((n2, n1) -> Double.compare(n1.getDemandRating(), n2.getDemandRating()));

        Need[] needArray = new Need[needArrayList.size()];
        needArrayList.toArray(needArray);
        return needArray;
    }

    /**
     * Saves the {@linkplain Need needs} from the map into the file as an array of JSON objects
     * 
     * @return true if the {@link Need needs} were written successfully
     * 
     * @throws IOException when file cannot be accessed or written to
     */
    private boolean save() throws IOException {
        Need[] needsArray = getNeedsArray();

        // Serializes the Java Objects to JSON objects into the file
        // writeValue will thrown an IOException if there is an issue
        // with the file or reading from the file
        objectMapper.writeValue(new File(filename), needsArray);
        return true;
    }

        /**
     * Loads {@linkplain Need needs} from the JSON file into the map
     * <br>
     * 
     * @return true if the file was read successfully
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    private boolean load() throws IOException {
        needs = new TreeMap<>();

        // Deserializes the JSON objects from the file into an array of needs
        // readValue will throw an IOException if there's an issue with the file
        // or reading from the file
        Need[] needArray = objectMapper.readValue(new File(filename), Need[].class);

        // Add each need to the tree map
        for (Need need : needArray) {
            needs.put(need.getId(), need);
        }

        return true;
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Need[] getNeeds() throws IOException{
        synchronized(needs){
            return getNeedsArray();
        }
    }

    /**
    ** {@inheritDoc}
     */
    public Need[] searchNeeds(String containsText) {
        synchronized(needs){
            return getNeedsArray(containsText);
        }
    }

    /**
    ** {@inheritDoc}
     */
    public Need getNeed(String id) throws IOException{
        synchronized(needs) {
            if (needs.containsKey(id))
                return needs.get(id);
            else
                return null;
        }
    }

    /**
    ** {@inheritDoc}
     */
    public Need createNeed(Need need) throws IOException{
        if(need.getDemandRating() < 0 || need.getDemandRating() > 100){
            return null;
        }
        if(need.getQuantity() <= 0){
            return null;
        }
        if(need.getCost() < 0){
            return null;
        }
        // Create a new need object because the id field is immutable
        // and we need to assign a new unique id
        Need newNeed = new Need(UUID.randomUUID().toString(), need.getName(), need.getDescription(), need.getDemandRating(), need.getQuantity(), need.getCost());
        needs.put(newNeed.getId(),newNeed);
        save(); // may throw an IOException
        System.out.println(newNeed);
        return newNeed;
    }

    /**
    ** {@inheritDoc}
     */
    public Need updateNeed(Need need) throws IOException{
        synchronized(needs) {
            if (needs.containsKey(need.getId()) == false){
                return null;  // need does not exist
            }
            needs.put(need.getId(),need);
            save(); // may throw an IOException
            return need;
        }
    }

    /**
    ** {@inheritDoc}
     */
    public boolean deleteNeed(String id) throws IOException{
        synchronized(needs) {
            if (needs.containsKey(id)) {
                needs.remove(id);
                return save();
            }
            else {
                return false;
            }
        }
    }
}

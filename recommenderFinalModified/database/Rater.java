package database;
import java.util.*;
/**
 * Rater interface to create framework of raters
 * All implementing interface must override the listed method
 * @author Adil Shamim
 * @version september,2020 
 */
public interface Rater {

    public void addRating(String item, double rating);

    public boolean hasRating(String item);

    public String getID();

    public double getRating(String item);
    
    public int numRatings();
    
    public ArrayList<String> getItemsRated();
}

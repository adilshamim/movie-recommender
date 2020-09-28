package database;
import java.util.*;
/**
 * implementation of rater interface
 * efficient implementation using hashmap that maps movie with ratings
 * @author Adil Shamim
 * @version september,2020 
 */

public class EfficientRater implements Rater {
    private String myID;
    private HashMap<String,Rating> myRatings;//this maps movieId with ratings

    public EfficientRater(String id) {
        myID = id;
        myRatings = new HashMap<String,Rating>();
    }

    public void addRating(String item, double rating) {//item=movie id in string format
        myRatings.put(item,new Rating(item,rating));
    }

    public boolean hasRating(String item) {//item=movie id in string format
        Rating r=myRatings.get(item);
        if(r!=null)
        {
            return true; 
        }
        else
        {
            return false;
        }
    }

    public String getID() {
        return myID;
    }

    public double getRating(String item) {
        Rating r=myRatings.get(item);
        if(r!=null)
        {
            return r.getValue();
        }
        else
        {
            return -1;
        }
    }

    public int numRatings() {
        return myRatings.size();
    }

    public ArrayList<String> getItemsRated() {
        ArrayList<String> list = new ArrayList<String>();
        for(String movieID:myRatings.keySet()){
            list.add(myRatings.get(movieID).getItem());
        }

        return list;
    }

}

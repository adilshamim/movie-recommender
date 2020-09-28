package client;
import database.*;
import filter.*;
import java.util.*;
/**
 * class uses filter to search movie
 * this class has first Rating object to load movies
 * @author Adil Shamim 
 * @version september,2020
 */
public class ThirdRatings {
    private ArrayList<Rater> myRaters;

    public ThirdRatings() {
        // default constructor called
        this("ratings.csv");
        //this("ratedmovies_short.csv", "ratings_short.csv");
    }

    ThirdRatings(String ratingFile)
    {
        FirstRatings fr=new FirstRatings();
        myRaters=fr.loadRaters("data/"+ratingFile);
    }

    //this method find the no of Raters read

    public int getRaterSize(){
        return myRaters.size();    
    }

    //helper method that finds avg rating of a single movie

    public double getAverageByID(String movieID,int minimalRaters){
        double runsum=0.0;
        int numOfRaters=0;
        for(int k=0;k<myRaters.size();k++)
        {
            double currating=myRaters.get(k).getRating(movieID);
            if(currating!=-1)
            {
                runsum+=currating;
                numOfRaters++;
            }
        }

        if(numOfRaters>=minimalRaters)
        {
            return runsum/numOfRaters;
        }
        else{
            return 0.0;
        }
    }

    //this method finds avg rating of all movies which has some minimum of raters who rated this movie

    public ArrayList<Rating> getAverageRating(int minimalRaters)
    {
        ArrayList<Rating> ans=new ArrayList<>();//this a new syntax of defining arraylist
        ArrayList<String> movies=MovieDatabase.filterBy(new AllFilters());
        double sum=0.0;
        int numOfRaters=0;
        for(int k=0;k<movies.size();k++)
        {
            String moviID=movies.get(k);
            double curr=getAverageByID(moviID,minimalRaters);
            if(curr!=0.0)
            {
                ans.add(new Rating(moviID,curr));
            }
        }
        return ans;
    }
    
    public ArrayList<Rating> getAverageRatingByFilters(int minimalRaters,Filter filterCriteria)
    {
    ArrayList<Rating> ans=new ArrayList<>();
    ArrayList<String> movies=MovieDatabase.filterBy(filterCriteria);
        double sum=0.0;
        int numOfRaters=0;
        for(int k=0;k<movies.size();k++)
        {
            String moviID=movies.get(k);
            double curr=getAverageByID(moviID,minimalRaters);
            if(curr!=0.0)
            {
                ans.add(new Rating(moviID,curr));
            }
        }
        return ans;
    }

}

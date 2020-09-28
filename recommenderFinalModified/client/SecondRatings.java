package client;
import java.util.*;
import database.*;
/**
 * class to find average rating of movies
 * this class has first Rating object to load movies
 * @author Adil Shamim 
 * @version september,2020
 */


public class SecondRatings {
    private ArrayList<Movie> myMovies;
    private ArrayList<Rater> myRaters;

    public SecondRatings() {
        // default constructor called
        this("ratedmoviesfull.csv", "ratings.csv");
        //this("ratedmovies_short.csv", "ratings_short.csv");
    }

    SecondRatings(String movieFile,String ratingFile)
    {
        FirstRatings fr=new FirstRatings();
        myMovies=fr.loadMovies("data/"+movieFile);
        myRaters=fr.loadRaters("data/"+ratingFile);
    }

    //this method find the no of movies read

    public int getMovieSize(){
        return myMovies.size();    
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
        for(int k=0;k<myMovies.size();k++)
        {
            String movieID=myMovies.get(k).getID();
            double rating=getAverageByID(movieID,minimalRaters);
            if(rating!=0.0)
            {
                ans.add(new Rating(movieID,rating));
            }
        }
        return ans;
    }
    
    //this method returns the name of the movie with given movieID
    //overriding??or overloading
    public String getTitle(String movieID)
    {
        for(int k=0;k<myMovies.size();k++)
        {
            if(myMovies.get(k).getID().equals(movieID))
            {
            return myMovies.get(k).getTitle();
            }
        }
        return "";
    }
    
    //this method return id on giving name of movie
    
    public String getID(String title)
    {
         for(int k=0;k<myMovies.size();k++)
        {
            if(myMovies.get(k).getTitle().equalsIgnoreCase((title)))
            {
            return myMovies.get(k).getID();
            }
        }
        return "NO SUCH TITLE FOUND";
    
    }
}
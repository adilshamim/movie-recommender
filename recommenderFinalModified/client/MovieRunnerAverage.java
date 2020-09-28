package client;
import database.*;
import java.util.*;
/**
 * class to find average rating of all movies
 * this class has second rating object to get similar ratings
 * @author Adil Shamim 
 * @version september,2020
 */
public class MovieRunnerAverage {

    public void printAverageRatings()
    {
        //SecondRatings sr = new SecondRatings("ratedmovies_short.csv","ratings_short.csv");
        SecondRatings sr = new SecondRatings();
        System.out.println("Total Movie read "+sr.getMovieSize());
        System.out.println("Total Rater read "+sr.getRaterSize());
        ArrayList<Rating> rating=sr.getAverageRating(3);//parametr is minimal raters
        Collections.sort(rating);
        for(int k=0;k<rating.size();k++)
        {
            System.out.println(String.format("%.2f",rating.get(k).getValue())+"\t"+sr.getTitle(rating.get(k).getItem()));
        }
    }

    public void getAverageRatingOneMovie()
    {
        SecondRatings sr = new SecondRatings("ratedmovies_short.csv","ratings_short.csv");
        String title="the godfather";
        String id=sr.getID(title);
        if(!"NO SUCH TITLE FOUND".equals(id))
        {
            double rating=sr.getAverageByID(id,1);
            System.out.println("Average rating of movie: "+title+" :"+rating);
        }
    }

}

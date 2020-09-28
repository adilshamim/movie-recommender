package client;
import database.*;
import filter.*;
import java.util.*;
/**
 * class to find average rating of movies based on different filters
 * this class has fourth Rating object to load movies
 * @author Adil Shamim 
 * @version september,2020
 */

public class MovieRunnerSimilarRatings {

    public void printSimilarRatings()
    {  
        FourthRating fr=new FourthRating();
        RaterDatabase.initialize("ratings.csv");
        System.out.println("Total Rater read "+RaterDatabase.size());
        //MovieDatabase.initialize("ratedmovies_short.csv");
        MovieDatabase.initialize("ratedmoviesfull.csv");
        System.out.println("Total Movies read "+MovieDatabase.size());
        //ArrayList<Rating> rating=fr.getSimilarRatings("337",10,3);//parametr is rater_id(reference taste),no. of top raters similar to rater_id,minimal raters
        ArrayList<Rating> rating=fr.getSimilarRatings("71",20,5);
        System.out.println("found "+rating.size()+" movies");
        //Collections.sort(rating);
        for(int k=0;k<rating.size();k++)
        {
            System.out.println(String.format("%.2f",rating.get(k).getValue())+"\t"+MovieDatabase.getTitle(rating.get(k).getItem()));
        }  

    }

    public void printSimilarRatingsByGenre()
    {
        FourthRating fr=new FourthRating();
        //RaterDatabase.initialize("ratings_short.csv");
        RaterDatabase.initialize("ratings.csv");
        System.out.println("Total Rater read "+RaterDatabase.size());
        //MovieDatabase.initialize("ratedmovies_short.csv");
        MovieDatabase.initialize("ratedmoviesfull.csv");
        System.out.println("Total Movies read "+MovieDatabase.size());
        ArrayList<Rating> rating=fr.getSimilarRatingsByFilter("964",20 ,5,new GenreFilter("Action"));//parametr is rater_id(reference taste),no. of top raters similar to rater_id,minimal raters
        System.out.println("found "+rating.size()+" movies");
        //Collections.sort(rating);
        for(int k=0;k<rating.size();k++)
        {
            System.out.println(String.format("%.2f",rating.get(k).getValue())+"\t"+MovieDatabase.getTitle(rating.get(k).getItem()));
        }
    }

    public void printSimilarRatingsByDirector()
    {
        FourthRating fr=new FourthRating();
        //RaterDatabase.initialize("ratings_short.csv");
        RaterDatabase.initialize("ratings.csv");
        System.out.println("Total Rater read "+RaterDatabase.size());
        //MovieDatabase.initialize("ratedmovies_short.csv");
        MovieDatabase.initialize("ratedmoviesfull.csv");
        System.out.println("Total Movies read "+MovieDatabase.size());
        ArrayList<Rating> rating=fr.getSimilarRatingsByFilter("120",10,2,new DirectorsFilter("Clint Eastwood,J.J. Abrams,Alfred Hitchcock,Sydney Pollack,David Cronenberg,Oliver Stone,Mike Leigh"));//parametr is rater_id(reference taste),no. of top raters similar to rater_id,minimal raters
        System.out.println("found "+rating.size()+" movies");
        //Collections.sort(rating);
        for(int k=0;k<rating.size();k++)
        {
            System.out.println(String.format("%.2f",rating.get(k).getValue())+"\t"+MovieDatabase.getTitle(rating.get(k).getItem()));
        }
    }

    public void printAverageRatingByGenreAndMinutes()
    {
        FourthRating fr=new FourthRating();
        //RaterDatabase.initialize("ratings_short.csv");
        RaterDatabase.initialize("ratings.csv");
        System.out.println("Total Rater read "+RaterDatabase.size());
        //MovieDatabase.initialize("ratedmovies_short.csv");
        MovieDatabase.initialize("ratedmoviesfull.csv");
        System.out.println("Total Movies read "+MovieDatabase.size());
        AllFilters af= new AllFilters();
        af.addFilter(new GenreFilter("Drama"));
        af.addFilter(new MinutesFilter(80,160) );
        ArrayList<Rating> rating=fr.getSimilarRatingsByFilter("168", 10, 3, af);//passing allFilters object
        System.out.println("found "+rating.size()+" movies");
        for(int k=0;k<rating.size();k++)
        {
            String id=rating.get(k).getItem();
            System.out.print(String.format("%.2f",rating.get(k).getValue())+"\t"+MovieDatabase.getYear(id));//rating&&title
            System.out.println("  "+MovieDatabase.getTitle(id));
            System.out.println(" \t{"+MovieDatabase.getGenres(id)+"}");
        }
    }

    public void printAverageRatingByYearAfterAndMinutes()
    {
        FourthRating fr=new FourthRating();
        //RaterDatabase.initialize("ratings_short.csv");
        RaterDatabase.initialize("ratings.csv");
        System.out.println("Total Rater read "+RaterDatabase.size());
        //MovieDatabase.initialize("ratedmovies_short.csv");
        MovieDatabase.initialize("ratedmoviesfull.csv");
        System.out.println("Total Movies read "+MovieDatabase.size());
        AllFilters af= new AllFilters();
        af.addFilter(new YearAfterFilter(1975));
        af.addFilter(new MinutesFilter(70,200) );
        ArrayList<Rating> rating=fr.getSimilarRatingsByFilter("314", 10, 5, af);//passing allFilters object
        System.out.println("found "+rating.size()+" movies");
        for(int k=0;k<rating.size();k++)
        {
            String id=rating.get(k).getItem();
            System.out.print(String.format("%.2f",rating.get(k).getValue())+"\t"+MovieDatabase.getYear(id));//rating&&title
            System.out.println("  "+MovieDatabase.getTitle(id));
            System.out.println(" \t{"+MovieDatabase.getGenres(id)+"}");
        }
    }
}

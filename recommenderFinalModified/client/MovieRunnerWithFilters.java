package client;

 
import database.*;
import filter.*;
import java.util.*;
/**
 * Write a description of MovieRunnerWithFilters here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MovieRunnerWithFilters {
    public void printAverageRatings()
    {
        //ThirdRatings tr = new ThirdRatings("ratings_short.csv");
        ThirdRatings tr = new ThirdRatings("ratings_short.csv");
        System.out.println("Total Rater read "+tr.getRaterSize());
        MovieDatabase.initialize("ratedmovies_short.csv");
        System.out.println("Total Movies read "+MovieDatabase.size());
        ArrayList<Rating> rating=tr.getAverageRating(1);//parametr is minimal raters
        System.out.println("found "+rating.size()+" movies");
        Collections.sort(rating);
        for(int k=0;k<rating.size();k++)
        {
            System.out.println(String.format("%.2f",rating.get(k).getValue())+"\t"+MovieDatabase.getTitle(rating.get(k).getItem()));
        }
    }

    public void printAverageRatingByYear()
    {
        ThirdRatings tr = new ThirdRatings("ratings_short.csv");
        System.out.println("Total Rater read "+tr.getRaterSize());
        MovieDatabase.initialize("ratedmovies_short.csv");
        System.out.println("Total Movies read "+MovieDatabase.size());
        ArrayList<Rating> rating=tr.getAverageRatingByFilters(1,new YearAfterFilter(2000));//parametr is minimal raters
        System.out.println("found "+rating.size()+" movies");
        Collections.sort(rating);
        for(int k=0;k<rating.size();k++)
        {
            System.out.println(String.format("%.2f",rating.get(k).getValue())+"\t"+MovieDatabase.getTitle(rating.get(k).getItem()));
        }

    }

    public void printAverageRatingByGenre()
    {
        ThirdRatings tr = new ThirdRatings("ratings_short.csv");
        System.out.println("Total Rater read "+tr.getRaterSize());
        MovieDatabase.initialize("ratedmovies_short.csv");
        System.out.println("Total Movies read "+MovieDatabase.size());
        ArrayList<Rating> rating=tr.getAverageRatingByFilters(1,new GenreFilter("Crime"));//parametr is minimal raters
        System.out.println("found "+rating.size()+" movies");
        Collections.sort(rating);
        for(int k=0;k<rating.size();k++)
        {
            System.out.println(String.format("%.2f",rating.get(k).getValue())+"\t"+MovieDatabase.getTitle(rating.get(k).getItem()));
            System.out.println(" \t{"+MovieDatabase.getGenres(rating.get(k).getItem())+"}");
        }

    }

    public void printAverageRatingByMinutes()
    {
        ThirdRatings tr = new ThirdRatings("ratings_short.csv");
        System.out.println("Total Rater read "+tr.getRaterSize());
        MovieDatabase.initialize("ratedmovies_short.csv");
        System.out.println("Total Movies read "+MovieDatabase.size());
        ArrayList<Rating> rating=tr.getAverageRatingByFilters(1,new MinutesFilter(110,170));//parametr is minimal raters
        System.out.println("found "+rating.size()+" movies");
        Collections.sort(rating);
        for(int k=0;k<rating.size();k++)
        {
            System.out.print(String.format("%.2f",rating.get(k).getValue())+"  Running Time:"+MovieDatabase.getMinutes(rating.get(k).getItem()));
            System.out.println(" minutes ("+MovieDatabase.getTitle(rating.get(k).getItem())+")");
        }

    }

    public void printAverageRatingByDirector()
    {
        ThirdRatings tr = new ThirdRatings("ratings_short.csv");
        System.out.println("Total Rater read "+tr.getRaterSize());
        MovieDatabase.initialize("ratedmovies_short.csv");
        System.out.println("Total Movies read "+MovieDatabase.size());
        ArrayList<Rating> rating=tr.getAverageRatingByFilters(1,new DirectorsFilter("Charles Chaplin,Michael Mann,Spike Jonze"));//parametr is minimal raters
        System.out.println("found "+rating.size()+" movies");
        Collections.sort(rating);
        for(int k=0;k<rating.size();k++)
        {
            System.out.println(String.format("%.2f",rating.get(k).getValue())+"\t"+MovieDatabase.getTitle(rating.get(k).getItem()));//rating&&title
            System.out.println(" \t{"+MovieDatabase.getDirector(rating.get(k).getItem())+"}");
        }

    }

    public void printAverageRatingByYearsAfterAndGenre()
    {
        ThirdRatings tr = new ThirdRatings("ratings.csv");
        System.out.println("Total Rater read "+tr.getRaterSize());
        MovieDatabase.initialize("ratedmoviesfull.csv");
        System.out.println("Total Movies read "+MovieDatabase.size());
        AllFilters af= new AllFilters();
        af.addFilter(new YearAfterFilter(1980) );
        af.addFilter(new GenreFilter("Comedy"));
        ArrayList<Rating> rating=tr.getAverageRatingByFilters(1,af);//passing allFilters object
        System.out.println("found "+rating.size()+" movies");
        Collections.sort(rating,Collections.reverseOrder());
        for(int k=0;k<rating.size();k++)
        {
            String id=rating.get(k).getItem();
            System.out.print(String.format("%.2f",rating.get(k).getValue())+"\t"+MovieDatabase.getYear(id));//rating&&title
            System.out.println("  "+MovieDatabase.getTitle(id));
            System.out.println(" \t{"+MovieDatabase.getGenres(id)+"}");
        }
    }
    
    
    public void printAverageRatingBydirectorAndMinutes()
    {
        ThirdRatings tr = new ThirdRatings("ratings_short.csv");
        System.out.println("Total Rater read "+tr.getRaterSize());
        MovieDatabase.initialize("ratedmovies_short.csv");
        System.out.println("Total Movies read "+MovieDatabase.size());
        AllFilters af= new AllFilters();
        af.addFilter(new MinutesFilter(30,170));
        af.addFilter(new DirectorsFilter("Charles Chaplin,Michael Mann,Spike Jonze,Francis Ford Coppola"));
        ArrayList<Rating> rating=tr.getAverageRatingByFilters(1,af);//passing allFilters object
        System.out.println("found "+rating.size()+" movies");
        Collections.sort(rating);
        for(int k=0;k<rating.size();k++)
        {
            String id=rating.get(k).getItem();
            System.out.print(String.format("%.2f",rating.get(k).getValue())+" Running Time: "+MovieDatabase.getMinutes(id));//rating&&title
            System.out.println(" minutes ("+MovieDatabase.getTitle(id)+")");
            System.out.println(" \t{"+MovieDatabase.getDirector(id)+"}");
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

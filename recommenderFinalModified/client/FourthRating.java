package client;
import database.*;
import filter.*;
import java.util.*;
/**
 * class to find average rating of movies after applying filters
 * this class uses raterDatabase and MovieDatabase to load raters and movies
 * @author Adil Shamim 
 * @version september,2020
 */

public class FourthRating  {

    //helper method that finds avg rating of a single movie
    public double getAverageByID(String movieID,int minimalRaters){
        double runsum=0.0;
        int numOfRaters=0;
        ArrayList<Rater> list=RaterDatabase.getRaters();
        for(int k=0;k<list.size();k++)
        {
            double currating=list.get(k).getRating(movieID);
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
        ArrayList<String> movies=MovieDatabase.filterBy(new TrueFilter());
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

    //helper method that finds the closeness by dotProduct method if product is negative then other is not close to me
    //else if product is positive then other is close to me
    private double dotProduct(Rater me,Rater other) {
        double p=0;
        //int count=Math.min(me.numRatings(), other.numRatings());//this approach failed instead now i search all the ratings done by 'me' rater object
        ArrayList<String> myRatedMovieID=me.getItemsRated();//here string in arraylist represents movie id
        for(int k=0;k<myRatedMovieID.size();k++)
        {
            String myMovieID=myRatedMovieID.get(k);
            if(other.hasRating(myMovieID))//agar mera movie id dusra ka paas hai to 
            {
                double myrating=me.getRating(myMovieID)-5;//mera rating lo usme 5 ghatao
                double otherrating=other.getRating(myMovieID)-5;//dusra ka wahi movie ka rating lo aur 5 ghatao
                p=p+(myrating*otherrating);//uske baad dot product kar do
            }
        }
        return p;
    }

    private ArrayList<Rating> getSimilarities(String myRaterID)//this is the rater id jiske saath baki raters ka closeness nikalna hai
    {
        ArrayList<Rating> ans= new ArrayList<Rating>();
        ArrayList<Rater> ratersList=RaterDatabase.getRaters();
        Rater myRater=RaterDatabase.getRater(myRaterID);
        for(int k=0;k<ratersList.size();k++)
        {
            Rater curRater=ratersList.get(k);//current rater
            if(!curRater.getID().equals(myRaterID))
            {
                double product=dotProduct(myRater,curRater);
                if(product>0.0)
                {
                    //System.out.println(product);
                    ans.add(new Rating(curRater.getID(),product));//new rating object craeted with rater id & raters closeness value with me
                }
                Collections.sort(ans,Collections.reverseOrder());//i wanted to sort in descending order
            }
        }
        return ans;
    }

    public ArrayList<Rating> getSimilarRatings(String rater_id,int numSimilarRaters,int minimalRaters){
        ArrayList<Rating> ans= new ArrayList<Rating>();
        ArrayList<Rating> close=getSimilarities(rater_id);//sorted in descending order i.e top raters closer to me are at the beginning
        System.out.println("Number of close raters "+close.size());
        //ArrayList<String> movies=MovieDatabase.filterBy(new TrueFilter());//here string in array is movieid
        for(String movieID:MovieDatabase.filterBy(new TrueFilter()))//for each movie
        {
            int ratersOfcurrentMovie=0;
            double weightsum=0.0;
            try{
            for(int i=0;i<numSimilarRaters;i++)//consider only the ratings of top 20 raters close to me
            {
                
                Rating cuRaterRating=close.get(i);
                String  curRaterID=cuRaterRating.getItem();
                Rater curRater=RaterDatabase.getRater(curRaterID);
                if(curRater.hasRating(movieID))
                {
                    ratersOfcurrentMovie++;
                    double weight=cuRaterRating.getValue();//it is raters closeness or weight
                    double ratingVal=curRater.getRating(movieID);//first i try the below way but later i found that this also works!!
                    //double ratingVal=RaterDatabase.getRater(cuRaterRating.getItem()).getRating(movieID);//this search all raters with raterid of current weight wala then 
                    // for that rater i find the value of rating for the curr movie
                    weightsum+=(weight*ratingVal);
                }

            }
            
        }
        catch(Exception e)
            {
            System.out.println("No movies found");
            break;
            }
    
        
            //System.out.println(movieID+" raters no. "+ratersOfcurrentMovie);
            if(ratersOfcurrentMovie>=minimalRaters)//if no. of raters in top 20(actually who rated this movie)>=minimum ratings to consider
            {
                ans.add(new Rating(movieID,weightsum/ratersOfcurrentMovie));//then include this movie
            }

        }
        Collections.sort(ans,Collections.reverseOrder());
        return ans;
    }

    public ArrayList<Rating> getSimilarRatingsByFilter(String rater_id,int numSimilarRaters,int minimalRaters,Filter filterCriteria){
        ArrayList<Rating> ans= new ArrayList<Rating>();
        ArrayList<Rating> close=getSimilarities(rater_id);//sorted in descending order i.e top raters closer to me are at the beginning
        ArrayList<String> movies=MovieDatabase.filterBy(filterCriteria);//here string in array is movieid
        for(String movieID:movies)
        {
            int ratersOfcurrentMovie=0;
            double weightsum=0.0;
            for(int i=0;i<numSimilarRaters;i++)//consider only the ratings of top 20 raters close to me
            {
                Rating cuRaterRating=close.get(i);
                String  curRaterID=cuRaterRating.getItem();
                Rater curRater=RaterDatabase.getRater(curRaterID);
                if(curRater.hasRating(movieID))
                {
                    ratersOfcurrentMovie++;
                    double weight=cuRaterRating.getValue();//it is raters closeness or weight
                    double ratingVal=curRater.getRating(movieID);//first i try the below way but later i found that this also works!!
                    //double ratingVal=RaterDatabase.getRater(cuRaterRating.getItem()).getRating(movieID);//this search all raters with raterid of current weight wala then 
                    // for that rater i find the value of rating for the curr movie
                    weightsum+=(weight*ratingVal);
                }

            }
            //System.out.println(movieID+" raters no. "+ratersOfcurrentMovie);
            if(ratersOfcurrentMovie>=minimalRaters)//if no. of raters in top 20(actually who rated this movie)>=minimum ratings to consider
            {
                ans.add(new Rating(movieID,weightsum/ratersOfcurrentMovie));//then include this movie
            }

        }
        Collections.sort(ans,Collections.reverseOrder()); 
        return ans;
    }
}

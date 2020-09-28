package client;
import database.*;
import edu.duke.*;
import java.util.*;
import org.apache.commons.csv.*;
/**
 * 
 * this code analyses the movie data and apply various filters
 * @author Adil Shamim 
 * @version september,2020
 */
public class FirstRatings {

    //this method reads the csv file and create list of movie object
    public ArrayList<Movie> loadMovies(String fileName)//fileName is csv file that contains movie
    {
        ArrayList<Movie> moviesList= new ArrayList<Movie>();
        FileResource fr=new FileResource(fileName);
        CSVParser parser=fr.getCSVParser();
        for(CSVRecord records:parser){
            String id=records.get(0);
            String title=records.get(1);
            String year=records.get(2);
            String country=records.get(3);
            String genres=records.get(4);
            String director=records.get(5);
            int minutes=Integer.parseInt(records.get(6));
            String poster=records.get(7);
            Movie mv=new Movie(id, title, year, genres, director,
                    country, poster, minutes);
            moviesList.add(mv);
        }
        return moviesList;
    }

    public ArrayList<Rater> loadRaters(String fileName)
    {
        ArrayList<Rater> raterList= new ArrayList<Rater>();
        FileResource fr=new FileResource(fileName);
        CSVParser parser=fr.getCSVParser();
        ArrayList<String> temp=new ArrayList<String>();
        Rater uniqueRater=null;
        for(CSVRecord records:parser){
            String id=records.get(0);
            if(!temp.contains(id))
            {
                temp.add(id);
                uniqueRater=new EfficientRater(id);
                raterList.add(uniqueRater);
                uniqueRater.addRating(records.get(1), Double.parseDouble(records.get(2)));
            }
            else{
                uniqueRater.addRating(records.get(1), Double.parseDouble(records.get(2)));       
            }

        }
        return raterList;
    }

    public void testLoadMovies()
    {
        //String fileName="data/ratedmovies_short.csv";
        String fileName="data/ratedmoviesfull.csv";
        ArrayList<Movie> list=loadMovies(fileName);
        System.out.println("Number of movies in the list: "+list.size());
        /*for(Movie mv:list)
        {
        System.out.println(mv);
        }*/
    }

    public void testLoadRaters()
    {
        String fileName="data/ratings_short.csv";
        //String fileName="data/ratings.csv";
        ArrayList<Rater> list=loadRaters(fileName);
        System.out.println("Number of unique raters in the list: "+list.size());
       for(Rater r:list)
        {
        System.out.println("Rater id is "+r.getID()+" and number of movies rated by him/her: "+r.numRatings());
        System.out.println("Movie Id\tMovie Rating");
        ArrayList<String> ratingList=r.getItemsRated();//this return list(imdb no) of movies rated by this rater
        for(int k=0;k<ratingList.size();k++)
        {
        String itemRated=ratingList.get(k);
        System.out.println(itemRated+"\t\t"+r.getRating(itemRated));
        }
        }
    }

    public int howManyOfGenre(String genre)
    {
        int count=0;
        //String fileName="data/ratedmovies_short.csv";
        String fileName="data/ratedmoviesfull.csv";
        ArrayList<Movie> list=loadMovies(fileName);//calling loadMovies to load movie in our program
        for(Movie mv:list)
        {
            String currgenre=mv.getGenres();
            genre=genre.toLowerCase();
            currgenre=currgenre.toLowerCase();
            if(currgenre.contains(genre))
            {
                count++;
            }
        }
        return count;
    }

    public int howManyOfLength(int lengthAboveThis)
    {
        int count=0;
        //String fileName="data/ratedmovies_short.csv";
        String fileName="data/ratedmoviesfull.csv";
        ArrayList<Movie> list=loadMovies(fileName);//calling loadMovies to load movie in our program
        for(Movie mv:list)
        {
            int currlength=mv.getMinutes();
            if(currlength>lengthAboveThis)
            {
                count++;
            }
        }
        return count;
    }

    //this code filters movie object with director who has directed max movie and no. of directors of max movie 

    public void directorFilter()
    {
        HashMap<String,Integer> map=new HashMap<String,Integer>();
        HashMap<String,Integer> map2=new HashMap<String,Integer>();
        //String fileName="data/ratedmovies_short.csv";
        String fileName="data/ratedmoviesfull.csv";
        ArrayList<Movie> list=loadMovies(fileName);//calling loadMovies to load movie in our program
        //this method creates the map of directors with no. of film he directed
        for(Movie mv:list)
        {
            String[] directors=mv.getDirector().split(",");
            for(int k=0;k<directors.length;k++){
                
                if(!map.containsKey(directors[k]))
                {
                    map.put(directors[k],1);
                }
                else
                {
                    map.put(directors[k],map.get(directors[k])+1);
                }
            }
        }
        int Max=-1;
        for(String dir:map.keySet()){
            int currCount=map.get(dir);
            if(currCount>Max){
                Max=currCount;
            }
        }
        System.out.println("Maximum movie directed by any director: "+Max);
        int count=0;
        String director="[";
        //this loop find the no of directors of x number of movie
        for(String dir:map.keySet()){
            int curCount=map.get(dir);
            if(curCount==Max){
                count++;
                director+=dir+"\n";
            }
        }
        System.out.println("and number of such Directors: "+count+" and directors are "+director+"]");
        //System.out.println("number of  Directors With 1 movie: "+count);
        
    }

    //this method returns the no. of rating done by a particular rater

    public int numRatings(String rater_id)
    {

        //String fileName="data/ratings_short.csv";
        String fileName="data/ratings.csv";
        ArrayList<Rater> list=loadRaters(fileName);
        for(Rater r:list)
        {
            if(r.getID().equals(rater_id))
            {
                return r.numRatings();
            }
        }

        return 0;
    }

    //this method finds the count of rater who has rated a particular movie
    public int numRatersMovie(String movieID){
        int count=0;
        //String fileName="data/ratings_short.csv";
        String fileName="data/ratings.csv";
        ArrayList<Rater> list=loadRaters(fileName);
        for(Rater r:list)
        {
            if(r.hasRating(movieID))
            {
                count++;
            }
        }

        return count;
    }

    //this method finds the total unique movies rated by all raters
    public int totalUniqueMoviesRated(){
        //String fileName="data/ratings_short.csv";
        String fileName="data/ratings.csv";
        ArrayList<String> count= new ArrayList<String>();//to count unique movies rated by all raters(size will give the ans)
        ArrayList<Rater> list=loadRaters(fileName);
        for(Rater r:list)
        {
            ArrayList<String> ratedMovieId=r.getItemsRated();//this method returns movieID in string format that is rated by current rater
            for(int k=0;k<ratedMovieId.size();k++){
            if(!count.contains(ratedMovieId.get(k)))
            {
            count.add(ratedMovieId.get(k));
            }
            
            }
           
        }

        return count.size();
    }

    //this method is logically same as directorfilter

    public void raterFilter()
    {
        HashMap<String,Integer> map=new HashMap<String,Integer>();
        //String fileName="data/ratings_short.csv";
        String fileName="data/ratings.csv";
        ArrayList<Rater> list=loadRaters(fileName);
        //this method creates the map of raters with no. of ratings he did
        for(Rater r:list)
        {
            map.put(r.getID(),r.numRatings());
        }
        int Max=-1;
        for(String id:map.keySet()){
            int currCount=map.get(id);
            if(currCount>Max){
                Max=currCount;
            }
        }
        System.out.println("Maximum movie rated by any rater: "+Max);
        int count=0;
        String idOfMaxRater="[ ";
        for(String id:map.keySet()){
            int currmaxRating=map.get(id);
            if(currmaxRating==Max){//this checks the key value is equal to Max or not
                count++;
                idOfMaxRater+=id+" ";
            }
        }
        System.out.println("and number of such Raters are "+count+" and their id's are "+idOfMaxRater+" ]");
    }

}

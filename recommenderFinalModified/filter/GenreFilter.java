package filter;
import database.*;
/**
 * Movie is filtered based on Genre of the movie
 * return true only if movie genre contains atleast one particular genre
 * @author Adil Shamim
 * @version september,2020 
 */
public class GenreFilter implements Filter {
    
    String genre;
    
    public GenreFilter(String genre){
    this.genre=genre;
    }
    
    public boolean satisfies(String movieID){
    return MovieDatabase.getGenres(movieID).contains(genre);
    }

}

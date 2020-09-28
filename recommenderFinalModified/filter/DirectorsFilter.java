package filter;
import database.*;
/**
 * Movie is filtered based on director who directed movie
 * return true only if movie is directed by atleast one particular director
 * @author Adil Shamim
 * @version september,2020 
 */
public class DirectorsFilter implements Filter {
    String[] director;
    String[] s;
    public DirectorsFilter(String director){
        this.director=director.split("\\s*,");
    }

    public boolean satisfies(String movieID){
        String s=MovieDatabase.getDirector(movieID).trim();
        for(String dir:director)
        {
            if(s.contains(dir))
            {
                return true;
            }
        }
        return false;
    }
}

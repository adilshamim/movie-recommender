package filter;
import database.*;
/**
 * Movie is filtered based on duration of the movie
 * return true only if movie duration is below and above certain threshold duration
 * @author Adil Shamim
 * @version september,2020 
 */

public class MinutesFilter implements Filter {
      private int minLength,MaxLength;
	
	public MinutesFilter(int minLength,int MaxLength) {
		this.minLength=minLength;
		this.MaxLength=MaxLength;
	}
	
	@Override
	public boolean satisfies(String id) {
	        int min=MovieDatabase.getMinutes(id);
		return minLength<=min&&min<=MaxLength;
	}
}

package filter;
import database.*;
/**
 * Movie is filtered based on year after which movie released
 * return true only if movie is released after a particular given year
 * @author Adil Shamim
 * @version september,2020 
 */
public class YearAfterFilter implements Filter {
	private int myYear;
	
	public YearAfterFilter(int year) {
		myYear = year;
	}
	
	@Override
	public boolean satisfies(String id) {
		return MovieDatabase.getYear(id) >= myYear;
	}

}

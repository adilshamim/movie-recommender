package filter;
/**
 * Movie is filter based on not any codition
 * return true for all movie
 * @author Adil Shamim
 * @version september,2020 
 */
public class TrueFilter implements Filter {
	@Override
	public boolean satisfies(String id) {
		return true;
	}

}

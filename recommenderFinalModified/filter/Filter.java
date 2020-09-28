package filter;
/**
 * filter interface all filters must implement this satisfies method
 * returns true only if codition on movie is satisfied
 * @author Adil Shamim
 * @version september,2020 
 */ 
public interface Filter {
	public boolean satisfies(String id);
}

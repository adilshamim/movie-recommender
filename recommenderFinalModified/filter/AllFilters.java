package filter;
import java.util.ArrayList;

/**
 * Multiple filters are checked for condition
 * return true only if all the filter satisfies the condition
 * @author Adil Shamim
 * @version september,2020 
 */


public class AllFilters implements Filter {
    ArrayList<Filter> filters;
    
    public AllFilters() {
        filters = new ArrayList<Filter>();
    }

    public void addFilter(Filter f) {
        filters.add(f);
    }

    @Override
    public boolean satisfies(String id) {
        for(Filter f : filters) {
            if (! f.satisfies(id)) {//if any 1 of the filter doesnt satisfy then return false
                return false;
            }
        }
        
        return true;
    }

}

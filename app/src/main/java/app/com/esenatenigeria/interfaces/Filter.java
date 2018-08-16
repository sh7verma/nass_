package app.com.esenatenigeria.interfaces;

/**
 * Created by dev on 7/5/18.
 */

public class Filter {

    public interface BillsFilterApplied {
        void onFilterApplied();
    }

    public interface ActsFilterApplied {
        void onFilterApplied();
    }
}

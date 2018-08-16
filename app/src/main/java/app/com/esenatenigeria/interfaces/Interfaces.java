package app.com.esenatenigeria.interfaces;

import app.com.esenatenigeria.model.ActsModel;
import app.com.esenatenigeria.model.BillsModel;

/**
 * Created by dev on 19/4/18.
 */

public class Interfaces {

    public interface AdapterItemClick {
        void onItemClick(int position);
    }

    public interface BillsAdapterItemClick {
        void onBillsItemClick(int position, BillsModel.DataBean model);
    }
    public interface ActsAdapterItemClick {
        void onActsItemClick(int position, ActsModel.DataBean model);
    }

    public interface FragmentClick {
        void onFragItemClick(int i);
    }
    public interface DialogClick {
        void onDialogClick(String edName, String edEmail, String edText);
    }

    public interface DocumentDeleted{
        void onDocumentDeleted(String category);
    }
}

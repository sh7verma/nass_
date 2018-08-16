package app.com.esenatenigeria.database;

import java.util.ArrayList;

/**
 * Created by app on 2/27/2017.
 */
public class DataHolder {
    static DataHolder mHolder;

    public static DataHolder getDataHolder() {
        if (mHolder == null) {
            mHolder = new DataHolder();
        }
        return mHolder;
    }

    ArrayList<String> contactNames, contactNumbers;

    public ArrayList<String> getContactNames() {
        if (contactNames == null) {
            contactNames = new ArrayList<>();
        }
        return contactNames;
    }

    public void setContactNames(ArrayList<String> contactNames) {
        this.contactNames = contactNames;
    }

    public ArrayList<String> getContactNumbers() {
        if (contactNumbers == null) {
            contactNumbers = new ArrayList<>();
        }
        return contactNumbers;
    }

    public void setContactNumbers(ArrayList<String> contactNumbers) {
        this.contactNumbers = contactNumbers;
    }

    public String getName(String number) {
        String name = "";
        if (DataHolder.getDataHolder().getContactNumbers().contains(number)) {
            int pos = DataHolder.getDataHolder().getContactNumbers().indexOf(number);
            name = DataHolder.getDataHolder().getContactNames().get(pos);
        } else {
            name = number;
        }
        return name;
    }
}

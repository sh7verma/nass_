package app.com.esenatenigeria.model;

import java.util.Date;

import app.com.esenatenigeria.utils.Consts;

/**
 * Created by dev on 4/5/18.
 */

public class FilterSingletonModel {

    private static FilterSingletonModel mInstance;
    //acts filter
    private String actsDateFrom;
    private String actsDateTo;
    private Date actsFromDate;
    private Date actsToDate;
    private int actsParliamentNo;
    private int actSessionNo;
    private String actsParliament;
    private String actsSession;
    private boolean actFilterApplied;


    //bills filter
    private String billsDateFrom;
    private String billsDateTo;
    private Date billsFromDate;
    private Date billsToDate;
    private int billsParliamentNo;
    private int billsSessionNo;
    private String billsParliament;
    private String billsSession;
    private boolean billsFilterApplied;

    private FilterSingletonModel() {
    }

    public static FilterSingletonModel getInstance() {
        if (mInstance == null) {
            mInstance = new FilterSingletonModel();
        }
        return mInstance;
    }

    public String getBillsDateFrom() {
        return billsDateFrom;
    }

    public void setBillsDateFrom(String billsDateFrom) {
        this.billsDateFrom = billsDateFrom;
    }

    public String getBillsDateTo() {
        return billsDateTo;
    }

    public void setBillsDateTo(String billsDateTo) {
        this.billsDateTo = billsDateTo;
    }

    public int getBillsParliamentNo() {
        return billsParliamentNo;
    }

    public void setBillsParliamentNo(int billsParliamentNo) {
        this.billsParliamentNo = billsParliamentNo;
    }

    public int getBillsSessionNo() {
        return billsSessionNo;
    }

    public void setBillsSessionNo(int billsSessionNo) {
        this.billsSessionNo = billsSessionNo;
    }

    public int getActsParliamentNo() {
        return actsParliamentNo;
    }

    public void setActsParliamentNo(int actsParliamentNo) {
        this.actsParliamentNo = actsParliamentNo;
    }

    public String getActsDateFrom() {
        return actsDateFrom;
    }

    public void setActsDateFrom(String actsDateFrom) {
        this.actsDateFrom = actsDateFrom;
    }

    public String getActsDateTo() {
        return actsDateTo;
    }

    public void setActsDateTo(String actsDateTo) {
        this.actsDateTo = actsDateTo;
    }


    public int getActSessionNo() {
        return actSessionNo;
    }

    public void setActSessionNo(int actSessionNo) {
        this.actSessionNo = actSessionNo;
    }


    public String getActsParliament() {
        return actsParliament;
    }

    public void setActsParliament(String actsParliament) {
        this.actsParliament = actsParliament;
    }

    public String getActsSession() {
        return actsSession;
    }

    public void setActsSession(String actsSession) {
        this.actsSession = actsSession;
    }

    public String getBillsParliament() {
        return billsParliament;
    }

    public void setBillsParliament(String billsParliament) {
        this.billsParliament = billsParliament;
    }

    public String getBillsSession() {
        return billsSession;
    }

    public void setBillsSession(String billsSession) {
        this.billsSession = billsSession;
    }

    public boolean isActFilterApplied() {
        return actFilterApplied;
    }

    public void setActFilterApplied(boolean actFilterApplied) {
        this.actFilterApplied = actFilterApplied;
    }

    public boolean isBillsFilterApplied() {
        return billsFilterApplied;
    }

    public void setBillsFilterApplied(boolean billsFilterApplied) {
        this.billsFilterApplied = billsFilterApplied;
    }

    public Date getActsFromDate() {
        return actsFromDate;
    }

    public void setActsFromDate(Date actsFromDate) {
        this.actsFromDate = actsFromDate;
    }

    public Date getActsToDate() {
        return actsToDate;
    }

    public void setActsToDate(Date actsToDate) {
        this.actsToDate = actsToDate;
    }

    public Date getBillsFromDate() {
        return billsFromDate;
    }

    public void setBillsFromDate(Date billsFromDate) {
        this.billsFromDate = billsFromDate;
    }

    public Date getBillsToDate() {
        return billsToDate;
    }

    public void setBillsToDate(Date billsToDate) {
        this.billsToDate = billsToDate;
    }


    public void clearActsFilter() {
        this.actSessionNo = Consts.NONE_SELECTED_FILTER;
        this.actsParliamentNo = Consts.NONE_SELECTED_FILTER;
        this.actsSession = null;
        this.actsParliament = null;
        this.actsDateTo = null;
        this.actsDateFrom = null;
        this.actFilterApplied = false;
        this.actsFromDate =null;
        this.actsToDate =null;

    }

    public void clearBillsFilter() {
        this.billsSessionNo = Consts.NONE_SELECTED_FILTER;
        this.billsParliamentNo = Consts.NONE_SELECTED_FILTER;
        this.billsSession = null;
        this.billsParliament = null;
        this.billsDateTo = null;
        this.billsDateFrom = null;
        this.billsFilterApplied = false;
        this.billsFromDate =null;
        this.billsToDate =null;
    }
}

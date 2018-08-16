package app.com.esenatenigeria.dialogs;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import app.com.esenatenigeria.R;
import app.com.esenatenigeria.interfaces.Filter;
import app.com.esenatenigeria.model.FilterSingletonModel;
import app.com.esenatenigeria.utils.Consts;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dev on 24/4/18.
 */

public class SearchFilterDialog implements View.OnClickListener {

    static private Dialog mDialog;
    private static Filter.ActsFilterApplied mActsFilterApplied;
    private static Filter.BillsFilterApplied mBillsFilterApplied;
    @BindView(R.id.txt_date_from)
    TextView txtDateFrom;
    @BindView(R.id.txt_date_to)
    TextView txtDateTo;
    @BindView(R.id.sp_session)
    Spinner spSession;
    @BindView(R.id.sp_parliament_no)
    Spinner spParliamentNo;
    FilterSingletonModel mFilterModel;
    String mFrom;
    List<String> mSessionArray = new ArrayList<String>();
    List<String> mParliamentArray = new ArrayList<String>();
    private SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
    private Context mContext;
    private String mDateFrom;
    private String mDateTo;
    private Date mFromDate;
    private Date mToDate;

    private int mParliamentNo;
    private int mSessionNo;
    private String mParliament;
    private String mSession;

    public SearchFilterDialog(Context context, String s) {
        mContext = context;
        mFrom = s;
    }

    public static void ActsFilterInterface(Filter.ActsFilterApplied open) {
        mActsFilterApplied = open;
    }

    public static void BillsFilterInterface(Filter.BillsFilterApplied open) {
        mBillsFilterApplied = open;
    }

    public void showDialog() {
        View view;
        if (mDialog == null) {
            mFilterModel = FilterSingletonModel.getInstance();
            mDialog = new Dialog(mContext);
            mDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            view = View.inflate(mContext, R.layout.dialog_filter, null);
            mDialog.setContentView(view);
            mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            mDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            mDialog.getWindow().getAttributes().windowAnimations = R.style.DialogSideAnimation;
            mDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            mDialog.show();

            ButterKnife.bind(this, view);
            initUI();
            initListeners();

            mDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface) {
                    mDialog = null;
                }
            });
        }
    }

    private void initUI() {
        setSessionSpinner();
        setParliamentSpinner();

        if (mFrom.equals(Consts.ACTS)) {
            mDateFrom = mFilterModel.getActsDateFrom();
            mDateTo = mFilterModel.getActsDateTo();
            mParliamentNo = mFilterModel.getActsParliamentNo();
            mSessionNo = mFilterModel.getActSessionNo();

            mToDate = mFilterModel.getActsToDate();
            mFromDate = mFilterModel.getActsFromDate();

        } else if (mFrom.equals(Consts.BILLS)) {
            mDateFrom = mFilterModel.getBillsDateFrom();
            mDateTo = mFilterModel.getBillsDateTo();
            mParliamentNo = mFilterModel.getBillsParliamentNo();
            mSessionNo = mFilterModel.getBillsSessionNo();

            mToDate = mFilterModel.getActsToDate();
            mFromDate = mFilterModel.getActsFromDate();
        }
        if (mDateFrom != null) {
            txtDateFrom.setText(Consts.timeFilterChange(mDateFrom));
        }
        if (mDateTo != null) {
            txtDateTo.setText(Consts.timeFilterChange(mDateTo));
        }
        spParliamentNo.setSelection(mParliamentNo);
        spSession.setSelection(mSessionNo);
    }

    private void initListeners() {

    }

    @Override
    public void onClick(View v) {

    }

    @OnClick(R.id.img_cross)
    void dismiss() {
        if (mDialog != null) {
            mDialog.dismiss();
            mDialog = null;
        }
    }

    @OnClick(R.id.txt_apply_filter)
    void applyFilter() {
        if (mDateTo != null && mDateFrom == null) {
            Toast.makeText(mContext, mContext.getResources().getString(R.string.select_from_date), Toast.LENGTH_SHORT).show();
        } else {
            if (mFrom.equals(Consts.ACTS)) {
                mFilterModel.setActsDateFrom(mDateFrom);
                if (mDateFrom != null && mDateTo == null) {
                    mDateTo = mFormat.format(Calendar.getInstance(TimeZone.getDefault()).getTime());
                    mToDate = Calendar.getInstance(TimeZone.getDefault()).getTime();
                }
                mFilterModel.setActsDateTo(mDateTo);

                mFilterModel.setActsFromDate(mFromDate);
                mFilterModel.setActsToDate(mToDate);

                mFilterModel.setActsParliamentNo(mParliamentNo);
                mFilterModel.setActsParliament(mParliament);

                mFilterModel.setActSessionNo(mSessionNo);
                mFilterModel.setActsSession(mSession);
                mFilterModel.setActFilterApplied(true);

                mActsFilterApplied.onFilterApplied();
            } else if (mFrom.equals(Consts.BILLS)) {
                mFilterModel.setBillsDateFrom(mDateFrom);
                if (mDateFrom != null && mDateTo == null) {
                    mDateTo = mFormat.format(Calendar.getInstance(TimeZone.getDefault()).getTime());
                    mToDate = Calendar.getInstance(TimeZone.getDefault()).getTime();
                }
                mFilterModel.setBillsDateTo(mDateTo);

                mFilterModel.setBillsFromDate(mFromDate);
                mFilterModel.setBillsToDate(mToDate);

                mFilterModel.setBillsParliamentNo(mParliamentNo);
                mFilterModel.setBillsParliament(mParliament);

                mFilterModel.setBillsSessionNo(mSessionNo);
                mFilterModel.setBillsSession(mSession);
                mFilterModel.setBillsFilterApplied(true);

                mBillsFilterApplied.onFilterApplied();
            }
            dismiss();
        }
    }

    @OnClick(R.id.txt_clear_filter)
    void clearFilter() {
        if (mFrom.equals(Consts.ACTS)) {
            mFilterModel.clearActsFilter();
            mActsFilterApplied.onFilterApplied();
        } else if (mFrom.equals(Consts.BILLS)) {
            mFilterModel.clearBillsFilter();
            mBillsFilterApplied.onFilterApplied();
        }
        dismiss();
    }

    @OnClick(R.id.txt_date_from)
    void setTxtDateFrom() {

        final Calendar cal = Calendar.getInstance(TimeZone.getDefault());

        if (mFromDate != null) {
            cal.setTime(mFromDate);
        }

        DatePickerDialog datePickerDialog = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                cal.set(year, monthOfYear, dayOfMonth, 0, 0);
                txtDateFrom.setText(Consts.timeFilterChange(mFormat.format(cal.getTime())));
                mFromDate = cal.getTime();
                mDateFrom = mFormat.format(cal.getTime());
            }
        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.setCancelable(false);
        if (mToDate != null) {
            datePickerDialog.getDatePicker().setMaxDate(mToDate.getTime());
        } else {
            datePickerDialog.getDatePicker().setMaxDate(Calendar.getInstance().getTimeInMillis());
        }
        datePickerDialog.setTitle("");
        datePickerDialog.show();
    }

    @OnClick(R.id.txt_date_to)
    void setTxtDateTo() {
        final Calendar cal = Calendar.getInstance(TimeZone.getDefault());
        if (mToDate != null) {
            cal.setTime(mToDate);
        }
        DatePickerDialog datePickerDialog = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                cal.set(year, monthOfYear, dayOfMonth, 0, 0);
                txtDateTo.setText(Consts.timeFilterChange(mFormat.format(cal.getTime())));
                mToDate = cal.getTime();
                mDateTo = mFormat.format(cal.getTime());
            }
        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.setCancelable(false);
        datePickerDialog.getDatePicker().setMaxDate(Calendar.getInstance().getTimeInMillis());
        if (mFromDate != null) {
            datePickerDialog.getDatePicker().setMinDate(mFromDate.getTime());
        }
        datePickerDialog.setTitle("");
        datePickerDialog.show();
    }

    private void setSessionSpinner() {
        mSessionArray.add("All Session");
        mSessionArray.add("1st Session");
        mSessionArray.add("2nd Session");
        mSessionArray.add("3rd Session");
        mSessionArray.add("4th Session");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, R.layout.item_spinner_filter, mSessionArray);

        spSession.setAdapter(adapter);

        spSession.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mSessionNo = i;
                mSession = mSessionArray.get(mSessionNo);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void setParliamentSpinner() {
        mParliamentArray.add("All Parliament");
        mParliamentArray.add("1st Parliament");
        mParliamentArray.add("2nd Parliament");
        mParliamentArray.add("3rd Parliament");
        mParliamentArray.add("4th Parliament");
        mParliamentArray.add("5th Parliament");
        mParliamentArray.add("6th Parliament");
        mParliamentArray.add("7th Parliament");
        mParliamentArray.add("8th Parliament");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, R.layout.item_spinner_filter, mParliamentArray);
        spParliamentNo.setAdapter(adapter);

        spParliamentNo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mParliamentNo = i;
                mParliament = mParliamentArray.get(mParliamentNo);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


}

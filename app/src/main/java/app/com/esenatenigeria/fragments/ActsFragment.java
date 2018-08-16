package app.com.esenatenigeria.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import app.com.esenatenigeria.R;
import app.com.esenatenigeria.activities.ViewPdfActivity;
import app.com.esenatenigeria.adapters.ActsAdapter;
import app.com.esenatenigeria.customviews.GridSpacingItemDecoration;
import app.com.esenatenigeria.dialogs.SearchFilterDialog;
import app.com.esenatenigeria.interfaces.Filter;
import app.com.esenatenigeria.interfaces.Interfaces;
import app.com.esenatenigeria.model.ActsModel;
import app.com.esenatenigeria.model.DeleteDataModel;
import app.com.esenatenigeria.model.FilterSingletonModel;
import app.com.esenatenigeria.network.RetrofitClient;
import app.com.esenatenigeria.utils.Consts;
import app.com.esenatenigeria.utils.MarshMallowPermission;
import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

/**
 * Created by dev on 19/4/18.
 */

public class ActsFragment extends BaseFragment implements Interfaces.ActsAdapterItemClick, Filter.ActsFilterApplied, Interfaces.DocumentDeleted {

    @SuppressLint("StaticFieldLeak")
    static ActsFragment fragment;
    @SuppressLint("StaticFieldLeak")
    static Context mContext;

    @BindView(R.id.rv_acts)
    RecyclerView rvActs;
    @BindView(R.id.ed_search_bar)
    EditText edSearchBar;
    @BindView(R.id.img_search_cross)
    ImageView imgSearchCross;
    @BindView(R.id.img_filter)
    ImageView imgFilter;
    @BindView(R.id.progress)
    ProgressBar progressBar;
    @BindView(R.id.ll_no_result)
    LinearLayout llNoResult;

    FilterSingletonModel mFilterModel;
    ActsAdapter mAdapter;
    ArrayList<ActsModel.DataBean> mDocumentArray = new ArrayList<>();
    ArrayList<ActsModel.DataBean> mDocumentArrayTemp = new ArrayList<>();

    public static ActsFragment newInstance(Context context, TextView txtTitle) {
        fragment = new ActsFragment();
        mContext = context;
        txtTitle.setText(mContext.getResources().getString(R.string.acts));
        return fragment;
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_acts;
    }

    @Override
    protected void onCreateStuff() {
        mFilterModel = FilterSingletonModel.getInstance();
        mAdapter = new ActsAdapter(getActivity(), mDocumentArray);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        rvActs.setLayoutManager(mLayoutManager);
        rvActs.addItemDecoration(new GridSpacingItemDecoration(2, (int) (mHeight * 0.010), true));
        rvActs.setItemAnimator(new DefaultItemAnimator());
        rvActs.setAdapter(mAdapter);
    }

    @Override
    protected void initListeners() {
        ActsAdapter.ListenerDownloadPdfInterface(this);
        SearchFilterDialog.ActsFilterInterface(this);
        ViewPdfActivity.DeletedInterface(this);

        edSearchBar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER))
                        || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    Log.i("TAG", "Enter pressed");
                    edSearchBar.setCursorVisible(false);
                    if (!TextUtils.isEmpty(edSearchBar.getText())) {
                        hideKeyboard(getActivity());
                        if (mDocumentArrayTemp != null) {
                            mDocumentArrayTemp.clear();
                        }
                        new FilterAsync(mFilterModel).execute();
                    }
                }
                return false;
            }
        });


        edSearchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0) {
                    imgSearchCross.setImageDrawable(getResources().getDrawable(R.mipmap.ic_cancel_search));
                } else {
                    imgSearchCross.setImageDrawable(getResources().getDrawable(R.mipmap.ic_search));
                    if (mDocumentArrayTemp != null) {
                        mDocumentArrayTemp.clear();
                    }
                    mAdapter = new ActsAdapter(getActivity(), mDocumentArray);
                    rvActs.setAdapter(mAdapter);
                    if (mDocumentArray.size() == 0) {
                        llNoResult.setVisibility(View.VISIBLE);
                    } else {
                        llNoResult.setVisibility(View.GONE);
                        Collections.sort(mDocumentArray, new Comparator<ActsModel.DataBean>() {
                            @Override
                            public int compare(ActsModel.DataBean dataBean, ActsModel.DataBean t1) {
                                return t1.getCodeUpdatedAt().compareTo(dataBean.getCodeUpdatedAt());
                            }
                        });
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        hitApi();
    }

    @OnClick(R.id.img_search_cross)
    void searchClear() {
        edSearchBar.setText("");
    }

    @OnClick(R.id.img_filter)
    void openFilter() {
        hideKeyboard(getActivity());
        edSearchBar.setText("");
        new SearchFilterDialog(mContext, Consts.ACTS).showDialog();
    }

    void hitApi() {
        edSearchBar.setEnabled(false);
        imgFilter.setEnabled(false);
        changeFilterImage();
        if (mFilterModel.isActFilterApplied()) {
            new FilterAsync(mFilterModel).execute();
        } else {
            mDocumentArray.addAll(mRoomDb.daoAccess().fetchActsAllData());
            if (mDocumentArray.size() == 0 || mDocumentArray == null) {
                progressBar.setVisibility(View.VISIBLE);
            } else {
                Collections.sort(mDocumentArray, new Comparator<ActsModel.DataBean>() {
                    @Override
                    public int compare(ActsModel.DataBean dataBean, ActsModel.DataBean t1) {
                        return t1.getDate().compareTo(dataBean.getDate());
                    }
                });
                edSearchBar.setEnabled(true);
                imgFilter.setEnabled(true);
                progressBar.setVisibility(View.GONE);
                mAdapter.notifyDataSetChanged();
            }
        }
        if (connectedToInternet()) {
            Call<ActsModel> call = RetrofitClient.getInstance().getActsUserDocuments(encode.encrypt(Consts.ACTS));
            call.enqueue(new Callback<ActsModel>() {
                @Override
                public void onResponse(Call<ActsModel> call, Response<ActsModel> response) {
                    try {
                        if (response.body().getDataX() != null) {
                            new AddToDb(response).execute();
                        }
                    } catch (Exception e) {
                        if (mDocumentArray.size() == 0) {
                            llNoResult.setVisibility(View.VISIBLE);
                        } else {
                            edSearchBar.setEnabled(true);
                            imgFilter.setEnabled(true);
                            llNoResult.setVisibility(View.GONE);
                        }
                        toast(mContext.getResources().getString(R.string.something_went_wrong));
                        progressBar.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onFailure(Call<ActsModel> call, Throwable t) {
                    if (mDocumentArray.size() == 0) {
                        llNoResult.setVisibility(View.VISIBLE);
                    } else {
                        edSearchBar.setEnabled(true);
                        imgFilter.setEnabled(true);
                        llNoResult.setVisibility(View.GONE);
                    }
                    toast(mContext.getResources().getString(R.string.something_went_wrong));
                    progressBar.setVisibility(View.GONE);
                }
            });
        } else {
            if (mDocumentArray.size() == 0) {
                llNoResult.setVisibility(View.VISIBLE);
            } else {
                edSearchBar.setEnabled(true);
                imgFilter.setEnabled(true);
                llNoResult.setVisibility(View.GONE);
            }
            showInternetDialog();
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onFilterApplied() {
        edSearchBar.setCursorVisible(false);
        new FilterAsync(mFilterModel).execute();
    }

    @OnClick(R.id.ed_search_bar)
    void cursorVisible() {
        edSearchBar.setCursorVisible(true);
    }

    void changeFilterImage() {
        if (mFilterModel.isActFilterApplied()) {
            imgFilter.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.ic_filter_applied));
        } else {
            imgFilter.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.ic_filter));
        }
    }

    @Override
    public void onActsItemClick(int position, ActsModel.DataBean model) {
        hideKeyboard(getActivity());
        if (mPermission.checkPermissionForExternalStorage()) {
            Intent intent = new Intent(mContext, ViewPdfActivity.class);
            intent.putExtra(Consts.ACT_ID, model.getDoc_id());
            intent.putExtra(Consts.FILE_NAME, encode.decrypt(model.getDocURL()));
            startActivity(intent);
        } else {
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    mPermission.snackBarStorage();
                } else {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{WRITE_EXTERNAL_STORAGE}, MarshMallowPermission.EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE);
                }
            }
        }
    }

    void hitDeleteApi() {
        if (connectedToInternet()) {
            Call<DeleteDataModel> call = RetrofitClient.getInstance().getDeletedDocuments();
            call.enqueue(new Callback<DeleteDataModel>() {
                @Override
                public void onResponse(@NonNull Call<DeleteDataModel> call, @NonNull Response<DeleteDataModel> response) {
                    try {
                        if (response.body().getData() != null) {
                            for (int i = 0; i < response.body().getData().size(); i++) {
                                if (response.body().getData().get(i).getCategory() != null ||
                                        !TextUtils.isEmpty(response.body().getData().get(i).getCategory())) {
                                    String category = encode.decrypt(response.body().getData().get(i).getCategory());

                                    if (category.equals(Consts.BILLS)) {
                                        if (mRoomDb.daoAccess().containBillsSingleRecord(String.valueOf(response.body().getData().get(i).getDoc_id()))) {
                                            mRoomDb.daoAccess().deleteBillsRecord(String.valueOf(response.body().getData().get(i).getDoc_id()));
                                        }

                                    } else if (category.equals(Consts.ACTS)) {
                                        if (mRoomDb.daoAccess().containActsSingleRecord(String.valueOf(response.body().getData().get(i).getDoc_id()))) {
                                            mRoomDb.daoAccess().deleteActsRecord(String.valueOf(response.body().getData().get(i).getDoc_id()));
                                        }
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<DeleteDataModel> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        }
    }

    void setAdapter() {
        changeFilterImage();
        if (mFilterModel.isActFilterApplied()) {
            new FilterAsync(mFilterModel).execute();
        } else {
            if (mDocumentArray != null && mDocumentArray.size() > 0) {
                mDocumentArray.clear();
            }
            mDocumentArray.addAll(mRoomDb.daoAccess().fetchActsAllData());

            if (mDocumentArray.size() == 0 || mDocumentArray == null) {
                progressBar.setVisibility(View.GONE);
                llNoResult.setVisibility(View.VISIBLE);
            } else {
                Collections.sort(mDocumentArray, new Comparator<ActsModel.DataBean>() {
                    @Override
                    public int compare(ActsModel.DataBean dataBean, ActsModel.DataBean t1) {
                        return t1.getDate().compareTo(dataBean.getDate());
                    }
                });
                progressBar.setVisibility(View.GONE);
                mAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onDocumentDeleted(String category) {
        if (category.equals(Consts.ACTS)) {
            setAdapter();
        }
    }


    @SuppressLint("StaticFieldLeak")
    class FilterAsync extends AsyncTask {

        FilterSingletonModel mFilterModel;

        FilterAsync(FilterSingletonModel model) {
            mFilterModel = model;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            edSearchBar.setEnabled(false);
            imgFilter.setEnabled(false);
            llNoResult.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
            rvActs.setVisibility(View.GONE);
        }

        @Override
        protected Object doInBackground(Object[] objects) {

            if (!TextUtils.isEmpty(mFilterModel.getActsDateFrom())
                    && mFilterModel.getActsParliamentNo() == Consts.NONE_SELECTED_FILTER
                    && mFilterModel.getActSessionNo() == Consts.NONE_SELECTED_FILTER) {
                // only date
                if (mDocumentArray != null) {
                    mDocumentArray.clear();
                }
                mDocumentArray.addAll(mRoomDb.daoAccess().searchActDate(mFilterModel.getActsDateFrom()
                        , mFilterModel.getActsDateTo()));

            } else if (TextUtils.isEmpty(mFilterModel.getActsDateFrom())
                    && mFilterModel.getActsParliamentNo() != Consts.NONE_SELECTED_FILTER
                    && mFilterModel.getActSessionNo() == Consts.NONE_SELECTED_FILTER) {
                // only parliament
                if (mDocumentArray != null) {
                    mDocumentArray.clear();
                }
                mDocumentArray.addAll(mRoomDb.daoAccess().searchActParliament(String.valueOf(mFilterModel.getActsParliament())));

            } else if (TextUtils.isEmpty(mFilterModel.getActsDateFrom())
                    && mFilterModel.getActsParliamentNo() == Consts.NONE_SELECTED_FILTER
                    && mFilterModel.getActSessionNo() != Consts.NONE_SELECTED_FILTER) {
                // only session
                if (mDocumentArray != null) {
                    mDocumentArray.clear();
                }
                mDocumentArray.addAll(mRoomDb.daoAccess().searchActSession(String.valueOf(mFilterModel.getActsSession())));

            } else if (!TextUtils.isEmpty(mFilterModel.getActsDateFrom())
                    && mFilterModel.getActsParliamentNo() != Consts.NONE_SELECTED_FILTER
                    && mFilterModel.getActSessionNo() == Consts.NONE_SELECTED_FILTER) {
                // date & parliament
                if (mDocumentArray != null) {
                    mDocumentArray.clear();
                }
                mDocumentArray.addAll(mRoomDb.daoAccess().searchActDateParliament(mFilterModel.getActsDateFrom()
                        , mFilterModel.getActsDateTo(), String.valueOf(mFilterModel.getActsParliament())));

            } else if (!TextUtils.isEmpty(mFilterModel.getActsDateFrom())
                    && mFilterModel.getActsParliamentNo() == Consts.NONE_SELECTED_FILTER
                    && mFilterModel.getActSessionNo() != Consts.NONE_SELECTED_FILTER) {
                // date & session
                if (mDocumentArray != null) {
                    mDocumentArray.clear();
                }
                mDocumentArray.addAll(mRoomDb.daoAccess().searchActDateSession(mFilterModel.getActsDateFrom()
                        , mFilterModel.getActsDateTo(), String.valueOf(mFilterModel.getActsSession())));

            } else if (TextUtils.isEmpty(mFilterModel.getActsDateFrom())
                    && mFilterModel.getActsParliamentNo() != Consts.NONE_SELECTED_FILTER
                    && mFilterModel.getActSessionNo() != Consts.NONE_SELECTED_FILTER) {
                // parliament & session
                if (mDocumentArray != null) {
                    mDocumentArray.clear();
                }
                mDocumentArray.addAll(mRoomDb.daoAccess().searchActPS(String.valueOf(mFilterModel.getActsParliament()),
                        String.valueOf(mFilterModel.getActsSession())));

            } else if (!TextUtils.isEmpty(mFilterModel.getActsDateFrom())
                    && mFilterModel.getActsParliamentNo() != Consts.NONE_SELECTED_FILTER
                    && mFilterModel.getActSessionNo() != Consts.NONE_SELECTED_FILTER) {
                // all
                if (mDocumentArray != null) {
                    mDocumentArray.clear();
                }
                mDocumentArray.addAll(mRoomDb.daoAccess().searchActAll(mFilterModel.getActsDateFrom()
                        , mFilterModel.getActsDateTo()
                        , String.valueOf(mFilterModel.getActsParliament())
                        , String.valueOf(mFilterModel.getActsSession())));

            } else if (TextUtils.isEmpty(mFilterModel.getActsDateFrom())
                    && mFilterModel.getActsParliamentNo() == Consts.NONE_SELECTED_FILTER
                    && mFilterModel.getActSessionNo() == Consts.NONE_SELECTED_FILTER) {
                // no filter applied
                if (mDocumentArray != null) {
                    mDocumentArray.clear();
                }
                mDocumentArray.addAll(mRoomDb.daoAccess().fetchActsAllData());
                mFilterModel.clearActsFilter();
            }

            if (mDocumentArrayTemp != null) {
                mDocumentArrayTemp.clear();
            }
            ArrayList<ActsModel.DataBean> temp = new ArrayList<>();
            for (int k = 0; k < mDocumentArray.size(); k++) {
                if (mDocumentArray.get(k).getName().toUpperCase().contains(edSearchBar.getText().toString().toUpperCase())) {
                    temp.add(mDocumentArray.get(k));
                }
            }
            mDocumentArrayTemp.addAll(temp);
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            progressBar.setVisibility(View.GONE);
            rvActs.setVisibility(View.VISIBLE);
            changeFilterImage();
            if (mDocumentArrayTemp.size() == 0) {
                llNoResult.setVisibility(View.VISIBLE);
            } else {
                llNoResult.setVisibility(View.GONE);
                Collections.sort(mDocumentArrayTemp, new Comparator<ActsModel.DataBean>() {
                    @Override
                    public int compare(ActsModel.DataBean dataBean, ActsModel.DataBean t1) {
                        return t1.getDate().compareTo(dataBean.getDate());
                    }
                });
            }
            edSearchBar.setEnabled(true);
            imgFilter.setEnabled(true);
            mAdapter = new ActsAdapter(getActivity(), mDocumentArrayTemp);
            rvActs.setAdapter(mAdapter);
        }
    }

    @SuppressLint("StaticFieldLeak")
    class AddToDb extends AsyncTask {
        Response<ActsModel> response;

        AddToDb(Response<ActsModel> response) {
            this.response = response;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Object doInBackground(Object[] objects) {

            ArrayList<ActsModel.DataBean> temp = new ArrayList<>();

            mRoomDb.daoAccess().deleteAllActsRecord();

            for (int i = 0; i < response.body().getDataX().getData().size(); i++) {
                ActsModel.DataBean dataBean = new ActsModel.DataBean();
                dataBean.setDoc_id(response.body().getDataX().getData().get(i).getDoc_id());
                dataBean.setName(encode.decrypt(response.body().getDataX().getData().get(i).getName()));
                dataBean.setCategory(encode.decrypt(response.body().getDataX().getData().get(i).getCategory()));
                dataBean.setChamber(encode.decrypt(response.body().getDataX().getData().get(i).getChamber()));
                dataBean.setDocURL(response.body().getDataX().getData().get(i).getDocURL());
                dataBean.setParliament(encode.decrypt(response.body().getDataX().getData().get(i).getParliament()));
                dataBean.setSession(encode.decrypt(response.body().getDataX().getData().get(i).getSession()));
                dataBean.setSource(encode.decrypt(response.body().getDataX().getData().get(i).getSource()));
                dataBean.setSummary(encode.decrypt(response.body().getDataX().getData().get(i).getSummary()));
                dataBean.setDate(Consts.timeFormatChange(response.body().getDataX().getData().get(i).getDate()));

                dataBean.setCodeUpdatedAt(response.body().getDataX().getData().get(i).getCodeUpdatedAt());

                if (!mRoomDb.daoAccess().containActsSingleRecord(dataBean.getDoc_id())) {
                    mRoomDb.daoAccess().insertActsOnlySingleRecord(dataBean);
                } else if (Consts.convertStringToDate(response.body().getDataX().getData().get(i).getCodeUpdatedAt())
                        .after(Consts.convertStringToDate(mRoomDb.daoAccess().getUpdatedActsDate(dataBean.getDoc_id())))) {
                    mRoomDb.daoAccess().updateActsRecord(dataBean);
                }
                temp.add(dataBean);
            }

            if (mDocumentArray != null) {
                mDocumentArray.clear();
                mDocumentArray.addAll(temp);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            progressBar.setVisibility(View.GONE);
            if (mDocumentArray.size() == 0) {
                llNoResult.setVisibility(View.VISIBLE);
            } else {
                Collections.sort(mDocumentArray, new Comparator<ActsModel.DataBean>() {
                    @Override
                    public int compare(ActsModel.DataBean dataBean, ActsModel.DataBean t1) {
                        return t1.getDate().compareTo(dataBean.getDate());
                    }
                });
                edSearchBar.setEnabled(true);
                imgFilter.setEnabled(true);
                llNoResult.setVisibility(View.GONE);
                mAdapter.notifyDataSetChanged();
//                hitDeleteApi();
            }
        }
    }
}

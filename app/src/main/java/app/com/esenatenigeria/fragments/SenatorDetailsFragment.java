package app.com.esenatenigeria.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
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
import app.com.esenatenigeria.adapters.SenatorDetailsAdapter;
import app.com.esenatenigeria.interfaces.Interfaces;
import app.com.esenatenigeria.model.SenatorDetailModel;
import app.com.esenatenigeria.network.RetrofitClient;
import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by dev on 23/4/18.
 */

public class SenatorDetailsFragment extends BaseFragment implements Interfaces.AdapterItemClick {

    @SuppressLint("StaticFieldLeak")
    static SenatorDetailsFragment fragment;

    @SuppressLint("StaticFieldLeak")
    static Context mContext;
    static Interfaces.FragmentClick mOpenDetail;
    @BindView(R.id.ed_search_bar)
    EditText edSearchBar;
    @BindView(R.id.img_search_cross)
    ImageView imgSearchCross;
    @BindView(R.id.progress)
    ProgressBar progressBar;
    @BindView(R.id.ll_no_result)
    LinearLayout llNoResult;
    @BindView(R.id.rv_senator_details)
    RecyclerView rvOfficers;
    SenatorDetailsAdapter mAdapter;
    ArrayList<SenatorDetailModel.DataBean> mSenatorArray = new ArrayList<>();
    ArrayList<SenatorDetailModel.DataBean> mSenatorArrayTemp = new ArrayList<>();
    boolean isSearched = false;

    public static void ListenerSenatorDetailsInterface(Interfaces.FragmentClick openDetail) {
        mOpenDetail = openDetail;
    }

    public static SenatorDetailsFragment newInstance(Context context, TextView textView) {
        fragment = new SenatorDetailsFragment();
        mContext = context;
        textView.setText(mContext.getResources().getString(R.string.senator_details));
        return fragment;
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_senator_details;
    }

    @Override
    protected void onCreateStuff() {
        mAdapter = new SenatorDetailsAdapter(getActivity(), mSenatorArray, mHeight);
        rvOfficers.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        rvOfficers.setNestedScrollingEnabled(false);
        rvOfficers.setAdapter(mAdapter);
    }

    @Override
    protected void initListeners() {
        SenatorDetailsAdapter.ListenerSenatorDetailInterface(this);

        edSearchBar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER))
                        || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    Log.i("TAG", "Enter pressed");

                    if (!TextUtils.isEmpty(edSearchBar.getText())) {
                        hideKeyboard(getActivity());
                        if (mSenatorArrayTemp != null) {
                            mSenatorArrayTemp.clear();
                        }
                        search();
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
                    isSearched = false;
                    imgSearchCross.setImageDrawable(getResources().getDrawable(R.mipmap.ic_search));
                    if (mSenatorArrayTemp != null) {
                        mSenatorArrayTemp.clear();
                    }
                    mAdapter = new SenatorDetailsAdapter(getActivity(), mSenatorArray, mHeight);
                    rvOfficers.setAdapter(mAdapter);
                    if (mSenatorArray.size() == 0) {
                        llNoResult.setVisibility(View.VISIBLE);
                    } else {
                        llNoResult.setVisibility(View.GONE);
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

    @Override
    public void onItemClick(int position) {
        if (!isSearched) {
            mOpenDetail.onFragItemClick(mSenatorArray.get(position).getUser_id());
        } else {
            mOpenDetail.onFragItemClick(mSenatorArrayTemp.get(position).getUser_id());
        }
    }

    void hitApi() {
        edSearchBar.setEnabled(false);

        new GetFromDb().execute();

        if (connectedToInternet()) {
            Call<SenatorDetailModel> call = RetrofitClient.getInstance().getUserDirectory();
            call.enqueue(new Callback<SenatorDetailModel>() {
                @Override
                public void onResponse(Call<SenatorDetailModel> call, Response<SenatorDetailModel> response) {
                    new AddToDb(response).execute();
                }

                @Override
                public void onFailure(Call<SenatorDetailModel> call, Throwable t) {
                    progressBar.setVisibility(View.GONE);
                    if (mSenatorArray.size() == 0) {
                        llNoResult.setVisibility(View.VISIBLE);
                    } else {
                        edSearchBar.setEnabled(true);
                        llNoResult.setVisibility(View.GONE);
                    }
                    toast(mContext.getResources().getString(R.string.something_went_wrong));
                }
            });
        } else {
            if (mSenatorArray.size() == 0) {
                llNoResult.setVisibility(View.VISIBLE);
            } else {
                edSearchBar.setEnabled(true);
                llNoResult.setVisibility(View.GONE);
            }
            showInternetDialog();
            progressBar.setVisibility(View.GONE);
        }

    }

    void search() {
        isSearched = true;
        llNoResult.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        rvOfficers.setVisibility(View.GONE);

        ArrayList<SenatorDetailModel.DataBean> temp = new ArrayList<>();

        for (int k = 0; k < mSenatorArray.size(); k++) {
            if (mSenatorArray.get(k).getState().toUpperCase().contains(edSearchBar.getText().toString().toUpperCase())
                    || mSenatorArray.get(k).getConstituency().toUpperCase().contains(edSearchBar.getText().toString().toUpperCase())) {
                temp.add(mSenatorArray.get(k));
            }
        }
        mSenatorArrayTemp.addAll(temp);

        Collections.sort(mSenatorArrayTemp, new Comparator<SenatorDetailModel.DataBean>() {
            @Override
            public int compare(SenatorDetailModel.DataBean dataBean, SenatorDetailModel.DataBean t1) {
                return dataBean.getState().compareTo(t1.getState());
            }
        });

        mAdapter = new SenatorDetailsAdapter(getActivity(), mSenatorArrayTemp, mHeight);
        rvOfficers.setAdapter(mAdapter);
        progressBar.setVisibility(View.GONE);
        rvOfficers.setVisibility(View.VISIBLE);
        if (mSenatorArrayTemp.size() == 0) {
            llNoResult.setVisibility(View.VISIBLE);
        } else {
            llNoResult.setVisibility(View.GONE);
        }
    }

    @SuppressLint("StaticFieldLeak")
    class GetFromDb extends AsyncTask {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            llNoResult.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            mSenatorArray.addAll(mRoomDb.daoAccess().fetchSenatorsAllData());
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            Collections.sort(mSenatorArray, new Comparator<SenatorDetailModel.DataBean>() {
                @Override
                public int compare(SenatorDetailModel.DataBean dataBean, SenatorDetailModel.DataBean t1) {
                    return dataBean.getState().compareTo(t1.getState());
                }
            });
            if (mSenatorArray.size() == 0 || mSenatorArray == null) {
                if (!connectedToInternet()) {
                    llNoResult.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                }

            } else {
                edSearchBar.setEnabled(true);
                llNoResult.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
                mAdapter.notifyDataSetChanged();
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    class AddToDb extends AsyncTask {
        Response<SenatorDetailModel> response;

        AddToDb(Response<SenatorDetailModel> response) {
            this.response = response;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Object doInBackground(Object[] objects) {

            ArrayList<SenatorDetailModel.DataBean> temp = new ArrayList<>();

            for (int i = 0; i < response.body().getData().getData().size(); i++) {
                SenatorDetailModel.DataBean dataBean = new SenatorDetailModel.DataBean();
                dataBean = response.body().getData().getData().get(i);

                dataBean.setName(encode.decrypt(response.body().getData().getData().get(i).getName()));

                if (response.body().getData().getData().get(i).getProfilePicURL() != null) {
                    SenatorDetailModel.DataBean.ProfilePicURLBean picURLBean = new SenatorDetailModel.DataBean.ProfilePicURLBean();
                    picURLBean.setOriginal(response.body().getData().getData().get(i).getProfilePicURL().getOriginal());
                    picURLBean.setThumbnail(response.body().getData().getData().get(i).getProfilePicURL().getThumbnail());
                    dataBean.setProfilePicURL(picURLBean);
                }

                if (!TextUtils.isEmpty(response.body().getData().getData().get(i).getConstituency()))
                    dataBean.setConstituency(encode.decrypt(response.body().getData().getData().get(i).getConstituency()));

                if (!TextUtils.isEmpty(response.body().getData().getData().get(i).getState()))
                    dataBean.setState(encode.decrypt(response.body().getData().getData().get(i).getState()));


                /*Delete Blocked users from local db and skip adding blocked users in db*/
                if (dataBean.getIsAdmin() == 0) {
                    if (dataBean.getIsBlocked() == 0) {
                        if (!mRoomDb.daoAccess().containSenatorsSingleRecord(dataBean.getUser_id())) {
                            mRoomDb.daoAccess().insertSenatorsOnlySingleRecord(dataBean);
                        } else {
                            mRoomDb.daoAccess().updateSenatorsRecord(dataBean);
                        }
                        temp.add(dataBean);
                    } else {
                        if (mRoomDb.daoAccess().containSenatorsSingleRecord(dataBean.getUser_id())) {
                            mRoomDb.daoAccess().deleteSenatorsRecord(dataBean);
                        }
                    }
                }
            }
            if (temp.size() > 0) {
                if (mSenatorArray != null) {
                    mSenatorArray.clear();
                }
                mSenatorArray.addAll(temp);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            Collections.sort(mSenatorArray, new Comparator<SenatorDetailModel.DataBean>() {
                @Override
                public int compare(SenatorDetailModel.DataBean dataBean, SenatorDetailModel.DataBean t1) {
                    return dataBean.getState().compareTo(t1.getState());
                }
            });
            progressBar.setVisibility(View.GONE);
            if (mSenatorArray.size() == 0) {
                llNoResult.setVisibility(View.VISIBLE);
            } else {
                mAdapter.notifyDataSetChanged();
                edSearchBar.setEnabled(true);
                llNoResult.setVisibility(View.GONE);
            }
        }
    }

//
//    @SuppressLint("StaticFieldLeak")
//    class SearchAsync extends AsyncTask {
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//
//        }
//
//        @Override
//        protected Object doInBackground(Object[] objects) {
//
//
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Object o) {
//            super.onPostExecute(o);
//
//        }
//    }

}




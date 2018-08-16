package app.com.esenatenigeria.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import app.com.esenatenigeria.adapters.CommitteesAdapter;
import app.com.esenatenigeria.model.CommitteeModel;
import app.com.esenatenigeria.network.RetrofitClient;
import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by dev on 19/4/18.
 */

public class CommitteesFragment extends BaseFragment {

    @SuppressLint("StaticFieldLeak")
    static CommitteesFragment fragment;
    @SuppressLint("StaticFieldLeak")
    static Context mContext;

    @BindView(R.id.rv_committees)
    RecyclerView rvCommittee;
    @BindView(R.id.ed_search_bar)
    EditText edSearchBar;
    @BindView(R.id.img_search_cross)
    ImageView imgSearchCross;
    @BindView(R.id.progress)
    ProgressBar progressBar;
    @BindView(R.id.ll_no_result)
    LinearLayout llNoResult;

    ArrayList<CommitteeModel.DataBean> mCommitteeArray = new ArrayList<>();
    ArrayList<CommitteeModel.DataBean> mCommitteeArrayTemp = new ArrayList<>();

    CommitteesAdapter mAdapter;

    public static CommitteesFragment newInstance(Context context, TextView textView) {
        fragment = new CommitteesFragment();
        mContext = context;
        textView.setText(mContext.getResources().getString(R.string.committees));
        return fragment;
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_committees;
    }

    @Override
    protected void onCreateStuff() {
        rvCommittee.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        rvCommittee.setNestedScrollingEnabled(false);

        mAdapter = new CommitteesAdapter(getActivity(), mCommitteeArray);
        rvCommittee.setAdapter(mAdapter);
    }

    @Override
    protected void initListeners() {

        edSearchBar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER))
                        || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    Log.i("TAG", "Enter pressed");

                    if (!TextUtils.isEmpty(edSearchBar.getText())) {
                        hideKeyboard(getActivity());
                        if (mCommitteeArrayTemp != null) {
                            mCommitteeArrayTemp.clear();
                        }
                        llNoResult.setVisibility(View.GONE);
                        progressBar.setVisibility(View.VISIBLE);
                        rvCommittee.setVisibility(View.GONE);
                        ArrayList<CommitteeModel.DataBean> temp = new ArrayList<>();

                        for (int k = 0; k < mCommitteeArray.size(); k++) {
                            if (mCommitteeArray.get(k).getName().toUpperCase().contains(edSearchBar.getText().toString().toUpperCase())
                                    || mCommitteeArray.get(k).getChairman().getName().toUpperCase().contains(edSearchBar.getText().toString().toUpperCase())
                                    || mCommitteeArray.get(k).getViceChairman().getName().toUpperCase().contains(edSearchBar.getText().toString().toUpperCase())) {

                                temp.add(mCommitteeArray.get(k));
                            }
                        }
                        if (temp != null)
                            mCommitteeArrayTemp.addAll(temp);

                        progressBar.setVisibility(View.GONE);
                        rvCommittee.setVisibility(View.VISIBLE);

                        Collections.sort(mCommitteeArrayTemp, new Comparator<CommitteeModel.DataBean>() {
                            @Override
                            public int compare(CommitteeModel.DataBean dataBean, CommitteeModel.DataBean t1) {
                                return dataBean.getName().toUpperCase()
                                        .compareToIgnoreCase(t1.getName().toUpperCase());
                            }
                        });

                        mAdapter = new CommitteesAdapter(getActivity(), mCommitteeArrayTemp);
                        rvCommittee.setAdapter(mAdapter);
                        if (mCommitteeArrayTemp.size() == 0) {
                            llNoResult.setVisibility(View.VISIBLE);
                        } else {
                            llNoResult.setVisibility(View.GONE);
                        }
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
                    if (mCommitteeArrayTemp != null) {
                        mCommitteeArrayTemp.clear();
                    }
                    mAdapter = new CommitteesAdapter(getActivity(), mCommitteeArray);
                    rvCommittee.setAdapter(mAdapter);
                    if (mCommitteeArray.size() == 0) {
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

    @OnClick(R.id.img_search_cross)
    void searchClear() {
        edSearchBar.setText("");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        hitApi();
    }

    void hitApi() {
        edSearchBar.setEnabled(false);
        mCommitteeArray.addAll(mRoomDb.daoAccess().fetchCommitteeAllData());
        Collections.sort(mCommitteeArray, new Comparator<CommitteeModel.DataBean>() {
            @Override
            public int compare(CommitteeModel.DataBean dataBean, CommitteeModel.DataBean t1) {
                return dataBean.getName().toUpperCase()
                        .compareToIgnoreCase(t1.getName().toUpperCase());
            }
        });
        if (mCommitteeArray.size() == 0 || mCommitteeArray == null) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            edSearchBar.setEnabled(true);
            progressBar.setVisibility(View.GONE);
            mAdapter.notifyDataSetChanged();
        }

        if (connectedToInternet()) {
            Call<CommitteeModel> call = RetrofitClient.getInstance().getUserCommittees();
            call.enqueue(new Callback<CommitteeModel>() {
                @Override
                public void onResponse(@NonNull Call<CommitteeModel> call, @NonNull Response<CommitteeModel> response) {
                    if (response.body().getDataX() != null) {
                        new AddToDb(response).execute();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<CommitteeModel> call, @NonNull Throwable t) {
                    if (mCommitteeArray.size() == 0) {
                        llNoResult.setVisibility(View.VISIBLE);
                    } else {
                        edSearchBar.setEnabled(true);
                        llNoResult.setVisibility(View.GONE);
                    }
                    toast(mContext.getResources().getString(R.string.something_went_wrong));
                    progressBar.setVisibility(View.GONE);
                }
            });
        } else {
            if (mCommitteeArray.size() == 0) {
                llNoResult.setVisibility(View.VISIBLE);
            } else {
                edSearchBar.setEnabled(true);
                llNoResult.setVisibility(View.GONE);
            }
            progressBar.setVisibility(View.GONE);
            showInternetDialog();
        }
    }

    @SuppressLint("StaticFieldLeak")
    class AddToDb extends AsyncTask {
        Response<CommitteeModel> response;

        AddToDb(Response<CommitteeModel> response) {
            this.response = response;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            ArrayList<CommitteeModel.DataBean> temp = new ArrayList<>();

            mRoomDb.daoAccess().deleteAllCommitteeRecord();

            for (int i = 0; i < response.body().getDataX().getData().size(); i++) {

                CommitteeModel.DataBean bean = new CommitteeModel.DataBean();
                bean.setCommittee_id(response.body().getDataX().getData().get(i).getCommittee_id());
                bean.setName(encode.decrypt(response.body().getDataX().getData().get(i).getName()));

                CommitteeModel.DataBean.ChairmanBean chairmanBean = new CommitteeModel.DataBean.ChairmanBean();
                chairmanBean.setName(encode.decrypt(response.body().getDataX().getData().get(i).getChairman().getName()));
                chairmanBean.setUser_id(response.body().getDataX().getData().get(i).getChairman().getUser_id());
                bean.setChairman(chairmanBean);

                bean.setChamber(response.body().getDataX().getData().get(i).getChamber());

                CommitteeModel.DataBean.ViceChairmanBean viceChairmanBean = new CommitteeModel.DataBean.ViceChairmanBean();
                viceChairmanBean.setName(encode.decrypt(response.body().getDataX().getData().get(i).getViceChairman().getName()));
                viceChairmanBean.setUser_id(response.body().getDataX().getData().get(i).getViceChairman().getUser_id());
                bean.setViceChairman(viceChairmanBean);

                bean.set__v(response.body().getDataX().getData().get(i).get__v());
                bean.setMembers(response.body().getDataX().getData().get(i).getMembers());

                if (!mRoomDb.daoAccess().containCommitteeSingleRecord(bean.getCommittee_id())) {
                    mRoomDb.daoAccess().insertCommitteeOnlySingleRecord(bean);
                } else {
                    mRoomDb.daoAccess().updateCommitteeRecord(bean);
                }
                temp.add(mRoomDb.daoAccess().getCommitteeSingleRecord(bean.getCommittee_id()));
            }

            if (mCommitteeArray != null) {
                mCommitteeArray.clear();
                mCommitteeArray.addAll(temp);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            Collections.sort(mCommitteeArray, new Comparator<CommitteeModel.DataBean>() {
                @Override
                public int compare(CommitteeModel.DataBean dataBean, CommitteeModel.DataBean t1) {
                    return dataBean.getName().toUpperCase()
                            .compareToIgnoreCase(t1.getName().toUpperCase());
                }
            });
            progressBar.setVisibility(View.GONE);
            if (mCommitteeArray.size() == 0) {
                llNoResult.setVisibility(View.VISIBLE);
            } else {
                mAdapter.notifyDataSetChanged();
                edSearchBar.setEnabled(true);
                llNoResult.setVisibility(View.GONE);
            }
        }
    }

}

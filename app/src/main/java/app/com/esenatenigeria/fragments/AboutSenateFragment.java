package app.com.esenatenigeria.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.widget.TextView;

import app.com.esenatenigeria.R;
import app.com.esenatenigeria.model.InformationModel;
import app.com.esenatenigeria.network.RetrofitClient;
import app.com.esenatenigeria.utils.Consts;
import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by dev on 19/4/18.
 */

public class AboutSenateFragment extends BaseFragment {

    @SuppressLint("StaticFieldLeak")
    static AboutSenateFragment fragment;

    @SuppressLint("StaticFieldLeak")
    static Context mContext;

    @BindView(R.id.txt_about)
    TextView txtAbout;

    InformationModel.DataBean mModel = new InformationModel.DataBean();

    public static AboutSenateFragment newInstance(Context context, TextView textView) {
        fragment = new AboutSenateFragment();
        mContext = context;
        textView.setText(mContext.getResources().getString(R.string.about_the_senate));
        return fragment;
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_about_senate;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        hitApi();
    }

    @Override
    protected void onCreateStuff() {
    }

    @Override
    protected void initListeners() {
    }

    void hitApi() {
        mModel = mRoomDb.daoAccess().getInfoSingleRecord(Consts.ABOUT_INFO);
        if (mModel != null) {
            txtAbout.setText(Html.fromHtml(mModel.getDetail().trim()));
        }
        if (connectedToInternet()) {
            Call<InformationModel> call = RetrofitClient.getInstance().getInformation();
            call.enqueue(new Callback<InformationModel>() {
                @Override
                public void onResponse(Call<InformationModel> call, Response<InformationModel> response) {
                    if (response != null) {
                        for (int i = 0; i < response.body().getData().getData().size(); i++) {
                            if (response.body().getData().getData().get(i).getId() == Consts.ABOUT_INFO) {
                                mModel = response.body().getData().getData().get(i);
                                txtAbout.setText(Html.fromHtml(mModel.getDetail()));
                            }
                            if (!mRoomDb.daoAccess().containInfoSingleRecord(response.body().getData().getData().get(i).getId())) {
                                mRoomDb.daoAccess().insertInfoSingleRecord(response.body().getData().getData().get(i));
                            } else {
                                mRoomDb.daoAccess().updateInfoRecord(response.body().getData().getData().get(i));
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<InformationModel> call, Throwable t) {

                }
            });
            if (mModel != null)
                txtAbout.setText(Html.fromHtml(mModel.getDetail()));

        } else {
            showInternetDialog();
        }
    }

}
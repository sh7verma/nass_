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

public class ConstitutionalFragment extends BaseFragment {

    @SuppressLint("StaticFieldLeak")
    static ConstitutionalFragment fragment;

    @SuppressLint("StaticFieldLeak")
    static Context mContext;
    @BindView(R.id.txt_constitutional_roles)
    TextView txt_constitutional_roles;

    InformationModel.DataBean mModel = new InformationModel.DataBean();


    public static ConstitutionalFragment newInstance(Context context, TextView txtTitle) {
        fragment = new ConstitutionalFragment();
        mContext = context;
        txtTitle.setText(mContext.getResources().getString(R.string.constitutional_roles));
        return fragment;
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_constitutional_roles;
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
        mModel = mRoomDb.daoAccess().getInfoSingleRecord(Consts.CONSTITUTIONAL_ROLES_INFO);
        if (mModel != null)
            txt_constitutional_roles.setText(Html.fromHtml(mModel.getDetail()));

        if (connectedToInternet()) {
            Call<InformationModel> call = RetrofitClient.getInstance().getInformation();
            call.enqueue(new Callback<InformationModel>() {
                @Override
                public void onResponse(Call<InformationModel> call, Response<InformationModel> response) {
                    for (int i = 0; i < response.body().getData().getData().size(); i++) {
                        if (response.body().getData().getData().get(i).getId() == Consts.CONSTITUTIONAL_ROLES_INFO) {
                            mModel = response.body().getData().getData().get(i);
                            txt_constitutional_roles.setText(Html.fromHtml(mModel.getDetail()));
                        }
                        if (!mRoomDb.daoAccess().containInfoSingleRecord(response.body().getData().getData().get(i).getId())) {
                            mRoomDb.daoAccess().insertInfoSingleRecord(response.body().getData().getData().get(i));
                        } else {
                            mRoomDb.daoAccess().updateInfoRecord(response.body().getData().getData().get(i));
                        }
                    }
                }

                @Override
                public void onFailure(Call<InformationModel> call, Throwable t) {

                }
            });
            if (mModel != null)
                txt_constitutional_roles.setText(Html.fromHtml(mModel.getDetail()));

        } else {
            showInternetDialog();
        }
    }
}

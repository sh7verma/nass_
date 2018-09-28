package app.com.esenatenigeria.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import app.com.esenatenigeria.R;
import app.com.esenatenigeria.model.SenatorDetailModel;
import app.com.esenatenigeria.utils.Consts;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dev on 23/4/18.
 */

public class SenateLeadershipFragment extends BaseFragment {

    @SuppressLint("StaticFieldLeak")
    static SenateLeadershipFragment fragment;
    @SuppressLint("StaticFieldLeak")
    static Context mContext;
    static Bundle args = new Bundle();
    @BindView(R.id.txt_president)
    TextView txtPresident;
    @BindView(R.id.txt_deputy)
    TextView txtDeputy;
    @BindView(R.id.img_arrow)
    ImageView imgArrow;
    @BindView(R.id.cv_president_details)
    CardView cvPresidentDetails;
    @BindView(R.id.cv_deputy_details)
    CardView cvDeputyDetails;

    Animation swipeleft, swiperight;

    SenatorDetailModel.DataBean mData = new SenatorDetailModel.DataBean();

    public static SenateLeadershipFragment newInstance(Context context, TextView textView) {
        fragment = new SenateLeadershipFragment();
        mContext = context;
        textView.setText(mContext.getResources().getString(R.string.senate_leadership));
        return fragment;
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_senate_leadership;
    }

    @Override
    protected void onCreateStuff() {
        args = fragment.getArguments();
        if (args.getInt(Consts.DETAIL_ID) == 0) {
            presidentSelected();
        } else if (args.getInt(Consts.DETAIL_ID) == 1) {
            deputySelected();
        }
    }

    @Override
    protected void initListeners() {

    }

    @OnClick(R.id.txt_president)
    void presidentSelected() {
        swipeleft = new TranslateAnimation((int) (mWidth - (mWidth / (3.1))), (int) (mWidth / (4.8)), 0, 0);
        swipeleft.setDuration(300);
        swipeleft.setFillAfter(true);

        txtPresident.setEnabled(false);
        txtDeputy.setEnabled(true);

        imgArrow.startAnimation(swipeleft);

        cvPresidentDetails.setVisibility(View.VISIBLE);
        cvDeputyDetails.setVisibility(View.GONE);

        txtPresident.setTextColor(mContext.getResources().getColor(R.color.black));

        txtDeputy.setTextColor(mContext.getResources().getColor(R.color.grey_color));
    }

    @OnClick(R.id.txt_deputy)
    void deputySelected() {
        swiperight = new TranslateAnimation((int) (mWidth / (4.8)), (int) (mWidth - (mWidth / (3.1))), 0, 0);
        swiperight.setDuration(300);
        swiperight.setFillAfter(true);

        txtPresident.setEnabled(true);
        txtDeputy.setEnabled(false);

        imgArrow.startAnimation(swiperight);

        cvPresidentDetails.setVisibility(View.GONE);
        cvDeputyDetails.setVisibility(View.VISIBLE);

        txtPresident.setTextColor(mContext.getResources().getColor(R.color.grey_color));

        txtDeputy.setTextColor(mContext.getResources().getColor(R.color.black));
    }


//    void hitApi() {
//        mData = mRoomDb.daoAccess().getSenatorsDetail(args.getInt(Consts.DETAIL_ID));
//        setData();
//
//        if (connectedToInternet()) {
//            Call<SenatorDetailModel> call = RetrofitClient.getInstance().getSenator(args.getInt(Consts.DETAIL_ID));
//            call.enqueue(new Callback<SenatorDetailModel>() {
//                @Override
//                public void onResponse(Call<SenatorDetailModel> call, Response<SenatorDetailModel> response) {
//                    mRoomDb.daoAccess().insertSenatorsDetail(response.body().getData());
//                    mData = response.body().getData();
//                    setData();
//                }
//
//                @Override
//                public void onFailure(Call<SenatorDetailModel> call, Throwable t) {
//
//                }
//            });
//        } else {
//            showInternetDialog();
//        }
//
//    }

//    void setData() {
//        if (mData != null) {
//
//            if (!TextUtils.isEmpty(mData.getName()))
//                txtName.setText(encode.decrypt(mData.getName()));
//
//            if (!TextUtils.isEmpty(mData.getAge()))
//                txtAge.setText(encode.decrypt(mData.getAge()));
//
//            if (!TextUtils.isEmpty(mData.getConstituency()))
//                txtConstituency.setText(encode.decrypt(mData.getConstituency()));
//
//            if (!TextUtils.isEmpty(mData.getState()))
//                txtState.setText(encode.decrypt(mData.getState()));
//
//            if (!TextUtils.isEmpty(mData.getChamber()))
//                txtChamber.setText(encode.decrypt(mData.getChamber()));
//
//            if (!TextUtils.isEmpty(mData.getParty()))
//                txtParty.setText(encode.decrypt(mData.getParty()));
//
//            if (!TextUtils.isEmpty(mData.getPhoneNo()))
//                txtConstituencyPhone.setText(encode.decrypt(mData.getPhoneNo()));
//
//            if (!TextUtils.isEmpty(mData.getAddress()))
//                txt_constituency_address.setText(encode.decrypt(mData.getAddress()));
//
//            if (!TextUtils.isEmpty(mData.getEmail()))
//                txt_email.setText(encode.decrypt(mData.getEmail()));
//
//        }
//    }
}

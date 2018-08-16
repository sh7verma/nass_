package app.com.esenatenigeria.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import app.com.esenatenigeria.R;
import app.com.esenatenigeria.adapters.ProfileCommitteeAdapter;
import app.com.esenatenigeria.adapters.ProfileEducationAdapter;
import app.com.esenatenigeria.adapters.ProfilePreviousOfficesAdapter;
import app.com.esenatenigeria.customviews.RoundCorners;
import app.com.esenatenigeria.dialogs.ContactUsDialog;
import app.com.esenatenigeria.interfaces.Interfaces;
import app.com.esenatenigeria.model.SenatorDetailModel;
import app.com.esenatenigeria.network.RetrofitClient;
import app.com.esenatenigeria.utils.Consts;
import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by dev on 24/4/18.
 */

public class ProfileFragment extends BaseFragment implements Interfaces.DialogClick {


    @SuppressLint("StaticFieldLeak")
    static ProfileFragment fragment;

    @SuppressLint("StaticFieldLeak")
    static Context mContext;
    static Bundle args = new Bundle();

    @BindView(R.id.img_profile)
    ImageView imgProfile;
    @BindView(R.id.txt_name)
    TextView txtName;
    @BindView(R.id.txt_position)
    TextView txtPosition;
    @BindView(R.id.txt_age)
    TextView txtAge;
    @BindView(R.id.txt_constituency)
    TextView txtConstituency;
    @BindView(R.id.txt_state)
    TextView txtState;
    @BindView(R.id.txt_chamber)
    TextView txtChamber;
    @BindView(R.id.txt_party)
    TextView txtParty;
    @BindView(R.id.txt_constituency_phone)
    TextView txtConstituencyPhone;
    @BindView(R.id.txt_constituency_address)
    TextView txt_constituency_address;
    @BindView(R.id.txt_email)
    TextView txt_email;

    @BindView(R.id.ll_committee_empty)
    LinearLayout llCommitteeEmpty;

    @BindView(R.id.rv_profile_committee)
    RecyclerView rvCommittee;

    @BindView(R.id.ll_education_empty)
    LinearLayout llEducationEmpty;
    @BindView(R.id.cv_education)
    CardView cvEducation;
    @BindView(R.id.txt_education_title)
    TextView txtEducationTitle;
    @BindView(R.id.rv_education)
    RecyclerView rvEducation;

    @BindView(R.id.ll_previous_office_empty)
    LinearLayout llOfficesEmpty;
    @BindView(R.id.cv_previous_office)
    CardView cvPreviousOffice;
    @BindView(R.id.txt_previous_office_title)
    TextView txtPreviousOfficeTitle;
    @BindView(R.id.rv_previous_offices)
    RecyclerView rvPreviousOffices;

    @BindView(R.id.cv_award)
    CardView cv_award;
    @BindView(R.id.txt_award_title)
    TextView txt_award_title;
    @BindView(R.id.txt_interests)
    TextView txtInterests;
    @BindView(R.id.txt_achievements)
    TextView txtAchievements;
    @BindView(R.id.txt_awards)
    TextView txtAwards;

    @BindView(R.id.txt_show_more)
    TextView txtShowMore;
    @BindView(R.id.txt_contact)
    TextView txtContact;

    ProfileCommitteeAdapter mAdapter;
    ProfilePreviousOfficesAdapter mAdapterOffices;
    ProfileEducationAdapter mAdapterEducation;


    SenatorDetailModel.DataBean mData = new SenatorDetailModel.DataBean();

    public static ProfileFragment newInstance(Context context, TextView textView) {
        fragment = new ProfileFragment();
        mContext = context;
        textView.setText(mContext.getResources().getString(R.string.profile));
        return fragment;
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_profile;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        hitApi();
    }

    @Override
    protected void onCreateStuff() {
        args = fragment.getArguments();
        rvCommittee.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        rvCommittee.setNestedScrollingEnabled(false);

        rvEducation.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        rvEducation.setNestedScrollingEnabled(false);

        rvPreviousOffices.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        rvPreviousOffices.setNestedScrollingEnabled(false);

        ContactUsDialog.DialogClickInterface(this);
    }

    @Override
    protected void initListeners() {

    }

    void hitApi() {
        mData = mRoomDb.daoAccess().getSenatorsSingleRecord(args.getInt(Consts.DETAIL_ID));
        setData();
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
//                    toast(mContext.getResources().getString(R.string.something_went_wrong));
//                }
//            });
//        } else {
//            showInternetDialog();
//        }

    }

    void setData() {
        if (mData != null) {
            if (mData.getProfilePicURL() != null) {
                if (!TextUtils.isEmpty(mData.getProfilePicURL().getOriginal())) {
                    Picasso.with(mContext)
                            .load(mData.getProfilePicURL().getOriginal())
                            .transform(new RoundCorners())
                            .resize((int) (mHeight * 0.13), (int) (mHeight * 0.13))
                            .placeholder(R.mipmap.ic_img_ph)
                            .error(R.mipmap.ic_img_ph)
                            .centerCrop()
                            .into(imgProfile);
                } else {
                    Picasso.with(mContext)
                            .load(R.mipmap.ic_img_ph)
                            .into(imgProfile);
                }
            }

            if (!TextUtils.isEmpty(mData.getName()))
                txtName.setText(mData.getName());

            if (!TextUtils.isEmpty(mData.getPosition()))
                txtPosition.setText(encode.decrypt(mData.getPosition()));

            if (!TextUtils.isEmpty(mData.getAge()))
                txtAge.setText(encode.decrypt(mData.getAge()));

            if (!TextUtils.isEmpty(mData.getConstituency()))
                txtConstituency.setText(mData.getConstituency());

            if (!TextUtils.isEmpty(mData.getState()))
                txtState.setText(mData.getState());

            if (!TextUtils.isEmpty(mData.getChamber()))
                txtChamber.setText(encode.decrypt(mData.getChamber()));

            if (!TextUtils.isEmpty(mData.getParty()))
                txtParty.setText(encode.decrypt(mData.getParty()));

            if (!TextUtils.isEmpty(mData.getPhoneNo()))
                txtConstituencyPhone.setText(encode.decrypt(mData.getPhoneNo()));

            if (!TextUtils.isEmpty(mData.getAddress()))
                txt_constituency_address.setText(encode.decrypt(mData.getAddress()));

            if (!TextUtils.isEmpty(mData.getEmail()))
                txt_email.setText(encode.decrypt(mData.getEmail()));

            if (mData.getCommittee() != null && mData.getCommittee().size() > 0) {
                llCommitteeEmpty.setVisibility(View.GONE);
                mAdapter = new ProfileCommitteeAdapter(getActivity(), mData.getCommittee());
                rvCommittee.setAdapter(mAdapter);
            }

            if (mData.getEducation() != null && mData.getEducation().size() > 0) {
                llEducationEmpty.setVisibility(View.GONE);
                mAdapterEducation = new ProfileEducationAdapter(getActivity(), mData.getEducation());
                rvEducation.setAdapter(mAdapterEducation);
            }

            if (mData.getPreviousOffice() != null && mData.getPreviousOffice().size() > 0) {
                llOfficesEmpty.setVisibility(View.GONE);
                mAdapterOffices = new ProfilePreviousOfficesAdapter(getActivity(), mData.getPreviousOffice());
                rvPreviousOffices.setAdapter(mAdapterOffices);
            }

            if (mData.getAchievementAndAwards() != null) {
                if (!TextUtils.isEmpty(mData.getAchievementAndAwards().getAwards()))
                    txtAwards.setText(encode.decrypt(mData.getAchievementAndAwards().getAwards()));
                if (!TextUtils.isEmpty(mData.getAchievementAndAwards().getInterests()))
                    txtInterests.setText(encode.decrypt(mData.getAchievementAndAwards().getInterests()));
                if (!TextUtils.isEmpty(mData.getAchievementAndAwards().getAchievements()))
                    txtAwards.setText(encode.decrypt(mData.getAchievementAndAwards().getAchievements()));
            }

        }
    }

    @OnClick(R.id.txt_show_more)
    void showMore() {
        txtShowMore.setVisibility(View.GONE);
        txtContact.setVisibility(View.VISIBLE);
        showEducation();
        showPreviousOffices();
        showAwards();
    }

    @OnClick(R.id.txt_contact)
    void contactUs() {
        new ContactUsDialog(mContext).showDialog();
    }

    void showEducation() {
        txtEducationTitle.setVisibility(View.VISIBLE);
        cvEducation.setVisibility(View.VISIBLE);
    }

    void showPreviousOffices() {
        txtPreviousOfficeTitle.setVisibility(View.VISIBLE);
        cvPreviousOffice.setVisibility(View.VISIBLE);
    }

    void showAwards() {
        txt_award_title.setVisibility(View.VISIBLE);
        cv_award.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDialogClick(String name, String email, String text) {
        hideKeyboard(getActivity());
        hitSendApi(name, email, text);
    }

    void hitSendApi(String name, String email, String text) {
        hideKeyboard(getActivity());
        if (connectedToInternet()) {
            Call<SenatorDetailModel> call = RetrofitClient.getInstance().sendEmail(encode.encrypt(String.valueOf(mData.getUser_id())),
                    encode.encrypt(text.trim()),
                    encode.encrypt(name.trim()),
                    encode.encrypt(email.trim()));
            call.enqueue(new Callback<SenatorDetailModel>() {
                @Override
                public void onResponse(Call<SenatorDetailModel> call, Response<SenatorDetailModel> response) {
                    hideKeyboard(getActivity());
                    Toast.makeText(getActivity(), "Email Sent", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<SenatorDetailModel> call, Throwable t) {
                    t.printStackTrace();
                    hideKeyboard(getActivity());
                }
            });
        } else {
            showInternetDialog();
        }
    }

}

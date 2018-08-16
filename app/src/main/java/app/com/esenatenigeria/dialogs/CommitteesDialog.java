package app.com.esenatenigeria.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import app.com.esenatenigeria.R;
import app.com.esenatenigeria.customviews.FlowLayout;
import app.com.esenatenigeria.model.CommitteeModel;
import app.com.esenatenigeria.utils.Encode;
import butterknife.BindView;
import butterknife.ButterKnife;

public class CommitteesDialog implements View.OnClickListener {

    static private Dialog mDialog;
    @BindView(R.id.img_cross)
    ImageView imgCross;
    @BindView(R.id.fl_interests)
    FlowLayout flInterests;
    @BindView(R.id.txt_committee_name)
    TextView txtCommitteeName;
    @BindView(R.id.txt_chairman_name)
    TextView txtChairmanName;
    @BindView(R.id.txt_vice_chairman_name)
    TextView txtViceChairmanName;
    @BindView(R.id.txt_member_title)
    TextView txtMemberTitle;

    Encode encode;
    CommitteeModel.DataBean mData;
    private Context mContext;
    private ArrayList<CommitteeModel.DataBean.MemberBean> mMembersArray = new ArrayList<>();

    public CommitteesDialog(Context context, CommitteeModel.DataBean dataBean) {
        mContext = context;
        mData = dataBean;
    }

    public void showDialog() {
        View view;
        if (mDialog == null) {
            mDialog = new Dialog(mContext);
            mDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            view = View.inflate(mContext, R.layout.dialog_committees, null);
            mDialog.setContentView(view);
            mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            mDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            mDialog.getWindow().getAttributes().windowAnimations = R.style.DialogBottomAnimation;
            mDialog.show();

            encode = new Encode();
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
        if (mData.getMembers() != null) {
            txtMemberTitle.setText("Members" + " (" + mData.getMembers().size() + ")");
            setMembers();
        } else {
            txtMemberTitle.setText("Members" + " (0)");
        }

        txtCommitteeName.setText(mData.getName());

        if (!TextUtils.isEmpty(mData.getChairman().getName()))
            txtChairmanName.setText(mData.getChairman().getName());

        if (!TextUtils.isEmpty(mData.getViceChairman().getName()))
            txtViceChairmanName.setText(mData.getViceChairman().getName());


    }

    private void setMembers() {

        if (mMembersArray != null) {
            mMembersArray.clear();
        }
        mMembersArray.addAll(mData.getMembers());

        if (mMembersArray.size() > 0) {
            flInterests.removeAllViews();
            for (CommitteeModel.DataBean.MemberBean item : mMembersArray) {
                flInterests.addView(inflateView(encode.decrypt(item.getName())));
            }
        }
    }

    private void initListeners() {
        imgCross.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_cross:
                dismiss();
                break;
        }
    }

    public void dismiss() {
        mDialog.dismiss();
        mDialog = null;
    }


    private View inflateView(final String amenities) {
        View interestChip = LayoutInflater.from(mContext).inflate(R.layout.layout_amenities, null, false);

        FlowLayout.LayoutParams innerParms = new FlowLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final LinearLayout llMain = (LinearLayout) interestChip.findViewById(R.id.ll_main);
        llMain.setLayoutParams(innerParms);

        final TextView txtAmenities = (TextView) interestChip.findViewById(R.id.txt_amenities);
        txtAmenities.setText(amenities);
        txtAmenities.setTextColor(Color.BLACK);

        return interestChip;
    }
}

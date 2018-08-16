package app.com.esenatenigeria.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import app.com.esenatenigeria.R;
import app.com.esenatenigeria.model.SenatorDetailModel;
import app.com.esenatenigeria.utils.Encode;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dev on 11/5/18.
 */

public class ProfileEducationAdapter extends RecyclerView.Adapter<ProfileEducationAdapter.ViewHolder> {

    Context context;
    List<SenatorDetailModel.DataBean.EducationBean> mData = new ArrayList<>();
    Encode encode;

    public ProfileEducationAdapter(Context context, List<SenatorDetailModel.DataBean.EducationBean> array) {
        this.context = context;
        mData = array;
        encode = new Encode();
    }


    @Override
    public ProfileEducationAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_profile_list, parent, false);
        ProfileEducationAdapter.ViewHolder vhItem = new ProfileEducationAdapter.ViewHolder(v);
        return vhItem;
    }

    @Override
    public void onBindViewHolder(final ProfileEducationAdapter.ViewHolder holder, int position) {

        if (!TextUtils.isEmpty(mData.get(position).getInstitution()))
            holder.txtInstitution.setText(encode.decrypt(mData.get(position).getInstitution()));
        if (!TextUtils.isEmpty(mData.get(position).getDateOfGraduation()))
            holder.txtDateOfGraduation.setText(encode.decrypt(mData.get(position).getDateOfGraduation()));
        if (!TextUtils.isEmpty(mData.get(position).getCertificate()))
            holder.txtCertificate.setText(encode.decrypt(mData.get(position).getCertificate()));
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txt_first)
        TextView txtInstitution;
        @BindView(R.id.txt_second)
        TextView txtDateOfGraduation;
        @BindView(R.id.txt_third)
        TextView txtCertificate;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

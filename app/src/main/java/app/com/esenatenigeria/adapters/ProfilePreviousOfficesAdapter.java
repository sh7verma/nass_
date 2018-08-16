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

public class ProfilePreviousOfficesAdapter extends RecyclerView.Adapter<ProfilePreviousOfficesAdapter.ViewHolder> {

    Context context;
    List<SenatorDetailModel.DataBean.PreviousOfficeBean> mData = new ArrayList<>();
    Encode encode;

    public ProfilePreviousOfficesAdapter(Context context, List<SenatorDetailModel.DataBean.PreviousOfficeBean> array) {
        this.context = context;
        mData = array;
        encode = new Encode();
    }


    @Override
    public ProfilePreviousOfficesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_profile_list, parent, false);
        ProfilePreviousOfficesAdapter.ViewHolder vhItem = new ProfilePreviousOfficesAdapter.ViewHolder(v);
        return vhItem;
    }

    @Override
    public void onBindViewHolder(final ProfilePreviousOfficesAdapter.ViewHolder holder, int position) {
        if (!TextUtils.isEmpty(mData.get(position).getFrom()))
            holder.txtFrom.setText(encode.decrypt(mData.get(position).getFrom()));
        if (!TextUtils.isEmpty(mData.get(position).getTo()))
            holder.txtTo.setText(encode.decrypt(mData.get(position).getTo()));
        if (!TextUtils.isEmpty(mData.get(position).getRank()))
            holder.txtRank.setText(encode.decrypt(mData.get(position).getRank()));
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txt_first)
        TextView txtFrom;
        @BindView(R.id.txt_second)
        TextView txtTo;
        @BindView(R.id.txt_third)
        TextView txtRank;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

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

public class ProfileCommitteeAdapter extends RecyclerView.Adapter<ProfileCommitteeAdapter.ViewHolder> {

    Context context;
    List<SenatorDetailModel.DataBean.Committee> mData = new ArrayList<>();
    Encode encode;

    public ProfileCommitteeAdapter(Context context, List<SenatorDetailModel.DataBean.Committee> array) {
        this.context = context;
        mData = array;
        encode = new Encode();
    }


    @Override
    public ProfileCommitteeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_profile_committee, parent, false);
        ProfileCommitteeAdapter.ViewHolder vhItem = new ProfileCommitteeAdapter.ViewHolder(v);
        return vhItem;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        if (!TextUtils.isEmpty(mData.get(position).getPosition()))
            holder.txtPosition.setText(encode.decrypt(mData.get(position).getPosition()));

        if (!TextUtils.isEmpty(mData.get(position).getName()))
            holder.txtName.setText(encode.decrypt(mData.get(position).getName()));
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txt_committee_position)
        TextView txtPosition;
        @BindView(R.id.txt_committee_position_name)
        TextView txtName;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

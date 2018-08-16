package app.com.esenatenigeria.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import app.com.esenatenigeria.R;
import app.com.esenatenigeria.interfaces.Interfaces;
import app.com.esenatenigeria.model.SenatorDetailModel;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dev on 23/4/18.
 */

public class SenatorDetailsAdapter extends RecyclerView.Adapter<SenatorDetailsAdapter.ViewHolder> {

    static Interfaces.AdapterItemClick mClick;
    Context context;
    ArrayList<SenatorDetailModel.DataBean> mData;

    int mHeight;

    public SenatorDetailsAdapter(Context context, ArrayList<SenatorDetailModel.DataBean> mSenatorArray, int mHeight) {
        this.context = context;
        mData = mSenatorArray;
        this.mHeight = mHeight;
    }

    public static void ListenerSenatorDetailInterface(Interfaces.AdapterItemClick click) {
        mClick = click;
    }

    @Override
    public SenatorDetailsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_senator_details, parent, false);
        SenatorDetailsAdapter.ViewHolder vhItem = new SenatorDetailsAdapter.ViewHolder(v);
        return vhItem;
    }

    @Override
    public void onBindViewHolder(final SenatorDetailsAdapter.ViewHolder holder, int position) {

        if (!TextUtils.isEmpty(mData.get(position).getState()))
            holder.txt_state.setText(mData.get(position).getState());
        else
            holder.txt_state.setText(context.getResources().getString(R.string.no_data));

        if (!TextUtils.isEmpty(mData.get(position).getConstituency()))
            holder.txt_constituency.setText(mData.get(position).getConstituency());
        else
            holder.txt_constituency.setText(context.getResources().getString(R.string.no_data));

        holder.llOfficer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClick.onItemClick(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ll_officer)
        LinearLayout llOfficer;
        @BindView(R.id.txt_state)
        TextView txt_state;
        @BindView(R.id.txt_constituency)
        TextView txt_constituency;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}



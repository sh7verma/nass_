package app.com.esenatenigeria.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import app.com.esenatenigeria.R;
import app.com.esenatenigeria.dialogs.CommitteesDialog;
import app.com.esenatenigeria.model.CommitteeModel;
import app.com.esenatenigeria.utils.Encode;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dev on 19/4/18.
 */

public class CommitteesAdapter extends RecyclerView.Adapter<CommitteesAdapter.ViewHolder> {

    Encode encode;
    private Context context;
    private ArrayList<CommitteeModel.DataBean> mData = new ArrayList<>();

    public CommitteesAdapter(Context context, ArrayList<CommitteeModel.DataBean> array) {
        this.context = context;
        encode = new Encode();
        mData = array;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_committess, parent, false);
        return new CommitteesAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final CommitteesAdapter.ViewHolder holder, int position) {

        holder.txtCommiteeName.setText(mData.get(position).getName());

        if (!TextUtils.isEmpty(mData.get(position).getChairman().getName())) {
            holder.txtChairmanName.setText(mData.get(position).getChairman().getName());
        } else {
            holder.txtChairmanName.setText(context.getResources().getString(R.string.no_data));
        }


        holder.cvMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new CommitteesDialog(context, mData.get(holder.getAdapterPosition())).showDialog();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_arrow_down)
        ImageView imgArrowDown;
        @BindView(R.id.cv_committee)
        CardView cvMain;

        @BindView(R.id.txt_committee_name)
        TextView txtCommiteeName;

        @BindView(R.id.txt_chairman_name)
        TextView txtChairmanName;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

}

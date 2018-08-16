package app.com.esenatenigeria.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import app.com.esenatenigeria.R;
import app.com.esenatenigeria.activities.RecentActDetailActivity;
import app.com.esenatenigeria.customviews.RoundCorners;
import app.com.esenatenigeria.model.RecentModel;
import app.com.esenatenigeria.utils.Consts;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dev on 18/4/18.
 */

public class DashBoardRecentEventsAdapter extends RecyclerView.Adapter<DashBoardRecentEventsAdapter.ViewHolder> {

    Context context;
    ArrayList<RecentModel> mData = new ArrayList<>();
    int mHeight;

    public DashBoardRecentEventsAdapter(Context context, ArrayList<RecentModel> array, int height) {
        this.context = context;
        mData = array;
        mHeight = height;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dashboard_recent_events, parent, false);
        ViewHolder vhItem = new ViewHolder(v);
        return vhItem;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        holder.txtTitle.setText(mData.get(position).getTitle());
        holder.txtDescription.setText(mData.get(position).getDescription());

        if (mData.get(position).getImage() != null) {
            Picasso.with(context)
                    .load(mData.get(position).getImage())
                    .resize((int) (mHeight * 0.15), (int) (mHeight * 0.13))
                    .centerCrop()
                    .placeholder(R.mipmap.ic_img_ph)
                    .into(holder.imgRecent);
        } else {
            Picasso.with(context)
                    .load(R.mipmap.ic_img_ph)
                    .into(holder.imgRecent);
        }

        holder.cvRecentActDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, RecentActDetailActivity.class);
                intent.putExtra(Consts.RECENT_DATA,mData.get(holder.getAdapterPosition()));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.cv_recent_act)
        CardView cvRecentActDetail;

        @BindView(R.id.img_recent)
        ImageView imgRecent;
        @BindView(R.id.txt_title)
        TextView txtTitle;
        @BindView(R.id.txt_description)
        TextView txtDescription;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

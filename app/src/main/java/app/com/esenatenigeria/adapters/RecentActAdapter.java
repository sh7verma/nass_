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
import app.com.esenatenigeria.model.RecentModel;
import app.com.esenatenigeria.utils.Consts;

/**
 * Created by dev on 23/4/18.
 */

public class RecentActAdapter extends RecyclerView.Adapter<RecentActAdapter.ViewHolder> {

    Context context;
    private int HEADER = 0;
    private int ITEM = 1;
    ArrayList<RecentModel> mData = new ArrayList<>();
    private int mHeight;
    private int holderId = -1;

    public RecentActAdapter(Context context, ArrayList<RecentModel> array, int height) {
        this.context = context;
        mData = array;
        mHeight = height;
    }


    @Override
    public RecentActAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = null;
        RecentActAdapter.ViewHolder vhItem = null;
        if (viewType == HEADER) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recent_activities_header, parent, false);
            vhItem = new RecentActAdapter.ViewHolder(v, HEADER);
        } else {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dashboard_recent_events, parent, false);
            vhItem = new RecentActAdapter.ViewHolder(v, 1);
        }
        return vhItem;
    }

    @Override
    public void onBindViewHolder(final RecentActAdapter.ViewHolder holder, int position) {

        if (holderId == HEADER) {
            holder.cvRecentActHeader.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, RecentActDetailActivity.class);
                    intent.putExtra(Consts.RECENT_DATA, mData.get(holder.getAdapterPosition()));
                    context.startActivity(intent);
                }
            });
            if (mData.get(position).getImage() != null) {
                Picasso.with(context)
                        .load(mData.get(position).getImage())
                        .placeholder(R.mipmap.ic_img_ph)
                        .into(holder.img_header_recent);
            } else {
                Picasso.with(context)
                        .load(R.mipmap.ic_img_ph)
                        .into(holder.img_header_recent);
            }
            holder.txt_header_description.setText(mData.get(position).getDescription());
            holder.txt_header_title.setText(mData.get(position).getTitle());
        } else {
            if (mData.get(position).getImage() != null) {
                Picasso.with(context)
                        .load(mData.get(position).getImage())
                        .resize((int) (mHeight * 0.15), (int) (mHeight * 0.13))
                        .centerCrop()
                        .placeholder(R.mipmap.ic_img_ph)
                        .into(holder.img_recent);
            } else {
                Picasso.with(context)
                        .load(R.mipmap.ic_img_ph)
                        .into(holder.img_recent);
            }
            holder.txt_description.setText(mData.get(position).getDescription());
            holder.txt_title.setText(mData.get(position).getTitle());

            holder.cvRecentActs.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, RecentActDetailActivity.class);
                    intent.putExtra(Consts.RECENT_DATA, mData.get(holder.getAdapterPosition()));
                    context.startActivity(intent);
                }
            });
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return HEADER;
        } else {
            return position;
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CardView cvRecentActHeader;
        CardView cvRecentActs;

        ImageView img_header_recent;
        TextView txt_header_description;
        TextView txt_header_title;

        ImageView img_recent;
        TextView txt_title;
        TextView txt_description;

        public ViewHolder(View itemView, int viewType) {
            super(itemView);
            if (viewType == HEADER) {
                cvRecentActHeader = itemView.findViewById(R.id.cv_recent_act_header);

                img_header_recent = itemView.findViewById(R.id.img_header_recent);
                txt_header_description = itemView.findViewById(R.id.txt_header_description);
                txt_header_title = itemView.findViewById(R.id.txt_header_title);

                holderId = HEADER;
            } else {
                cvRecentActs = itemView.findViewById(R.id.cv_recent_act);

                img_recent = itemView.findViewById(R.id.img_recent);
                txt_title = itemView.findViewById(R.id.txt_title);
                txt_description = itemView.findViewById(R.id.txt_description);

                holderId = ITEM;
            }

        }
    }
}

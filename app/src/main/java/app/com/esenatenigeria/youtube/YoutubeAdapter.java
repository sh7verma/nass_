package app.com.esenatenigeria.youtube;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import app.com.esenatenigeria.R;
import app.com.esenatenigeria.utils.Encode;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dev on 16/5/18.
 */

public class YoutubeAdapter extends RecyclerView.Adapter<YoutubeAdapter.ViewHolder> {

    Context mContext;
    ArrayList<YoutubeModel.ItemsBean> mData = new ArrayList<>();
    Encode encode;
    int mHeight;

    public YoutubeAdapter(Context context, ArrayList<YoutubeModel.ItemsBean> array, int mHeight) {
        mContext = context;
        mData = array;
        this.mHeight = mHeight;
        encode = new Encode();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_youtube, parent, false);
        YoutubeAdapter.ViewHolder vhItem = new YoutubeAdapter.ViewHolder(v);
        return vhItem;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        if (mData.get(position).getSnippet().getThumbnails().getHigh() != null) {
            if (!TextUtils.isEmpty(mData.get(position).getSnippet().getThumbnails().getHigh().getUrl())) {
                Picasso.with(mContext)
                        .load(mData.get(position).getSnippet().getThumbnails().getHigh().getUrl())
                        .resize((int) (mHeight * 0.15), (int) (mHeight * 0.12))
                        .placeholder(R.mipmap.ic_img_ph)
                        .into(holder.img_video);
            }
        } else {
            Picasso.with(mContext)
                    .load(R.mipmap.ic_img_ph)
                    .into(holder.img_video);
        }

        holder.txt_title.setText(mData.get(position).getSnippet().getTitle());
        holder.txt_description.setText(mData.get(position).getSnippet().getDescription());

        holder.cvYoutube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), VideoActivity.class);
                intent.putExtra("videoId", mData.get(holder.getAdapterPosition()).getId().getVideoId());
                view.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.cv_youtube)
        CardView cvYoutube;
        @BindView(R.id.img_video)
        ImageView img_video;
        @BindView(R.id.txt_title)
        TextView txt_title;
//        @BindView(R.id.txt_id)
//        TextView txt_id;
//        @BindView(R.id.tv_url)
//        TextView txt_url;
        @BindView(R.id.txt_description)
        TextView txt_description;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

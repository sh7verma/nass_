package app.com.esenatenigeria.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import app.com.esenatenigeria.R;
import app.com.esenatenigeria.interfaces.Interfaces;
import app.com.esenatenigeria.model.ActsModel;
import app.com.esenatenigeria.utils.Consts;
import app.com.esenatenigeria.utils.CustomTypefaceSpan;
import app.com.esenatenigeria.utils.Encode;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dev on 19/4/18.
 */

public class ActsAdapter extends RecyclerView.Adapter<ActsAdapter.ViewHolder> {

    private static Interfaces.ActsAdapterItemClick startDownload;
    Context context;
    ArrayList<ActsModel.DataBean> mData = new ArrayList<>();
    Encode encode;
    Typeface typefaceBold,typefaceRegular;


    public ActsAdapter(Context context, ArrayList<ActsModel.DataBean> array) {
        this.context = context;
        mData = array;
        encode = new Encode();
    }

    public static void ListenerDownloadPdfInterface(Interfaces.ActsAdapterItemClick open) {
        startDownload = open;
    }

    @Override
    public ActsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_acts, parent, false);
        ActsAdapter.ViewHolder vhItem = new ActsAdapter.ViewHolder(v);
        return vhItem;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        holder.txtTitle.setText(mData.get(position).getName());
        holder.txtDetail.setText(mData.get(position).getSummary());
        holder.txtSession.setText(mData.get(position).getSession() + " |");
        holder.txtParliament.setText(mData.get(position).getParliament() + " | ");
        holder.txtDate.setText(" " + Consts.timeFilterChange(mData.get(position).getDate()));

        typefaceBold = Typeface.createFromAsset(context
                .getAssets(), "fonts/ProximaNova-Bold_0.otf");
        typefaceRegular = Typeface.createFromAsset(context
                .getAssets(), "fonts/ProximaNova-Regular.otf");

        String dateValue = ""+Consts.timeFilterChange(mData.get(position).getDate());
        String finalValue = mData.get(position).getChamber() + " | Acts | " + dateValue;
        SpannableString ss = new SpannableString(finalValue);
        ss.setSpan(new CustomTypefaceSpan("", typefaceBold), 0, mData.get(position).getChamber().length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        ss.setSpan(new CustomTypefaceSpan("", typefaceRegular), mData.get(position).getChamber().length() ,mData.get(position).getChamber().length()+10, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        ss.setSpan(new CustomTypefaceSpan("", typefaceBold), mData.get(position).getChamber().length() + 10, finalValue.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        holder.txtChamber.setText(ss);

        holder.cvActs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startDownload.onActsItemClick(holder.getAdapterPosition(),mData.get(holder.getAdapterPosition()));
            }
        });
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.cv_act)
        CardView cvActs;

        @BindView(R.id.txt_title)
        TextView txtTitle;
        @BindView(R.id.txt_detail)
        TextView txtDetail;
        @BindView(R.id.txt_parliament)
        TextView txtParliament;
        @BindView(R.id.txt_session)
        TextView txtSession;
        @BindView(R.id.txt_date)
        TextView txtDate;
        @BindView(R.id.txt_chamber)
        TextView txtChamber;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

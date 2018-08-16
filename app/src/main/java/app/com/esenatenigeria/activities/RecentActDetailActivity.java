package app.com.esenatenigeria.activities;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import app.com.esenatenigeria.R;
import app.com.esenatenigeria.model.RecentModel;
import app.com.esenatenigeria.utils.Consts;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dev on 24/4/18.
 */

public class RecentActDetailActivity extends BaseActivity {
    RecentModel mData;

    @BindView(R.id.img_recent)
    ImageView imgRecent;
    @BindView(R.id.txt_act_description)
    TextView txt_act_description;
    @BindView(R.id.txt_act_title)
    TextView txt_act_title;

    @Override
    protected int getContentView() {
        return R.layout.activity_recent_acts_details;
    }

    @Override
    protected void onCreateStuff() {
        mData = getIntent().getParcelableExtra(Consts.RECENT_DATA);

        txt_act_description.setText(mData.getDescription());
        txt_act_title.setText(mData.getTitle());
        if (mData.getImage() != null) {
            Picasso.with(mContext)
                    .load(mData.getImage())
                    .placeholder(R.mipmap.ic_img_ph)
                    .into(imgRecent);
        } else {
            Picasso.with(mContext)
                    .load(R.mipmap.ic_img_ph)
                    .into(imgRecent);
        }
    }

    @Override
    protected void initUI() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected Context getContext() {
        return this;
    }

    @OnClick(R.id.img_back)
    void backPress() {
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        backPress();
    }

    @Override
    public void onClick(View view) {

    }
}

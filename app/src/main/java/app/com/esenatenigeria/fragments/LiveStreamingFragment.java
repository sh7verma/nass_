package app.com.esenatenigeria.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import app.com.esenatenigeria.R;
import app.com.esenatenigeria.network.RetrofitClient;
import app.com.esenatenigeria.utils.Consts;
import app.com.esenatenigeria.youtube.YoutubeAdapter;
import app.com.esenatenigeria.youtube.YoutubeModel;
import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by dev on 16/5/18.
 */

public class LiveStreamingFragment extends BaseFragment {

    @SuppressLint("StaticFieldLeak")
    static LiveStreamingFragment fragment;
    @SuppressLint("StaticFieldLeak")
    static Context mContext;

    @BindView(R.id.rv_videos)
    RecyclerView rvVideos;

    @BindView(R.id.ed_search_bar)
    EditText edSearchBar;
    @BindView(R.id.img_search_cross)
    ImageView imgSearchCross;

    @BindView(R.id.progress)
    ProgressBar progressBar;
    @BindView(R.id.ll_no_result)
    LinearLayout llNoResult;

    ArrayList<YoutubeModel.ItemsBean> mVideoArrayList;
    ArrayList<YoutubeModel.ItemsBean> mVideoArrayArrayTemp = new ArrayList<>();

    YoutubeAdapter mAdapter;
    YoutubeModel mModel;
    LinearLayoutManager mLayoutManager;
    private boolean loading = true;
    boolean isSearched = false;


    public static LiveStreamingFragment newInstance(Context context, TextView txtTitle) {
        fragment = new LiveStreamingFragment();
        mContext = context;
        txtTitle.setText(mContext.getResources().getString(R.string.live_streaming));
        return fragment;
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_live_streaming;
    }

    @Override
    protected void onCreateStuff() {
        mVideoArrayList = new ArrayList<>();
        mModel = new YoutubeModel();
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvVideos.setLayoutManager(mLayoutManager);
        rvVideos.setNestedScrollingEnabled(false);
        mAdapter = new YoutubeAdapter(getActivity(), mVideoArrayList, mHeight);
        rvVideos.setAdapter(mAdapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        hitYoutubeApi();
    }

    @Override
    protected void initListeners() {

        edSearchBar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER))
                        || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    Log.i("TAG", "Enter pressed");

                    if (!TextUtils.isEmpty(edSearchBar.getText())) {
                        hideKeyboard(getActivity());
                        if (mVideoArrayArrayTemp != null) {
                            mVideoArrayArrayTemp.clear();
                        }
                        // Search is Working only on the Items in the array not on the whole data of the channel.
                        new SearchAsync().execute();
                    }
                }
                return false;
            }
        });

        edSearchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0) {
                    imgSearchCross.setImageDrawable(getResources().getDrawable(R.mipmap.ic_cancel_search));
                    loading = false;
                } else {
                    loading = true;
                    isSearched=false;
                    imgSearchCross.setImageDrawable(getResources().getDrawable(R.mipmap.ic_search));
                    if (mVideoArrayArrayTemp != null) {
                        mVideoArrayArrayTemp.clear();
                    }
                    mAdapter = new YoutubeAdapter(getActivity(), mVideoArrayList, mHeight);
                    rvVideos.setAdapter(mAdapter);
                    if (mVideoArrayList.size() == 0) {
                        llNoResult.setVisibility(View.VISIBLE);
                    } else {
                        llNoResult.setVisibility(View.GONE);
                    }
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        rvVideos.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    int visibleItemCount = mLayoutManager.getChildCount();
                    int totalItemCount = mLayoutManager.getItemCount();
                    int firstVisibleItemPosition = mLayoutManager.findFirstVisibleItemPosition();
                    if (loading) {
                        if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                                && firstVisibleItemPosition >= 0 && mModel.getNextPageToken() != null) {
                            hitYoutubeApi();
                        }
                    }
                }
            }
        });

    }

    @OnClick(R.id.img_search_cross)
    void searchClear() {
        edSearchBar.setText("");
    }

    void hitYoutubeApi() {
        String YOUTUBE = "";

        if (TextUtils.isEmpty(mModel.getNextPageToken()) && TextUtils.isEmpty(mModel.getPrevPageToken())) {
            edSearchBar.setEnabled(false);
            YOUTUBE = "youtube/v3/search?part=snippet&channelId=" + Consts.YOUTUBE_CHANNEL_ID + "&maxResults=" + "50" +
                    "&order=date&key=" + Consts.YOUTUBE_KEY;
        } else if (!TextUtils.isEmpty(mModel.getNextPageToken())) {
            YOUTUBE = "youtube/v3/search?part=snippet&channelId=" + Consts.YOUTUBE_CHANNEL_ID + "&maxResults=" + "50" +
                    "&pageToken=" + mModel.getNextPageToken() + "&order=date&key=" + Consts.YOUTUBE_KEY;
        }

        if (connectedToInternet()) {
            loading = false;
            Call<YoutubeModel> call = RetrofitClient.getYoutubeInstance().getYoutubeVideos(YOUTUBE);
            call.enqueue(new Callback<YoutubeModel>() {
                @Override
                public void onResponse(Call<YoutubeModel> call, retrofit2.Response<YoutubeModel> response) {
                    try {
                        if (response != null) {
                            mModel = response.body();
                            for (int i = 0; i < response.body().getItems().size(); i++) {
                                if (response.body().getItems().get(i).getId().getVideoId() != null) {
                                    mVideoArrayList.add(response.body().getItems().get(i));
                                }
                            }
                            progressBar.setVisibility(View.GONE);
                            if (mVideoArrayList.size() == 0) {
                                llNoResult.setVisibility(View.VISIBLE);
                            }
                            mAdapter.notifyDataSetChanged();
                            edSearchBar.setEnabled(true);
                            loading = true;
                        }
                    } catch (Exception e) {
                        toast(mContext.getResources().getString(R.string.something_went_wrong));

                        progressBar.setVisibility(View.GONE);
                        if (mVideoArrayList.size() == 0) {
                            llNoResult.setVisibility(View.VISIBLE);
                        }
                        mAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Call<YoutubeModel> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        } else {
            if (mVideoArrayList.size() == 0) {
                llNoResult.setVisibility(View.VISIBLE);
            } else {
                llNoResult.setVisibility(View.GONE);
            }
            showInternetDialog();
            progressBar.setVisibility(View.GONE);
        }
    }

    @SuppressLint("StaticFieldLeak")
    class SearchAsync extends AsyncTask {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            llNoResult.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
            rvVideos.setVisibility(View.GONE);
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            // Search is Working only on the Items in the array not on the whole data of the channel.
            for (int k = 0; k < mVideoArrayList.size(); k++) {
                if (mVideoArrayList.get(k).getSnippet().getTitle().toUpperCase().contains(edSearchBar.getText().toString().toUpperCase())) {
                    mVideoArrayArrayTemp.add(mVideoArrayList.get(k));
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            progressBar.setVisibility(View.GONE);
            rvVideos.setVisibility(View.VISIBLE);
            isSearched=true;
            mAdapter = new YoutubeAdapter(getActivity(), mVideoArrayArrayTemp, mHeight);
            rvVideos.setAdapter(mAdapter);
            if (mVideoArrayArrayTemp.size() == 0) {
                llNoResult.setVisibility(View.VISIBLE);
            } else {
                llNoResult.setVisibility(View.GONE);
            }

        }
    }
}

package app.com.esenatenigeria.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import app.com.esenatenigeria.R;
import app.com.esenatenigeria.fragments.AboutSenateFragment;
import app.com.esenatenigeria.fragments.ActsFragment;
import app.com.esenatenigeria.fragments.BillsFragment;
import app.com.esenatenigeria.fragments.CommitteesFragment;
import app.com.esenatenigeria.fragments.CompositionsFragment;
import app.com.esenatenigeria.fragments.ConstitutionalFragment;
import app.com.esenatenigeria.fragments.DashBoardFragment;
import app.com.esenatenigeria.fragments.LiveStreamingFragment;
import app.com.esenatenigeria.fragments.ProfileFragment;
import app.com.esenatenigeria.fragments.RecentActivitiesFragment;
import app.com.esenatenigeria.fragments.SenateLeadershipFragment;
import app.com.esenatenigeria.fragments.SenatorDetailsFragment;
import app.com.esenatenigeria.interfaces.Interfaces;
import app.com.esenatenigeria.model.DeleteDataModel;
import app.com.esenatenigeria.network.RetrofitClient;
import app.com.esenatenigeria.utils.Consts;
import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LandingActivity extends BaseActivity implements Interfaces.FragmentClick {


    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.img_nav)
    ImageView imgNav;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.nav_view)
    LinearLayout navigationView;

    // bottom bar
    @BindView(R.id.ll_bottom_dashboard)
    LinearLayout ll_bottom_dashboard;
    @BindView(R.id.ll_bottom_bills)
    LinearLayout ll_bottom_bills;
    @BindView(R.id.ll_bottom_committee)
    LinearLayout ll_bottom_committee;
    @BindView(R.id.ll_bottom_acts)
    LinearLayout ll_bottom_acts;
    @BindView(R.id.ll_bottom_live)
    LinearLayout ll_bottom_live;

    // navigation bar
    @BindView(R.id.ll_nav_header)
    LinearLayout ll_nav_header;
    @BindView(R.id.txt_nav_senate_leader)
    TextView txt_nav_senate_leader;
    @BindView(R.id.txt_nav_recent_activities)
    TextView txt_nav_recent_activities;
    @BindView(R.id.txt_nav_about_senate)
    TextView txt_nav_about_senate;
    @BindView(R.id.txt_nav_officers)
    TextView txt_nav_officers;
    @BindView(R.id.txt_nav_constitutional)
    TextView txt_nav_constitutional;
    @BindView(R.id.txt_nav_compositions)
    TextView txt_nav_compositions;
    @BindView(R.id.img_back)
    ImageView imgBack;

    // Current fragment selected
    int mViewSelection = Consts.FRAG_NULL;

    @Override
    protected int getContentView() {
        return R.layout.activity_landing;
    }

    @Override
    protected void initUI() {
    }

    @Override
    protected void onResume() {
        super.onResume();
        hideKeyboard(this);
    }

    @Override
    protected void onCreateStuff() {
        loadFragment(DashBoardFragment.newInstance(this, txtTitle), Consts.FRAG_DASHBOARD);
    }

    @Override
    protected void initListener() {
        DashBoardFragment.ListenerDashBoardLeaderClickInterface(this);
        SenatorDetailsFragment.ListenerSenatorDetailsInterface(this);

        ll_bottom_dashboard.setOnClickListener(this);
        ll_bottom_bills.setOnClickListener(this);
        ll_bottom_committee.setOnClickListener(this);
        ll_bottom_acts.setOnClickListener(this);
        ll_bottom_live.setOnClickListener(this);

        ll_nav_header.setOnClickListener(this);
        txt_nav_senate_leader.setOnClickListener(this);
        txt_nav_recent_activities.setOnClickListener(this);
        txt_nav_about_senate.setOnClickListener(this);
        txt_nav_officers.setOnClickListener(this);
        txt_nav_constitutional.setOnClickListener(this);
        txt_nav_compositions.setOnClickListener(this);
    }

    @Override
    protected Context getContext() {
        return this;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (mViewSelection == Consts.FRAG_DASHBOARD) {
                super.onBackPressed();
            } else {
                loadFragment(DashBoardFragment.newInstance(this, txtTitle), Consts.FRAG_DASHBOARD);
            }
        }
    }

    public void loadFragment(Fragment fragment, int selected) {

        hideKeyboard(this);
        ll_bottom_dashboard.setBackgroundColor(getContext().getResources().getColor(R.color.red_dark));
        ll_bottom_bills.setBackgroundColor(getContext().getResources().getColor(R.color.red_dark));
        ll_bottom_committee.setBackgroundColor(getContext().getResources().getColor(R.color.red_dark));
        ll_bottom_acts.setBackgroundColor(getContext().getResources().getColor(R.color.red_dark));
        ll_bottom_live.setBackgroundColor(getContext().getResources().getColor(R.color.red_dark));

        if (selected == Consts.FRAG_DASHBOARD) {
            ll_bottom_dashboard.setBackgroundColor(getContext().getResources().getColor(R.color.red_dark_selected));
        } else if (selected == Consts.FRAG_BILLS) {
            ll_bottom_bills.setBackgroundColor(getContext().getResources().getColor(R.color.red_dark_selected));
        } else if (selected == Consts.FRAG_COMMITTEE) {
            ll_bottom_committee.setBackgroundColor(getContext().getResources().getColor(R.color.red_dark_selected));
        } else if (selected == Consts.FRAG_ACTS) {
            ll_bottom_acts.setBackgroundColor(getContext().getResources().getColor(R.color.red_dark_selected));
        } else if (selected == Consts.FRAG_LIVE) {
            ll_bottom_live.setBackgroundColor(getContext().getResources().getColor(R.color.red_dark_selected));
        }


        if (selected == Consts.FRAG_PROFILE) {
            imgBack.setVisibility(View.VISIBLE);
            imgNav.setVisibility(View.GONE);
        } else {
            imgBack.setVisibility(View.GONE);
            imgNav.setVisibility(View.VISIBLE);
        }

        if (mViewSelection != selected) {
            mViewSelection = selected;
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.frame_container, fragment);
            transaction.commit();
        }
    }

    @OnClick(R.id.img_nav)
    void openNav() {
        closeDrawer();
    }

    void closeDrawer() {
        hideKeyboard(this);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            drawer.openDrawer(GravityCompat.START);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            // bottom bar
            case R.id.ll_bottom_dashboard:
                loadFragment(DashBoardFragment.newInstance(this, txtTitle), Consts.FRAG_DASHBOARD);
                break;

            case R.id.ll_bottom_bills:
                loadFragment(BillsFragment.newInstance(this, txtTitle), Consts.FRAG_BILLS);
                break;

            case R.id.ll_bottom_committee:
                loadFragment(CommitteesFragment.newInstance(this, txtTitle), Consts.FRAG_COMMITTEE);
                break;
            case R.id.ll_bottom_acts:
                loadFragment(ActsFragment.newInstance(this, txtTitle), Consts.FRAG_ACTS);
                break;

            case R.id.ll_bottom_live:
                loadFragment(LiveStreamingFragment.newInstance(this, txtTitle), Consts.FRAG_LIVE);
                break;

            // navigation bar
            case R.id.ll_nav_header:
                closeDrawer();
                break;

            case R.id.txt_nav_senate_leader:
                Fragment fragment = SenateLeadershipFragment.newInstance(this, txtTitle);
                Bundle bundle = new Bundle();
                bundle.putInt(Consts.DETAIL_ID, Consts.PRESIDENT);
                fragment.setArguments(bundle);

                loadFragment(fragment, Consts.FRAG_SENATE_LEADERSHIP);
                closeDrawer();
                break;

            case R.id.txt_nav_recent_activities:
                loadFragment(RecentActivitiesFragment.newInstance(this, txtTitle), Consts.FRAG_RECENT_ACTIVITIES);
                closeDrawer();
                break;

            case R.id.txt_nav_about_senate:
                loadFragment(AboutSenateFragment.newInstance(this, txtTitle), Consts.FRAG_ABOUT_SENATE);
                closeDrawer();
                break;

            case R.id.txt_nav_officers:
                loadFragment(SenatorDetailsFragment.newInstance(this, txtTitle), Consts.FRAG_SENATOR_DETAILS);
                closeDrawer();
                break;

            case R.id.txt_nav_constitutional:
                loadFragment(ConstitutionalFragment.newInstance(this, txtTitle), Consts.FRAG_CONSTITUTIONAL_ROLES);
                closeDrawer();
                break;

            case R.id.txt_nav_compositions:
                loadFragment(CompositionsFragment.newInstance(this, txtTitle), Consts.FRAG_COMPOSITIONS);
                closeDrawer();
                break;

        }
    }

    @OnClick(R.id.img_back)
    void imgBack() {
        loadFragment(SenatorDetailsFragment.newInstance(this, txtTitle), Consts.FRAG_SENATOR_DETAILS);
    }


    @Override
    public void onFragItemClick(int id) {
        if (mViewSelection == Consts.FRAG_DASHBOARD) {
            // When you click senator detail in DASHBOARD fragment
            // id = user_id of the officer
            Fragment fragment = SenateLeadershipFragment.newInstance(this, txtTitle);
            Bundle bundle = new Bundle();
            bundle.putInt(Consts.DETAIL_ID, id);
            fragment.setArguments(bundle);

            loadFragment(fragment, Consts.FRAG_SENATE_LEADERSHIP);

        } else if (mViewSelection == Consts.FRAG_SENATOR_DETAILS) {
            // When you click senator detail in SENATOR DETAILS fragment
            //  id = user_id of the officer
            Fragment fragment = ProfileFragment.newInstance(this, txtTitle);
            Bundle bundle = new Bundle();
            bundle.putInt(Consts.DETAIL_ID, id);
            fragment.setArguments(bundle);

            loadFragment(fragment, Consts.FRAG_PROFILE);

        }
    }


}

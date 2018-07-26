package com.example.gagan.proj1;

import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.gagan.proj1.adapter.PagerAdapter;
import com.example.gagan.proj1.dbhelper.DbHelper;
import com.example.gagan.proj1.fragments.AddUserFragment;
import com.example.gagan.proj1.fragments.BaseFragment;
import com.example.gagan.proj1.fragments.ChattFragment;
import com.example.gagan.proj1.fragments.DownloadFragment;
import com.example.gagan.proj1.fragments.LogInFragment;
import com.example.gagan.proj1.pojo.User;
import com.example.gagan.proj1.dbhelper.valueeventlistner.UserValueEventListener;
import com.example.gagan.proj1.utils.Utils;
import com.example.gagan.proj1.widgets.CircleTransform;
import com.google.firebase.database.DatabaseReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.online_status)
    ImageView online_status;
    @BindView(R.id.btnAddUsers)
    View btnAddUsers;


    @BindView(R.id.tv_info)
    TextView tv_info;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.profile_image)
    ImageView profile_image;

    private Unbinder unbinder;
    private List<BaseFragment> fragments;
    private PagerAdapter adapter;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);
        init();
    }

    private void init() {
        currentUser = DbHelper.getDbHepler().getCurrentUserObj();
        DbHelper.getDbHepler().getCurrentUser(new UserValueEventListener() {
            @Override
            public void updatedUser(User user) {

            }

            @Override
            public void updatedCurrentUser(User user) {
                if (DbHelper.getDbHepler().isSameUser(currentUser, user)) {
                    currentUser = user;
                }
                setUserDetail();
            }
        });
        setUserDetail();
        initFragments();
        initTabLayot(fragments);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(this);
        adapter = new PagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(4);
    }

    private void initTabLayot(List<BaseFragment> fragments) {
        for (BaseFragment item : fragments) {
            tabLayout.addTab(tabLayout.newTab().setText(item.getTitle()));
        }
        tabLayout.setLayoutParams(new LinearLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, 50));
    }


    private void initFragments() {
        fragments = new ArrayList<>();
        fragments.add(new LogInFragment());
        fragments.add(new ChattFragment());
        fragments.add(new DownloadFragment());
        fragments.add(new AddUserFragment());
        fragments.add(new BaseFragment());
    }

    private void setImage(String uri) {
        Picasso.get()
                .load(uri)
                .transform(new CircleTransform())
                .into(profile_image);
    }

    private void setUserDetail() {
        if (currentUser != null) {
            setImage(currentUser.getImageUrl());
            if (currentUser.getTyping()) {
                tv_info.setText("typing...");
            } else {
                tv_info.setText(currentUser == DbHelper.getDbHepler().getCurrentUserObj() ? "You" : currentUser.getName());
            }
            boolean isOnline = currentUser.isOnlineStatus();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                online_status.setImageDrawable(getDrawable(isOnline ? R.drawable.ic_action_online : R.drawable.ic_action_offline));
            }
            online_status.setVisibility(Utils.isOnline(this) ? View.VISIBLE : View.GONE);
        }

    }

    public void replace(int index) {
        viewPager.setCurrentItem(index);
    }

    public void openChatt(User user) {
        if (!DbHelper.getDbHepler().isSameUser(user, DbHelper.getDbHepler().getCurrentUserObj())) {
            DatabaseReference ref = DbHelper.getDbHepler().getUsersRef().child(user.getId());
            ref.addValueEventListener(new UserValueEventListener() {
                @Override
                public void updatedUser(User user) {
                    if (currentUser.getId().equals(user.getId()))
                        currentUser = user;
                    setUserDetail();

                }
            });
            adapter.getChattFragment().init(user);
            replace(adapter.getChattFragmentPosition());

        } else {
            viewPager.setCurrentItem(adapter.getProfilePos());
        }

    }

    @OnClick(R.id.btnAddUsers)
    public void onAddClick() {
        viewPager.setCurrentItem(adapter.getAddUserId());
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        btnAddUsers.setVisibility(tab.getPosition() == 0 ? View.VISIBLE : View.GONE);
        currentUser = adapter.getItem(tab.getPosition()).getUserDetailsToDisplay();
        setUserDetail();
        viewPager.setCurrentItem(tab.getPosition());
    }


    @Override
    public void onBackPressed() {
        if (viewPager.getCurrentItem() != 0) {
            viewPager.setCurrentItem(0);
            return;
        }
        super.onBackPressed();
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

//    public void setCurrentUserDetail() {
//
////        final DatabaseReference currentUserRef = MyApplication.getApplication(this).getCurrentUserRef();
////        currentUserRef.addListenerForSingleValueEvent(new ValueEventListener() {
////            @Override
////            public void onDataChange(DataSnapshot dataSnapshot) {
//////                final Target target = new Target() {
//////                    @Override
//////                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
//////                        Drawable drawable = new BitmapDrawable(getResources(), bitmap);
//////                        profile_image.setIcon(drawable);
//////                    }
//////
//////                    @Override
//////                    public void onBitmapFailed(Exception e, Drawable errorDrawable) {
//////
//////                    }
//////
//////                    @Override
//////                    public void onPrepareLoad(Drawable placeHolderDrawable) {
//////                    }
//////                };
////                User user = dataSnapshot.getValue(User.class);
////                Picasso.get()
////                        .load(user.getImageUrl())
////                        .transform(new CircleTransform())
////                        .into(profile_image);
////            }
////
////
////            @Override
////            public void onCancelled(DatabaseError databaseError) {
////
////            }
////        });
//    }


}

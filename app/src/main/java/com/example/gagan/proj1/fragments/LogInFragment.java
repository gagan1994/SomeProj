package com.example.gagan.proj1.fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gagan.proj1.MainActivity;
import com.example.gagan.proj1.R;
import com.example.gagan.proj1.adapter.UsersListAdapter;
import com.example.gagan.proj1.dbhelper.DbHelper;
import com.example.gagan.proj1.dbhelper.valueeventlistner.UserValueEventListener;
import com.example.gagan.proj1.interfaces.UpdateUserInterface;
import com.example.gagan.proj1.pojo.User;
import com.example.gagan.proj1.widgets.SeparatorDecoration;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class LogInFragment extends BaseFragment implements UpdateUserInterface {
    public final static int Id = 1;
    @BindView(R.id.rv_list)
    RecyclerView mRecyclerView;
    private List<User> users = new ArrayList<>();
    private UsersListAdapter mAdapter;

    public LogInFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_log_in, container, false);
        unbinder = ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        mRecyclerView.addItemDecoration(new SeparatorDecoration(getActivity(), Color.TRANSPARENT, 5));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new UsersListAdapter( this);
        mRecyclerView.setAdapter(mAdapter);
        DbHelper.getDbHepler().getCurrentUserRef().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                users.clear();
                User usersRef = dataSnapshot.getValue(User.class);

                if (usersRef.getRegisteredUserId() != null)
                    for (String item : usersRef.getRegisteredUserId()) {
                        DbHelper.getDbHepler().getUsersRef().child(item).
                                addListenerForSingleValueEvent(new UserValueEventListener() {
                                    @Override
                                    public void updatedUser(User user) {
                                        users.add(user);
                                        mAdapter.update(users);
                                    }
                                });
                    }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public String getTitle() {
        return "Home";
    }

    @Override
    public void OnClickUser(User user, View view) {
        ((MainActivity) getActivity()).openChatt(user);
    }
}

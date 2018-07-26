package com.example.gagan.proj1.fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gagan.proj1.R;
import com.example.gagan.proj1.adapter.AddUserListAdapter;
import com.example.gagan.proj1.adapter.FirebaseUserListAdapter;
import com.example.gagan.proj1.adapter.UsersListAdapter;
import com.example.gagan.proj1.adapter.UsersListViewHolder;
import com.example.gagan.proj1.dbhelper.DbHelper;
import com.example.gagan.proj1.interfaces.UpdateUserInterface;
import com.example.gagan.proj1.pojo.User;
import com.example.gagan.proj1.pojo.UsersId;
import com.example.gagan.proj1.widgets.SeparatorDecoration;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddUserFragment extends BaseFragment {

    @BindView(R.id.list_users)
    RecyclerView mRecyclerView;
    @BindView(R.id.et_search)
    AppCompatEditText et_search;
    List<User> users = new ArrayList<>();
    private AddUserListAdapter mAdapter = new AddUserListAdapter();

    private TextWatcher textWatcher=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    public AddUserFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_user, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    private void init() {
        et_search.addTextChangedListener(textWatcher);

        mRecyclerView.addItemDecoration(new SeparatorDecoration(getActivity(), Color.TRANSPARENT, 5));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);
        final DatabaseReference usersRef = DbHelper.getDbHepler().getUsersRef();
        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                users.clear();
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    try {
                        User user = item.getValue(User.class);
                        if (!user.getId().equals(DbHelper.getDbHepler().getCurrentUserObj().getId()))
                            users.add(user);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
                mAdapter.update(users);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
//        Query query = usersRef
//                .limitToLast(50);
//        FirebaseRecyclerOptions<User> options =
//                new FirebaseRecyclerOptions.Builder<User>()
//                        .setQuery(query, User.class)
//                        .build();
//
//        mFirebaseAdapter = new FirebaseUserListAdapter(options);
//        mRecyclerView.addItemDecoration(new SeparatorDecoration(getActivity(), Color.TRANSPARENT, 5));
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        mRecyclerView.setAdapter(mFirebaseAdapter);
//        mFirebaseAdapter.startListening();
    }


    @Override
    public String getTitle() {
        return "Add users";
    }
}

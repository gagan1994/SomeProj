package com.example.gagan.proj1.adapter;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gagan.proj1.R;
import com.example.gagan.proj1.dbhelper.DbHelper;
import com.example.gagan.proj1.dbhelper.valueeventlistner.UserValueEventListener;
import com.example.gagan.proj1.interfaces.UpdateUserInterface;
import com.example.gagan.proj1.interfaces.UsersViewHolder;
import com.example.gagan.proj1.pojo.User;
import com.example.gagan.proj1.pojo.User;
import com.example.gagan.proj1.widgets.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Gagan on 4/23/2018.
 */

public class UsersListAdapter extends RecyclerView.Adapter<UsersListAdapter.UsersListViewHolder> {
    private final List<User> mData;
    private final UpdateUserInterface listner;

    public UsersListAdapter(UpdateUserInterface userInterface) {
        this.mData = new ArrayList<>();
        this.listner = userInterface;
    }

    @NonNull
    @Override
    public UsersListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_user_lists, null, false);
        return new UsersListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UsersListViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        holder.bindUser(mData.get(position), listner);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void update(List<User> users) {
        mData.clear();
        mData.addAll(users);
        notifyItemRangeChanged(0, mData.size());
    }

    public class UsersListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.tv_name)
        TextView tv_name;
        @BindView(R.id.tv_nick_name)
        TextView tv_nick_name;
        @BindView(R.id.tv_email)
        TextView tv_email;
        @BindView(R.id.iv_profile_pic)
        ImageView iv_profile_pic;
        private User user;
        private UpdateUserInterface lisviewListner;


        public UsersListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        public void bindUser(User user, UpdateUserInterface listViewListners) {
            this.user = user;
            tv_email.setText(user.getEmail());
            tv_name.setText(user.getName());
            tv_nick_name.setText(user.getNickName());
            Picasso.get().load(user.getImageUrl())
                    .transform(new CircleTransform()).into(iv_profile_pic);
            this.lisviewListner = listViewListners;
        }

        @Override
        public void onClick(View v) {
            lisviewListner.OnClickUser(user, itemView);
        }

//        public void bindUserId(User User, UpdateUserInterface listner) {
//            itemView.setBackgroundColor(User.isAdded() ? selectedColor : notSelectedColor);
//
//            DbHelper.getDbHepler().getUsersRef().child(User.getId()).addListenerForSingleValueEvent(new UserValueEventListener() {
//                @Override
//                public void updatedUser(User user) {
//                    bindUser(user, listner);
//                }
//            });
//        }
    }

}

package com.example.gagan.proj1.adapter;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gagan.proj1.R;
import com.example.gagan.proj1.dbhelper.DbHelper;
import com.example.gagan.proj1.pojo.User;
import com.example.gagan.proj1.widgets.CircleTransform;
import com.google.android.gms.tasks.Task;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Gagan on 4/26/2018.
 */

public class AddUserListAdapter extends RecyclerView.Adapter<AddUserListAdapter.UsersListViewHolder> {
    List<User> mData;

    public AddUserListAdapter() {
        mData = new ArrayList<>();
    }

    @NonNull
    @Override
    public AddUserListAdapter.UsersListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_user_lists, null, false);
        return new UsersListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddUserListAdapter.UsersListViewHolder holder, int position) {
        holder.bindUser(mData.get(position));
        holder.setIsRecyclable(false);
        holder.itemView.setOnClickListener(v -> {
            Task<Void> task = DbHelper.getDbHepler().addOrRemoveUserToCurrentUserList(holder.getUser());
            task.addOnSuccessListener(aVoid -> notifyDataSetChanged());
            task.addOnFailureListener(e -> Toast.makeText(v.getContext(), "can't add some error", Toast.LENGTH_SHORT).show());
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void update(List<User> users) {
        mData = users;
        notifyDataSetChanged();
    }

    public class UsersListViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_name)
        TextView tv_name;
        @BindView(R.id.tv_nick_name)
        TextView tv_nick_name;
        @BindView(R.id.tv_email)
        TextView tv_email;
        @BindView(R.id.iv_profile_pic)
        ImageView iv_profile_pic;
        private User user;


        public UsersListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindUser(User user) {
            List<String> ids = DbHelper.getDbHepler().getCurrentUserObj().getRegisteredUserId();
            if (ids != null)
                for (String id : ids) {
                    if (id.equals(user.getId())) {
                        itemView.setBackgroundColor(itemView.getContext().getResources().getColor(R.color.primaryLight));
                        break;
                    } else {
                        itemView.setBackgroundColor(Color.TRANSPARENT);

                    }
                }
            this.user = user;
            tv_email.setText(user.getEmail());
            tv_name.setText(user.getName());
            tv_nick_name.setText(user.getNickName());
            Picasso.get().load(user.getImageUrl())
                    .transform(new CircleTransform()).into(iv_profile_pic);
        }

        public User getUser() {
            return user;
        }
    }
}

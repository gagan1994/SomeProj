package com.example.gagan.proj1.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gagan.proj1.R;
import com.example.gagan.proj1.dbhelper.DbHelper;
import com.example.gagan.proj1.dbhelper.valueeventlistner.UserValueEventListener;
import com.example.gagan.proj1.interfaces.UpdateUserInterface;
import com.example.gagan.proj1.pojo.User;
import com.example.gagan.proj1.widgets.CircleTransform;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Gagan on 4/17/2018.
 */

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
                    itemView.setBackgroundColor(itemView.getContext().getResources().getColor(R.color.white));

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

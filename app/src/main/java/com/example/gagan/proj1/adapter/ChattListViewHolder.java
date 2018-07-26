package com.example.gagan.proj1.adapter;

import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gagan.proj1.R;
import com.example.gagan.proj1.dbhelper.DbHelper;
import com.example.gagan.proj1.pojo.Chatt;
import com.example.gagan.proj1.pojo.User;
import com.example.gagan.proj1.dbhelper.valueeventlistner.UserValueEventListener;
import com.example.gagan.proj1.widgets.CircleTransform;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Gagan on 4/18/2018.
 */

public class ChattListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
    @BindView(R.id.tv_message)
    TextView tv_message;
    @BindView(R.id.tv_time)
    TextView tv_time;
    @BindView(R.id.image)
    ImageView image;
    private Chatt chatt;

    public ChattListViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    public void bindData(final Chatt chatt) {
        String message = chatt.getMessage();
        String time = chatt.getSentTime();
        this.chatt = chatt;
        switch (getItemViewType()) {
            case R.layout.rv_chatt_from_layout:
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        DbHelper.getDbHepler().getUsersRef().child(chatt.getToId())
                                .addValueEventListener(new UserValueEventListener() {
                                    @Override
                                    public void updatedUser(User user) {
                                        inputImageBlur(user.getImageUrl());
                                    }
                                });

                    }
                }, 100);

                break;
        }
        tv_message.setText(message);
        tv_time.setText(time);
    }

    private void inputImageBlur(final String imageUrl) {
        DbHelper.getDbHepler().getSeen(chatt, new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Picasso.get().load(imageUrl)
                        .transform(new CircleTransform()).into(image);
                if (dataSnapshot.getValue() instanceof Boolean && (boolean) dataSnapshot.getValue()) {
                    image.setAlpha(1f);
                } else {
                    image.setAlpha(0.4f);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public boolean onLongClick(View v) {
        ClipboardManager _clipboard = (ClipboardManager) itemView.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        _clipboard.setText(chatt.getMessage());
        Toast.makeText(itemView.getContext(), "Coppied", Toast.LENGTH_SHORT).show();
        return true;
    }
}

package com.example.gagan.proj1.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gagan.proj1.MyApplication;
import com.example.gagan.proj1.R;
import com.example.gagan.proj1.pojo.Chatt;
import com.example.gagan.proj1.pojo.User;

import org.w3c.dom.Comment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gagan on 4/18/2018.
 */

public class DummyAdapter extends RecyclerView.Adapter<ChattListViewHolder> {
    private final Context mContext;
    List<Chatt> comments = new ArrayList<>();

    public DummyAdapter(Context context) {
        comments = getItems();
        mContext = context;

    }

    private List<Chatt> getItems() {
        for (int i = 0; i < 10; i++) {
            comments.add(Chatt.getDummyChatt());
        }
        return comments;
    }

    @NonNull
    @Override
    public ChattListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(mContext).inflate(viewType,null,false);
        return new ChattListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ChattListViewHolder holder, int position) {
        holder.bindData(comments.get(position));
    }
    @Override
    public int getItemViewType(int position) {
        boolean isSentByMe = position%2==0;
        return isSentByMe ? R.layout.rv_chatt_from_layout :R.layout.rv_chatt_to_layout;
    }
    @Override
    public int getItemCount() {
        return comments.size();
    }
}

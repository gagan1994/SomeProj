package com.example.gagan.proj1.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gagan.proj1.MyApplication;
import com.example.gagan.proj1.R;
import com.example.gagan.proj1.dbhelper.DbHelper;
import com.example.gagan.proj1.pojo.Chatt;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

/**
 * Created by Gagan on 4/18/2018.
 */

public class ChatListAdapter extends FirebaseRecyclerAdapter<Chatt, ChattListViewHolder> {

    private final Context mContext;

    public ChatListAdapter(@NonNull FirebaseRecyclerOptions<Chatt> options, Context context) {
        super(options);
        mContext = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull ChattListViewHolder holder, int position, @NonNull Chatt model) {
        DbHelper.getDbHepler().setSeen(model);
        holder.bindData(model);
    }

    @NonNull
    @Override
    public ChattListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(viewType, null, false);
        return new ChattListViewHolder(v);
    }

    @Override
    public int getItemViewType(int position) {
        boolean isSentByMe = getItem(position).getFromId().
                equals(DbHelper.getDbHepler().getCurrentUserObj().getId());
        return isSentByMe ? R.layout.rv_chatt_from_layout : R.layout.rv_chatt_to_layout;
    }
}

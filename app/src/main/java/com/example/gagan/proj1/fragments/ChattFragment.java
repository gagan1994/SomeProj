package com.example.gagan.proj1.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.gagan.proj1.MainActivity;
import com.example.gagan.proj1.R;
import com.example.gagan.proj1.adapter.ChatListAdapter;
import com.example.gagan.proj1.adapter.ChattListViewHolder;
import com.example.gagan.proj1.dbhelper.DbHelper;
import com.example.gagan.proj1.dbhelper.valueeventlistner.ChattValueEventListner;
import com.example.gagan.proj1.pojo.Chatt;
import com.example.gagan.proj1.pojo.User;
import com.example.gagan.proj1.utils.Constant;
import com.example.gagan.proj1.widgets.SeparatorDecoration;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChattFragment extends BaseFragment {

    private User to;
    @BindView(R.id.new_chatt)
    View new_chatt;
    @BindView(R.id.no_item)
    View no_item;
    @BindView(R.id.chat_box)
    View chat_box;
    @BindView(R.id.smily)
    ImageView smily;
    @BindView(R.id.rv_chatt)
    RecyclerView mRecyclerView;
    @BindView(R.id.et_text_message)
    AppCompatEditText et_text_message;
    private FirebaseRecyclerAdapter<Chatt, ChattListViewHolder> mFirebaseAdapter;

    private TextWatcher textWatcher;
    private static final int MESSAGE_TEXT_CHANGED = 100;
    private static final int DEFAULT_AUTOCOMPLETE_DELAY = 750;
    private int mAutoCompleteDelay = DEFAULT_AUTOCOMPLETE_DELAY;
    private DatabaseReference chattsRef;

    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            DbHelper.getDbHepler().changeTypingStatus(false);
        }
    };

    public ChattFragment() {
        Tag = "ChattFragment";
    }


    @Override
    public String getTitle() {
        return "Chat";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chatt, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    public void init() {
        if (to == null) {
            newChatt();
        } else {
            diplayChatt();
            et_text_message.addTextChangedListener(getTextWacher());
        }
    }

    private TextWatcher getTextWacher() {
        if (textWatcher == null) {
            textWatcher = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    DbHelper.getDbHepler().changeTypingStatus(true);
                    mHandler.removeMessages(MESSAGE_TEXT_CHANGED);
                    mHandler.sendMessageDelayed(mHandler.obtainMessage(MESSAGE_TEXT_CHANGED, false), mAutoCompleteDelay);

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            };
        }
        return textWatcher;
    }

    private void diplayChatt() {

        checkVisibility(false);
        chattsRef = DbHelper.getDbHepler().getChattsRef(to.getId());
        FirebaseRecyclerOptions<Chatt> options =
                new FirebaseRecyclerOptions.Builder<Chatt>()
                        .setQuery(chattsRef, Chatt.class)
                        .build();
        mFirebaseAdapter = new ChatListAdapter(options, getActivity());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new SeparatorDecoration(getActivity(), Color.TRANSPARENT, 5));
        mRecyclerView.setAdapter(mFirebaseAdapter);
        mFirebaseAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                mRecyclerView.smoothScrollToPosition(mFirebaseAdapter.getItemCount());
            }
        });
        mFirebaseAdapter.startListening();

    }

    private void newChatt() {
        checkVisibility(true);
    }

    @Override
    public User getUserDetailsToDisplay() {
        return to != null ? to : super.getUserDetailsToDisplay();
    }

    private void checkVisibility(boolean b) {
        new_chatt.setVisibility(b ? View.VISIBLE : View.GONE);
        no_item.setVisibility(b ? View.VISIBLE : View.GONE);
        chat_box.setVisibility(!b ? View.VISIBLE : View.GONE);
    }

    @OnClick(R.id.send)
    public void onSend() {
        try {
            if (et_text_message.getText().toString().trim().length() == 0) {
                Toast.makeText(getActivity(), "No message to send", Toast.LENGTH_SHORT).show();
                return;
            }
            User current_user = DbHelper.getDbHepler().getCurrentUserObj();
            Chatt chatt = new Chatt(current_user, to, et_text_message.getText().toString());
            et_text_message.setText("");
            DbHelper.getDbHepler().sendMessage(chatt, getActivity());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        if (mFirebaseAdapter != null)
            mFirebaseAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mFirebaseAdapter != null)
            mFirebaseAdapter.stopListening();
    }

    @OnClick(R.id.new_chatt)
    public void onNewChatt() {
        ((MainActivity) getContext()).replace(0);
    }

    public void init(User user) {
        this.to = user;
        init();
    }
}

package com.example.gagan.proj1.dbhelper.valueeventlistner;

import com.example.gagan.proj1.dbhelper.DbHelper;
import com.example.gagan.proj1.pojo.Chatt;
import com.example.gagan.proj1.pojo.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by Gagan on 4/20/2018.
 */

public class ChattValueEventListner implements ValueEventListener {
    private final String id;

    public ChattValueEventListner() {
        id = null;
    }

    public ChattValueEventListner(String id) {
        this.id = id;
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {

    }

    private void getChattWithId(Chatt chatt) {

    }

    private void getChatts(ArrayList<Chatt> chatts) {

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}

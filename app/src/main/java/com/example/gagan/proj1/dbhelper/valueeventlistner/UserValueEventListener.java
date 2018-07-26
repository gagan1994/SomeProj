package com.example.gagan.proj1.dbhelper.valueeventlistner;

import android.util.Log;

import com.example.gagan.proj1.dbhelper.DbHelper;
import com.example.gagan.proj1.pojo.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Gagan on 4/18/2018.
 */

public abstract class UserValueEventListener implements ValueEventListener {
    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        User user = dataSnapshot.getValue(User.class);
        if (!DbHelper.getDbHepler().setCurrentUser(user))
            updatedUser(user);
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        Log.w("exc", databaseError.getMessage());
    }

    public void updatedCurrentUser(User user) {

    }

    public void updatedUser(User user) {

    }
}

package com.example.gagan.proj1.dbhelper;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.example.gagan.proj1.pojo.Chatt;
import com.example.gagan.proj1.pojo.User;
import com.example.gagan.proj1.dbhelper.valueeventlistner.UserValueEventListener;
import com.example.gagan.proj1.utils.Constant;
import com.example.gagan.proj1.utils.Utils;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.math.BigInteger;
import java.util.ArrayList;

/**
 * Created by Gagan on 4/18/2018.
 */

public class DbHelper {
    private static DbHelper _helper = new DbHelper();
    private FirebaseDatabase database;
    private static User user;
    DatabaseReference dataBaseRef;
    private DatabaseReference current_user;


    public static DbHelper getDbHepler() {
        return _helper;
    }

    private DbHelper() {
        database = FirebaseDatabase.getInstance();
        dataBaseRef = database.getReference("my_database");
        dataBaseRef.keepSynced(true);
    }


    public void logInUser(GoogleSignInAccount account, Context context) {
        getUsersRef().child(account.getId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);
                user.init(account);
                checkAndAddUser(user, context);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void checkAndAddUser(final User user, final Context context) {
        //        users.put(user.getId(), user);
        Task<Void> task = updateUser(user);
        task.addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                onlineStatusChanged(context);
                Toast.makeText(context, "Logged in successfull", Toast.LENGTH_SHORT).show();
            }
        });
        task.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "Logged in failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setRegisteredUsers() {
        getUsersRef().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    User _user = (User) item.getValue();
                    if (!_user.getId().equals(user.getId()))
                        getUsersRegForThisRef().child(_user.getId()).setValue(_user);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public DatabaseReference getTypingRef() {
        return getDatabaseRef().child(Constant.FIELD_TYPE);
    }

    public DatabaseReference getUsersRef() {
        return getDatabaseRef().child(Constant.FIELD_USERS);
    }

    public DatabaseReference getUsersSeenRef(Chatt chatt) {
        BigInteger id1 = new BigInteger(chatt.getFromId());
        BigInteger id2 = new BigInteger(chatt.getToId());
        BigInteger total = id1.add(id2);
        return getDatabaseRef().child(Constant.FIELD_USERS_SEEN).child(total.toString()).child(chatt.getId());
    }

    public DatabaseReference getChattsRef(String id) {
        DatabaseReference chattid = null;
        try {
            BigInteger id1 = new BigInteger(user.getId());
            BigInteger id2 = new BigInteger(id);
            BigInteger total = id1.add(id2);
            chattid = getChattRef().child(total.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return chattid;
    }

    private DatabaseReference getChattRef() {
        return getDatabaseRef().child(Constant.CHATT_FIELD);
    }

    public void getCurrentUser(@NonNull UserValueEventListener userValueEventListener) {
        current_user.addValueEventListener(userValueEventListener);
    }


    public DatabaseReference getDatabaseRef() {
        return dataBaseRef;
    }

    public void onlineStatusChanged(Context context) {
        DatabaseReference userOnlineRef = getCurrentUserRef().child("onlineStatus");
        userOnlineRef.setValue(Utils.isOnline(context));
        userOnlineRef.onDisconnect().setValue(false);
    }

    public DatabaseReference getCurrentUserRef() {
        return current_user;
    }


    public boolean setCurrentUser(User user) {
        if (isSameUser(user, this.user)) {
            DbHelper.user = user;
            return true;
        }
        return false;
    }

    public User getCurrentUserObj() {
        return user;
    }

    public Task<Void> updateUser(User currentUser) {
        DatabaseReference usersRef = getDatabaseRef().child(Constant.FIELD_USERS);
        current_user = usersRef.child(currentUser.getId());
        return current_user.setValue(currentUser);
    }

    public void changeTypingStatus(boolean b) {
        user.setTyping(b);
        updateUser(user);
    }

    public boolean isSameUser(User currentUser, User user) {
        boolean iseq = currentUser.getId().equals(user.getId());
        return iseq;
    }

    public void setSeen(Chatt chatt) {
        getUsersSeenRef(chatt).child(user.getId()).setValue(true);
    }

    public void getSeen(Chatt chatt, ValueEventListener listener) {
        getUsersSeenRef(chatt).child(chatt.getToId()).addValueEventListener(listener);
    }

    public void sendMessage(final Chatt chatt, final Context context) {
        Task<Void> task = getChattRef().child(chatt.getId()).setValue(chatt);
        task.addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        });
        task.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "not able to send message", Toast.LENGTH_SHORT).show();

            }
        });
    }

    public DatabaseReference getUsersRegForThisRef() {
        return getDatabaseRef().child(Constant.REGISTERED_USER).child(user.getId());
    }

    public Task<Void> addOrRemoveUserToCurrentUserList(User userId) {
        if (user.getRegisteredUserId() == null || user.getRegisteredUserId().size() == 0) {
            user.setRegisteredUserId(new ArrayList<>());
            user.getRegisteredUserId().add(userId.getId());
        } else {

            if (user.getRegisteredUserId().contains(userId.getId())) {
                user.getRegisteredUserId().remove(userId.getId());
            } else {
                user.getRegisteredUserId().add(userId.getId());
            }
        }
        return updateUser(user);
    }


}

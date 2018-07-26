package com.example.gagan.proj1;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.example.gagan.proj1.dbhelper.DbHelper;
import com.example.gagan.proj1.pojo.User;
import com.example.gagan.proj1.utils.Constant;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Gagan on 4/17/2018.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        DbHelper.getDbHepler();
    }

    public static MyApplication getApplication(Context context) {
        return ((MyApplication) context.getApplicationContext());
    }


    public void logInUser(GoogleSignInAccount account) {
        DbHelper.getDbHepler().logInUser(account,this);
    }
}

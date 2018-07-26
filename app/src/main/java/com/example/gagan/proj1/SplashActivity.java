package com.example.gagan.proj1;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.example.gagan.proj1.utils.Constant;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class SplashActivity extends AppCompatActivity {
    private static final long SPLASH_TIME_OUT = 3000;
    private GoogleSignInOptions gso;
    private GoogleSignInClient mGoogleSignInClient;
    @BindView(R.id.logo)
    View logo;
    @BindView(R.id.sign_in)
    View sign_in;

    private static final int RC_SIGN_IN = 1;
    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        unbinder = ButterKnife.bind(this);
        logo.startAnimation(AnimationUtils.loadAnimation(this, R.anim.rotate));

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null) {
            afterLogin(account);
        } else {
            sign_in.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.sign_in)
    public void onClickSignin() {
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void afterLogin(final GoogleSignInAccount account) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                goNext(account);
            }
        }, SPLASH_TIME_OUT);
    }

    private void goNext(GoogleSignInAccount account) {
        initToFirebase(account);
        Intent i = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(i);
        finish();
    }

    private void initToFirebase(GoogleSignInAccount account) {
        MyApplication.getApplication(getApplicationContext()).logInUser(account);
    }


    private void logInFailure() {
        sign_in.setVisibility(View.VISIBLE);
        Toast.makeText(this, "Please try again!", Toast.LENGTH_SHORT);
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("SplashActivity", "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }

    private void updateUI(GoogleSignInAccount account) {
        if (account != null) {
            sign_in.setVisibility(View.GONE);
            goNext(account);
        } else {
            logInFailure();
        }
    }


}

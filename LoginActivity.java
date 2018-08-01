package com.ogalo.partympakache.wanlive;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.ogalo.partympakache.wanlive.utils.PreferenceUtils;
import com.ogalo.partympakache.wanlive.utils.PushUtils;
import com.sendbird.android.SendBird;
import com.sendbird.android.SendBirdException;
import com.sendbird.android.User;

public class LoginActivity extends AppCompatActivity {



    private EditText mLoginEmail;
    private EditText mLoginPassword;

    private Button mConnectButton;
    private Button regist_button;
    private ProgressDialog mLoginProgress;
    private ContentLoadingProgressBar mProgressBar;

    private boolean isMainLobbyStart=false;
    private ProgressDialog mRegProgress;


    private String user_id;


    private FirebaseAuth mAuth;
    private RelativeLayout mLoginLayout;

    private DatabaseReference mUserDatabase;
    private EditText mEditText;
    private String display_name;
    private String userId;
    String userNickname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();






        mRegProgress = new ProgressDialog(this);

        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users");

        mLoginEmail=(EditText) findViewById(R.id.city);
        mLoginPassword=(EditText) findViewById(R.id.pass);


        mLoginPassword.setText(PreferenceUtils.getUserId());
        mLoginEmail.setText(PreferenceUtils.getNickname());


        mConnectButton=(Button)findViewById(R.id.login_button);
        mLoginLayout = (RelativeLayout) findViewById(R.id.layout_logina);
        regist_button=(Button)findViewById(R.id.logina);

        regist_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginSignUp.class));
            }
        });

        mConnectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {






                userId = mLoginPassword.getText().toString();
                userNickname = mLoginEmail.getText().toString();


                userId = userId.replaceAll("\\s", "");



                PreferenceUtils.setUserId(userId);
                PreferenceUtils.setNickname(userNickname);






                String email = mLoginEmail.getText().toString();
                String password = mLoginPassword.getText().toString();

                if(!TextUtils.isEmpty(email) || !TextUtils.isEmpty(password)){

                    mRegProgress.setTitle("Logging in");
                    mRegProgress.setMessage("Checking in");
                    mRegProgress.setCanceledOnTouchOutside(false);
                    mRegProgress.show();
                    loginUser(email, password);

                }

                else
                {

                    mLoginEmail.setHint("Please Enter Email");

                }

            }
        });




        mProgressBar = (ContentLoadingProgressBar) findViewById(R.id.progress_bar_login);

    }



    private void connectToSendBird(final String userId, final String userNickname) {
        // Show the loading indicator
        showProgressBar(true);
        mConnectButton.setEnabled(false);

        ConnectionManager.login(userId, new SendBird.ConnectHandler() {
            @Override
            public void onConnected(User user, SendBirdException e) {
                // Callback received; hide the progress bar.
                showProgressBar(false);

                if (e != null) {
                    // Error!
                    Toast.makeText(
                            getApplicationContext(), "" + e.getCode() + ": " + e.getMessage(),
                            Toast.LENGTH_SHORT)
                            .show();

                    // Show login failure snackbar
                    mRegProgress.dismiss();
                    showSnackbar("Login to WanLive failed");
                    mConnectButton.setEnabled(true);
                    PreferenceUtils.setConnected(false);
                    return;
                }

                PreferenceUtils.setNickname(user.getNickname());
                PreferenceUtils.setProfileUrl(user.getProfileUrl());
                PreferenceUtils.setConnected(true);

                // Update the user's nickname
                updateCurrentUserInfo(userNickname);
                updateCurrentUserPushToken();

                // Proceed to MainActivity
                Intent intent = new Intent(getApplicationContext(), WanMaps.class);

                startActivity(intent);
                mRegProgress.dismiss();
                finish();
            }
        });
    }





    private void loginUser(String email, String password) {


        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){


                    String current_user_id = mAuth.getCurrentUser().getUid();
                    String deviceToken = FirebaseInstanceId.getInstance().getToken();

                    mUserDatabase.child(current_user_id).child("device_token").setValue(deviceToken).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

//                            Intent mainIntent = new Intent(LoginActivity.this, WanMaps.class);
//                            mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                            startActivity(mainIntent);
//                            finish();
                            mRegProgress.dismiss();

//                                Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
//                                mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                                startActivity(mainIntent);
                            connectToSendBird(userId, userNickname);

                        }
                    });




                } else {

                    mRegProgress.dismiss();

                    String task_result = task.getException().getMessage().toString();

                    Toast.makeText(LoginActivity.this, "Error : " + task_result, Toast.LENGTH_LONG).show();

                }

            }
        });


    }
















    /**
     * Update the user's push token.
     */
    private void updateCurrentUserPushToken() {
        PushUtils.registerPushTokenForCurrentUser(getApplicationContext(), null);
    }

    /**
     * Updates the user's nickname.
     * @param userNickname  The new nickname of the user.
     */
    private void updateCurrentUserInfo(final String userNickname) {
        SendBird.updateCurrentUserInfo(userNickname, null, new SendBird.UserInfoUpdateHandler() {
            @Override
            public void onUpdated(SendBirdException e) {
                if (e != null) {
                    // Error!
                    Toast.makeText(
                            getApplicationContext(), "" + e.getCode() + ":" + e.getMessage(),
                            Toast.LENGTH_SHORT)
                            .show();

                    // Show update failed snackbar
                    showSnackbar("Update user nickname failed");

                    return;
                }

                PreferenceUtils.setNickname(userNickname);
            }
        });
    }

    // Displays a Snackbar from the bottom of the screen
    private void showSnackbar(String text) {
        Snackbar snackbar = Snackbar.make(mLoginLayout, text, Snackbar.LENGTH_SHORT);

        snackbar.show();
    }

    // Shows or hides the ProgressBar
    private void showProgressBar(boolean show) {
        if (show) {
            mProgressBar.show();
        } else {
            mProgressBar.hide();
        }
    }


}

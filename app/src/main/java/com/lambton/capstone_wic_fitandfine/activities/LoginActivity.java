package com.lambton.capstone_wic_fitandfine.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.lambton.capstone_wic_fitandfine.R;

import com.lambton.capstone_wic_fitandfine.common.IAppConstants;
import com.lambton.capstone_wic_fitandfine.helper.FAFineInstance;
import com.lambton.capstone_wic_fitandfine.models.Facebook;
import com.lambton.capstone_wic_fitandfine.util.AlertUtil;
import com.lambton.capstone_wic_fitandfine.util.AppUtil;
import com.lambton.capstone_wic_fitandfine.util.PermissionManager;
import com.lambton.capstone_wic_fitandfine.util.ValidationUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends BasicActivity implements View.OnClickListener {

    @BindView(R.id.txt_change_login_signup)
    TextView txt_change_login_signup;

    @BindView(R.id.but_login_splash)
    Button but_login_splash;

    @BindView(R.id.input_email_main)
    EditText input_email;

    @BindView(R.id.input_password_main)
    EditText input_password;

    @BindView(R.id.but_signup_reg)
    Button but_signup_reg;

    @BindView(R.id.ll_signup)
    LinearLayout ll_signup;

    @BindView(R.id.ll_login)
    LinearLayout ll_login;

    @BindView(R.id.edt_name_reg)
    EditText edt_name_reg;

    @BindView(R.id.edt_email_reg)
    EditText edt_email_reg;

    @BindView(R.id.edt_pass_reg)
    EditText edt_pass_reg;

    @BindView(R.id.edt_confirm_pass_reg)
    EditText edt_confirm_pass_reg;

    @BindView(R.id.ll_splash)
    LinearLayout ll_splash;

    @BindView(R.id.ll_google)
    LinearLayout ll_google;

    @BindView(R.id.ll_facebook)
    LinearLayout ll_facebook;

    boolean change = false;
    Animation slideUp;
    FirebaseAuth auth;
    GoogleSignInClient mGoogleSignInClient;
    GoogleSignInOptions gso;
    private static final int RC_SIGN_IN = 007;

    CallbackManager callbackManager;
    Facebook facedata;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        int colorCodeDark = Color.parseColor("#000000");
        window.setStatusBarColor(colorCodeDark);

        ButterKnife.bind(this);
        txt_change_login_signup.setOnClickListener(this);
        auth = FirebaseAuth.getInstance();
        slideUp = AnimationUtils.loadAnimation(this, R.anim.slide_up);
        but_login_splash.setOnClickListener(this);
        but_signup_reg.setOnClickListener(this);
        ll_google.setOnClickListener(this);
        ll_facebook.setOnClickListener(this);

     //   hidekeyboard();
    }
    public void hidekeyboard()
    {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.but_login_splash:

                String txt_email = input_email.getText().toString().trim();
                String txt_password = input_password.getText().toString().trim();
                Log.d("Monika12", "onComplete:dsd Monika12 ");
                if (TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_password)) {
                    displayAlert("All fileds are required", ll_splash);
                } else {
                    displaydialog("Loading please wait....");
                 /*   auth.signInWithEmailAndPassword(txt_email, txt_password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Log.d("Monika", "onComplete:dsd Monika ");
                                        dialog.dismiss();

                                        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                                        System.out.println(">>>>>>>>>>>>>" + firebaseUser.getUid());
                                        System.out.println(">>>>>>>>>>>>>" + firebaseUser.getPhoneNumber());
                                        //System.out.println(">>>>>>>>>>>>>"+firebaseUser.getUid());

                                        Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                                        startActivity(intent);
                                        displayAlert("Login successfully", ll_splash);
                                        finish();
                                    } else {
                                        dialog.dismiss();
                                        displayAlert("Authentication failed!", ll_splash);
                                    }
                                }
                            });*/
                    // [START sign_in_with_email]
                    auth.signInWithEmailAndPassword(txt_email, txt_password)
                            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        //Log.d(TAG, "signInWithEmail:success");
                                        FirebaseUser user = auth.getCurrentUser();

                                    } else {
                                        // If sign in fails, display a message to the user.
                                       // Log.w(TAG, "signInWithEmail:failure", task.getException());
                                        Toast.makeText(LoginActivity.this, task.getException().getMessage(),
                                                Toast.LENGTH_SHORT).show();
                                       // updateUI(null);
                                    }
                                  //  hideProgressDialog();
                                }
                            });
                    // [END sign_in_with_email]

                }

                break;

        }
    }



   /* @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        //for google
       // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
       if (requestCode == RC_SIGN_IN) {
           // The Task returned from this call is always completed, no need to attach
//            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
           handleSignInResult(task);
        }
        else
        {

            //for facebook
            callbackManager.onActivityResult(requestCode, resultCode, data);
            super.onActivityResult(requestCode, resultCode, data);
         }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask)
    {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            Toast.makeText(LoginActivity.this, "First name = "+account.getDisplayName()+ System.lineSeparator() + "Last name = "+account.getDisplayName()+ System.lineSeparator() + "Email id = "+account.getEmail()+ System.lineSeparator() + "google id = "+account.getId(), Toast.LENGTH_LONG).show();

        } catch (ApiException e) {

        }
    }*/



}

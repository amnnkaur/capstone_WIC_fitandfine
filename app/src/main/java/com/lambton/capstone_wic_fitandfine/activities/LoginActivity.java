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
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.lambton.capstone_wic_fitandfine.R;

import com.lambton.capstone_wic_fitandfine.common.IAppConstants;
import com.lambton.capstone_wic_fitandfine.helper.FAFineInstance;
import com.lambton.capstone_wic_fitandfine.models.Facebook;
import com.lambton.capstone_wic_fitandfine.models.Workout;
import com.lambton.capstone_wic_fitandfine.util.AlertUtil;
import com.lambton.capstone_wic_fitandfine.util.AppUtil;
import com.lambton.capstone_wic_fitandfine.util.PermissionManager;
import com.lambton.capstone_wic_fitandfine.util.ValidationUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

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
    private static final String EMAIL = "email";
    CallbackManager callbackManager;
    Facebook facedata;
    private String currentDate;
    private List<Workout> workoutList;

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

        callbackManager = CallbackManager.Factory.create();
        facedata = new Facebook();
        facebook_login();

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        currentDate = sdf.format(new Date());

        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = mDatabase.getReference("Default");
        myRef.keepSynced(true);
        workoutList = new ArrayList<>();
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                    workoutList.add(childDataSnapshot.getValue(Workout.class));
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.d("NOPE", "Failed to read value.", error.toException());
            }
        });
        // [END initialize_auth]
        hidekeyboard();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.ll_google:

                signIn();

                break;

            case R.id.ll_facebook:

                //disconnectFromFacebook();
                LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email", "public_profile"));

                break;


            case R.id.but_login_splash:

                String txt_email = input_email.getText().toString().trim();
                String txt_password = input_password.getText().toString().trim();

                if (TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_password)) {
                    //Toast.makeText(MainActivity.this, "All fileds are required", Toast.LENGTH_SHORT).show();
                    displayAlert("All fileds are required", ll_splash);
                } else {
                    displaydialog("Loading please wait....");
                    auth.signInWithEmailAndPassword(txt_email, txt_password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {

                                        dialog.dismiss();

                                        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                                        System.out.println(">>>>>>>>>>>>>" + firebaseUser.getUid());
                                        System.out.println(">>>>>>>>>>>>>" + firebaseUser.getPhoneNumber());
                                        //System.out.println(">>>>>>>>>>>>>"+firebaseUser.getUid());

                                        Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                                        startActivity(intent);

                                        //Toast.makeText(MainActivity.this, "Login successfully", Toast.LENGTH_SHORT).show();
                                        displayAlert("Login successfully", ll_splash);
                                        finish();
                                    } else {
                                        dialog.dismiss();
                                        //Toast.makeText(MainActivity.this, "Authentication failed!", Toast.LENGTH_SHORT).show();
                                        displayAlert("Authentication failed!", ll_splash);
                                    }
                                }
                            });
                }

                break;

            case R.id.but_signup_reg:


                if (edt_name_reg.getText().toString().equals("")) {
                    displayAlert("First name can't be blank", ll_splash);
                } else if (edt_email_reg.getText().toString().equals("")) {

                    displayAlert("Email can't be blank", ll_splash);
                } else if (!isValidEmail(edt_email_reg.getText().toString())) {

                    displayAlert("Please enter the valid email", ll_splash);
                } else if (edt_pass_reg.getText().toString().equals("")) {

                    displayAlert("Password can't be blank", ll_splash);
                } else if (edt_pass_reg.getText().toString().length() < 7) {

                    displayAlert("Password must be between 8-16 Character", ll_splash);
                } else if (edt_confirm_pass_reg.getText().toString().equals("")) {

                    displayAlert("Confirm Password can't be blank", ll_splash);
                } else if (!edt_pass_reg.getText().toString().equals(edt_confirm_pass_reg.getText().toString())) {

                    displayAlert("Confirm Password and Password not matched", ll_splash);
                } else {
                    String txt_email_reg = edt_email_reg.getText().toString().trim();
                    String txt_password_reg = edt_pass_reg.getText().toString().trim();
                    registeruser(txt_email_reg, txt_password_reg);
                }

                break;


            case R.id.txt_change_login_signup:

                if (change) {
                    try {
                        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    slide_To_Left_invisible(ll_signup);
                    slide_To_left_visible(ll_login);

                    but_login_splash.setVisibility(View.VISIBLE);

                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //Do something after 500ms
                            but_signup_reg.setVisibility(View.GONE);
                        }
                    }, 500);


                    edt_name_reg.setText("");
                    edt_email_reg.setText("");
                    edt_pass_reg.setText("");
                    edt_confirm_pass_reg.setText("");

                    txt_change_login_signup.setText("Don't have an account? Signup");

                    change = false;
                } else {
                    try {
                        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    slide_To_Right_invisible(ll_login);

                    if (ll_signup.getVisibility() == View.GONE) {
                        ll_signup.setVisibility(View.VISIBLE);
                        ll_signup.startAnimation(slideUp);
                    }


                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //Do something after 500ms
                            but_login_splash.setVisibility(View.GONE);
                        }
                    }, 500);

                    input_email.setText("");
                    input_password.setText("");


                    but_signup_reg.setVisibility(View.VISIBLE);

                    txt_change_login_signup.setText("Already have an account? Login");

                    change = true;
                }
                break;
        }
    }

    public static boolean isValidEmail(String st_email) {
        if (st_email == null) {
            return false;
        } else {
            return Patterns.EMAIL_ADDRESS.matcher(st_email).matches();
        }
    }


    public void registeruser(String txt_email, String txt_password) {
        displaydialog("Loading please wait....");
        auth.createUserWithEmailAndPassword(txt_email, txt_password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            userProfile();

                        } else {

                            // Toast.makeText(MainActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            displayAlert("Authentication failed.", ll_splash);
                            dialog.dismiss();
                        }
                    }
                });
    }


    //Set UserDisplay Name
    private void userProfile() {
        final FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(edt_name_reg.getText().toString().trim())
                    //.setPhotoUri(Uri.parse("https://example.com/jane-q-user/profile.jpg"))  // here you can set image link also.
                    .build();

            user.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Log.d("TESTING", "User profile updated.");

                        String currentuserid = user.getUid();

                        FirebaseUser user = auth.getCurrentUser();
                        String UserID = user.getEmail().replace("@", "").replace(".", "");

                        DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
                        DatabaseReference ref1 = mRootRef.child("Users").child(UserID);

                        ref1.child("Name").setValue(edt_name_reg.getText().toString().trim());
                      //  ref1.child("Image_Url").setValue("Null");
                        ref1.child("Email").setValue(user.getEmail());
                        ref1.child("userID").setValue(user.getUid());
                        ref1.child("dateReg").setValue(currentDate);
                        ref1.child("workouts").setValue(workoutList);
                        ref1.child("weight").setValue("new");


                        Log.d("TESTING", "Sign up Successful" + task.isSuccessful());
                        Toast.makeText(LoginActivity.this, "Account Created ", Toast.LENGTH_SHORT).show();
                        Log.d("TESTING", "Created Account");

                        startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
                        // Toast.makeText(MainActivity.this, "Registered successfully", Toast.LENGTH_SHORT).show();
                        displayAlert("Registered successfully", ll_splash);
                        dialog.dismiss();
                    }
                }
            });
        }
    }


    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //for google
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            //handleSignInResult(task);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w("sss", "Google sign in failed", e);
                // [START_EXCLUDE]
               // updateUI(null);
                // [END_EXCLUDE]
            }
        }
        else
        {
            //for facebook
            callbackManager.onActivityResult(requestCode, resultCode, data);
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        // [START_EXCLUDE silent]
        // [END_EXCLUDE]

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            // Sign in success, update UI with the signed-in user's information

                            final FirebaseUser user = auth.getCurrentUser();
                            final String em = user.getEmail();
                            DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
                            final DatabaseReference mChildDatabase = mRootRef.child("Users").child(auth.getCurrentUser().getUid());
                            mChildDatabase.keepSynced(true);
                            mRootRef.child("Users").orderByChild("userEmail").equalTo(em).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.exists())
                                        Log.e("ss", "User exists");
                                    else {
                                        mChildDatabase.child("userEmail").setValue(user.getEmail());
                                        mChildDatabase.child("userID").setValue(user.getUid());
                                        mChildDatabase.child("dateReg").setValue(currentDate);
                                        mChildDatabase.child("workouts").setValue(workoutList);
                                        mChildDatabase.child("weight").setValue("new");
                                        startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
                                        // Toast.makeText(MainActivity.this, "Registered successfully", Toast.LENGTH_SHORT).show();
                                        displayAlert("Registered successfully", ll_splash);
                                        dialog.dismiss();
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                }
                            });

                        } else {

                        }

                        // [START_EXCLUDE]

                        // [END_EXCLUDE]
                    }
                });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask)
    {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            Toast.makeText(LoginActivity.this, "First name = "+account.getDisplayName()+ System.lineSeparator() + "Last name = "+account.getDisplayName()+ System.lineSeparator() + "Email id = "+account.getEmail()+ System.lineSeparator() + "google id = "+account.getId(), Toast.LENGTH_LONG).show();

        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.

            //updateUI(null);
        }
    }


    public void facebook_login() {
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                System.out.println("onSuccess");
                String accessToken = loginResult.getAccessToken().getToken();
                Log.i("accessToken", accessToken);

                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {

                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.i("LoginActivity", response.toString());
                        // Get facebook data from login
                        String jsonString = object.toString();
                        Gson gson = new Gson();
                        facedata = gson.fromJson(jsonString, Facebook.class);

                        if(facedata.getEmail() == null || facedata.getEmail().equals(""))
                        {
                            Toast.makeText(LoginActivity.this, "Facebook data is null", Toast.LENGTH_LONG).show();
                        }
                        else {
                            Toast.makeText(LoginActivity.this, "First name = "+facedata.getFirstName()+ System.lineSeparator() + "Last name = "+facedata.getLastName()+ System.lineSeparator() + "Email id = "+facedata.getEmail()+ System.lineSeparator() + "Facebook id = "+facedata.getId(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id, first_name, last_name, email"); // Par�metros que pedimos a facebook
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(LoginActivity.this, "" + error.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }
}
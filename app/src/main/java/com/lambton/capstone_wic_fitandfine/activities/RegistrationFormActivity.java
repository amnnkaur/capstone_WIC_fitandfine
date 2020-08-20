package com.lambton.capstone_wic_fitandfine.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.telephony.PhoneNumberUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.lambton.capstone_wic_fitandfine.R;
import com.lambton.capstone_wic_fitandfine.models.Facebook;
import com.lambton.capstone_wic_fitandfine.models.User;
import com.lambton.capstone_wic_fitandfine.models.Workout;
import com.lambton.capstone_wic_fitandfine.util.AlertUtil;
import com.lambton.capstone_wic_fitandfine.util.ValidationUtil;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegistrationFormActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = RegistrationFormActivity.class.getSimpleName();
    public static final String BUNDLE_USER_OBJECT = "bundle_user_object";
    @BindView(R.id.ll_facebook)
    LinearLayout llFacebook;
    @BindView(R.id.ll_google)
    LinearLayout llGoogle;
    User user;
    private EditText userNameEditText;
    private EditText emailEditText;
    private EditText phoneEditText;
    private EditText confirmPasswordEditText;
    private EditText passwordEditText;
    private Button but_signup_reg;
    DatabaseReference mDatabaseRef;
    private static final int RC_SIGN_IN = 007;
    private static final String EMAIL = "email";
    CallbackManager callbackManager;
    Facebook facedata;

    private Workout wr;
    public ProgressDialog mProgressDialog;
    private Toolbar mToolbar;
    private String currentDate;
    private List<Workout> workoutList;
    // [START declare_auth]

    private FirebaseAuth mAuth;
    private DatabaseReference mRef;
    // [END declare_auth]
    GoogleSignInClient mGoogleSignInClient;
    GoogleSignInOptions gso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        int colorCodeDark = Color.parseColor("#000000");
        window.setStatusBarColor(colorCodeDark);

        ButterKnife.bind(this);
        initUIViews();
        // [START initialize_auth]
        mAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference();
        mDatabaseRef.keepSynced(true);
        mRef = FirebaseDatabase.getInstance().getReference();
        mRef.keepSynced(true);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        currentDate = sdf.format(new Date());

        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = mDatabase.getReference("Default");
        myRef.keepSynced(true);
        workoutList = new ArrayList<>();
        but_signup_reg.setOnClickListener(this);
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

        llFacebook.setOnClickListener(this);
        llGoogle.setOnClickListener(this);

        callbackManager = CallbackManager.Factory.create();
        facedata = new Facebook();
        facebooklogin();

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        hidekeyboard();

    }
    public void hidekeyboard() {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


        private void initUIViews() {

        userNameEditText = (EditText) findViewById(R.id.activity_reg_form_edittext_name);
        emailEditText = (EditText) findViewById(R.id.activity_reg_form_edittext_email);
        phoneEditText = (EditText) findViewById(R.id.activity_reg_form_edittext_phone);
        but_signup_reg = (Button) findViewById(R.id.but_signup_reg);
        PhoneNumberUtils.formatNumber(phoneEditText.getText().toString());
        PhoneNumberFormattingTextWatcher phoneNumberFormattingTextWatcher = new PhoneNumberFormattingTextWatcher() {
            @Override
            public synchronized void afterTextChanged(Editable s) {
                super.afterTextChanged(s);
                supportInvalidateOptionsMenu();
            }
        };

        confirmPasswordEditText = (EditText) findViewById(R.id.activity_reg_form_edittext_password);
        passwordEditText = (EditText) findViewById(R.id.activity_reg_form_edittext_confirm_password);


        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                supportInvalidateOptionsMenu();
            }
        };

        userNameEditText.addTextChangedListener(textWatcher);
        emailEditText.addTextChangedListener(textWatcher);
        phoneEditText.addTextChangedListener(phoneNumberFormattingTextWatcher);
        confirmPasswordEditText.addTextChangedListener(textWatcher);
        passwordEditText.addTextChangedListener(textWatcher);

    }



    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.but_signup_reg:
                if (isValidForm()) {
                    String txt_email_reg = emailEditText.getText().toString().trim();
                    String txt_password_reg = passwordEditText.getText().toString().trim();
                    System.out.println("ssdsd" +txt_email_reg);
                    Log.d(TAG, "onClick:"+txt_email_reg);
                    Log.d(TAG, "onClick:"+txt_password_reg);
                    registeruser(txt_email_reg, txt_password_reg);
                }
                break;

                case R.id.ll_google:
                    signIn();

                    break;

                case R.id.ll_facebook:

                    //disconnectFromFacebook();
                    LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email", "public_profile"));
                    break;
        }
    }
    public void registeruser(String email, String password) {
        displaydialog("Loading please wait....");
        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            final FirebaseUser firebaseUser = mAuth.getCurrentUser();
                            Log.d(TAG, "onComplete: dddd");
                            if (firebaseUser != null) {
                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                        .setDisplayName(user.getFormattedName().trim())
                                        .setDisplayName(user.getFormattedName().trim())
                                        //.setPhotoUri(Uri.parse("https://example.com/jane-q-user/profile.jpg"))  // here you can set image link also.
                                        .build();
                                firebaseUser.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {

                                            String currentuserid = firebaseUser.getUid();

                                            FirebaseUser current = mAuth.getCurrentUser();
                                            String UserID = current.getEmail().replace("@", "").replace(".", "");

                                            DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
                                            DatabaseReference mChildDatabase = mRootRef.child("Users").child(UserID);
                                            mChildDatabase.child("userEmail").setValue(current.getEmail());
                                            mChildDatabase.child("userID").setValue(current.getUid());
                                            mChildDatabase.child("dateReg").setValue(currentDate);
                                            mChildDatabase.child("workouts").setValue(workoutList);
                                            mChildDatabase.child("weight").setValue("new");

                                            startActivity(new Intent(RegistrationFormActivity.this, LoginActivity.class));
                                            // Toast.makeText(MainActivity.this, "Registered successfully", Toast.LENGTH_SHORT).show();
                                            AlertUtil.showAlertDialog(RegistrationFormActivity.this,"Registered successfully");

                                        }
                                    }
                                });

                            // [START_EXCLUDE]
                                displaydialog("Loading please wait....");
                            // [END_EXCLUDE]
                        }
                    }
                }});
        // [END create_user_with_email]

    }
    public  void displaydialog( String strMessage) {
        Dialog dialog;
        dialog = new Dialog(RegistrationFormActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(RegistrationFormActivity.this, R.color.transparent)));
        dialog.setContentView(R.layout.loading_dialog);
        dialog.setCancelable(true);

        TextView message= dialog.findViewById(R.id.txt_loading);
        message.setText(strMessage);

        dialog.show();
    }
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private User prepareUser() {

        user.setUsername(userNameEditText.getText().toString());
        user.setEmailAddress(emailEditText.getText().toString());
        user.setPassword(passwordEditText.getText().toString());
        user.setContactNumber(phoneEditText.getText().toString());
        return user;
    }

    private boolean isValidForm() {

        if (ValidationUtil.isValidText(userNameEditText.getText().toString()) &&
                ValidationUtil.isValidText(emailEditText.getText().toString()) &&
                ValidationUtil.isValidText(phoneEditText.getText().toString()) &&
                ValidationUtil.isValidText(passwordEditText.getText().toString()) &&
                ValidationUtil.isValidText(confirmPasswordEditText.getText().toString())) {


            if (ValidationUtil.isEmailAddressValid(emailEditText.getText().toString())) {

                if (ValidationUtil.isValidPassword(passwordEditText.getText().toString())) {

                    //check for password now.
                    if (passwordEditText.getText().toString().equalsIgnoreCase(confirmPasswordEditText.getText().toString())) {

                        if (ValidationUtil.isValidPhoneNumber(phoneEditText.getText().toString())) {
                            return true;
                        } else {
                            AlertUtil.showAlertDialog(this, getString(R.string.error_phone_length_wrong));
                        }
                    }

                } else {
                    AlertUtil.showAlertDialog(this, getString(R.string.error_confirm_password_not_matched));
                }


            } else {
                AlertUtil.showAlertDialog(this, getString(R.string.error_enter_valid_email_msg));

            }

        } else {
            AlertUtil.showAlertDialog(this, getString(R.string.error_enter_all_fields_msg));
        }


        if (ValidationUtil.isEmailAddressValid(passwordEditText.getText().toString())
                || ValidationUtil.isValidText(confirmPasswordEditText.getText().toString())) {


            return false;
        }

        return true;
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
           // Toast.makeText(LoginActivity.this, "First name = "+account.getDisplayName()+ System.lineSeparator() + "Last name = "+account.getDisplayName()+ System.lineSeparator() + "Email id = "+account.getEmail()+ System.lineSeparator() + "google id = "+account.getId(), Toast.LENGTH_LONG).show();

        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.

            //updateUI(null);
        }
    }


    public void facebooklogin() {
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                String accessToken = loginResult.getAccessToken().getToken();
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
                          //  AlertUtil.showAlertDialog(this,"Facebook data is null");
                        }
                        else {
                          //  Toast.makeText(MainActivity.this, "First name = "+facedata.getFirstName()+ System.lineSeparator() + "Last name = "+facedata.getLastName()+ System.lineSeparator() + "Email id = "+facedata.getEmail()+ System.lineSeparator() + "Facebook id = "+facedata.getId(), Toast.LENGTH_LONG).show();
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
               // Toast.makeText(LoginActivity.this, "" + error.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }

}

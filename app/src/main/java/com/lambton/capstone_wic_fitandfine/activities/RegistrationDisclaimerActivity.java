package com.lambton.capstone_wic_fitandfine.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.lambton.capstone_wic_fitandfine.R;
import com.lambton.capstone_wic_fitandfine.adapter.login.RegistrationDisclaimerAdapter;
import com.lambton.capstone_wic_fitandfine.models.User;
import com.lambton.capstone_wic_fitandfine.util.AlertUtil;
import com.lambton.capstone_wic_fitandfine.util.JsonUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class RegistrationDisclaimerActivity extends AppCompatActivity {

    private static final String TAG = RegistrationDisclaimerActivity.class.getSimpleName();
    DatabaseReference mDatabaseRef;
    private RecyclerView recyclerView;
    private Toolbar mToolbar;
    private ArrayList<JSONObject> articles;
    private RegistrationDisclaimerAdapter disclaimerAdapter;
    private CheckBox mCheckBox;
    private User user;
    // [START declare_auth]
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;
    private DatabaseReference mRef;
    // [END declare_auth]
    private String currentDate;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
        //      WindowManager.LayoutParams.FLAG_SECURE);

        setContentView(R.layout.activity_registration_disclaimer);
        initUIViews();
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // [START initialize_auth]
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_onboarding, menu);

        menu.findItem(R.id.action_next).setEnabled(termsAcctepted());
        return true;
    }
    // [START on_start_check_user]
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            updateUI(currentUser);
        }
    }
    // [END on_start_check_user]

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_next:
                if (termsAcctepted()) {
                    registerButtonClicked();
                }
        }
        return true;
    }

    protected void initUIViews() {
        mToolbar = (Toolbar) findViewById(R.id.activity_terms_toolbar);
        mCheckBox = (CheckBox) findViewById(R.id.activity_terms_checkbox);
        mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                supportInvalidateOptionsMenu();
            }
        });
        if (getIntent().getExtras() != null) {
            user = (User) getIntent().getExtras().get(RegistrationFormActivity.BUNDLE_USER_OBJECT);
            Log.d("RegistrationDisclaimer", "Pass: " + user.getPassword());
        }
        recyclerView = (RecyclerView) findViewById(R.id.activity_reg_disclaimer_recycler_view);
        initRecyclerView();
    }

    @SuppressLint("WrongConstant")
    private void initRecyclerView() {
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(OrientationHelper.VERTICAL);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(llm);
        disclaimerAdapter = new RegistrationDisclaimerAdapter(this, addMedicalDisclaimer(getPrivacyTerms()));
        recyclerView.setAdapter(disclaimerAdapter);
    }

    private boolean termsAcctepted() {
        return mCheckBox.isChecked();
    }

    private ArrayList<JSONObject> addMedicalDisclaimer(ArrayList<JSONObject> terms) {

        if (terms != null) {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("title", getString(R.string.register_medical_disclaimer));
                jsonObject.put("description", getString(R.string.register_medical_description));
                jsonObject.put("isExpanded", false);
                terms.add(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return terms;
    }

    private void doRegistration() {
        Log.d("DoRegistration", "Pass: " + user.getPassword());

        HashMap<String, String> params = new HashMap<>();
        params.put("userName", user.getUsername());
        params.put("userEmailId", user.getEmailAddress());
        params.put("userContact", user.getContactNumber());
        params.put("userNewPwd1", user.getPassword());
        params.put("userNewPwd2", user.getPassword());
        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(user.getEmailAddress(), user.getPassword())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            // Sign in success, update UI with the signed-in user's information
                            final DatabaseReference mChildDatabase = mDatabaseRef.child("Users").child(mAuth.getCurrentUser().getUid());
                            mChildDatabase.keepSynced(true);
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            mChildDatabase.child("userEmailId").setValue(user.getEmail());
                            mChildDatabase.child("userID").setValue(user.getUid());
                            mChildDatabase.child("dateReg").setValue(currentDate);
                            mChildDatabase.child("weight").setValue("new");
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegistrationDisclaimerActivity.this, task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                    }
                });
        // [END create_user_with_email]

    }
    private void updateUI(FirebaseUser user) {

        if (user != null) {
            //for (UserInfo userz: FirebaseAuth.getInstance().getCurrentUser().getProviderData()) {
            Intent intent = new Intent(RegistrationDisclaimerActivity.this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }
    private void registerButtonClicked() {
        doRegistration();
    }

    private ArrayList<JSONObject> getPrivacyTerms() {
        ArrayList<JSONObject> jsonObjects = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("title", "Agreement");
            jsonObject.put("description", "By using AskWatsonPain, you signify your assent to these terms and conditions. If you do not agree to all of these terms and conditions, do not use AskWatsonPain! \n\n We may revise and update these terms and conditions at any time.  Your continued usage of AskWatsonPain will mean you accept those changes. \n");
            jsonObject.put("isExpanded", false);
            jsonObjects.add(jsonObject);

            jsonObject = new JSONObject();
            jsonObject.put("title", "Privacy Policy");
            jsonObject.put("description", "The privacy of personal information is of paramount importance to AWP and its users. AWP follows strict HIPAA guidelines with respect to the collection, storage and treatment of personal information - https://www.hhs.gov/hipaa/for-professionals/privacy/index.html. As a further safeguard, blockchain technology is the backbone of AWP’s data storage and transfer. Blockchain uses best practice data encryption to ensure the most secure environment for the privacy of your personal information. ");
            jsonObject.put("isExpanded", false);

            jsonObject = new JSONObject();
            jsonObject.put("title", "We Do Not Provide Medical Advice");
            jsonObject.put("description", "Our service is intended to provide general educational information and to help users better utilize their own health care services.  We do not provide medical advice. \n\n The contents of AskWatsonPain, such as text, graphics, images, information obtained from our licensors, and other material provided by AskWatsonPain (“Content”) are for informational purposes only.  The Content is not intended to be a substitute for professional medical advice, diagnosis, or treatment.  Always seek the advice of your physician or other qualified health provider with any questions you may have regarding a medical condition. \nNever disregard professional medical advice or delay in seeking it because of something you have read on the Websites! \n\n If you think you may have a medical emergency, call your doctor or 911 immediately.  We do not recommend or endorse any specific tests, physicians, products, procedures, opinions, or other information that may be mentioned by AskWatsonPain.  Reliance on any information provided by us, our employees, others appearing at our invitation, or other visitors to AskWatsonPain is solely at your own risk. ");
            jsonObject.put("isExpanded", false);
            jsonObjects.add(jsonObject);


            jsonObject = new JSONObject();
            jsonObject.put("title", "Children's Privacy");
            jsonObject.put("description", "We are committed to protecting the privacy of children.  You should be aware that AskWatsonPain is not intended or designed to attract children under the age of 13.  We do not collect personally identifiable information from any person we actually know is a child under the age of 13.");
            jsonObject.put("isExpanded", false);
            jsonObjects.add(jsonObject);


            jsonObject = new JSONObject();
            jsonObject.put("title", "Use of Materials");
            jsonObject.put("description", "All materials available through AskWatsonPain are protected by the copyright laws in the United States and in foreign countries.  We authorize you to view or download a single copy of such material solely for your personal, noncommercial use if you include the copyright notice located at the end of the material.  All rights reserved and other copyright and proprietary rights notices that are contained in the Content.  Any special rules for the use of certain software and other items accessible through our website or mobile app may be included elsewhere within the website or mobile app and are incorporated by reference into these terms and conditions \n Title to the materials remains with us or our licensors.  Any use of the materials not expressly permitted by these terms and conditions is a breach of these terms and conditions and may violate copyright, trademark, and other laws.  Content and features are subject to change or termination without notice in our editorial discretion.  All rights not expressly granted herein are reserved to us and our licensors. \n If you violate any of these terms and conditions, your permission to use the materials automatically terminates and you must immediately destroy any copies you have made of any portion of the materials.");
            jsonObject.put("isExpanded", false);
            jsonObjects.add(jsonObject);


            jsonObject = new JSONObject();
            jsonObject.put("title", "Our Liability");
            jsonObject.put("description", "The use of AskWatsonPain is at your own risk. \n When using AskWatsonPain, information will be transmitted over a medium that may be beyond the control and jurisdiction of us and our suppliers.  Accordingly, we assume no liability for or relating to the delay, failure, interruption, or corruption of any data or other information transmitted in connection with use of AskWatsonPain. \n AskWatsonPain and the Content are provided on an \"as is\" basis.  WE, OUR LICENSORS, AND OUR SUPPLIERS, TO THE FULLEST EXTENT PERMITTED BY LAW, DISCLAIM ALL WARRANTIES, EITHER EXPRESS OR IMPLIED, STATUTORY OR OTHERWISE, INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES OF MERCHANTABILITY, NON-INFRINGEMENT OF THIRD PARTIES' RIGHTS, AND FITNESS FOR PARTICULAR PURPOSE.  \n In no event shall we, our licensors, our suppliers, or any third parties mentioned by AskWatsonPain be liable for any damages (including, without limitation, incidental and consequential damages, personal injury/wrongful death, lost profits, or damages resulting from lost data or business interruption) resulting from the use of or inability to use AskWatsonPain or the Content, whether based on warranty, contract, tort, or any other legal theory, and whether or not we, our licensors, our suppliers, or any third parties mentioned by AskWatsonPain are advised of the possibility of such damages.  We, our licensors, our suppliers, or any third parties mentioned by AskWatsonPain shall be liable only to the extent of actual damages incurred by you, not to exceed U.S. $1000.  We, our licensors, our suppliers, or any third parties mentioned by AskWatsonPain are not liable for any personal injury, including death, caused by your use or misuse AskWatsonPain, Content, or Public Areas (as defined below).  Any claims arising in connection with your use of AskWatsonPain, any Content, or the Public Areas must be brought within one (1) year of the date of the event giving rise to such action occurred.  Remedies under these Terms and Conditions are exclusive and are limited to those expressly provided for in these Terms and Conditions. \n ");
            jsonObject.put("isExpanded", false);
            jsonObjects.add(jsonObject);


            jsonObject = new JSONObject();
            jsonObject.put("title", "User Submissions");
            jsonObject.put("description", "The personal information you submit to us is governed by our Privacy Policy.  To the extent there is an inconsistency between this Agreement and the Privacy Policy, this Agreement will govern.\n AskWatsonPain contains functionality (such as message boards, blogs, etc.) that allows users to upload content to AskWatsonPain (collectively \"Public Areas\" ).\n  You agree that you will not upload or transmit any communications or content of any type to the Public Areas that infringe or violate any rights of any party.  By submitting communications or content to the Public Areas, you agree that such submission is non-confidential for all purposes. \n");
            jsonObject.put("isExpanded", false);
            jsonObjects.add(jsonObject);


            jsonObject = new JSONObject();
            jsonObject.put("title", "User Submissions — Image, Video, Audio Files");
            jsonObject.put("description", "You agree to only post or upload Media (like photos, videos or audio) that you have taken yourself or that you have all rights to transmit and license and which do not violate trademark, copyright, privacy or any other rights of any other person.  Photos or videos of celebrities and cartoon or comic images are usually copyrighted by the owner. \n To protect your privacy, you agree that you will not submit any media that contains Personally Identifiable Information (like name, phone number, email address or web site URL) of you or of anyone else.  Uploading media like images or video of other people without their permission is strictly prohibited. \n By uploading any media to AskWatsonPain, you warrant that you have permission from all persons appearing in your media for you to make this contribution and grant rights described herein.  Never post a picture or video of or with someone else unless you have their explicit permission. \n It is strictly prohibited to upload media of any kind that contain expressions of hate, abuse, offensive images or conduct, obscenity, pornography, sexually explicit or any material that could give rise to any civil or criminal liability under applicable law or regulations or that otherwise may be in conflict with these Terms and Conditions, or our Privacy Policy. \n You agree that you will not upload any material that contains software viruses or any other computer code, files or programs designed to interrupt, destroy or limit the functionality of any computer software or our website. \n By uploading any media like a photo or video, (a) you grant to us a perpetual, non-exclusive, worldwide, royalty-free license to use, copy, print, display, reproduce, modify, publish, post, transmit and distribute the media and any material included in the media; (b) you certify that any person pictured in the submitted media (or, if a minor, his/her parent/legal guardian) authorizes us to use, copy, print, display, reproduce, modify, publish, post, transmit and distribute the media and any material included in such media; and (c) you agree to indemnify our company and its affiliates, directors, officers and employees and hold them harmless from any and all claims and expenses, including attorneys' fees, arising from the media and/or your failure to comply with these the terms described in this document. \n We reserve the right to review all media prior to submission to the site and to remove any media for any reason, at any time, without prior notice, at our sole discretion");
            jsonObject.put("isExpanded", false);
            jsonObjects.add(jsonObject);


            jsonObject = new JSONObject();
            jsonObject.put("title", "Passwords");
            jsonObject.put("description", "We have several tools that allow you to record and store information.  You are responsible for taking all reasonable steps to ensure that no unauthorized person shall have access to your passwords or accounts.  It is your sole responsibility to (1) control the dissemination and use of sign-in name, screen name and passwords; (2) authorize, monitor, and control access to and use of your account and password; (3) promptly inform us if you believe your account or password has been compromised or if there is any other reason you need to deactivate a password.  You grant us and all other persons or entities involved in the operation of AskWatsonPain the right to transmit, monitor, retrieve, store, and use your information in connection with the operation of AskWatsonPain.  We cannot and does not assume any responsibility or liability for any information you submit, or your or third parties' use or misuse of information transmitted or received using our tools and services.");
            jsonObject.put("isExpanded", false);
            jsonObjects.add(jsonObject);


            jsonObject = new JSONObject();
            jsonObject.put("title", "AskWatsonPain Community and Member to Member Areas (\"Public Areas\")");
            jsonObject.put("description", "");
            jsonObject.put("isExpanded", false);
            jsonObjects.add(jsonObject);


            jsonObject = new JSONObject();
            jsonObject.put("title", "Advertisements, Searches, and Links to Other Sites");
            jsonObject.put("description", "If you use a Public Area, such as message boards and blogs, or other member communities, you are solely responsible for your own communications, the consequences of posting those communications, and your reliance on any communications found in the Public Areas.  We and our licensors are not responsible for the consequences of any communications in the Public Areas.  In cases where you feel threatened or believe someone else is in danger, you should contact your local law enforcement agency immediately.  If you think you may have a medical emergency, call your doctor or 911 immediately. \n In consideration of being allowed to use the Public Areas, you agree that the following actions will constitute a material breach of these Terms and Conditions: \n  \n •\tUsing a Public Area for any purpose in violation of local, state, national, or international laws; \n  \n •\tPosting material that infringes on the intellectual property rights of others or on the privacy or publicity rights of others; \n  \n •\tPosting material that is unlawful, obscene, defamatory, threatening, harassing, abusive, slanderous, hateful, or embarrassing to any other person or entity as determined by us in our sole discretion; \n  \n •\tPosting advertisements or solicitations of business; \n  \n •\tAfter receiving a warning, continuing to disrupt the normal flow of dialogue, or posting comments that are not related to the topic being discussed (unless it is clear the discussion is free-form); \n  \n •\tPosting chain letters or pyramid schemes; \n \n •\tImpersonating another person; \n \n •\tDistributing viruses or other harmful computer code; \n \n •\tHarvesting, scraping or otherwise collecting information about others, including email addresses, without their identification for posting or viewing comments; \n \n •\tAllowing any other person or entity to use your identification for posting or viewing comments \n \n •\tPosting the same note more than once or \"spamming\"; or \n  \n •\tEngaging in any other conduct that restricts or inhibits any other person from using or enjoying the Public Area or AskWatsonPain, or which, in our judgment, exposes us or any of our customers or suppliers to any liability or detriment of any type. \n We reserve the right (but are not obligated) to do any or all of the following: \n  \n •\tRecord the dialogue in public chat rooms; \n \n •\tInvestigate an allegation that a communication(s) do(es) not conform to the terms of this section and determine in our sole discretion to remove or request the removal of the communication(s); \n  \n •\tRemove communications which are abusive, illegal, or disruptive, or that otherwise fail to conform with these Terms and Conditions; \n \n•\tTerminate a user's access to any or all Public Areas and/or the AskWatsonPain upon any breach of these Terms and Conditions; \n  \n •\tMonitor, edit, or disclose any communication in the Public Areas; \n  \n •\tEdit or delete any communication(s) posted on AskWatsonPain, regardless of whether such communication(s) violate these standards.\n We and our licensors have no liability or responsibility to users of the AskWatsonPain or any other person or entity for performance or nonperformance of the aforementioned activities.");
            jsonObject.put("isExpanded", false);
            jsonObjects.add(jsonObject);


            jsonObject = new JSONObject();
            jsonObject.put("title", "Indemnity");
            jsonObject.put("description", "You agree to defend, indemnify, and hold our company, its officers, directors, employees, agents, licensors, and suppliers, harmless from and against any claims, actions or demands, liabilities and settlements including without limitation, reasonable legal and accounting fees, resulting from, or alleged to result from, your violation of these Terms and Conditions.");
            jsonObject.put("isExpanded", false);
            jsonObjects.add(jsonObject);


            jsonObject = new JSONObject();
            jsonObject.put("title", "General");
            jsonObject.put("description", "We designed AskWatsonPain in compliance with the laws of the United States.  We make no claims that AskWatsonPain and the Content are appropriate or may be downloaded outside of the United States.  Access to the Content may not be legal by certain persons or in certain countries.  If you access the AskWatsonPain from outside the United States, you do so at your own risk and are responsible for compliance with the laws of your jurisdiction.\n The following provisions survive the expiration or termination of this Agreement for any reason whatsoever:  Liability, User Submissions, User Submissions – image, video, audio files, Indemnity, Jurisdiction, and Complete Agreement.");
            jsonObject.put("isExpanded", false);
            jsonObjects.add(jsonObject);


            jsonObject = new JSONObject();
            jsonObject.put("title", "Jurisdiction");
            jsonObject.put("description", "You expressly agree that exclusive jurisdiction for any dispute with us, or in any way relating to your use of the AskWatsonPain, resides in the courts of the State of California located in the County of Los Angeles and you further agree and expressly consent to the exercise of personal jurisdiction in the courts of the State of California in connection with any such dispute including any claim involving us or our affiliates, subsidiaries, employees, contractors, officers, directors, telecommunication providers, and content providers. \n These Terms and Conditions are governed by the internal substantive laws of the State of California, without respect to its conflict of laws principles.  If any provision of these Terms and Conditions is found to be invalid by any court having competent jurisdiction, the invalidity of such provision shall not affect the validity of the remaining provisions of these Terms and Conditions, which shall remain in full force and effect.  No waiver of any of these Terms and Conditions shall be deemed a further or continuing waiver of such term or condition or any other term or condition.");
            jsonObject.put("isExpanded", false);
            jsonObjects.add(jsonObject);


            jsonObject = new JSONObject();
            jsonObject.put("title", "Notice and Takedown Procedures; and Copyright Agent");
            jsonObject.put("description", "If you believe any materials accessible on or from AskWatsonPain infringe your copyright, you may request removal of those materials (or access thereto) by contacting our's copyright agent (identified below) and providing the following information: \n \n•\tIdentification of the copyrighted work that you believe to be infringed. Please describe the work, and where possible include a copy or the location (e.g., URL) of an authorized version of the work. \n \n•\tIdentification of the material that you believe to be infringing and its location. Please describe the material, and provide us with its URL or any other pertinent information that will allow us to locate the material. \n \n •\tYour name, address, telephone number and (if available) e-mail address. \n \n•\tA statement that you have a good faith belief that the complained of use of the materials is not authorized by the copyright owner, its agent, or the law.\n \n •\tA statement that the information that you have supplied is accurate, and indicating that \"under penalty of perjury,\" you are the copyright owner or are authorized to act on the copyright owner's behalf.\n\n•\tA signature or the electronic equivalent from the copyright holder or authorized representative.\n Our agent for copyright issues relating to this web site is as follows:\n AskWatsonPain \n Attn: Legal Department \n 5242 Seneca Place \n Simi Valley, CA 93063 \n dgcahm@gmail.com \n Or call: 805.368.4164");
            jsonObject.put("isExpanded", false);
            jsonObjects.add(jsonObject);


            jsonObject = new JSONObject();
            jsonObject.put("title", "Complete Agreement");
            jsonObject.put("description", "Except as expressly provided in a particular \"legal notice\" on AskWatsonPain, these Terms and Conditions and the Privacy Policy constitute the entire agreement between you and us with respect to the use of AskWatsonPain.");
            jsonObject.put("isExpanded", false);
            jsonObjects.add(jsonObject);


            jsonObject = new JSONObject();
            jsonObject.put("title", "Privacy Notice");
            jsonObject.put("description", "This privacy notice discloses the privacy practices for @WatsonPain.  This privacy notice applies solely to information collected by [Offering Name]. Green Sky Labs and IBM are providing notice of the following:\n •\tWhat personally identifiable information is collected from you through the website, how it is used and with whom it may be shared.\n •\tWhat choices are available to you regarding the use of your data.\n •\tThe security procedures in place to protect the misuse of your information.\n •\tHow you can correct any inaccuracies in the information.");
            jsonObject.put("isExpanded", false);
            jsonObjects.add(jsonObject);


            jsonObject = new JSONObject();
            jsonObject.put("title", "Information Collection, Use, and Sharing");
            jsonObject.put("description", "Your information is being collected and processed by Green Sky Labs and IBM for to develop a personality type graph.  The information we collect includes:  name, email, phone, location {optional), height and weight (optional}.  We will also collect information contained on your Facebook page. \n Your Facebook information will not be retained once the personality graph is generated.  You will be able to view your personality type graph; subject to your signing an appropriate release, your physician may be able to view the personality type graph.  \n Green Sky Labs and IBM will not share your information with any third party outside of our organization.\n We may contact you via email in the future regarding follow up information requests for additional research. ");
            jsonObject.put("isExpanded", false);
            jsonObjects.add(jsonObject);


            jsonObject = new JSONObject();
            jsonObject.put("title", "Our Access to and Control Over Information");
            jsonObject.put("description", "You are not required to provide any additional information to Green Sky Labs or IBM.  You can do the following at any time by contacting us via the email at askwatsonpain@gsl.com.\n •\tRequest information about the data Green Sky Labs and IBM has about you.\n •\tRequest changes or correction to any data Green Sky Labs and IBM has about you.\n •\tHave Green Sky Labs and IBM delete any data we have about you.\n •\tExpress any concern you have about our use of your data.");
            jsonObject.put("isExpanded", false);
            jsonObjects.add(jsonObject);


            jsonObject = new JSONObject();
            jsonObject.put("title", "Security");
            jsonObject.put("description", "We take precautions to protect your information. When you submit sensitive information via the website, your information is protected both online and offline.\n Wherever we collect sensitive information (such as medical data), that information is encrypted and transmitted to us in a secure way. \n While we use encryption to protect sensitive information transmitted online, we also protect your information offline. Only employees who need the information to perform a specific job (for example, billing or customer service) are granted access to personally identifiable information. The computers/servers in which we store personally identifiable information are kept in a secure environment.Stored data is also encrypted.");
            jsonObject.put("isExpanded", false);
            jsonObjects.add(jsonObject);


            jsonObject = new JSONObject();
            jsonObject.put("title", "Medical Disclaimer");
            jsonObject.put("description", "Our service is intended to provide general educational information and to help users better utilize their own health care services. We do not provide medical advice.");
            jsonObject.put("isExpanded", false);
            jsonObjects.add(jsonObject);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObjects;
    }
}
package com.sportcitizen.sportcitizen.activities;

/**
 * Created by Axel Drozdzynski
 */

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sportcitizen.sportcitizen.R;
import com.sportcitizen.sportcitizen.models.UserModel;

import org.json.JSONArray;

import java.util.Arrays;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private FirebaseDatabase mDatabase;
    private DatabaseReference mDatabaseRef;

    private static final int RC_SIGN_IN = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        runLoginMenu();
    }

    private void runLoginMenu() {
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.Builder(AuthUI.FACEBOOK_PROVIDER).build());


        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(),
                RC_SIGN_IN);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                try {
                    mDatabase = FirebaseDatabase.getInstance();
                }catch (Exception e) {
                    Log.d("Exception", e.getMessage());
                }
                setInfoOnDB();
                Intent intent = new Intent(this, MainActivity.class);
                this.startActivity(intent);
                this.finish();
            } else {

            }
        }
    }

    public void signOut() {
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });
    }

    /**
     * Useful for first connexion
     * And when facebook info has been updated
     */
    public void setInfoOnDB() {
        FirebaseUser user;
        UserModel model;
        DatabaseReference ref;
        AccessToken facebookToken = null;
        GraphRequest request;

        model = new UserModel();
        user = FirebaseAuth.getInstance().getCurrentUser();
        //facebookToken =
                ref = mDatabase.getReference("users").child(user.getUid());
        model.email = user.getEmail();
        if (!user.getProviderData().isEmpty() && user.getProviderData().size() > 1)
            model.photoURL = "https://graph.facebook.com/" + user.getProviderData().get(1).getUid() + "/picture?type=large";
        else
            model.photoURL = user.getPhotoUrl().toString();
        model.age = 23; // we'll need to get from facebook
        model.city = "Bordeaux 2"; // we'll need to get from location

        ref.child("email").setValue(model.email);
        ref.child("photoURL").setValue(model.photoURL);
        ref.child("age").setValue(model.age); // we'll need to get from facebook
        ref.child("city").setValue(model.city); // we'll need to get from location
    }

    public void askFacebookBH(AccessToken token, String userId, UserModel model) {
        GraphRequest request;

        request = GraphRequest.newGraphPathRequest(token, "/me?fields=id", new GraphRequest.Callback() {
            @Override
            public void onCompleted(GraphResponse response) {
                String str = response.toString();
                Log.d("FacebookRequest", str);
            }
        });
    }


    private class CustomUserModel {
        public String email = "";
        public String photoURL = "";
        public long age = 0;
        public String city = "";

        public CustomUserModel(){}
    }

}

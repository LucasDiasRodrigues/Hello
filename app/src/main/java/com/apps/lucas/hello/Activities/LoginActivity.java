package com.apps.lucas.hello.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.apps.lucas.hello.Adapter.LoginSlidesFragmentPagerAdapter;
import com.apps.lucas.hello.Modelo.Usuario;
import com.apps.lucas.hello.R;
import com.apps.lucas.hello.Util.FacebookJson;
import com.apps.lucas.hello.Util.FirebaseDB;
import com.apps.lucas.hello.Util.Preferencias;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.plus.model.people.Person;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    //Facebook
    CallbackManager callbackManager;
    Profile profile;

    //Firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private String TAG = "firebaseAuth";

    final Preferencias preferencias = new Preferencias(this);

    private ViewPager mViewPager;
    private ImageView mWallpaper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //inicializando o Facebook antes de vincular o layout da activity
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_login);

        //firebase
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };

        mWallpaper = (ImageView) findViewById(R.id.imageWallpaper);

        //Slides
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        ArrayList<String> txts = new ArrayList<>();
        txts.add("Textinho motivador 1");
        txts.add("Textinho motivador 2");
        txts.add("Textinho motivador 3");
        txts.add("Textinho motivador 4");

        LoginSlidesFragmentPagerAdapter adapter = new LoginSlidesFragmentPagerAdapter(getSupportFragmentManager(),this, txts);
        mViewPager.setAdapter(adapter);







        LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("email", "public_profile");
        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.i("succesLogin", "Sucesso");

                profile = Profile.getCurrentProfile();

                String userId = loginResult.getAccessToken().getUserId();
                String accessToken = loginResult.getAccessToken().getToken();

                // save accessToken to SharedPreference
                preferencias.saveFacebookAccessToken(accessToken);
                preferencias.saveFacebookUserId(userId);

                //faz o login no firebase
                handleFacebookAccessToken(loginResult.getAccessToken());


            }

            @Override
            public void onCancel() {

                Log.i("cancelLogin", "Cancel");
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                Log.i("errorLogin", "erro");
            }
        });


    }


    private void handleFacebookAccessToken(final AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        final AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(LoginActivity.this, "Falha na autenticação com o Firebase.",
                                    Toast.LENGTH_SHORT).show();
                        } else {

                          //  verificarCadastroFirebaseDB();
                            onLogin();
                        }

                        // ...
                    }
                });
    }

    public void verificarCadastroFirebaseDB(){

        FirebaseDB firebaseDB = new FirebaseDB();
        firebaseDB.isCadastrado(mAuth.getCurrentUser().getUid(),this);




    }

    public void cadastrarNovoUsuarioFireDB(){

        Usuario usuario = new Usuario();
        usuario.setNome(profile.getName());
        usuario.setFacebookProfileId(profile.getId());

        FirebaseDB firebaseDB = new FirebaseDB();
        firebaseDB.cadastrarNewUser(mAuth.getCurrentUser().getUid(),usuario,this);
    }





    public void onLogin() {
        Log.i("Login", "onLogin");
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("faceProfile", profile);

        startActivity(intent);
        finish();
    }


    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Direciona o usuario para a proxima activity se logado

        AccessToken act = AccessToken.getCurrentAccessToken();
        if (act != null && !act.isExpired()) {
            profile = Profile.getCurrentProfile();
            onLogin();
        }


    }

    @Override
    //Obrigatorio sempre que o app utilizar umas das fragments do facebook
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


}


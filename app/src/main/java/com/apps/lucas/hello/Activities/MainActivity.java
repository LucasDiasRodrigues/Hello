package com.apps.lucas.hello.Activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.apps.lucas.hello.Fragments.ConversasFragment;
import com.apps.lucas.hello.Fragments.HomeFragment;
import com.apps.lucas.hello.Fragments.PerfilFragment;
import com.apps.lucas.hello.Modelo.Usuario;
import com.apps.lucas.hello.R;
import com.apps.lucas.hello.Util.FacebookJson;
import com.apps.lucas.hello.Util.Preferencias;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener {

    Profile userProfile;

    Usuario usuario;
    String coverUrl = "";

    Preferencias prefs;


    CallbackManager callbackManager;

    private Toolbar toolbar;
    private AppBarLayout appBarLayout;
    private CollapsingToolbarLayout collapsingToolbarLayout;
   // private FloatingActionButton fabEditPerfil;

    //Imagem com Animacao
    private static final int PERCENTAGE_TO_ANIMATE_AVATAR = 20;
    private boolean mIsAvatarShown = true;
    private ImageView mProfileImage;
    private TextView txtCurtidas;
    private TextView txtSeguidores;
    private int mMaxScrollSize;
    private boolean blockTollbar;
    //


    private int FragVisivel;//0, 1 ou 2


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //inicializando o Facebook antes de vincular o layout da activity
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        //Pega os dados adicionais do facebook
        getFacebookData();

        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        appBarLayout = (AppBarLayout) findViewById(R.id.appBar);
        appBarLayout.addOnOffsetChangedListener(this);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing);


        //Trocar o avatar aqui com o Picasso
        //
        mProfileImage = (CircleImageView) findViewById(R.id.materialup_profile_image);
        mProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PerfilActivity.class);
                startActivity(intent);
            }
        });

      //  fabEditPerfil = (FloatingActionButton) findViewById(R.id.fabEdit);
        /*fabEditPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PerfilActivity.class);
                startActivity(intent);
            }
        });
*/

        //Recupera o profile entregue na Intent
        final Intent intent = getIntent();
        userProfile = intent.getParcelableExtra("faceProfile");

        //inicializa preferencias
        prefs = new Preferencias(this);


        //BottomBar
        BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        appBarLayout.setExpanded(false, false);
        appBarLayout.setActivated(false);
        appBarLayout.setEnabled(false);
        bottomBar.selectTabAtPosition(1);
        toolbar.setTitle(R.string.home);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                if (tabId == R.id.tabPerfil) {
                    // The tab with id R.id.tab_favorites was selected,
                    // change your content accordingly.

                    PerfilFragment perfilFragment = new PerfilFragment();

                    Bundle bundle = new Bundle();
                    bundle.putString("userId", userProfile.getId());
                    perfilFragment.setArguments(bundle);

                    FragmentTransaction transactionPerfil = getSupportFragmentManager().beginTransaction();
                    transactionPerfil.replace(R.id.main_container, perfilFragment);

                    transactionPerfil.commit();

                    //TollBar
                    toolbar.setTitle(R.string.perfil);
                    appBarLayout.setExpanded(true, true);
                    appBarLayout.setActivated(true);
                    appBarLayout.setEnabled(true);
                    blockTollbar = false;
                    //TollBar


                    FragVisivel = 0;
                }

                if (tabId == R.id.tabHome) {

                    HomeFragment homeFragment = new HomeFragment();
                    FragmentTransaction transactionHome = getSupportFragmentManager().beginTransaction();
                    transactionHome.replace(R.id.main_container, homeFragment);
                    transactionHome.setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    transactionHome.commit();

                    //TollBar
                    toolbar.setTitle(R.string.home);
                    appBarLayout.setExpanded(false, true);
                    appBarLayout.setActivated(false);
                    appBarLayout.setEnabled(false);

                    new Thread() {
                        public void run() {
                            try {
                                sleep(300);
                                blockTollbar = true;
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }

                        ;
                    }.start();

                    //TollBar

                    FragVisivel = 1;
                }

                if (tabId == R.id.tabMensagens) {

                    ConversasFragment conversasFragment = new ConversasFragment();
                    FragmentTransaction transactionConversas = getSupportFragmentManager().beginTransaction();
                    transactionConversas.replace(R.id.main_container, conversasFragment);
                    transactionConversas.commit();


                    //TollBar
                    toolbar.setTitle(R.string.mensagens);
                    appBarLayout.setExpanded(false, true);
                    appBarLayout.setActivated(false);
                    appBarLayout.setEnabled(false);

                    new Thread() {
                        public void run() {
                            try {
                                sleep(300);
                                blockTollbar = true;
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }

                        ;
                    }.start();

                    //TollBar

                    FragVisivel = 2;
                }
            }
        });


        //Inicia na fragment principal
        //    HomeFragment homeFragment = new HomeFragment();
        //  FragmentTransaction transactionHome = getSupportFragmentManager().beginTransaction();
        //transactionHome.replace(R.id.main_container, homeFragment);
        //transactionHome.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();

        verificaLogin();

        //Atualizar Header da Navigation
        //   if (userProfile != null)
        //   Picasso.with(MainActivity.this).load("https://graph.facebook.com/" + userProfile.getId() + "/picture?type=large").into(imageProfile);

        //  if(!coverUrl.equals("") && !prefs.getToken().equals(null))
        //   Picasso.with(this).load(coverUrl).into(coverProfile);

        // if (userProfile != null)
        //    txtNome.setText(userProfile.getName());


    }


    //Verifica se login do Facebook esta ativo
    private void verificaLogin() {

        AccessToken act = AccessToken.getCurrentAccessToken();
        if (act != null && !act.isExpired()) {

            //usuario logged in
            userProfile = Profile.getCurrentProfile();
            String userId = AccessToken.getCurrentAccessToken().getUserId();
            String accessToken = AccessToken.getCurrentAccessToken().getToken();

            // save accessToken to SharedPreference
            prefs.saveFacebookAccessToken(accessToken);
            prefs.saveFacebookUserId(userId);
            Log.i("succesLogin", "LoginReconhecido");

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_config) {

            Intent intent1 = new Intent(MainActivity.this, ConfiguracoesActivity.class);
            startActivity(intent1);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    //Obrigatorio sempre que o app utilizar umas das fragments do facebook
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


    //Utiliza o GraphRequest do Facebook pra pegar dados adicionais
    private void getFacebookData() {
        GraphRequest request = GraphRequest.newMeRequest(
                AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.v("LoginActivity", response.toString());
                        //     Log.v("LoginActivityRaw", response.getRawResponse());
                        Log.v("LoginActJsontoString", response.getJSONObject().toString());

                        FacebookJson faceJson = new FacebookJson();
                        usuario = faceJson.jsonToUsuario(response.getRawResponse());
                        coverUrl = faceJson.jsonToCoverPic(response.getRawResponse());

                        //Mostra a coverPic
                        // if(!coverUrl.equals("") && !prefs.getToken().equals(null))
                        //  Picasso.with(MainActivity.this).load(coverUrl).into(coverProfile);

                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "about,age_range,birthday,bio,cover,first_name,gender,last_name,education,likes,name");
        request.setParameters(parameters);
        request.executeAsync();

    }


    //nao usado
    private void logOut() {


        FirebaseAuth.getInstance().signOut();
        LoginManager.getInstance().logOut();


        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();

    }


    //Controla a animacao da foto de perfil e da toolbar
    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if (mMaxScrollSize == 0)
            mMaxScrollSize = appBarLayout.getTotalScrollRange();

        int percentage = (Math.abs(verticalOffset)) * 100 / mMaxScrollSize;

        if (percentage >= PERCENTAGE_TO_ANIMATE_AVATAR && mIsAvatarShown) {
            mIsAvatarShown = false;
            mProfileImage.animate().scaleY(0).scaleX(0).setDuration(200).start();
          //  fabEditPerfil.hide();
        }


        if (percentage <= PERCENTAGE_TO_ANIMATE_AVATAR && !mIsAvatarShown) {

            if (FragVisivel == 0) {
                mIsAvatarShown = true;

                mProfileImage.animate()
                        .scaleY(1).scaleX(1)
                        .start();

            //    fabEditPerfil.show();

            }
        }

        if (percentage <= 99 && !mIsAvatarShown && FragVisivel != 0 && blockTollbar) {

            appBarLayout.setExpanded(false, false);
          //  fabEditPerfil.hide();

        }


    }


    public Profile getUserProfile() {
        return userProfile;
    }
}

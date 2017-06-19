package com.nextu.loginfacebook_nextu;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;


/**
 * Created by LoKo555 on 05/02/2017.
 */

public class Framento_usuarios extends AppCompatActivity {
    private CallbackManager callbackManager;

    private static String loginFace;
    Profile profile;
    AccessTokenTracker accessTokenTracker;
    private AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_perfiles_usuarios);
        adView = (AdView) findViewById(R.id.ad_view);

        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
        adView.loadAd(adRequest);
        //initToolBar();
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        callbackManager = CallbackManager.Factory.create();
        /*LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>()
        {
            @Override
            public void onSuccess(LoginResult loginResult) {
                loginFace=loginResult.getAccessToken().getUserId();
                Log.i("Validacion Logeo","LOGIN SUCEECFULLER");

            }

            @Override
            public void onCancel() {
                Log.i("Fragmento usuario","Cancelamiento en login facebook");
            }

            @Override
            public void onError(FacebookException error) {
                Log.i("Fragmento usuario","error al registrar login face");
            }
        });*/
        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(
                    AccessToken oldAccessToken,
                    AccessToken currentAccessToken) {
                // Set the access token using
                // currentAccessToken when it's loaded or set.
            }
        };
        // If the access token is available already assign it.
        AccessToken accessToken = AccessToken.getCurrentAccessToken();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();

        if(accessToken!=null)
        {
                        Perfil_Usuario fragment1 = new Perfil_Usuario();
                        fragmentTransaction.replace(R.id.fragment, fragment1)
                                .commit();
                        Log.i("Logeo Correct","entra al fragmento 1 ");

        }
        else
        {
                        Login fragment2 = new Login();
                        Log.i("Logeo Correct","Devuelve al login");
                        fragmentTransaction.replace(R.id.fragment, fragment2)
                                .commit();
        }




    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
       // LoginManager.getInstance().logOut();
        accessTokenTracker.stopTracking();
    }
}

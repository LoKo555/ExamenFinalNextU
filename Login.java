package com.nextu.loginfacebook_nextu;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

/**
 * Created by LoKo555 on 04/02/2017.
 */

public class Login extends Fragment {

    private LoginButton loginButton;
    private CallbackManager callbackManager;

    private ProgressBar progressBar;

    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);*/
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.login_activity, container, false);

        loginButton = (LoginButton)rootView.findViewById(R.id.login_button);
        progressBar=(ProgressBar)rootView.findViewById(R.id.progreso);
        loginButton.setReadPermissions(Arrays.asList("public_profile", "email"));
        callbackManager = CallbackManager.Factory.create();
        loginButton.setFragment(this);


        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code

                //obtenerDatosFacebook(loginResult);
                Perfil_Usuario fragment1 = new Perfil_Usuario();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, fragment1).commit();
                Log.i("Logeo Correct","entra al fragmento 1 ");

               // handleFacebookAccesToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                // App code
                Log.i("Logeo cancelado","Cancelando..");
                //Toast.makeText(Login.this, "Logeo cancelado", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                //Toast.makeText(Login.this, "Ups error", Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }
    public void obtenerDatosFacebook(LoginResult loginResult)
    {
        GraphRequest request = GraphRequest.newMeRequest(
                loginResult.getAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {

                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.v("Main", response.toString());
                        setProfileToView(object);
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,gender,picture.type(large)");
        request.setParameters(parameters);
        request.executeAsync();
    }
    private void setProfileToView(JSONObject jsonObject) {
        try {

            String id_user=jsonObject.getString("id");
            //SQLite sqLite = new SQLite(getContext());
            //sqLite.abrir();
            String id="";
            String correo="";
            String nombre="";
            String genero="";
            String url_foto="";


                if (id_user!="")
                {
                    Log.i("Ingresa Usuario","Si");
                   id=jsonObject.getString("id");
                    correo=jsonObject.getString("email");
                    nombre=jsonObject.getString("name");
                   genero=jsonObject.getString("gender");
                    url_foto=jsonObject.getJSONObject("picture").getJSONObject("data").getString("url");
                    //sqLite.agregarUsuarios(id,nombre,correo,genero,url_foto);

                }
                else {
                   id=jsonObject.getString("id");
                   correo=jsonObject.getString("email");
                   nombre=jsonObject.getString("name");
                    genero=jsonObject.getString("gender");
                    url_foto=jsonObject.getJSONObject("picture").getJSONObject("data").getString("url");
                    //sqLite.updateRegistroUsuario(id,nombre,correo,genero,url_foto);
                    Log.i("Actualiza Usuario","No + "+url_foto);
                }

            User_Facebook user_facebook=new User_Facebook();
            user_facebook.setId(id);
            user_facebook.setCorreo(correo);
            user_facebook.setName(nombre);
            user_facebook.setGenero(genero);
            user_facebook.setUrl_foto(url_foto);

            Log.i("Registro ingresados",""+   user_facebook.getCorreo());

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
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
    public void onDestroy() {
        super.onDestroy();
        //LoginManager.getInstance().logOut();
    }
}

package com.nextu.loginfacebook_nextu;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.ProfilePictureView;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.Arrays;

/**
 * Created by LoKo555 on 05/02/2017.
 */

public class Perfil_Usuario extends Fragment {

    private static final String TAG = "";
    private ImageView foto_perfil;
    private CallbackManager callbackManager;
    public static String FILENAME="";
    private ProfilePictureView profilePicture;
    private static String URL_FINAL="";
    private static boolean LOGIN_FACEBOK=false;
    ImageView profile2;
    ProgressBar progresoBar;
    private Toolbar toolbar;
    public User_Facebook userFacebook;
    public TextView bienvenida;
    public TextView nombre1;
    public TextView correo1;
    AccessTokenTracker accessTokenTracker;
    ProfilePictureView profilePictureView;


    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {

        View rootView=inflater.inflate(R.layout.perfil_usuarios,container,false);
        Button salirButton = (Button)rootView.findViewById(R.id.salir);
        bienvenida=(TextView)rootView.findViewById(R.id.mensaje);
        nombre1=(TextView)rootView.findViewById(R.id.nombre_perfil_facebook);
        correo1=(TextView)rootView.findViewById(R.id.correo_perfil_facebook);
        //profilePicture=(ProfilePictureView)rootView.findViewById(R.id.profilePicture);
        profilePictureView = (ProfilePictureView) rootView.findViewById(R.id.friendProfilePicture);
        //profile2=(ImageView)rootView.findViewById(R.id.foto_profile);
        progresoBar=(ProgressBar)rootView.findViewById(R.id.barra_progreso_foto_prueba);
        progresoBar.setMax(100);
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email","public_profile"));
       // Log.i("Perfil usuario","LOGIN ENCONTRADO CORRECTO" +userFacebook.getName()+" "+userFacebook.getCorreo());
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                obtenerDatosFacebook(loginResult);
                Log.i("Perfil usuario","LOGIN ENCONTRADO CORRECTO");
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
                Log.i("Erro Logeo","Cancelando..");
            }
        });




            //Bitmap imagen=profilePicture.getDrawingCache();

           // Log.d(TAG, "Id de imagen:"+User_Facebook.getId());



        salirButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
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
                Log.i("id_imagen",id +" "+nombre+" "+correo+" "+genero+" "+ url_foto);
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


            bienvenida.setText("Bienvenido");
            nombre1.setText(nombre);
            correo1.setText(correo);
            //profilePicture.setDrawingCacheEnabled(true);
            //profilePicture.setPresetSize(ProfilePictureView.LARGE);
            //profilePicture.setProfileId(id);
            //validarImagen(id, url_foto);

            profilePictureView.setProfileId(id);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void validarImagen(String id,String url)
    {
        FILENAME = "fotoPerfilAppMovil"+".jpg";
        String path= String.valueOf(Environment.getExternalStorageDirectory());;
        File foto=new File(path+"/"+FILENAME);
        Log.i("Ruta imagen",""+foto);

            if(foto.exists())
            {
                /*String ruta_imagen=foto.toString();
                Bitmap foto_bipmap = BitmapFactory.decodeFile(ruta_imagen);
                foto_bipmap=getCircleBitmap(foto_bipmap);
                Log.i("validando foto","La imagen de la foto ya existe");
                profile2.setImageBitmap(foto_bipmap);*/
                foto.delete();
            }

                   Log.i("URL IMAGEN FINAL",URL_FINAL);
                    progresoBar.setVisibility(View.VISIBLE);
                    new DowloandaimagenPerfil(profile2,progresoBar,getContext()).execute(url);



    }

    public static Bitmap getCircleBitmap(Bitmap bm)
    {

        int sice = Math.min((bm.getWidth()), (bm.getHeight()));

        Bitmap bitmap = ThumbnailUtils.extractThumbnail(bm, sice, sice);

        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(output);

        final int color = 0xffff0000;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setFilterBitmap(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawOval(rectF, paint);

        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth((float) 4);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }
    public void logout()
    {

        LoginManager.getInstance().logOut();
        Login fragment1 = new Login();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment, fragment1).commit();
        //getActivity().finish();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}

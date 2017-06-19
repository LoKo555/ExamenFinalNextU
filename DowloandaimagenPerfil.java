package com.nextu.loginfacebook_nextu;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.ThumbnailUtils;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Sistemas1 on 05/01/2017.
 */
public class DowloandaimagenPerfil extends AsyncTask<String, Integer, String> {
    ImageView bmImage;
    ProgressBar bar;
    int myProgress=0;
    int fileLength;
    String FILENAME="";
    Context context;




    //private static final int progr[]  = {30, 15, 20, 25, 20};
    //private int index;
    public DowloandaimagenPerfil(ImageView bmImage, ProgressBar pBar, Context mcontext) {
        this.bmImage = bmImage;
        this.bar=pBar;
        this.context=mcontext;


    }

    @SuppressLint("LongLogTag")
    protected String doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap mIcon11 = null;
        int count=0;


        try {
            URL url = new URL(urldisplay);
            URLConnection conection = url.openConnection();
            conection.setDoOutput(true);
            conection.setReadTimeout(10000);
            conection.setRequestProperty ("Accept-Encoding", "identity");
            Long timestamp = System.currentTimeMillis() / 1000;
            FILENAME = "fotoPerfilAppMovil"+".jpg";

            String path= String.valueOf(Environment.getExternalStorageDirectory());;
            //Toast.makeText(context,path,Toast.LENGTH_LONG).show();
            //File fileThatExists = new File(path);

            conection.connect();

            //FileOutputStream output = new FileOutputStream(FILENAME,false);


            InputStream input_File = new BufferedInputStream(url.openStream(), 8192);
            fileLength = conection.getContentLength();

            OutputStream output = new FileOutputStream(path+"/"+FILENAME);
            //mIcon11 = BitmapFactory.decodeStream(input_File);
            byte data[] = new byte[1024];
            long total = 0;

            while ((count = input_File.read(data)) != -1)
            {
                total += count;
                myProgress=(int)total;
                publishProgress((int)((myProgress*100)/fileLength));
                //Log.e("CurrentAmount Downloaded: ", myProgress + " size: "+fileLength+" total:"+total);
                output.write(data, 0, count);
            }
            //Log.e("CurrentAmount Downloaded: ",myProgress+" - "+path+" size: "+fileLength );
            //InputStream in = new java.net.URL(urldisplay).openStream();
            //bar.setProgress(0);
                output.flush();
                output.close();
                //input_File.close();
                input_File.close();
                return path+"/"+FILENAME;


          } catch (Exception e) {

            Log.e("Error 1", e.getMessage());
            e.printStackTrace();
        }
        return null;
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
@Override
    protected void onProgressUpdate(Integer... values) {

         super.onProgressUpdate(values);
        bar.setMax(fileLength);
        bar.setProgress(myProgress);



}

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    protected void onPostExecute(String result) {
        bar.setVisibility(View.GONE);
        //bar.setProgress(100);
        //Notificaciones_firebase.URL_IMAGEN_NOTIFICACION=result;
        Bitmap bMap2 = BitmapFactory.decodeFile(result);
        bMap2=getCircleBitmap(bMap2);
        bmImage.setImageBitmap(bMap2);


    }
}
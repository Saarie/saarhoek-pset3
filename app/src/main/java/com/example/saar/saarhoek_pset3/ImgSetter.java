package com.example.saar.saarhoek_pset3;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Saar on 22-11-2016.
 */

public class ImgSetter extends AsyncTask<String, Void, Bitmap>{
     protected Bitmap doInBackground(String... params) {
         // the passed url
         String strimgurl = params[0];
         URL imgurl = null;
         Bitmap posterbmp = null;

         try {
             // making new url using the passed string url
             imgurl = new URL(strimgurl);
             InputStream img = imgurl.openStream();
             posterbmp = BitmapFactory.decodeStream(img);
         } catch (Exception e) {
             e.printStackTrace();
         }

         // return
         return posterbmp;
     }
}

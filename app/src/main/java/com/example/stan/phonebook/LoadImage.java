package com.example.stan.phonebook;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;

/**
 * Created by Stan on 1/30/2016.
 *
 * So what this little Asynchronous task does is load in an image from an URL and return it as a
 * Bitmap, to be used in ImageViews
 *
 * Of course in the Slow Loading Image issue documented, we know that images are taking their sweet time
 * being loaded. It begs to ask, is this really the best way to be grabbing images?
 *
 * Some alternatives I've looked into but haven't fully implemented yet or learned are to use the AQuery
 * or Volley libraries. There is also the notion of cacheing images which I did attempt, but couldn't get
 * working. I'd love to learn about how to do it efficiently, and well, have it work too.
 *
 */
public class LoadImage extends AsyncTask<String, Void, Bitmap> {
    ImageView bmImage;

    public LoadImage(ImageView bmImage) {
        this.bmImage = bmImage;
    }

    protected Bitmap doInBackground(String... urls) {
        String url = urls[0]; // Grab the url
        Bitmap mIcon = null;
        try {
            InputStream in = new java.net.URL(url).openStream();
            mIcon = BitmapFactory.decodeStream(in); // decode the url and get a Bitmap
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
        }
        return mIcon; // Return the bitmap
    }

    protected void onPostExecute(Bitmap result) {
        bmImage.setImageBitmap(result);
    }

}


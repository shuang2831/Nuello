package com.example.stan.phonebook;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.ImageLoader;

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


public class LoadImage {

    private static LoadImage loadImage;
    private static Context context;
    private RequestQueue requestQueue;
    private ImageLoader imageLoader;


    private LoadImage(Context context) {
        this.context = context;
        this.requestQueue = getRequestQueue();

        imageLoader = new ImageLoader(requestQueue,
                new ImageLoader.ImageCache() {
                    private final LruCache<String, Bitmap>
                            cache = new LruCache<String, Bitmap>(20);

                    @Override
                    public Bitmap getBitmap(String url) {
                        return cache.get(url);
                    }

                    @Override
                    public void putBitmap(String url, Bitmap bitmap) {
                        cache.put(url, bitmap);
                    }
                });
    }

    public static synchronized LoadImage getInstance(Context context) {
        if (loadImage == null) {
            loadImage = new LoadImage(context);
        }
        return loadImage;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            Cache cache = new DiskBasedCache(context.getCacheDir(), 10 * 1024 * 1024);
            Network network = new BasicNetwork(new HurlStack());
            requestQueue = new RequestQueue(cache, network);
            requestQueue.start();
        }
        return requestQueue;
    }

    public ImageLoader getImageLoader() {
        return imageLoader;
    }

}


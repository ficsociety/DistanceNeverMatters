package apm.muei.distancenevermatters.volley;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.Image;
import android.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

public class VolleySingleton {

    private static VolleySingleton instance = null;

    private RequestQueue requestQueue;
    private ImageLoader imageLoader;

    private VolleySingleton(Context context) {

        requestQueue = Volley.newRequestQueue(context);
        imageLoader = new ImageLoader(this.requestQueue, new ImageLoader.ImageCache() {
            private final LruCache<String, Bitmap> mCache = new LruCache<String, Bitmap>(10);
            public void putBitmap(String url, Bitmap bitmap) {
                mCache.put(url, bitmap);
            }
            public Bitmap getBitmap(String url) {
                return mCache.get(url);
            }
        });
    }

    public static VolleySingleton getInstance(Context context) {
        if (instance == null) {
            instance = new VolleySingleton(context);
        }
        return instance;
    }

    public RequestQueue getRequestQueue() {
        return this.requestQueue;
    }
    public ImageLoader getImageLoader(){return this.imageLoader; }

}

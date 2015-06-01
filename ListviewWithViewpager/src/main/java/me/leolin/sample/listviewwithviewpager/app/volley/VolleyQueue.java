package me.leolin.sample.listviewwithviewpager.app.volley;

import android.content.Context;
import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;

/**
 * @author Leolin
 */
public class VolleyQueue {

    private static VolleyQueue instance;
    private Context context;
    private RequestQueue requestQueue;

    public static VolleyQueue getInstance(Context context) {
        if (instance == null) {
            instance = new VolleyQueue(context);
        }
        return instance;
    }

    public VolleyQueue(Context context) {
        this.context = context;
        Cache cache = new DiskBasedCache(context.getCacheDir(), 10 * 1024 * 1024);
        Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network);
        // Don't forget to start the volley request queue
        requestQueue.start();
    }

    public RequestQueue getRequestQueue() {
        return requestQueue;
    }
}

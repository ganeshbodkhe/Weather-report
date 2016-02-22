package com.example.ganesh.weatherreport;

import android.app.Application;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by Ganesh on 2/6/2016.
 */
public class MyApplication extends Application {

    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    private static MyApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    //getIntance() return the Application object...
    public static synchronized MyApplication getInstance() {
        return mInstance;
    }

    //getRequestQueue returns the Request queue object
    public RequestQueue getReqQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }


    //addToReqQueue method we are adding calling add() method upon RequestObject and passing request as paramter to it .

    public <T> void addToReqQueue(Request<T> req, String tag) {

        getReqQueue().add(req);

    }

    public <T> void addToReqQueue(Request<T> req) {

        getReqQueue().add(req);
    }

    //    Inside getImageLoader method were we are calling to a constructor of ImageLoader
    // Class by passing requestQueue object and BitmapLruCache object as parameter.



    public ImageLoader getImageLoader() {
        getReqQueue();
        if (mImageLoader == null) {
            mImageLoader = new ImageLoader(this.mRequestQueue, new BitmapLruCache());
        }
        return this.mImageLoader;
    }

    public void cancelPendingReq(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }
}
package com.tezz.tezzapp.utils;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by User on 12/27/2017.
 */
public class RequestSingleton {
    private static RequestSingleton mInstance;
    private RequestQueue requestQueue;
    private Context context;

    private RequestSingleton(Context context){
        this.context = context;
        requestQueue = getRequestQueue();
    }


    public RequestQueue getRequestQueue(){
        if (requestQueue==null){
            requestQueue = Volley.newRequestQueue(context);
        }

        return requestQueue;
    }

    public static synchronized RequestSingleton getmInstance(Context context){
        if(mInstance==null){
            mInstance = new RequestSingleton(context);
        }

        return mInstance;
    }

    public<T> void addToRequestQue(Request<T> request){

        requestQueue.add(request);

    }
}

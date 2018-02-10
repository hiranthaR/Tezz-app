package com.tezz.tezzapp.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.tezz.tezzapp.entities.User;
import com.tezz.tezzapp.uis.CustomerOdersActivity;
import com.tezz.tezzapp.uis.LoginActivity;
import com.tezz.tezzapp.uis.TailorsListActivity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by User on 12/31/2017.
 */
public class LoginManager {

    public static void setLogin(Context context, final User user) {

        SharedPreferences.Editor editor = context.getSharedPreferences("user", Context.MODE_PRIVATE).edit();
        editor.putInt("id", user.getId());
        editor.putString("name", user.getName());
        editor.putString("address", user.getAddress());
        editor.putString("username", user.getUsername());
        editor.putString("email", user.getEmail());
        editor.putString("telephne", user.getTelephone());
        editor.putInt("who", user.getWho());

        editor.apply();
        editor.commit();

        SharedPreferences preferences = context.getSharedPreferences("fcm",context.MODE_PRIVATE);
        final String token = preferences.getString("token","");

        System.out.println(token);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.ADD_TOKEN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String, String>();
                map.put("fcm_token",token);
                map.put("id",String.valueOf(user.getId()));
                return map;
            }
        };
        RequestSingleton.getmInstance(context).addToRequestQue(stringRequest);
    }

    public static int loginState(Context context) {

        SharedPreferences preferences = context.getSharedPreferences("user", Context.MODE_PRIVATE);

        if (preferences.contains("who")) {
            if (preferences.getInt("who", 10) == 0) {
                return 1;
            } else if (preferences.getInt("who", 10) == 1) {
                return 2;
            } else return 0;

        } else return 0;
    }

    public static void setLogout(final Context context){


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.DELETE_TOKEN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println(response);
                SharedPreferences.Editor editor = context.getSharedPreferences("user", Context.MODE_PRIVATE).edit();
                editor.clear();
                editor.apply();
                editor.commit();

                context.startActivity(new Intent(context, LoginActivity.class));
                if(ME.user.getWho()==0)
                    ((TailorsListActivity) context).finish();
                else if(ME.user.getWho()==1)
                    ((CustomerOdersActivity) context).finish();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String, String>();
                map.put("id",String.valueOf(ME.user.getId()));
                return map;
            }
        };
        RequestSingleton.getmInstance(context).addToRequestQue(stringRequest);
    }

    public static void getLogin(Context context){
        SharedPreferences preferences = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        int id = preferences.getInt("id",0);
        String username = preferences.getString("username","");
        String name = preferences.getString("name","");
        String address = preferences.getString("address","");
        String telephone = preferences.getString("telephone","");
        String email = preferences.getString("email","");
        int who = preferences.getInt("who",0);

        ME.user = new User(id, username, name, null, address, email, telephone, who);

    }

}

package com.tezz.tezzapp.uis;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.tezz.tezzapp.R;
import com.tezz.tezzapp.entities.User;
import com.tezz.tezzapp.utils.LoginManager;
import com.tezz.tezzapp.utils.ME;
import com.tezz.tezzapp.utils.RequestSingleton;
import com.tezz.tezzapp.utils.URLs;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    EditText txtUsername, txtPassword;
    Button btnLogn, btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tailor_login);
        final SharedPreferences preferences = getSharedPreferences("ip", MODE_PRIVATE);

        if (!preferences.contains("ip")) {
            this.finish();
            startActivity(new Intent(this, IPSetActivity.class));
        } else {
            URLs.setIP(preferences.
                    getString("ip", ""));
        }
        getSupportActionBar().setTitle("Login");

        switch (LoginManager.loginState(this)){
            case 1:
                LoginManager.getLogin(this);
                Intent intent = new Intent(LoginActivity.this, TailorsListActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                this.finish();
                startActivity(intent);
                break;
            case 2:
                LoginManager.getLogin(this);
                Intent intent2 = new Intent(LoginActivity.this, CustomerOdersActivity.class);
                intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                this.finish();
                startActivity(intent2);
                break;
            default:
                break;
        }

        initComponents();
        setListeners();
    }

    private void setListeners() {
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
            }
        });

        btnLogn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String username = txtUsername.getText().toString().trim();
                final String password = txtPassword.getText().toString().trim();

                final ProgressDialog progressBar = new ProgressDialog(LoginActivity.this);
                progressBar.setCancelable(true);
                progressBar.setMessage("Waiting For Server!");
                progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressBar.show();

                final StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.TAILOR_LOGIN, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //on request success
                        System.out.println(response);

                        if (response.trim().equals("f")) {
                            Toast.makeText(LoginActivity.this, "Username or Password Incorrect!", Toast.LENGTH_LONG).show();
                            txtPassword.setText("");
                        } else {
                            Intent intent;
                            try {
                                JSONObject object = new JSONObject(response);
                                if (object.getInt("who") == 1)
                                    intent = new Intent(LoginActivity.this, CustomerOdersActivity.class);
                                else
                                    intent = new Intent(LoginActivity.this, TailorsListActivity.class);

                                int id = object.getInt("id");
                                String username = object.getString("username");
                                String name = object.getString("name");
                                String address = object.getString("address");
                                String telephone = object.getString("telephone");
                                String email = object.getString("email");
                                int who = object.getInt("who");

                                ME.user = new User(id, username, name, null, address, email, telephone, who);
                                LoginManager.setLogin(LoginActivity.this,ME.user);

                                startActivity(intent);
                                LoginActivity.this.finish();



                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        progressBar.dismiss();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //on error
                        Toast.makeText(LoginActivity.this, "Error: " + error, Toast.LENGTH_LONG).show();
                        progressBar.dismiss();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> map = new HashMap<>();
                        map.put("username", username);
                        map.put("password", password);
                        return map;
                    }
                };

                stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                        30000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                RequestSingleton.getmInstance(LoginActivity.this).addToRequestQue(stringRequest);
            }
        });
    }

    private void initComponents() {
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        txtUsername = (EditText) findViewById(R.id.txtUsername);
        btnLogn = (Button) findViewById(R.id.btnLogin);
        btnRegister = (Button) findViewById(R.id.btnRegister);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.settings) {
            startActivity(new Intent(LoginActivity.this, IPSetActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}

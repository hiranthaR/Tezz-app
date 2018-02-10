package com.tezz.tezzapp.uis;

import android.app.ProgressDialog;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.tezz.tezzapp.R;
import com.tezz.tezzapp.entities.User;
import com.tezz.tezzapp.utils.RequestSingleton;
import com.tezz.tezzapp.utils.URLs;

import java.util.HashMap;
import java.util.Map;

public class RegistrationActivity extends AppCompatActivity {
    EditText txtName, txtPassword, txtCPassword, txtEmail, txtTelephone, txtAddress,txtUsername;
    Button btnRegister;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tailor_registration);

        getSupportActionBar().setTitle("Tailors Registration");

        //init components
        initComponents();
        setListeners();
    }

    private void setListeners() {

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cheak()) {

                    String username = txtUsername.getText().toString().trim();
                    String name = txtName.getText().toString().trim();
                    String password = txtPassword.getText().toString().trim();
                    String address = txtAddress.getText().toString().trim();
                    String telephone= txtTelephone.getText().toString().trim();
                    String email = txtEmail.getText().toString().trim();
                    int who = spinner.getSelectedItemPosition();

                    final User user = new User(0,username,name,password,address,email,telephone,who);

                    final ProgressDialog progressBar = new ProgressDialog(RegistrationActivity.this);
                    progressBar.setCancelable(false);
                    progressBar.setMessage("Connecting to Server!");
                    progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressBar.show();

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.TAILOR_REGISTER, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            //on request success
                            System.out.println(response);
                            if (response.equals("true")) {
                                Toast.makeText(RegistrationActivity.this, "Registration Successfull!", Toast.LENGTH_LONG).show();
                                RegistrationActivity.this.finish();
                            } else if (response.equals("false")) {
                                showSnack("username already taken!");
                                txtUsername.setText("");
                            } else {
                                showSnack("unknown error!");
                            }
                            progressBar.dismiss();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //on error
                            Toast.makeText(RegistrationActivity.this, "Error: " + error, Toast.LENGTH_LONG).show();
                            progressBar.dismiss();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> map = new HashMap<>();
                            map.put("username", user.getUsername());
                            map.put("name", user.getName());
                            map.put("password", user.getPassword());
                            map.put("address", user.getAddress());
                            map.put("email", user.getEmail());
                            map.put("telephone", user.getTelephone());
                            map.put("who",String.valueOf(user.getWho()));
                            return map;
                        }
                    };

                    stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                            3000,
                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                    RequestSingleton.getmInstance(RegistrationActivity.this).addToRequestQue(stringRequest);
                }
            }
        });
    }

    private boolean cheak() {

        if (txtUsername.getText().toString().trim().length()==0) {
            showSnack("username empty!");
            return false;
        }else if (txtName.getText().toString().trim().length() == 0) {
            showSnack("name empty!");
            return false;
        } else if (txtPassword.getText().toString().trim().length() == 0) {
            showSnack("password empty!");
            return false;
        } else if (!txtPassword.getText().toString().matches(txtCPassword.getText().toString())) {
            //execute when passwords doesnt match
            showSnack("passwords don't match!");
            return false;
        } else if (txtAddress.getText().toString().trim().length() == 00) {
            showSnack("Address empty!");
            return false;
        } else if (txtEmail.getText().toString().trim().length() == 00) {
            showSnack("Email empty!");
            return false;
        } else if (!txtEmail.getText().toString().trim().matches("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")) {
            //execute if email invalid
            showSnack("email invalid!");
            return false;
        } else if (txtTelephone.getText().toString().trim().length() == 00) {
            showSnack("Telephone number empty!");
            return false;
        } else if (txtTelephone.getText().toString().trim().length() !=10) {
            showSnack("Telephone number too short!");
            return false;
        } else return true;
    }

    private void showSnack(String message) {
        Snackbar.make(getCurrentFocus(), message, Snackbar.LENGTH_LONG).show();
    }

    private void initComponents() {
        txtName = (EditText) findViewById(R.id.txtName);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        txtCPassword = (EditText) findViewById(R.id.txtConfirmPassword);
        txtAddress = (EditText) findViewById(R.id.txtAddress);
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtTelephone = (EditText) findViewById(R.id.txtTelephone);
        txtUsername = (EditText) findViewById(R.id.txtUsername);

        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setAdapter(new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,new String[]{"I am a customer","I am a tailor"}));

        btnRegister = (Button) findViewById(R.id.btnRegister);

    }
}

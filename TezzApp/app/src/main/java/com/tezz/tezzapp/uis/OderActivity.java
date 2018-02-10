package com.tezz.tezzapp.uis;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.tezz.tezzapp.R;
import com.tezz.tezzapp.entities.Oder;
import com.tezz.tezzapp.entities.User;
import com.tezz.tezzapp.utils.ImageHandler;
import com.tezz.tezzapp.utils.ME;
import com.tezz.tezzapp.utils.RequestSingleton;
import com.tezz.tezzapp.utils.URLs;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class OderActivity extends AppCompatActivity {

    EditText txtHeight,txtChest,txtDetails;
    Button btnPlaceOder;
    ImageView imgSideView, imgFrontView;
    Bitmap bmpSide=null, bmpFont=null;

    User tailor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oder);

        initComponents();
        setListeners();

    }

    private void setListeners() {

        imgSideView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photopickerIntent = new Intent(Intent.ACTION_PICK);
                photopickerIntent.setType("image/*");
                startActivityForResult(photopickerIntent, 20);
            }
        });

        imgFrontView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photopickerIntent = new Intent(Intent.ACTION_PICK);
                photopickerIntent.setType("image/*");
                startActivityForResult(photopickerIntent, 30);
            }
        });

        btnPlaceOder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check()){

                    final ProgressDialog progressBar = new ProgressDialog(OderActivity.this);
                    progressBar.setCancelable(false);
                    progressBar.setMessage("Connecting to Server!");
                    progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressBar.show();

                    final Oder oder = new Oder(0,tailor.getId(), ME.user.getId(),txtHeight.getText().toString().trim(),txtChest.getText().toString().trim(),txtDetails.getText().toString().trim(), ImageHandler.BitmapToString(bmpSide),ImageHandler.BitmapToString(bmpFont));

                    StringRequest stringRequest = new StringRequest(Request.Method.POST,URLs.ADD_ODER, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            System.out.println(response);
                            if(response.toString().trim().equals("t")){
                                Toast.makeText(OderActivity.this,"Oder placed!",Toast.LENGTH_LONG).show();
                                OderActivity.this.finish();
                            } else showSnack("Something went Wrong!");

                            progressBar.dismiss();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(OderActivity.this, "Error: " + error, Toast.LENGTH_LONG).show();
                            progressBar.dismiss();
                        }
                    }){

                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> map = new HashMap<>();
                            map.put("tid",String.valueOf(oder.getTid()));
                            map.put("cid",String.valueOf(oder.getCid()));
                            map.put("height",oder.getHeight());
                            map.put("details",oder.getDetails());
                            map.put("chest",oder.getChest());
                            map.put("side",oder.getSide());
                            map.put("front",oder.getFront());
                            return map;
                        }
                    };

                    stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                            600000,
                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                    RequestSingleton.getmInstance(OderActivity.this).addToRequestQue(stringRequest);
                }
            }
        });

    }

    private void initComponents() {

        txtHeight = (EditText) findViewById(R.id.txtHeight);
        txtChest= (EditText) findViewById(R.id.txtChest);
        txtDetails= (EditText) findViewById(R.id.txtDetails);
        imgFrontView = (ImageView) findViewById(R.id.imgFrontView);
        imgSideView = (ImageView) findViewById(R.id.imgSideView);
        btnPlaceOder = (Button) findViewById(R.id.btnPlaceOder);

        Intent intent = getIntent();

        int id = intent.getIntExtra("id", 0);
        String username = intent.getStringExtra("username");
        String name = intent.getStringExtra("name");
        String address = intent.getStringExtra("address");
        String email = intent.getStringExtra("email");
        String telephone = intent.getStringExtra("telephone");
        int who = intent.getIntExtra("who", 1);

        tailor = new User(id, username, name, null, address, email, telephone, who);
    }

    private void showSnack(String message) {
        Snackbar.make(getCurrentFocus(), message, Snackbar.LENGTH_LONG).show();
    }

    public boolean check() {
        if (txtHeight.getText().toString().trim().length() == 00) {
            showSnack("Must add height!");
            return false;
        } else if (bmpSide==null) {
            showSnack("Add Side view image!");
            return false;
        } else if (bmpFont==null) {
            showSnack("Add front view image!");
            return false;
        }else return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);

                if (requestCode == 20) {
                    bmpSide = BitmapFactory.decodeStream(imageStream);
                    imgSideView.setImageBitmap(bmpSide);
                } else if (requestCode == 30) {
                    bmpFont = BitmapFactory.decodeStream(imageStream);
                    imgFrontView.setImageBitmap(bmpFont);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(OderActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }
        }
    }
}

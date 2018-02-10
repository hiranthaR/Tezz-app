package com.tezz.tezzapp.uis;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tezz.tezzapp.R;
import com.tezz.tezzapp.entities.User;

import org.json.JSONException;
import org.json.JSONObject;

public class ViewTailorActivity extends AppCompatActivity {

    TextView lblName, lblAddress, lblEmail, lblTelephone;
    Button btnPlaceOder;
    User tailor = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_tailor);

        intComponents();
        setListeners();
    }

    private void setListeners() {

        btnPlaceOder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ViewTailorActivity.this,OderActivity.class);

                intent.putExtra("id",tailor.getId());
                intent.putExtra("username", tailor.getUsername());
                intent.putExtra("name", tailor.getName());
                intent.putExtra("address", tailor.getAddress());
                intent.putExtra("email", tailor.getEmail());
                intent.putExtra("telephone", tailor.getTelephone());
                intent.putExtra("who",tailor.getWho());

                startActivity(intent);
            }
        });

    }



    private void intComponents() {
        lblName = (TextView) findViewById(R.id.lblName);
        lblAddress = (TextView) findViewById(R.id.lblAddress);
        lblTelephone = (TextView) findViewById(R.id.lblTelephone);
        lblEmail = (TextView) findViewById(R.id.lblEmail);

        btnPlaceOder = (Button) findViewById(R.id.btnPlaceOder);

        Intent intent = getIntent();

        int id = intent.getIntExtra("id", 0);
        String username = intent.getStringExtra("username");
        String name = intent.getStringExtra("name");
        String address = intent.getStringExtra("address");
        String email = intent.getStringExtra("email");
        String telephone = intent.getStringExtra("telephone");
        int who = intent.getIntExtra("who", 1);

        tailor = new User(id,username,name,null,address,email,telephone,who);

        lblName.setText(tailor.getName());
        lblAddress.setText(tailor.getAddress());
        lblTelephone.setText(tailor.getTelephone());
        lblEmail.setText(tailor.getEmail());
    }


}

package com.tezz.tezzapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.tezz.tezzapp.uis.TailorsListActivity;
import com.tezz.tezzapp.uis.IPSetActivity;
import com.tezz.tezzapp.uis.LoginActivity;
import com.tezz.tezzapp.utils.URLs;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final SharedPreferences preferences = getSharedPreferences("ip",MODE_PRIVATE);

        if(!preferences.contains("ip")){
            this.finish();
            startActivity(new Intent(this,IPSetActivity.class));
        } else {
            URLs.setIP(preferences.
                    getString("ip",""));
        }

        findViewById(R.id.btnTailor).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });

        findViewById(R.id.btnCustomer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, TailorsListActivity.class));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.settings){
            startActivity(new Intent(MainActivity.this,IPSetActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}

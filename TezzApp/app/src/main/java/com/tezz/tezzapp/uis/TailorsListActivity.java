package com.tezz.tezzapp.uis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.tezz.tezzapp.R;
import com.tezz.tezzapp.entities.User;
import com.tezz.tezzapp.utils.LoginManager;
import com.tezz.tezzapp.utils.RequestSingleton;
import com.tezz.tezzapp.utils.URLs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TailorsListActivity extends AppCompatActivity {

    List<User> users = new ArrayList<>();
    RecyclerView rvTailors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tailors_list);

        getSupportActionBar().setTitle("Available Tailors");

        initComponents();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.logout) {
            LoginManager.setLogout(this);
        }
        return super.onOptionsItemSelected(item);
    }

    private void initComponents() {
        rvTailors = (RecyclerView) findViewById(R.id.rvTailors);
        setList();

        rvTailors.setLayoutManager(new GridLayoutManager(this,1));
        rvTailors.setHasFixedSize(true);


    }

    private void setList() {
        JsonArrayRequest arrayRequest = new JsonArrayRequest(URLs.ALL_TAILOR, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i = 0;i<response.length();i++){
                    try {
                        JSONObject object = response.getJSONObject(i);

                        int id = object.getInt("id");
                        String username = object.getString("username");
                        String name = object.getString("name");
                        String address = object.getString("address");
                        String telephone = object.getString("telephone");
                        String email = object.getString("email");
                        int who = object.getInt("who");

                        users.add(new User(id,username,name,null,address,email,telephone,who));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    rvTailors.setAdapter(new RVTailorAdapter());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(TailorsListActivity.this,"Something went wrong!",Toast.LENGTH_LONG).show();
            }
        });

        RequestSingleton.getmInstance(this).addToRequestQue(arrayRequest);
    }


    class RVTailorAdapter extends RecyclerView.Adapter<RVTailorAdapter.RVViewHolder>{


        @Override
        public RVViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_user, parent, false);
            return new RVViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RVViewHolder holder, final int position) {
            holder.lblName.setText(users.get(position).getName());
            holder.lblAddress.setText(users.get(position).getAddress());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    User user =users.get(position);

                    Intent intent = new Intent(TailorsListActivity.this,ViewTailorActivity.class);

                    intent.putExtra("id",user.getId());
                    intent.putExtra("username", user.getUsername());
                    intent.putExtra("name", user.getName());
                    intent.putExtra("address", user.getAddress());
                    intent.putExtra("email", user.getEmail());
                    intent.putExtra("telephone", user.getTelephone());
                    intent.putExtra("who",user.getWho());

                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return users.size();
        }

        class RVViewHolder extends RecyclerView.ViewHolder{

            TextView lblName,lblAddress;

            public RVViewHolder(View itemView) {
                super(itemView);
                lblName = (TextView) itemView.findViewById(R.id.lblName);
                lblAddress = (TextView) itemView.findViewById(R.id.lblAddress);

            }
        }
    }
}

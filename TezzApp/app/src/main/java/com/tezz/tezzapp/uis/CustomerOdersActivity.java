package com.tezz.tezzapp.uis;

import android.app.ProgressDialog;
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

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.tezz.tezzapp.R;
import com.tezz.tezzapp.entities.Oder;
import com.tezz.tezzapp.entities.User;
import com.tezz.tezzapp.utils.LoginManager;
import com.tezz.tezzapp.utils.ME;
import com.tezz.tezzapp.utils.RequestSingleton;
import com.tezz.tezzapp.utils.URLs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerOdersActivity extends AppCompatActivity {

    List<Oder> oders = new ArrayList<>();
    RecyclerView rvTailors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_oders);

        initComponents();
    }

    private void initComponents() {
        rvTailors = (RecyclerView) findViewById(R.id.rvTailors);

        final ProgressDialog progressBar = new ProgressDialog(CustomerOdersActivity.this);
        progressBar.setCancelable(true);
        progressBar.setMessage("Waiting For Server!");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.show();

        setList();

        rvTailors.setLayoutManager(new GridLayoutManager(this, 1));
        rvTailors.setHasFixedSize(true);
        progressBar.dismiss();

    }

    private void setList() {
        StringRequest arrayRequest = new StringRequest(Request.Method.POST, URLs.ALL_ODERS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                System.out.println(response);

                try {
                    JSONArray jsonArray = new JSONArray(response);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);

                        int id = object.getInt("id");
                        int tid = object.getInt("tid");
                        int cid = object.getInt("cid");
                        String height = object.getString("height");
                        String chest = object.getString("chest");
                        String details = object.getString("details");
                        String side = object.getString("side");
                        String front = object.getString("front");

                        oders.add(new Oder(id, tid, cid, height,chest,details, side, front));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                rvTailors.setAdapter(new RVTailorAdapter());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CustomerOdersActivity.this, "Something went wrong! " + error, Toast.LENGTH_LONG).show();
                System.out.println(error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("tid", String.valueOf(ME.user.getId()));
                return map;
            }
        };

        arrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestSingleton.getmInstance(this).addToRequestQue(arrayRequest);
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


    class RVTailorAdapter extends RecyclerView.Adapter<RVTailorAdapter.RVViewHolder> {


        @Override
        public RVViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_user, parent, false);
            return new RVViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final RVViewHolder holder, final int position) {

            StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.GET_CUSTOMER, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    //on request success
                    System.out.println(response);
                    if (response.trim().equals("f")) {
                        Toast.makeText(CustomerOdersActivity.this, "Something went wrong!", Toast.LENGTH_LONG).show();
                    } else {
                        try {
                            JSONObject object = new JSONObject(response);
                            int id = object.getInt("id");
                            String username = object.getString("username");
                            String name = object.getString("name");
                            String address = object.getString("address");
                            String telephone = object.getString("telephone");
                            String email = object.getString("email");
                            int who = object.getInt("who");

                            oders.get(position).setCustomer(new User(id, username, name, null, address, email, telephone, who));
                            System.out.println("here");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    holder.lblName.setText(oders.get(position).getCustomer().getName());
                    holder.lblAddress.setText(oders.get(position).getCustomer().getAddress());
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ViewOderActivity.oder = oders.get(position);
                            Intent intent = new Intent(CustomerOdersActivity.this, ViewOderActivity.class);
                            startActivity(intent);
                        }
                    });
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //on error
                    Toast.makeText(CustomerOdersActivity.this, "Error: " + error, Toast.LENGTH_LONG).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> map = new HashMap<>();
                    map.put("id", String.valueOf(oders.get(position).getCid()));
                    return map;
                }
            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    30000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            RequestSingleton.getmInstance(CustomerOdersActivity.this).addToRequestQue(stringRequest);
        }

        @Override
        public int getItemCount() {
            return oders.size();
        }

        class RVViewHolder extends RecyclerView.ViewHolder {

            TextView lblName, lblAddress;

            public RVViewHolder(View itemView) {
                super(itemView);
                lblName = (TextView) itemView.findViewById(R.id.lblName);
                lblAddress = (TextView) itemView.findViewById(R.id.lblAddress);
            }
        }
    }
}

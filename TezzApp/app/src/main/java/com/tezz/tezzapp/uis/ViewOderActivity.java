package com.tezz.tezzapp.uis;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.tezz.tezzapp.R;
import com.tezz.tezzapp.entities.Oder;
import com.tezz.tezzapp.utils.ImageHandler;
import com.tezz.tezzapp.utils.RequestSingleton;
import com.tezz.tezzapp.utils.URLs;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class ViewOderActivity extends AppCompatActivity {

    public static Oder oder = null;
    TextView lblName, lblAddress, lblEmail, lblTelephone,lblHeight,lblChest,lblDetails;
    TextView lblW,lblUA,lblSWL,lblAH,lblTl,lblFA,lblclv,lblSl;
    ImageView imgSide,imgFront;
    Button btnSubmit;
    DecimalFormat df = new DecimalFormat("#.##");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_oder);

        initComponents();
        setListeners();
    }

    private void setListeners() {
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.SEND_FCM, new Response.Listener<String>() {
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
                        map.put("id",String.valueOf(oder.getCustomer().getId()));
                        return map;
                    }
                };
                RequestSingleton.getmInstance(ViewOderActivity.this).addToRequestQue(stringRequest);
            }
        });
    }

    private void initComponents() {
        lblName = (TextView) findViewById(R.id.lblName);
        lblAddress = (TextView) findViewById(R.id.lblAddress);
        lblTelephone = (TextView) findViewById(R.id.lblTelephone);
        lblEmail = (TextView) findViewById(R.id.lblEmail);
        lblHeight = (TextView) findViewById(R.id.lblHeight);
        lblChest = (TextView) findViewById(R.id.lblChest);
        lblDetails = (TextView) findViewById(R.id.lblDetails);
        imgSide = (ImageView) findViewById(R.id.imgSideView);
        imgFront = (ImageView) findViewById(R.id.imgFrontView);

        lblW = (TextView) findViewById(R.id.lblW);
        lblUA = (TextView) findViewById(R.id.lblUA);
        lblSWL = (TextView) findViewById(R.id.lblSWL);
        lblAH = (TextView) findViewById(R.id.lblAH);
        lblTl = (TextView) findViewById(R.id.lblTL);
        lblFA = (TextView) findViewById(R.id.lblFA);
        lblclv = (TextView) findViewById(R.id.lblClv);
        lblSl = (TextView) findViewById(R.id.lblSL);

        double chest =0;

        try{
            chest = Double.parseDouble(oder.getChest());
        } catch (ClassCastException e){
        }

        lblName.setText(oder.getCustomer().getName());
        lblAddress.setText(oder.getCustomer().getAddress());
        lblTelephone.setText(oder.getCustomer().getTelephone());
        lblEmail.setText(oder.getCustomer().getEmail());
        lblHeight.setText(oder.getHeight());
        lblChest.setText(oder.getChest());
        lblDetails.setText(oder.getDetails());

        imgFront.setImageBitmap(ImageHandler.StringToBitmap(oder.getFront()));
        imgSide.setImageBitmap(ImageHandler.StringToBitmap(oder.getSide()));

        lblW.setText("  " + df.format(chest * 0.89));
        lblUA.setText("  " + df.format(chest * 0.36));
        lblSWL.setText("  " + df.format(chest * 0.64));
        lblAH.setText("  " + df.format(chest * 0.47));
        lblTl.setText("  " + df.format(chest * 1.06));
        lblFA.setText("  " + df.format(chest * 0.29));
        lblclv.setText("  " + df.format(chest * 0.34));
        lblSl.setText("  " + df.format(chest * 0.46));

        btnSubmit = (Button) findViewById(R.id.btnConfirm);
    }
}

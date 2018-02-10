package com.tezz.tezzapp.uis;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.tezz.tezzapp.R;

public class IPSetActivity extends AppCompatActivity {

    EditText txtIp;
    Button btnSetip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ipset);

        txtIp = (EditText) findViewById(R.id.txtIP);
        btnSetip = (Button) findViewById(R.id.btnSetIp);

        final SharedPreferences preferences = getSharedPreferences("ip",MODE_PRIVATE);

        if(preferences.contains("ip")){
            txtIp.setText(preferences.getString("ip",""));
        }

        InputFilter[] filters = new InputFilter[1];
        filters[0] = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end,
                                       android.text.Spanned dest, int dstart, int dend) {
                if (end > start) {
                    String destTxt = dest.toString();
                    String resultingTxt = destTxt.substring(0, dstart)
                            + source.subSequence(start, end)
                            + destTxt.substring(dend);
                    if (!resultingTxt
                            .matches("^\\d{1,3}(\\.(\\d{1,3}(\\.(\\d{1,3}(\\.(\\d{1,3})?)?)?)?)?)?")) {
                        return "";
                    } else {
                        String[] splits = resultingTxt.split("\\.");
                        for (int i = 0; i < splits.length; i++) {
                            if (Integer.valueOf(splits[i]) > 255) {
                                return "";
                            }
                        }
                    }
                }
                return null;
            }

        };
        txtIp.setFilters(filters);

        btnSetip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txtIp.getText().toString().matches("(^\\d{1,3}(\\.(\\d{1,3}(\\.(\\d{1,3}(\\.(\\d{1,3})?)?)?)?)?)?)")){
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("ip",txtIp.getText().toString());
                    editor.apply();
                    editor.commit();
                    IPSetActivity.this.finish();
                    Intent intent = new Intent(IPSetActivity.this,LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else {
                    //may be pass error.
                    Snackbar.make(getCurrentFocus(),"IP Pattern Not Matched!", Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }
}

package com.example.rahulkumar.applicationform;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import org.json.JSONException;
import org.json.JSONObject;
import java.lang.*;
import java.util.ArrayList;

public class BarCodeActivity extends AppCompatActivity implements View.OnClickListener {
    //add object for view
    public static String string,string1;boolean bool;
    public ArrayList<String> ar;
    private Button buttonScan;
    private TextView textViewName, textViewAddress;
    private IntentIntegrator qrScan;
    public  EditText textEditname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_code);

       // setContentView(R.layout.activity_bar_code);
        //Adda vie object
        buttonScan = (Button) findViewById(R.id.buttonScan);
       /*
        textViewName = (TextView) findViewById(R.id.textViewName);
        textViewAddress = (TextView) findViewById(R.id.textViewAddress);
        textEditname=(EditText)findViewById(R.id.textEditname);*/
        //intializing scan object
        qrScan = new IntentIntegrator(this);

        //attaching onclick listener
        buttonScan.setOnClickListener(this);

    }

    //Getting the scan results
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //if qrcode has nothing in it
            if (result.getContents() == null) {
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
            } else {
                //if qr contains data
                try {

                    JSONObject obj = new JSONObject(result.getContents());
                    //setting values to textviews
                    textViewName.setText(obj.getString("name"));

                    textViewAddress.setText(obj.getString("address"));

                } catch (JSONException e) {
                    e.printStackTrace();
                    setContentView(R.layout.activity_bar_code);
                    string=result.getContents();
                   /* bool=string1.contains("uid");
                    if(bool)
                    {
                       // string=string1.indexOf("uid");
                    }*/
                    Intent intent=new Intent(BarCodeActivity.this,RegistrationForm.class);
                    startActivity(intent);
                    finish();
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    @Override
    public void onClick(View view) {
        //initiating the qr code scan
        qrScan.initiateScan();
    }
}

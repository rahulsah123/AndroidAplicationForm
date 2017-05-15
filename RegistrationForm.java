package com.example.rahulkumar.applicationform;

/**
 * Created by rahulkumar on 12-04-2017.
 */
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import com.example.rahulkumar.applicationform.BarCodeActivity;

import static com.example.rahulkumar.applicationform.BarCodeActivity.*;

//implements View.OnClickListener
public class RegistrationForm extends AppCompatActivity implements View.OnClickListener {


    private EditText  editIdnumber,editTextEmail, editPhoneNumber,editTextName;
    private Button buttonRegister;
    private ProgressDialog progressDialog;

    private TextView textViewLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_form);

       /* if(SharedPrefManager.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(this, ProfileActivity.class));
            return;
        }*/
        editIdnumber=(EditText)findViewById(R.id.editUserId);
        editTextName=(EditText)findViewById((R.id.editTextName));
        editPhoneNumber = (EditText) findViewById(R.id.editPhoneNumber);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);

        buttonRegister = (Button) findViewById(R.id.buttonRegister);

        progressDialog = new ProgressDialog(this);
        editIdnumber.setText(string);
        buttonRegister.setOnClickListener(this);
       // textViewLogin.setOnClickListener(this);

       // textEditname.(EditText)findViewById(R.id.editTextUsername)
        //editTextUsername.setText(ar.get(0));
    }

    private void registerUser() {
        final String idnumber1=editIdnumber.getText().toString().trim();
        final String email = editTextEmail.getText().toString().trim();
        final String name = editTextName.getText().toString().trim();
        final String Snumber = editPhoneNumber.getText().toString().trim();

        progressDialog.setMessage("Registering user...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL_REGISTER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.hide();
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("idnumber",idnumber1);
                params.put("name", name);
                params.put("phnumber", Snumber);

                params.put("email", email);

                return params;
            }
          /*  @Override
            protected Map<String, Integer> getParams()throws AuthFailureError{
                Map<String, Integer> params1 = new HashMap<>();
                params1.put("idnumber",idnumber2);
                params1.put("phnumber", number);
                return params1;
            }*/
        };


        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);


    }

    @Override
    public void onClick(View view) {
        if (view == buttonRegister)
            registerUser();
       /* if(view == textViewLogin)
            startActivity(new Intent(this, LoginActivity.class));*/
    }

}


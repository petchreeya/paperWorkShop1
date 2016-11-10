package com.example.user.workshop;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity {


    private EditText etDisplay;
    private EditText etUser;
    private EditText etPass;
    private EditText etPassCon;
    private Button btnRegist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);



        bindWidget();
        setListener();
        validate();

    }

    private boolean validate() {
        String username = etUser.getText().toString();
        String password = etPass.getText().toString();
        String passwordConfirm = etPassCon.getText().toString();
        String displayName = etDisplay.getText().toString();

        if (username.isEmpty())
            return false;
        if (password.isEmpty())
            return false;
        if (passwordConfirm.isEmpty())
            return false;
        if (!password.equals(passwordConfirm))
            return false;
        if (displayName.isEmpty())
            return  false;

        return true;

    }

    private void bindWidget() {

        etDisplay = (EditText) findViewById(R.id.etDisplay);
        etUser = (EditText) findViewById(R.id.etUser);
        etPass = (EditText) findViewById(R.id.etPass);
        etPassCon = (EditText) findViewById(R.id.etPassCon);
    }

    private void setListener(){
        Button btnRegist = (Button) findViewById(R.id.btnRegist);
        btnRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    //ToDo
                    new Register1(etUser.getText().toString(),
                            etPass.getText().toString(),
                            etPassCon.getText().toString(),
                            etDisplay.getText().toString()).execute();
                }else{
                    Toast.makeText(RegisterActivity.this,"กรุณากรอกข้อมูลให้ครบ", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private class Register1 extends AsyncTask<Void, Void, String>{

        private String username;
        private String password;
        private String passwordCon;
        private String displayName;

        public Register1(String username, String password, String passwordCon, String displayName) {
            this.username = username;
            this.password = password;
            this.passwordCon = passwordCon;
            this.displayName = displayName;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {

            OkHttpClient client = new OkHttpClient();
            Request request;
            Response response;

            RequestBody requestBody = new FormBody.Builder()
                    .add("username", username)
                    .add("password", password)
                    .add("password_con" , passwordCon)
                    .add("display_name", displayName)
                    .build();
            request = new Request.Builder()
                    .url("http://kimhun55.com/pollservices/signup.php")
                    .post(requestBody)
                    .build();
            try {
                response = client.newCall(request).execute();

                if(response.isSuccessful()){
                    return response.body().string();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(RegisterActivity.this, s , Toast.LENGTH_SHORT).show();

            try {
                JSONObject rootObj = new JSONObject(s);
                if(rootObj.has("result")){
                    JSONObject resultObj = rootObj.getJSONObject("result");
                    if(resultObj.getInt("result") == 1) {
                        Toast.makeText(RegisterActivity.this, resultObj.getString("result_desc"), Toast.LENGTH_SHORT).show();
                        finish();
                    }else {
                        Toast.makeText(RegisterActivity.this, resultObj.getString("result_desc"), Toast.LENGTH_SHORT).show();
                    }
                }

            } catch (JSONException ex) {

            }

        }
    }
}

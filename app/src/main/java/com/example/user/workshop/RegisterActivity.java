package com.example.user.workshop;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

    private EditText edReUser;
    private EditText edRedis;
    private EditText edRePass;
    private EditText edReCon;
    private Button btReRegis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edRedis = (EditText) findViewById(R.id.edRedis);
        edReUser = (EditText) findViewById(R.id.edReUser);
        edRePass = (EditText) findViewById(R.id.edRePass);
        edReCon = (EditText) findViewById(R.id.edReCon);
        btReRegis = (Button) findViewById(R.id.btReRegis);

        setListener();
    }

    private void setListener() {
        btReRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RegisterActivity.this,LoginActivity. class);
                startActivity(i);

                if (validate()) {
                    new Register1(edReUser.getText().toString(),
                            edRePass.getText().toString(),
                            edReCon.getText().toString(),
                            edRedis.getText().toString()).execute();

                } else {
                    Toast.makeText(RegisterActivity.this,"ใส่ข้อมูลให้ครบ",Toast.LENGTH_SHORT);
                }


            }
        });
    }

    private boolean validate() {
        String username = edReUser.getText().toString();
        String password = edRePass.getText().toString();
        String passwordConfirm = edReCon.getText().toString();
        String displayName = edRedis.getText().toString();

        if (username.isEmpty()) return false;

        if (password.isEmpty()) return false;

        if (passwordConfirm.isEmpty()) return false;

        if (!password.equals(passwordConfirm)) return false;

        if (displayName.isEmpty()) return false;

        return true;
    }


    private class Register1 extends AsyncTask<Void, Void ,String> {

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
        protected String doInBackground(Void... params) {


            OkHttpClient client = new OkHttpClient();
            Request request;
            Response response;

                RequestBody requestBody = new FormBody.Builder()
                        .add("username" , username)
                        .add("password" , password)
                        .add("password_con" , passwordCon)
                        .add("display_name" , displayName)
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

                }catch (IOException ex) {
                    ex.printStackTrace();
                }

            return null;

        }

        @Override
        protected void onPreExecute(String s) {
            super.onPreExecute(s);
            Toast.makeText(RegisterActivity.this,s,Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try{
                JSONObject rootObj = new JSONObject(s);
                if (rootObj.has("result")){
                    JSONObject resultObj = rootObj.getJSONObject("result");
                    if(resultObj.getInt("result")==1) {
                        Toast.makeText(RegisterActivity.this,resultObj.getString("result_desc"),Toast.LENGTH_SHORT).show();
                        finish();
                    }else{
                        Toast.makeText(RegisterActivity.this,resultObj.getString("result_desc"),Toast.LENGTH_SHORT).show();
                    }
                    }

                }catch (JSONException ex) {

        }

    }
}


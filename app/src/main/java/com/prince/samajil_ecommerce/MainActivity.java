package com.prince.samajil_ecommerce;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.prince.samajil_ecommerce.activity.HomeActivity;
import com.prince.samajil_ecommerce.activity.RegisterActivity;
import com.prince.samajil_ecommerce.activity.UpdateActivity;
import com.prince.samajil_ecommerce.conf.Api;
import com.prince.samajil_ecommerce.model.UserLogin;
import com.prince.samajil_ecommerce.response.LoginResponse.UserLoginResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.os.StrictMode;

public class MainActivity extends AppCompatActivity {

    UserLoginResponse userLoginResponse;
    EditText username, password;
    Button login;
    TextView registerbutton;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        preferences = this.getSharedPreferences("MY_APP", Context.MODE_PRIVATE);
        String user  = preferences.getString("user",null);
        if (user !=null){
            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(intent);
        }
        super.onCreate(savedInstanceState);
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
        setContentView(R.layout.activity_main);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        registerbutton  = (TextView) findViewById(R.id.registerbutton);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate(username) && validate(password)) {
                    userLogin();
                }
            }
        });
        registerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private boolean validate(EditText editText) {
        if (editText.getText().toString().trim().length() > 0) {
            return true;
        }
        editText.setError("Please Fill This");
        editText.requestFocus();
        return false;
    }

    private void userLogin() {
        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait");
        progressDialog.show();

        UserLogin userLogin = new UserLogin(username.getText().toString().trim(),password.getText().toString().trim());

        Call<UserLoginResponse> call = Api.getInstance().getMyApi().getLoginUser(userLogin);

        call.enqueue(new Callback<UserLoginResponse>() {
            @Override
            public void onResponse(Call<UserLoginResponse> call, Response<UserLoginResponse> response) {
                userLoginResponse = response.body();
                preferences.edit().putString("Token",userLoginResponse.getDetails().getToken()).apply();
                preferences.edit().putString("user",userLoginResponse.getDetails().getUsername()).apply();
                preferences.edit().putBoolean("updatestatus", userLoginResponse.getDetails().getStatus()).apply();

                Toast.makeText(getApplicationContext(), response.body().getDetails().getUsername(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(intent);

            }

            @Override
            public void onFailure(Call<UserLoginResponse> call, Throwable t) {
                Log.d("response", t.getMessage().toString());
                Toast.makeText(getApplicationContext(), "Invalid Username and password", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }
        });
    }
}
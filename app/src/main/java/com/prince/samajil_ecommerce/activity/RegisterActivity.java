package com.prince.samajil_ecommerce.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.prince.samajil_ecommerce.MainActivity;
import com.prince.samajil_ecommerce.R;
import com.prince.samajil_ecommerce.conf.Api;
import com.prince.samajil_ecommerce.model.UserRegister;
import com.prince.samajil_ecommerce.response.RegisterResponse.UserRegisterResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    EditText username,password,confirmpass;
    Button register;

    UserRegisterResponse userRegisterResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
        setContentView(R.layout.activity_register);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        confirmpass = (EditText) findViewById(R.id.confirmpassword);
        register = (Button) findViewById(R.id.register);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate(username) && validate(password) && validate(confirmpass)){
                    userRegister();
                }
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
    private void userRegister(){
        final ProgressDialog progressDialog = new ProgressDialog(RegisterActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait");
        progressDialog.show();
        UserRegister userRegister = new UserRegister(username.getText().toString().trim(),password.getText().toString().trim(),confirmpass.getText().toString().trim());
        Call<UserRegisterResponse> call = Api.getInstance().getMyApi().getRegisterUser(userRegister);
        call.enqueue(new Callback<UserRegisterResponse>() {
            @Override
            public void onResponse(Call<UserRegisterResponse> call, Response<UserRegisterResponse> response) {
                userRegisterResponse = response.body();
                Toast.makeText(getApplicationContext(), userRegisterResponse.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                if (userRegisterResponse.getCode()==200){
                    Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<UserRegisterResponse> call, Throwable t) {
                Log.d("response", t.getMessage().toString());
                Toast.makeText(getApplicationContext(), "Unable to create user", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }
}

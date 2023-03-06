package com.prince.samajil_ecommerce.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.prince.samajil_ecommerce.MainActivity;
import com.prince.samajil_ecommerce.R;

public class HomeActivity extends AppCompatActivity {

    TextView logout;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        preferences = this.getSharedPreferences("MY_APP", Context.MODE_PRIVATE);
        Boolean updatestatus  = preferences.getBoolean("updatestatus",false);
        if (updatestatus.equals(false)){
            Intent intent = new Intent(HomeActivity.this, UpdateActivity.class);
            startActivity(intent);
        }
        super.onCreate(savedInstanceState);
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
        setContentView(R.layout.activity_home);
        logout = (TextView) findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutpage();
            }
        });
    }
    public void logoutpage(){
        preferences.edit().remove("Token").apply();
        preferences.edit().remove("user").apply();
        preferences.edit().remove("updatestatus").apply();
        Intent intent = new Intent(HomeActivity.this,MainActivity.class);
        startActivity(intent);
    }
}

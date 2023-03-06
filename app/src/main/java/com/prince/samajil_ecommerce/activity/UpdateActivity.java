package com.prince.samajil_ecommerce.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.prince.samajil_ecommerce.MainActivity;
import com.prince.samajil_ecommerce.R;
import com.prince.samajil_ecommerce.conf.Api;
import com.prince.samajil_ecommerce.model.UpdateProfile;
import com.prince.samajil_ecommerce.response.LoginResponse.UserLoginResponse;
import com.prince.samajil_ecommerce.response.UpdateProfileResponse.UpdateProfileResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.File;

public class UpdateActivity extends AppCompatActivity  {

    public static final String KEY_User_Document1 = "doc1";
    ImageView IDProf;
    Button updateprofile;
    EditText fullname, phoneno, address, instagramid, facebookid;
    RadioGroup radioSexGroup;
    RadioButton radioSexButton;
    SharedPreferences preferences;

    private String Document_img1="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }
        setContentView(R.layout.updateprofile_activity);
        preferences = this.getSharedPreferences("MY_APP", Context.MODE_PRIVATE);

        radioSexGroup = (RadioGroup) findViewById(R.id.radioGroup);
        IDProf=(ImageView)findViewById(R.id.IdProf);
        fullname = (EditText) findViewById(R.id.fullname);
        int selectedId = radioSexGroup.getCheckedRadioButtonId();
        if (selectedId == 0 ){
            Toast.makeText(getApplicationContext(), "select the gender ", Toast.LENGTH_SHORT).show();
        }
        phoneno = (EditText) findViewById(R.id.phoneno);
        address = (EditText) findViewById(R.id.address);
        instagramid = (EditText) findViewById(R.id.instagramid);
        facebookid = (EditText) findViewById(R.id.facebookid);
        updateprofile = (Button) findViewById(R.id.updateprofile);
        updateprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (IDProf.g)
//                return;
                int selectedId = radioSexGroup.getCheckedRadioButtonId();
                if (selectedId == 0){
                    Toast.makeText(getApplicationContext(),"Choode the Gender",Toast.LENGTH_SHORT).show();
                    return;
                }
                radioSexButton = (RadioButton) findViewById(selectedId);
                if (validate(fullname) && validate(phoneno) && validate(address) && validate(instagramid) && validate(facebookid)){
                    updateuserprofile();
                }
            }
        });
        IDProf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();

            }
        });
    }
    public boolean validate(EditText editText){
        if (editText.getText().toString().trim().length() > 0) {
            return true;
        }
        editText.setError("Please Fill This");
        editText.requestFocus();
        return false;
    }
    private void selectImage() {
        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(UpdateActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo"))
                {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    IDProf.setImageURI(intent.getData());
                    startActivityForResult(intent, 1);
                }
                else if (options[item].equals("Choose from Gallery"))
                {
                    Intent intent = new   Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    IDProf.setImageURI(intent.getData());
                    startActivityForResult(intent, 2);
                }
                else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }
    public void updateuserprofile(){
        final ProgressDialog progressDialog = new ProgressDialog(UpdateActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait");
        progressDialog.show();
        String token  = preferences.getString("Token",null);
        UpdateProfile updateProfile = new UpdateProfile(fullname.getText().toString().trim(),phoneno.getText().toString().trim(),address.getText().toString().trim(),instagramid.getText().toString().trim(),facebookid.getText().toString().trim(),radioSexButton.getText().toString());
        Call<UpdateProfileResponse> call = Api.getInstance().getMyApi().updateprofile("Bearer"+token,updateProfile);
        call.enqueue(new Callback<UpdateProfileResponse>() {
            @Override
            public void onResponse(Call<UpdateProfileResponse> call, Response<UpdateProfileResponse> response) {
                preferences.edit().remove("updatestatus").apply();
                preferences.edit().putBoolean("updatestatus",true).apply();
                Toast.makeText(getApplicationContext(), "Profile update Sucessfully", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                Intent intent = new Intent(UpdateActivity.this, HomeActivity.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<UpdateProfileResponse> call, Throwable t) {
                Log.d("response", t.getMessage().toString());
                Toast.makeText(getApplicationContext(), "Unable to update your profile", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }
}

package com.prince.samajil_ecommerce.inter;

import com.prince.samajil_ecommerce.model.UpdateProfile;
import com.prince.samajil_ecommerce.model.UserLogin;
import com.prince.samajil_ecommerce.model.UserRegister;
import com.prince.samajil_ecommerce.response.LoginResponse.UserLoginResponse;
import com.prince.samajil_ecommerce.response.RegisterResponse.UserRegisterResponse;
import com.prince.samajil_ecommerce.response.UpdateProfileResponse.UpdateProfileResponse;
import retrofit2.Call;
import retrofit2.http.*;

public interface Userapi {

    @POST("api/v1/login/")
    Call<UserLoginResponse> getLoginUser(@Body UserLogin userLogin);

    @POST("api/v1/register/")
    Call<UserRegisterResponse> getRegisterUser(@Body UserRegister userRegister);


    @POST("api/v1/add/additionaldata/")
    Call<UpdateProfileResponse> updateprofile(@Header("Authorization")String auth,@Body UpdateProfile updateProfile);

}

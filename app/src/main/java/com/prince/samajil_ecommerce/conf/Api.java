package com.prince.samajil_ecommerce.conf;

import com.prince.samajil_ecommerce.inter.Userapi;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Api {

    private static Api instance = null;

    private Userapi userapi;

//    private static Retrofit retrofit = null;
    private static String BASE_URL = "http://192.168.100.11:8000/";

    private Api() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        userapi = retrofit.create(Userapi.class);
    }
    public static synchronized Api getInstance() {
        if (instance == null) {
            instance = new Api();
        }
        return instance;
    }

    public Userapi getMyApi() {
        return userapi;
    }
}

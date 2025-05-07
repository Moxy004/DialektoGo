package com.example.dialektogo;

import com.example.dialektogo.EmailResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface EmailJSApi {
    @Headers("Content-Type: application/json")
    @POST("api/v1.0/email/send")
    Call<Void> sendEmail(@Body Map<String, Object> body);
}


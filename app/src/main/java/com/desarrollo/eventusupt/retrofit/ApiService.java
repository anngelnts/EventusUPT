package com.desarrollo.eventusupt.retrofit;

import com.desarrollo.eventusupt.retrofit.responses.EventResponse;
import com.desarrollo.eventusupt.retrofit.responses.UserResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {

    @FormUrlEncoded
    @POST("users/register")
    Call<UserResponse> createUser(@Field("audience") int audience,
                                  @Field("name") String name,
                                  @Field("lastname") String lastname,
                                  @Field("phone") String phone,
                                  @Field("email") String email,
                                  @Field("password") String password,
                                  @Field("status") int status);

    @GET("events")
    Call<List<EventResponse>> getEvents();
}

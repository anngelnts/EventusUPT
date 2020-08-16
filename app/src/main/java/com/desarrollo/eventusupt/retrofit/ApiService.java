package com.desarrollo.eventusupt.retrofit;

import com.desarrollo.eventusupt.retrofit.responses.EventResponse;
import com.desarrollo.eventusupt.retrofit.responses.LoginResponse;
import com.desarrollo.eventusupt.retrofit.responses.OrganizerResponse;
import com.desarrollo.eventusupt.retrofit.responses.UserResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {

    @FormUrlEncoded
    @POST("users/register")
    Call<UserResponse> createUser(@Field("audience_id") int audience,
                                  @Field("name") String name,
                                  @Field("lastname") String lastname,
                                  @Field("phone") String phone,
                                  @Field("email") String email,
                                  @Field("password") String password,
                                  @Field("status") int status);

    @FormUrlEncoded
    @POST("users/login")
    Call<LoginResponse> loginUser(@Field("email") String email, @Field("password") String password);


    //events
    @GET("events")
    Call<List<EventResponse>> getEvents();

    @FormUrlEncoded
    @POST("events")
    Call<List<EventResponse>> createEvents();


    //organizers
    @FormUrlEncoded
    @POST("organizers/login")
    Call<LoginResponse> loginOrganizer(@Field("email") String email, @Field("password") String password);
    
    @GET("organizers/profile")
    Call<OrganizerResponse> getProfileOrganizer(@Query("token") String token);
}

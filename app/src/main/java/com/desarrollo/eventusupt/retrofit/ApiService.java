package com.desarrollo.eventusupt.retrofit;

import com.desarrollo.eventusupt.retrofit.responses.EventResponse;
import com.desarrollo.eventusupt.retrofit.responses.EventTypeResponse;
import com.desarrollo.eventusupt.retrofit.responses.EventUserResponse;
import com.desarrollo.eventusupt.retrofit.responses.LoginResponse;
import com.desarrollo.eventusupt.retrofit.responses.OrganizerResponse;
import com.desarrollo.eventusupt.retrofit.responses.ParticipantResponse;
import com.desarrollo.eventusupt.retrofit.responses.UserResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    //login and register
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

    @GET("events/{id}")
    Call<EventResponse> getEventResponse(@Path("id")int id);

    @FormUrlEncoded
    @POST("events")
    Call<EventResponse> createEvents(//@Part MultipartBody.Part image,
                                     @Field("type_id") int type_id,
                                     @Field("school_id") int school_id,
                                     @Field("title") String title,
                                     @Field("description") String description,
                                     @Field("event_date") String event_date,
                                     @Field("start_time") String start_time,
                                     @Field("end_time") String end_time,
                                     @Field("is_outstanding") int is_outstanding,
                                     @Field("is_virtual") int is_virtual,
                                     @Field("is_open") int is_open,
                                     @Field("location") String location,
                                     @Field("event_link") String event_link,
                                     @Field("status") int status,
                                     @Field("token") String token);


    //event types
    @GET("event-types")
    Call<List<EventTypeResponse>> getEventTypes();


    //organizers
    @FormUrlEncoded
    @POST("organizers/login")
    Call<LoginResponse> loginOrganizer(@Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("organizers/profile")
    Call<OrganizerResponse> editProfileOrganizer(@Field("token") String token,
                                                 @Field("name") String name,
                                                 @Field("acronym") String acronym,
                                                 @Field("phone") String phone,
                                                 @Field("website") String website,
                                                 @Field("facebook") String facebook,
                                                 @Field("email") String email);

    @GET("organizers/profile")
    Call<OrganizerResponse> getProfileOrganizer(@Query("token") String token);


    //users
    @GET("users/profile")
    Call<UserResponse> getProfileUsers(@Query("token")String token);

    @FormUrlEncoded
    @POST("users/profile")
    Call<UserResponse> editProfileUser(@Field("token") String token,
                                       @Field("name") String name,
                                       @Field("lastname") String lastname,
                                       @Field("phone") String phone,
                                       @Field("email") String email);

    @GET("users/my-events")
    Call<List<EventUserResponse>> getAllEventsUser(@Query("token")String token);

    @GET("users/my-events/{id}")
    Call<EventUserResponse> getEventUserDetail(@Path("id")String id);

    //participants

    @FormUrlEncoded
    @POST("events/register/participant")
    Call<ParticipantResponse> registerParticipant(@Field("token") String token,
                                       @Field("event_id") int event_id);

    @FormUrlEncoded
    @POST("events/update/participant")
    Call<ParticipantResponse> checkParticipant(@Field("token") String token,
                                                  @Field("event_id") int event_id);
}

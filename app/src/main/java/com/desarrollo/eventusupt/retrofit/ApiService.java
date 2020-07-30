package com.desarrollo.eventusupt.retrofit;

import com.desarrollo.eventusupt.retrofit.responses.EventResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {

    @GET("events")
    Call<List<EventResponse>> getEvents();
}

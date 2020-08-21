package com.desarrollo.eventusupt.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.desarrollo.eventusupt.DetailMyEventActivity;
import com.desarrollo.eventusupt.R;
import com.desarrollo.eventusupt.adapters.EventAdapter;
import com.desarrollo.eventusupt.adapters.MyEventAdapter;
import com.desarrollo.eventusupt.helpers.SaveSharedPreference;
import com.desarrollo.eventusupt.models.EventModel;
import com.desarrollo.eventusupt.retrofit.RetrofitClient;
import com.desarrollo.eventusupt.retrofit.responses.EventUserResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MyEventFragment extends Fragment {

    private ArrayList<EventModel> listEvents;
    private RecyclerView recyclerView;

    public MyEventFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setEvents();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_myevent, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.my_events_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        MyEventAdapter myEventAdapter = new MyEventAdapter(listEvents);
        recyclerView.setAdapter(myEventAdapter);
    }

    private void setAdapter(){
        EventAdapter eventAdapter = new EventAdapter(listEvents);
        eventAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DetailMyEventActivity.class);
                int id = listEvents.get(recyclerView.getChildAdapterPosition(v)).getId();
                intent.putExtra("id", id);
                startActivity(intent);
                getActivity();
            }
        });
        recyclerView.setAdapter(eventAdapter);
    }

    private void setEvents(){
        String token = SaveSharedPreference.getLoggedToken(getContext());
        listEvents = new ArrayList<>();
       Call<List<EventUserResponse>> call = RetrofitClient.getInstance().getApi().getAllEventsUser(token);
        call.enqueue(new Callback<List<EventUserResponse>>() {
            @Override
            public void onResponse(retrofit2.Call<List<EventUserResponse>> call, Response<List<EventUserResponse>> response) {
                if(response.isSuccessful()) {
                    List<EventUserResponse> events = response.body();
                    for (EventUserResponse event : events) {
                        EventModel eventModel = new EventModel();
                        eventModel.setId(event.getId());
                        eventModel.setTitle(event.getTitle());
                        eventModel.setProvider("Dirección de Educación Continua");
                        eventModel.setImage(event.getImage());
                        eventModel.setDateString(event.getEventDate());
                        listEvents.add(eventModel);
                    }
                    setAdapter();
                }
            }

            @Override
            public void onFailure(Call<List<EventUserResponse>> call, Throwable t) {
                Log.d("TAG","request"+"\n"+t);
            }
        });
    }
}

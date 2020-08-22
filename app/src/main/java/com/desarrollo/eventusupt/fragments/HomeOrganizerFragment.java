package com.desarrollo.eventusupt.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.desarrollo.eventusupt.CreateEventActivity;
import com.desarrollo.eventusupt.DetailMyEventOrganizer;
import com.desarrollo.eventusupt.R;
import com.desarrollo.eventusupt.adapters.EventAdapter;
import com.desarrollo.eventusupt.adapters.EventOrganizerAdapter;
import com.desarrollo.eventusupt.helpers.SaveSharedPreference;
import com.desarrollo.eventusupt.models.EventModel;
import com.desarrollo.eventusupt.retrofit.RetrofitClient;
import com.desarrollo.eventusupt.retrofit.responses.EventResponse;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeOrganizerFragment extends Fragment {

    private ArrayList<EventModel> listEvents;
    private RecyclerView recyclerView;

    public HomeOrganizerFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setEvents();
    }

    private void setEvents() {
        String token = SaveSharedPreference.getLoggedToken(getContext());
        listEvents = new ArrayList<>();
        Call<List<EventResponse>> call = RetrofitClient.getInstance().getApi().getMyEventsOrganizer(token);
        call.enqueue(new Callback<List<EventResponse>>() {
            @Override
            public void onResponse(retrofit2.Call<List<EventResponse>> call, Response<List<EventResponse>> response) {
                if (response.isSuccessful()) {
                    List<EventResponse> events = response.body();
                    for (EventResponse event : events) {
                        EventModel eventModel = new EventModel();
                        eventModel.setId(event.getId());
                        eventModel.setTitle(event.getTitle());
                        eventModel.setProvider("EPIS");
                        eventModel.setImage(event.getImage());
                        eventModel.setDateString(event.getEventDate());
                        listEvents.add(eventModel);
                    }
                    setAdapter();
                }
            }

            @Override
            public void onFailure(Call<List<EventResponse>> call, Throwable t) {
                Log.d("TAG", "request" + "\n" + t);
            }
        });
    }

    private void setAdapter() {
        EventOrganizerAdapter eventOrganizerAdapter = new EventOrganizerAdapter(listEvents);
        eventOrganizerAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DetailMyEventOrganizer.class);
                int id = listEvents.get(recyclerView.getChildAdapterPosition(v)).getId();
                intent.putExtra("id", id);
                startActivity(intent);
                getActivity();
            }
        });
        recyclerView.setAdapter(eventOrganizerAdapter);
    }

    public void OnCreateEvent(){
        Intent intent = new Intent(getActivity(), CreateEventActivity.class);
        startActivity(intent);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_organizer, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.my_events_organizer_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        setAdapter();

        FloatingActionButton floatingActionButton = view.findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnCreateEvent();
            }
        });
    }
}


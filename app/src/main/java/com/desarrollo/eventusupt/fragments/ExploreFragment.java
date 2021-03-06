package com.desarrollo.eventusupt.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.desarrollo.eventusupt.R;
import com.desarrollo.eventusupt.adapters.EventAdapter;
import com.desarrollo.eventusupt.adapters.EventTypeAdapter;
import com.desarrollo.eventusupt.models.EventModel;
import com.desarrollo.eventusupt.models.EventTypeModel;
import com.desarrollo.eventusupt.retrofit.RetrofitClient;
import com.desarrollo.eventusupt.retrofit.responses.EventResponse;
import com.desarrollo.eventusupt.retrofit.responses.EventTypeResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ExploreFragment extends Fragment {

    private ArrayList<EventTypeModel> listEventTypes;
    private RecyclerView recyclerView;

    public ExploreFragment() {
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
        return inflater.inflate(R.layout.fragment_explore, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.events_types_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        setAdapter();
    }

    private void setAdapter(){
        EventTypeAdapter eventAdapter = new EventTypeAdapter(listEventTypes);
        recyclerView.setAdapter(eventAdapter);
    }

    private void setEvents(){
        listEventTypes = new ArrayList<>();

        Call<List<EventTypeResponse>> call = RetrofitClient.getInstance().getApi().getEventTypes();
        call.enqueue(new Callback<List<EventTypeResponse>>() {
            @Override
            public void onResponse(Call<List<EventTypeResponse>> call, Response<List<EventTypeResponse>> response) {
                if(response.isSuccessful()) {
                    List<EventTypeResponse> eventTypes = response.body();
                    for (EventTypeResponse type : eventTypes) {
                        EventTypeModel eventModel = new EventTypeModel();
                        eventModel.setId(type.getId());
                        eventModel.setName(type.getName());
                        listEventTypes.add(eventModel);
                    }
                    setAdapter();
                }
            }

            @Override
            public void onFailure(Call<List<EventTypeResponse>> call, Throwable t) {
                Toast.makeText(getContext(), "Algo salio mal", Toast.LENGTH_SHORT).show();
            }
        });

        /*listEventTypes.add(new EventTypeModel(1, "Académico"));
        listEventTypes.add(new EventTypeModel(2, "Ceremonia"));
        listEventTypes.add(new EventTypeModel(3, "Conferencia"));
        listEventTypes.add(new EventTypeModel(4, "Exposición"));
        listEventTypes.add(new EventTypeModel(5, "Sesión de información"));
        listEventTypes.add(new EventTypeModel(6, "Conferencia y seminarios"));
        listEventTypes.add(new EventTypeModel(7, "Reunión"));
        listEventTypes.add(new EventTypeModel(8, "Actuación"));
        listEventTypes.add(new EventTypeModel(9, "Evento especial"));
        listEventTypes.add(new EventTypeModel(10, "Actividad estudiantil"));
        listEventTypes.add(new EventTypeModel(11, "Taller"));*/
    }
}

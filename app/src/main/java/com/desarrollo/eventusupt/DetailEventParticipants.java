package com.desarrollo.eventusupt;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.desarrollo.eventusupt.adapters.ParticipantsAdapter;
import com.desarrollo.eventusupt.models.ParticipantModel;
import com.desarrollo.eventusupt.retrofit.RetrofitClient;
import com.desarrollo.eventusupt.retrofit.responses.ParticipantResponse;
import com.desarrollo.eventusupt.retrofit.responses.eventorganizer.EventOrganizerResponse;
import com.desarrollo.eventusupt.retrofit.responses.eventorganizer.Participant;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailEventParticipants extends AppCompatActivity {

    private int idevento;
    private ArrayList<ParticipantModel> listParticipants;
    private RecyclerView recyclerView;

    TextView txt_participant_name;
    TextView txt_participant_assistant;
    TextView txt_participant_assistant_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_event_participants);

        recyclerView = (RecyclerView) findViewById(R.id.participants_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        setAdapter();

        idevento = getIntent().getIntExtra("id",0);
        inicializarDatos();
        getEvent();
    }

    private void setAdapter() {
        ParticipantsAdapter participantsAdapter = new ParticipantsAdapter(listParticipants);
        recyclerView.setAdapter(participantsAdapter);
    }

    private void getEvent() {
        Call<EventOrganizerResponse> call = RetrofitClient.getInstance().getApi().getEventOrganizerDetail(idevento);
        call.enqueue(new Callback<EventOrganizerResponse>() {
            @Override
            public void onResponse(Call<EventOrganizerResponse> call, Response<EventOrganizerResponse> response) {
                if(response.code() == 200) {
                    assert response.body() != null;
                    getEventOrganizerDetail(response.body());
                }else if(response.code() == 401){
                    Toast.makeText(getApplicationContext(), "Evento no encontrado", Toast.LENGTH_SHORT).show();
                }
                else if(response.code() == 404){
                    Toast.makeText(getApplicationContext(), "Evento no encontrado", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<EventOrganizerResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "No se pudo obtener evento", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getEventOrganizerDetail(EventOrganizerResponse eventOrganizerResponse) {
        ArrayList<ParticipantModel> list = new ArrayList<>();
        for (Participant item: eventOrganizerResponse.getParticipants()
             ) {
            //Log.d("Hola",item.getAssistanceDate());
            list.add(new ParticipantModel(item.getId(),item.getName(),item.getAssistance(),item.getAssistanceDate()));
        }
        listParticipants=list;
        setAdapter();
    }

    private void inicializarDatos() {
        txt_participant_name = findViewById(R.id.txt_participant_name);
        txt_participant_assistant = findViewById(R.id.txt_participant_assistant);
        txt_participant_assistant_time = findViewById(R.id.txt_participant_assistant_time);
    }
}

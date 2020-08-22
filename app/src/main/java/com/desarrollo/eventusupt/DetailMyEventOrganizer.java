package com.desarrollo.eventusupt;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.desarrollo.eventusupt.retrofit.RetrofitClient;
import com.desarrollo.eventusupt.retrofit.responses.eventorganizer.EventOrganizerResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailMyEventOrganizer extends AppCompatActivity {

    private int idevento;

    TextView txt_my_event_organizer_title;
    TextView txt_my_event_organizer_description;
    TextView txt_my_event_organizer_starttime;
    TextView txt_my_event_organizer_endtime;
    TextView txt_my_event_organizer_format;
    TextView txt_my_event_organizer_address;
    Button detail_my_event_organizer_participants;
    ImageView my_event_organizer_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_my_event_organizer);
        idevento = getIntent().getIntExtra("id",0);
        inicializarDatos();
        getEvent();
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

    private void getEventOrganizerDetail(EventOrganizerResponse eventOrganizerDetail) {
        txt_my_event_organizer_title.setText(eventOrganizerDetail.getEvent().getTitle());
        txt_my_event_organizer_description.setText(eventOrganizerDetail.getEvent().getDescription());
        txt_my_event_organizer_starttime.setText(eventOrganizerDetail.getEvent().getStartTime());
        txt_my_event_organizer_endtime.setText(eventOrganizerDetail.getEvent().getEndTime());
        Glide.with(my_event_organizer_image.getContext()).load(eventOrganizerDetail.getEvent().getImage()).into(my_event_organizer_image);
        if (eventOrganizerDetail.getEvent().getIsVirtual()==1){
            String format = eventOrganizerDetail.getEvent().getStatus() == 1 ? "Virtual" : "Presencial";
            txt_my_event_organizer_format.setText(format);
            txt_my_event_organizer_address.setText(eventOrganizerDetail.getEvent().getEventLink());
        }else{
            String format = eventOrganizerDetail.getEvent().getStatus() == 2 ? "Virtual" : "Presencial";
            txt_my_event_organizer_format.setText(format);
            txt_my_event_organizer_address.setText(eventOrganizerDetail.getEvent().getLocation());
        }
    }

    private void inicializarDatos() {

        txt_my_event_organizer_title = findViewById(R.id.txt_my_event_organizer_title);
        txt_my_event_organizer_description = findViewById(R.id.txt_my_event_organizer_description);
        txt_my_event_organizer_starttime = findViewById(R.id.txt_my_event_organizer_starttime);
        txt_my_event_organizer_endtime = findViewById(R.id.txt_my_event_organizer_endtime);
        txt_my_event_organizer_format = findViewById(R.id.txt_my_event_organizer_format);
        txt_my_event_organizer_address = findViewById(R.id.txt_my_event_organizer_address);
        my_event_organizer_image = findViewById(R.id.my_event_organizer_image);

        detail_my_event_organizer_participants = findViewById(R.id.detail_my_event_organizer_participants);
        detail_my_event_organizer_participants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DetailEventParticipants.class);
                intent.putExtra("id",idevento);
                startActivity(intent);
            }
        });
    }
}

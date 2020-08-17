package com.desarrollo.eventusupt;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.desarrollo.eventusupt.helpers.SaveSharedPreference;
import com.desarrollo.eventusupt.retrofit.RetrofitClient;
import com.desarrollo.eventusupt.retrofit.responses.EventResponse;
import com.desarrollo.eventusupt.retrofit.responses.ParticipantResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailEventActivity extends AppCompatActivity {

    private int idevento;

    TextView txt_event_title;
    TextView txt_event_description;
    TextView txt_event_starttime;
    TextView txt_event_endtime;
    TextView txt_event_format;
    TextView txt_event_address;
    Button detail_event_button_participate;
    Button detail_event_button_check;
    ImageView img_event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_event);
        idevento = getIntent().getIntExtra("id",0);
        inicializarDatos();
        getEvent();
    }

    private void inicializarDatos() {
        txt_event_title = findViewById(R.id.txt_event_title);
        txt_event_description = findViewById(R.id.txt_event_description);
        txt_event_starttime = findViewById(R.id.txt_event_starttime);
        txt_event_endtime = findViewById(R.id.txt_event_endtime);
        txt_event_format = findViewById(R.id.txt_event_format);
        txt_event_address = findViewById(R.id.txt_event_address);
        img_event = findViewById(R.id.img_event);

        detail_event_button_participate = findViewById(R.id.detail_event_button_participate);
        detail_event_button_check = findViewById(R.id.detail_event_button_check);

        detail_event_button_participate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerParticipant();
            }
        });

        detail_event_button_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkParticipant();
            }
        });
    }

    private void checkParticipant() {
        String token = SaveSharedPreference.getLoggedToken(getApplicationContext());
        Call<ParticipantResponse> call = RetrofitClient.getInstance().getApi().checkParticipant(token,idevento);
        call.enqueue((new Callback<ParticipantResponse>() {
            @Override
            public void onResponse(Call<ParticipantResponse> call, Response<ParticipantResponse> response) {
                if(response.code() == 200) {
                    assert response.body() != null;
                    Toast.makeText(getApplicationContext(), "Se marco su asistencia correctamente", Toast.LENGTH_SHORT).show();
                }else if(response.code() == 401){
                    Toast.makeText(getApplicationContext(), "No se pudo marcar su asistencia", Toast.LENGTH_SHORT).show();
                }
                else if(response.code() == 404){
                    Toast.makeText(getApplicationContext(), "No se pudo marcar su asistencia", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ParticipantResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error fatal", Toast.LENGTH_SHORT).show();
            }
        }));
    }


    private void registerParticipant() {
        String token = SaveSharedPreference.getLoggedToken(getApplicationContext());
        Call<ParticipantResponse> call = RetrofitClient.getInstance().getApi().registerParticipant(token,idevento);
        call.enqueue((new Callback<ParticipantResponse>() {
            @Override
            public void onResponse(Call<ParticipantResponse> call, Response<ParticipantResponse> response) {
                if(response.code() == 200) {
                    assert response.body() != null;
                    Toast.makeText(getApplicationContext(), "Se registro al evento", Toast.LENGTH_SHORT).show();
                }else if(response.code() == 401){
                    Toast.makeText(getApplicationContext(), "No se pudo registrar al evento", Toast.LENGTH_SHORT).show();
                }
                else if(response.code() == 404){
                    Toast.makeText(getApplicationContext(), "No se pudo registrar al evento", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ParticipantResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "No se pudo registrar al evento", Toast.LENGTH_SHORT).show();
            }
        }));
    }

    private void getEvent() {
        Call<EventResponse> call = RetrofitClient.getInstance().getApi().getEventResponse(idevento);
        call.enqueue(new Callback<EventResponse>() {
            @Override
            public void onResponse(Call<EventResponse> call, Response<EventResponse> response) {
                if(response.code() == 200) {
                    assert response.body() != null;
                    setEventDetail(response.body());
                }else if(response.code() == 401){
                    Toast.makeText(getApplicationContext(), "Evento no encontrado", Toast.LENGTH_SHORT).show();
                }
                else if(response.code() == 404){
                    Toast.makeText(getApplicationContext(), "Evento no encontrado", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<EventResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "No se pudo obtener evento", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setEventDetail(EventResponse eventDetail){

        txt_event_title.setText(eventDetail.getTitle());
        txt_event_description.setText(eventDetail.getDescription());
        txt_event_starttime.setText(eventDetail.getStartTime());
        txt_event_endtime.setText(eventDetail.getEndTime());
        if (eventDetail.getIsVirtual()==1){
            String format = eventDetail.getStatus() == 1 ? "Virtual" : "Presencial";
            txt_event_format.setText(format);
            txt_event_address.setText(eventDetail.getEventLink());
        }else{
            String format = eventDetail.getStatus() == 2 ? "Virtual" : "Presencial";
            txt_event_format.setText(format);
            txt_event_address.setText(eventDetail.getLocation());
        }
    }
}

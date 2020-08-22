package com.desarrollo.eventusupt;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.desarrollo.eventusupt.helpers.SaveSharedPreference;
import com.desarrollo.eventusupt.retrofit.RetrofitClient;
import com.desarrollo.eventusupt.retrofit.responses.EventResponse;
import com.desarrollo.eventusupt.retrofit.responses.ParticipantResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailMyEventActivity extends AppCompatActivity {

    public static int idevento;

    ImageView img_event;
    TextView txt_my_event_title;
    TextView txt_my_event_description;
    TextView txt_my_event_starttime;
    TextView txt_my_event_endtime;
    TextView txt_my_event_format;
    TextView txt_my_event_address;

    Button detail_my_event_button_check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_my_event);
        idevento = getIntent().getIntExtra("id",0);
        iniciarlizarDatos();
        getUserEvent();
    }

    private void getUserEvent() {
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

    private void setEventDetail(EventResponse eventUserDetail) {
        txt_my_event_title.setText(eventUserDetail.getTitle());
        txt_my_event_description.setText(eventUserDetail.getDescription());
        txt_my_event_starttime.setText(eventUserDetail.getStartTime());
        txt_my_event_endtime.setText(eventUserDetail.getEndTime());
        if (eventUserDetail.getIsVirtual()==1){
            String format = eventUserDetail.getStatus() == 1 ? "Virtual" : "Presencial";
            txt_my_event_format.setText(format);
            txt_my_event_address.setText(eventUserDetail.getEventLink());
        }else{
            String format = eventUserDetail.getStatus() == 2 ? "Virtual" : "Presencial";
            txt_my_event_format.setText(format);
            txt_my_event_address.setText(eventUserDetail.getLocation());
        }
        Glide.with(img_event.getContext()).load(eventUserDetail.getImage()).into(img_event);
    }

    private void iniciarlizarDatos() {
        txt_my_event_title = findViewById(R.id.txt_my_event_title);
        txt_my_event_description = findViewById(R.id.txt_my_event_description);
        txt_my_event_starttime = findViewById(R.id.txt_my_event_starttime);
        txt_my_event_endtime = findViewById(R.id.txt_my_event_endtime);
        txt_my_event_format = findViewById(R.id.txt_my_event_format);
        txt_my_event_address = findViewById(R.id.txt_my_event_address);
        img_event = findViewById(R.id.img_event);

        detail_my_event_button_check = findViewById(R.id.detail_my_event_button_check);

        detail_my_event_button_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txt_my_event_format.getText().toString().equals("Virtual")){
                    checkParticipant();
                } else {
                    MyQRDialog myQRDialog = new MyQRDialog();
                    myQRDialog.show(getSupportFragmentManager(), "MyQRDialog");
                }
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
}

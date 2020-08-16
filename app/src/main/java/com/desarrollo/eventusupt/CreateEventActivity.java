package com.desarrollo.eventusupt;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.desarrollo.eventusupt.retrofit.ApiService;
import com.desarrollo.eventusupt.retrofit.RetrofitClient;
import com.desarrollo.eventusupt.retrofit.responses.UserResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateEventActivity extends AppCompatActivity {


    private ApiService mAPIService;

    EditText editTitle;
    EditText editDescription;
    EditText editDate;
    EditText editTimeStart;
    EditText editTimeEnd;
    EditText editFormat;
    EditText editStatus;
    EditText editUrlAddress;
    EditText editEventType;
    EditText editSchool;

    Button buttonImageAdd;
    Button buttonCreateEvent;
    Button buttonCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        editTitle = findViewById(R.id.create_event_edt_title);
        editDescription = findViewById(R.id.create_event_edt_description);
        editDate = findViewById(R.id.create_event_edt_date);
        editTimeStart = findViewById(R.id.create_event_edt_starttime);
        editTimeEnd = findViewById(R.id.create_event_edt_endtime);
        editFormat = findViewById(R.id.create_event_edt_format);
        editStatus = findViewById(R.id.create_event_edt_status);
        editUrlAddress = findViewById(R.id.create_event_edt_address);
        editEventType = findViewById(R.id.create_event_edt_type);
        editSchool = findViewById(R.id.create_event_edt_school);

        buttonImageAdd = findViewById(R.id.create_evento_button_image);
        buttonCreateEvent = findViewById(R.id.create_evento_button_create);
        buttonCancel = findViewById(R.id.create_event_button_cancel);

        mAPIService = RetrofitClient.getInstance().getApi();

        buttonCreateEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int audience = 1;
                String title = editTitle.getText().toString();
                String description = editDescription.getText().toString();
                String date = editDate.getText().toString();
                String timestart = editTimeStart.getText().toString();
                String timeend = editTimeEnd.getText().toString();
                String format = editFormat.getText().toString();
                String eventstatus = editStatus.getText().toString();
                String urladdress = editUrlAddress.getText().toString();
                String eventtype = editEventType.getText().toString();
                String school = editSchool.getText().toString();
                int status = 1;

                if (title.isEmpty()){
                    Toast.makeText(CreateEventActivity.this, "Por favor ingrese un título", Toast.LENGTH_SHORT).show();
                }else if (description.isEmpty()){
                    Toast.makeText(CreateEventActivity.this, "Por favor ingrese una descripción", Toast.LENGTH_SHORT).show();
                }else if (date.isEmpty()){
                    Toast.makeText(CreateEventActivity.this, "Por favor ingrese una fecha", Toast.LENGTH_SHORT).show();
                }else if (timestart.isEmpty()){
                    Toast.makeText(CreateEventActivity.this, "Por favor ingrese la hora inicio", Toast.LENGTH_SHORT).show();
                }else if (timeend.isEmpty()){
                    Toast.makeText(CreateEventActivity.this, "Por favor ingrese la hora fin", Toast.LENGTH_SHORT).show();
                }else if (format.isEmpty()){
                    Toast.makeText(CreateEventActivity.this, "Por favor ingrese un formato", Toast.LENGTH_SHORT).show();
                }else if (eventstatus.isEmpty()){
                    Toast.makeText(CreateEventActivity.this, "Por favor ingrese el estado", Toast.LENGTH_SHORT).show();
                }else if (urladdress.isEmpty()){
                    Toast.makeText(CreateEventActivity.this, "Por favor ingrese una Direccion/URL", Toast.LENGTH_SHORT).show();
                }else if (eventtype.isEmpty()){
                    Toast.makeText(CreateEventActivity.this, "Por favor ingrese un tipo de evento", Toast.LENGTH_SHORT).show();
                }else if (school.isEmpty()){
                    Toast.makeText(CreateEventActivity.this, "Por favor ingrese una escuela", Toast.LENGTH_SHORT).show();
                }else {
                    sendPost(audience,title,description,date,timestart,timeend,format,eventstatus,urladdress,eventtype,school,status);
                }
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnBack();
            }
        });

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void sendPost(int audience, String title, String description, String date, String timestart, String timeend, String format, String eventstatus, String urladdress, String eventtype, String school, int status){
        /*mAPIService.createEvent(audience,title,description,date,timestart,timeend,format,eventstatus,urladdress,eventtype,school,status).enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful()){
                    Toast.makeText(CreateEventActivity.this, "Evento Creado", Toast.LENGTH_SHORT).show();
                    OnBack();
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Toast.makeText(CreateEventActivity.this, "Unable to submit post to API.",Toast.LENGTH_SHORT).show();
            }
        });*/
    }

    private void OnBack(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}

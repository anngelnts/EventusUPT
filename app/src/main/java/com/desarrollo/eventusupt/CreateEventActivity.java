package com.desarrollo.eventusupt;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.desarrollo.eventusupt.helpers.DatePickerFragment;
import com.desarrollo.eventusupt.helpers.SaveSharedPreference;
import com.desarrollo.eventusupt.helpers.TimePickerFragment;
import com.desarrollo.eventusupt.helpers.Util;
import com.desarrollo.eventusupt.retrofit.RetrofitClient;
import com.desarrollo.eventusupt.retrofit.responses.EventResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateEventActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private static final int REQUEST_CODE_READ_STORAGE = 200;
    private Uri uriImage;

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


        editDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        editTimeStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showStartTimePickerDialog();
            }
        });

        editTimeEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEndTimePickerDialog();
            }
        });

        buttonImageAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        buttonCreateEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                    EventResponse eventResponse = new EventResponse();
                    eventResponse.setTypeId(1);
                    eventResponse.setSchoolId(1);
                    eventResponse.setTitle(title);
                    eventResponse.setDescription(description);
                    eventResponse.setEventDate(date);
                    eventResponse.setStartTime(timestart);
                    eventResponse.setEndTime(timeend);
                    eventResponse.setIsOutstanding(1);
                    eventResponse.setIsVirtual(1);
                    eventResponse.setIsOpen(0);
                    eventResponse.setLocation("");
                    eventResponse.setEventLink("");
                    eventResponse.setStatus(status);
                    sendPost(eventResponse);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_READ_STORAGE) {
            if (resultCode == RESULT_OK) {
                // Get the Uri of the selected file
                assert data != null;
                uriImage = data.getData();
                assert uriImage != null;
                Log.d(TAG, "File Uri: " + uriImage.toString());
            }
        }
    }

    private void showDatePickerDialog() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance((view, year, month, dayOfMonth) -> {
            final String selectedDate = year + "-" + Util.twoDigits((month+1)) + "-" + Util.twoDigits(dayOfMonth);
            editDate.setText(selectedDate);
        });

        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    private void showStartTimePickerDialog() {
        TimePickerFragment newFragment = TimePickerFragment.newInstance(new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                final String selectedTime = hourOfDay + ":" + minute;
                editTimeStart.setText(selectedTime);
            }
        });

        newFragment.show(getSupportFragmentManager(), "timeStartPicker");
    }

    private void showEndTimePickerDialog() {
        TimePickerFragment newFragment = TimePickerFragment.newInstance(new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                final String selectedTime = hourOfDay + ":" + minute;
                editTimeEnd.setText(selectedTime);
            }
        });

        newFragment.show(getSupportFragmentManager(), "timeEndPicker");
    }

    private void selectImage(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, REQUEST_CODE_READ_STORAGE);
        try {
            startActivityForResult(Intent.createChooser(intent, "Seleccione un archivo para cargar"), REQUEST_CODE_READ_STORAGE);
        } catch (android.content.ActivityNotFoundException ex) {
            // Potentially direct the user to the Market with a Dialog
            Toast.makeText(this, "Instale un administrador de archivos", Toast.LENGTH_SHORT).show();
        }
    }

    private void sendPost(EventResponse event){
        String tokenString = SaveSharedPreference.getLoggedToken(getApplicationContext());
        //File file = new File(String.valueOf(uriImage));

        //RequestBody requestBody = RequestBody.create(file, MediaType.parse("image/*"));
        //MultipartBody.Part image = MultipartBody.Part.createFormData("image", file.getName(), requestBody);
        /*RequestBody type_id = RequestBody.create(event.getTitle(), MediaType.parse("text/plain"));
        RequestBody school_id = RequestBody.create(event.getTitle(), MediaType.parse("text/plain"));
        RequestBody title = RequestBody.create(event.getTitle(), MediaType.parse("text/plain"));
        RequestBody description = RequestBody.create(event.getTitle(), MediaType.parse("text/plain"));
        RequestBody event_date = RequestBody.create(event.getTitle(), MediaType.parse("text/plain"));
        RequestBody start_time = RequestBody.create(event.getTitle(), MediaType.parse("text/plain"));
        RequestBody end_time = RequestBody.create(event.getTitle(), MediaType.parse("text/plain"));
        RequestBody is_outstanding = RequestBody.create(event.getTitle(), MediaType.parse("text/plain"));
        RequestBody is_virtual = RequestBody.create(event.getTitle(), MediaType.parse("text/plain"));
        RequestBody is_open = RequestBody.create(event.getTitle(), MediaType.parse("text/plain"));
        RequestBody location = RequestBody.create(event.getTitle(), MediaType.parse("text/plain"));
        RequestBody event_link = RequestBody.create(event.getTitle(), MediaType.parse("text/plain"));
        RequestBody status = RequestBody.create(event.getTitle(), MediaType.parse("text/plain"));
        RequestBody token = RequestBody.create(tokenString, MediaType.parse("text/plain"));*/


        int type_id = event.getTypeId();
        int school_id = event.getSchoolId();
        String title = event.getTitle();
        String description = event.getDescription();
        String event_date = event.getEventDate();
        String start_time = event.getStartTime();
        String end_time = event.getEndTime();
        int is_outstanding = event.getIsOutstanding();
        int is_virtual = event.getIsVirtual();
        int is_open = event.getIsOpen();
        String location = event.getLocation();
        String event_link = event.getEventLink();
        int status = event.getStatus();

        Call<EventResponse> call = RetrofitClient.getInstance().getApi().createEvents(
                //image,
                type_id,
                school_id,
                title,
                description,
                event_date,
                start_time,
                end_time,
                is_outstanding,
                is_virtual,
                is_open,
                location,
                event_link,
                status,
                tokenString
        );
        call.enqueue(new Callback<EventResponse>() {
            @Override
            public void onResponse(Call<EventResponse> call, Response<EventResponse> response) {
                if(response.code() == 201){
                    Toast.makeText(CreateEventActivity.this, "Evento registrado", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(CreateEventActivity.this, "No se pudo registrar el evento", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<EventResponse> call, Throwable t) {
                Toast.makeText(CreateEventActivity.this, "Algo salió mal", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void OnBack(){
        Intent intent = new Intent(this, MainActivityOrganizer.class);
        startActivity(intent);
        finish();
    }
}

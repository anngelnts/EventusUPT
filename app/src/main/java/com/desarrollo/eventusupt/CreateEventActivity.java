package com.desarrollo.eventusupt;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

    Spinner cmbFormat;
    Spinner cmbStatus;

    EditText editUrlAddress;

    Spinner cmbType;
    Spinner cmbFaculty;
    Spinner cmbSchools;

    EditText editUrlImage;

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

        cmbFormat = findViewById(R.id.create_event_cmb_format);
        cmbStatus = findViewById(R.id.create_event_cmb_status);

        editUrlAddress = findViewById(R.id.create_event_edt_address);

        cmbType = findViewById(R.id.create_event_cmb_type);
        cmbFaculty = findViewById(R.id.create_event_cmb_faculty);
        cmbSchools = findViewById(R.id.create_event_cmb_school);
        editUrlImage = findViewById(R.id.create_event_edt_imageurl);

        //buttonImageAdd = findViewById(R.id.create_evento_button_image);
        buttonCreateEvent = findViewById(R.id.create_evento_button_create);
        buttonCancel = findViewById(R.id.create_event_button_cancel);

        ArrayAdapter<CharSequence> adapterFormat = ArrayAdapter.createFromResource(this, R.array.create_event_format, R.layout.item_spinner);
        adapterFormat.setDropDownViewResource(R.layout.item_spinner);
        cmbFormat.setAdapter(adapterFormat);

        ArrayAdapter<CharSequence> adapterStatus = ArrayAdapter.createFromResource(this, R.array.create_event_status, R.layout.item_spinner);
        adapterStatus.setDropDownViewResource(R.layout.item_spinner);
        cmbStatus.setAdapter(adapterStatus);

        ArrayAdapter<CharSequence> adapterType = ArrayAdapter.createFromResource(this, R.array.create_event_type, R.layout.item_spinner);
        adapterType.setDropDownViewResource(R.layout.item_spinner);
        cmbType.setAdapter(adapterType);

        ArrayAdapter<CharSequence> adapterFaculty = ArrayAdapter.createFromResource(this, R.array.create_event_faculty, R.layout.item_spinner);
        adapterFaculty.setDropDownViewResource(R.layout.item_spinner);
        cmbFaculty.setAdapter(adapterFaculty);

        ArrayAdapter<CharSequence> adapterSchoolFaing = ArrayAdapter.createFromResource(this, R.array.create_event_faing_school, R.layout.item_spinner);
        adapterSchoolFaing.setDropDownViewResource(R.layout.item_spinner);

        ArrayAdapter<CharSequence> adapterSchoolFaedcoh = ArrayAdapter.createFromResource(this, R.array.create_event_faedcoh_school, R.layout.item_spinner);
        adapterSchoolFaing.setDropDownViewResource(R.layout.item_spinner);

        ArrayAdapter<CharSequence> adapterSchoolFade = ArrayAdapter.createFromResource(this, R.array.create_event_fade_school, R.layout.item_spinner);
        adapterSchoolFade.setDropDownViewResource(R.layout.item_spinner);

        ArrayAdapter<CharSequence> adapterSchoolFacsa = ArrayAdapter.createFromResource(this, R.array.create_event_facsa_school, R.layout.item_spinner);
        adapterSchoolFacsa.setDropDownViewResource(R.layout.item_spinner);

        ArrayAdapter<CharSequence> adapterSchoolFacem = ArrayAdapter.createFromResource(this, R.array.create_event_facem_school, R.layout.item_spinner);
        adapterSchoolFacem.setDropDownViewResource(R.layout.item_spinner);

        ArrayAdapter<CharSequence> adapterSchoolFau = ArrayAdapter.createFromResource(this, R.array.create_event_fau_school, R.layout.item_spinner);
        adapterSchoolFau.setDropDownViewResource(R.layout.item_spinner);

        cmbFaculty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (cmbFaculty.getSelectedItem().toString()){
                    case "FAING":
                        cmbSchools.setAdapter(adapterSchoolFaing);
                        break;

                    case "FAEDCOH":
                        cmbSchools.setAdapter(adapterSchoolFaedcoh);
                        break;

                    case "FADE":
                        cmbSchools.setAdapter(adapterSchoolFade);
                        break;

                    case "FACSA":
                        cmbSchools.setAdapter(adapterSchoolFacsa);
                        break;

                    case "FACEM":
                        cmbSchools.setAdapter(adapterSchoolFacem);
                        break;

                    case "FAU":
                        cmbSchools.setAdapter(adapterSchoolFau);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


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

        buttonCreateEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = editTitle.getText().toString();
                String description = editDescription.getText().toString();
                String date = editDate.getText().toString();
                String timestart = editTimeStart.getText().toString();
                String timeend = editTimeEnd.getText().toString();

                String formatCmb = cmbFormat.getSelectedItem().toString();
                int format = 0;
                if (formatCmb.equals("Virtual")) { format = 1; } else { format = 0; }

                String statusCmb = cmbStatus.getSelectedItem().toString();
                int status = 0;
                if (statusCmb.equals("Activo")) { status = 1; } else { status = 0; }

                String urladdress = editUrlAddress.getText().toString();

                String typeCmb = cmbType.getSelectedItem().toString();
                int type = 0;
                switch (typeCmb){
                    case "Académico":
                        type = 1;
                        break;

                    case "Atletismo":
                        type = 2;
                        break;

                    case "Ceremonia":
                        type = 3;
                        break;

                    case "Conferencia":
                        type = 4;
                        break;

                    case "Exposición":
                        type = 5;
                        break;

                    case "Sesion de informacion":
                        type = 6;
                        break;

                    case "Conferencias y seminarios":
                        type = 7;
                        break;

                    case "Reunión":
                        type = 8;
                        break;

                    case "Actuación":
                        type = 9;
                        break;

                    case "Evento especial":
                        type = 10;
                        break;

                    case "Actividad estudiantil":
                        type = 11;
                        break;

                    case "Taller":
                        type = 12;
                        break;
                }

                String schoolCmb = cmbSchools.getSelectedItem().toString();
                int school = 0;
                if (schoolCmb.equals("EPIS")) { school = 1; }

                String urlImage = editUrlImage.getText().toString();

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
                }else if (urladdress.isEmpty()){
                    Toast.makeText(CreateEventActivity.this, "Por favor ingrese una Direccion/URL", Toast.LENGTH_SHORT).show();
                }else if (school == 0){
                    Toast.makeText(CreateEventActivity.this, "Seleecione en escuela, EPIS", Toast.LENGTH_SHORT).show();
                }else if (urlImage.isEmpty()){
                    Toast.makeText(CreateEventActivity.this, "Por favor ingrese la URL de la imagen", Toast.LENGTH_SHORT).show();
                }else {
                    EventResponse eventResponse = new EventResponse();
                    eventResponse.setTypeId(type);
                    eventResponse.setSchoolId(school);
                    eventResponse.setTitle(title);
                    eventResponse.setDescription(description);
                    eventResponse.setEventDate(date);
                    eventResponse.setStartTime(timestart);
                    eventResponse.setEndTime(timeend);
                    eventResponse.setIsOutstanding(1);
                    eventResponse.setIsVirtual(format);
                    eventResponse.setIsOpen(1);

                    if (format == 1){
                        eventResponse.setEventLink(urladdress);
                    } else {
                        eventResponse.setLocation(urladdress);
                    }

                    eventResponse.setImage(urlImage);
                    eventResponse.setStatus(status);
                    sendPost(eventResponse);
                }
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDialogo();
            }
        });

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }


    private void mostrarDialogo() {
        new AlertDialog.Builder(this)
                .setTitle("Se perderan todos los cambios")
                .setMessage("¿Desea cancelar?")
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getApplicationContext(), MainActivityOrganizer.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("Mensaje","Se canceló la acción");
                    }
                })
                .show();
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
        String imageUrl = event.getImage();

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
                imageUrl,
                tokenString
        );
        call.enqueue(new Callback<EventResponse>() {
            @Override
            public void onResponse(Call<EventResponse> call, Response<EventResponse> response) {
                if(response.code() == 201){
                    Toast.makeText(CreateEventActivity.this, "Evento registrado", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), MainActivityOrganizer.class);
                    startActivity(intent);
                    finish();
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

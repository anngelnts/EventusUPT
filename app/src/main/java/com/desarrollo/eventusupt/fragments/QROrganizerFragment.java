package com.desarrollo.eventusupt.fragments;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.blikoon.qrcodescanner.QrCodeActivity;
import com.desarrollo.eventusupt.R;
import com.desarrollo.eventusupt.retrofit.RetrofitClient;
import com.desarrollo.eventusupt.retrofit.responses.ParticipantResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QROrganizerFragment  extends Fragment {

    private static final int REQUEST_CODE_QR_SCAN = 101;
    private final int MY_PERMISSIONS_REQUEST_CAMERA = 123;

    Button button_organizer_scan;

    public QROrganizerFragment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_qr_organizer, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        button_organizer_scan = view.findViewById(R.id.button_organizer_scan);

        if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT > 22) {
                if (shouldShowRequestPermissionRationale(android.Manifest.permission.CAMERA))
                    Toast.makeText(getContext(), "Esta aplicación necesita acceder a la cámara para funcionar", Toast.LENGTH_SHORT).show();
                requestPermissions(new String[]{android.Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
            }
        }

        button_organizer_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), QrCodeActivity.class);
                startActivityForResult(intent, REQUEST_CODE_QR_SCAN);
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,@Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_QR_SCAN && resultCode == Activity.RESULT_OK){
            String QRcode = data.getStringExtra("com.blikoon.qrcodescanner.got_qr_scan_relult");
            String[] QRparts = QRcode.split("==");
            if (QRparts.length > 1){
                String token = QRparts[0].trim();
                String idevento = QRparts[1].trim();
                checkParticipant(token, idevento);
            } else {
                Toast.makeText(getContext(), "No se pudo marcar la asistencia", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void checkParticipant(String token, String idevento) {
        Call<ParticipantResponse> call = RetrofitClient.getInstance().getApi().checkParticipant(token,Integer.parseInt(idevento));
        call.enqueue((new Callback<ParticipantResponse>() {
            @Override
            public void onResponse(Call<ParticipantResponse> call, Response<ParticipantResponse> response) {
                if(response.code() == 200) {
                    assert response.body() != null;
                    Toast.makeText(getContext(), "Se marco su asistencia correctamente", Toast.LENGTH_SHORT).show();
                }else if(response.code() == 401){
                    Toast.makeText(getContext(), "No se pudo marcar su asistencia", Toast.LENGTH_SHORT).show();
                }
                else if(response.code() == 404){
                    Toast.makeText(getContext(), "No se pudo marcar su asistencia", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ParticipantResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Error fatal", Toast.LENGTH_SHORT).show();
            }
        }));
    }

}

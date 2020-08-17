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

            public void onActivityResult(int requestCode, int resultCode, Intent data) {

                if (resultCode != Activity.RESULT_OK) {
                    Toast.makeText(getContext(), "No se pudo obtener una respuesta", Toast.LENGTH_SHORT).show();
                    String resultado = data.getStringExtra("com.blikoon.qrcodescanner.error_decoding_image");
                    if (resultado != null) {
                        Toast.makeText(getContext(), "No se pudo escanear el código QR", Toast.LENGTH_SHORT).show();
                    }
                    return;
                }
                if (requestCode == REQUEST_CODE_QR_SCAN) {
                    if (data != null) {
                        String lectura = data.getStringExtra("com.blikoon.qrcodescanner.got_qr_scan_relult");
                        Toast.makeText(getContext(), "Leído: " + lectura, Toast.LENGTH_SHORT).show();

                    }
                }
            }

        });

    }


}

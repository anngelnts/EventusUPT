package com.desarrollo.eventusupt.fragments;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.blikoon.qrcodescanner.QrCodeActivity;
import com.desarrollo.eventusupt.R;
import com.desarrollo.eventusupt.helpers.SaveSharedPreference;
import com.desarrollo.eventusupt.retrofit.RetrofitClient;
import com.desarrollo.eventusupt.retrofit.responses.FcmResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QRFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QRFragment extends Fragment {

    Button button_user_scan;
    private static final int REQUEST_CODE_QR_SCAN = 101;
    private final int MY_PERMISSIONS_REQUEST_CAMERA = 123;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public QRFragment() {
        // Required empty public constructor
    }

    public static QRFragment newInstance(String param1, String param2) {
        QRFragment fragment = new QRFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_qr, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        button_user_scan = view.findViewById(R.id.button_user_scan);

        if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT > 22) {
                if (shouldShowRequestPermissionRationale(android.Manifest.permission.CAMERA))
                    Toast.makeText(getContext(), "Esta aplicación necesita acceder a la cámara para funcionar", Toast.LENGTH_SHORT).show();
                requestPermissions(new String[]{android.Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
            }
        }

        button_user_scan.setOnClickListener(new View.OnClickListener() {
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
            String codeQR = data.getStringExtra("com.blikoon.qrcodescanner.got_qr_scan_relult");
            authenticationQR(codeQR);
            //Toast.makeText(getContext(), "Leído: " + codeQR, Toast.LENGTH_SHORT).show();
        } else {

        }

    }

    private void authenticationQR(String codeQR) {
        String token_user = SaveSharedPreference.getLoggedToken(getContext());
        Call<FcmResponse> call = RetrofitClient.getInstance().getApi().authQRUser(codeQR, token_user);
        call.enqueue(new Callback<FcmResponse>() {
            @Override
            public void onResponse(Call<FcmResponse> call, Response<FcmResponse> response) {
                if(response.code() == 200) {
                    assert response.body() != null;
                    Toast.makeText(getContext(), "Autenticacion correcta", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getContext(), "Usuario no autorizado", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<FcmResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Algo salio mal", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

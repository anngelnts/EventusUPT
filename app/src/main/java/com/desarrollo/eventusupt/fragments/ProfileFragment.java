package com.desarrollo.eventusupt.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.desarrollo.eventusupt.LoginActivity;
import com.desarrollo.eventusupt.MainActivity;
import com.desarrollo.eventusupt.ProfileEditActivity;
import com.desarrollo.eventusupt.R;
import com.desarrollo.eventusupt.helpers.SaveSharedPreference;
import com.desarrollo.eventusupt.retrofit.RetrofitClient;
import com.desarrollo.eventusupt.retrofit.responses.UserResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProfileFragment extends Fragment {

    TextView user_text_username;
    TextView user_text_email;
    TextView txt_user_name;
    TextView txt_user_lastname;
    TextView txt_user_phone;
    TextView txt_user_email;
    TextView txt_user_status;
    TextView user_textbutton_edit;
    Button user_button_signoff;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getProfile();
    }

    private void mostrarDialogo() {
        new AlertDialog.Builder(getContext())
                .setTitle("Cerrando sesión")
                .setMessage("¿Desea cerrar sesión?")
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SaveSharedPreference.setLoggedIn(getContext(), "", false, "");
                        Intent intent = new Intent(getContext(), LoginActivity.class);
                        startActivity(intent);
                        getActivity().finish();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //get xml
        user_text_username = view.findViewById(R.id.user_text_username);
        user_text_email = view.findViewById(R.id.user_text_email);
        txt_user_name = view.findViewById(R.id.txt_user_name);
        txt_user_lastname = view.findViewById(R.id.txt_user_lastname);
        txt_user_phone = view.findViewById(R.id.txt_user_phone);
        txt_user_email = view.findViewById(R.id.txt_user_email);
        txt_user_status = view.findViewById(R.id.txt_user_status);

        user_textbutton_edit = view.findViewById(R.id.user_textbutton_edit);
        user_button_signoff = view.findViewById(R.id.user_button_signoff);

        user_textbutton_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ProfileEditActivity.class);
                intent.putExtra("name", txt_user_name.getText());
                intent.putExtra("lastname", txt_user_lastname.getText());
                intent.putExtra("phone", txt_user_phone.getText());
                intent.putExtra("email", txt_user_email.getText());
                intent.putExtra("status", txt_user_status.getText());
                startActivity(intent);
            }
        });

        user_button_signoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDialogo();
            }
        });
    }

    public void OnEditProfile(){
        Intent intent = new Intent(getActivity(), ProfileEditActivity.class);
        startActivity(intent);
    }

    private void getProfile(){
        String token = SaveSharedPreference.getLoggedToken(getContext());
        Call<UserResponse> call = RetrofitClient.getInstance().getApi().getProfileUsers(token);
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if(response.code() == 200) {
                    assert response.body() != null;
                    setProfile(response.body());
                }else if(response.code() == 401){
                    Toast.makeText(getContext(), "Usuario no encontrado", Toast.LENGTH_SHORT).show();
                }
                else if(response.code() == 404){
                    Toast.makeText(getContext(), "Usuario no encontrado", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Toast.makeText(getContext(), "No se pudo obtener usuario", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setProfile(UserResponse user){
        String status = user.getStatus() == 1 ? "Activo" : "Inactivo";
        user_text_username.setText(user.getName());
        user_text_email.setText(user.getEmail());
        txt_user_name.setText(user.getName());
        txt_user_lastname.setText(user.getLastname());
        txt_user_phone.setText(user.getPhone());
        txt_user_email.setText(user.getEmail());
        txt_user_status.setText(status);
    }
}
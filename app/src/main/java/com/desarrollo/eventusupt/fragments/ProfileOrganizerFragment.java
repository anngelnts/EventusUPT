package com.desarrollo.eventusupt.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.desarrollo.eventusupt.LoginActivity;
import com.desarrollo.eventusupt.ProfileEditOrganizerActivity;
import com.desarrollo.eventusupt.R;
import com.desarrollo.eventusupt.helpers.SaveSharedPreference;
import com.desarrollo.eventusupt.retrofit.RetrofitClient;
import com.desarrollo.eventusupt.retrofit.responses.OrganizerResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileOrganizerFragment extends Fragment {

    TextView organizer_text_username;
    TextView organizer_text_email;
    TextView txt_organizer_name;
    TextView txt_organizer_acronym;
    TextView txt_organizer_phone;
    TextView txt_organizer_status;
    TextView txt_organizer_email;
    TextView txt_organizer_facebook;
    TextView txt_organizer_website;
    TextView organizer_textbutton_edit;
    Button organizer_button_signoff;

    public ProfileOrganizerFragment(){
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getProfile();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile_organizer, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //get xml
        organizer_text_username = view.findViewById(R.id.organizer_text_username);
        organizer_text_email = view.findViewById(R.id.organizer_text_email);
        txt_organizer_name = view.findViewById(R.id.txt_organizer_name);
        txt_organizer_acronym = view.findViewById(R.id.txt_organizer_acronym);
        txt_organizer_phone = view.findViewById(R.id.txt_organizer_phone);
        txt_organizer_status = view.findViewById(R.id.txt_organizer_status);
        txt_organizer_email = view.findViewById(R.id.txt_organizer_email);
        txt_organizer_facebook = view.findViewById(R.id.txt_organizer_facebook);
        txt_organizer_website = view.findViewById(R.id.txt_organizer_website);
        organizer_textbutton_edit = view.findViewById(R.id.organizer_textbutton_edit);
        organizer_button_signoff = view.findViewById(R.id.organizer_button_signoff);

        organizer_textbutton_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //edit perfil
                Intent intent = new Intent(getActivity(), ProfileEditOrganizerActivity.class);
                intent.putExtra("name", txt_organizer_name.getText());
                intent.putExtra("acronym", txt_organizer_acronym.getText());
                intent.putExtra("phone", txt_organizer_phone.getText());
                intent.putExtra("status", txt_organizer_status.getText());
                intent.putExtra("email", txt_organizer_email.getText());
                intent.putExtra("facebook", txt_organizer_facebook.getText());
                intent.putExtra("website", txt_organizer_website.getText());
                startActivity(intent);
            }
        });

        organizer_button_signoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //cerrar sesión
                SaveSharedPreference.setLoggedIn(getContext(), "", false, "");
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

    }

    private void getProfile(){
        String token = SaveSharedPreference.getLoggedToken(getContext());
        Call<OrganizerResponse> call = RetrofitClient.getInstance().getApi().getProfileOrganizer(token);
        call.enqueue(new Callback<OrganizerResponse>() {
            @Override
            public void onResponse(Call<OrganizerResponse> call, Response<OrganizerResponse> response) {
                if(response.code() == 200) {
                    assert response.body() != null;
                    setProfile(response.body());
                }else if(response.code() == 401){
                    Toast.makeText(getContext(), "Organizador no encontrado", Toast.LENGTH_SHORT).show();
                }
                else if(response.code() == 404){
                    Toast.makeText(getContext(), "Organizador no encontrado", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<OrganizerResponse> call, Throwable t) {
                Toast.makeText(getContext(), "No se pudo obtener organizador", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setProfile(OrganizerResponse organizer){
        String status = organizer.getStatus() == 1 ? "Activo" : "Inactivo";
        organizer_text_username.setText(organizer.getName());
        organizer_text_email.setText(organizer.getEmail());
        txt_organizer_name.setText(organizer.getName());
        txt_organizer_acronym.setText(organizer.getAcronym());
        txt_organizer_phone.setText(organizer.getPhone());
        txt_organizer_status.setText(status);
        txt_organizer_email.setText(organizer.getEmail());
        txt_organizer_facebook.setText(organizer.getFacebook());
        txt_organizer_website.setText(organizer.getWebsite());
    }

}

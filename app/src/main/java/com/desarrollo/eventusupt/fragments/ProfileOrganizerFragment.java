package com.desarrollo.eventusupt.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.desarrollo.eventusupt.ProfileEditOrganizerActivity;
import com.desarrollo.eventusupt.R;

public class ProfileOrganizerFragment extends Fragment {

    TextView textbuttonEdit;

    public ProfileOrganizerFragment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_organizer, container, false);

        textbuttonEdit = view.findViewById(R.id.organizer_textbutton_edit);

        textbuttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnEditProfile();
            }
        });
        return view;
    }

    public void OnEditProfile(){
        Intent intent = new Intent(getActivity(), ProfileEditOrganizerActivity.class);
        startActivity(intent);
    }

}

package com.desarrollo.eventusupt.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.desarrollo.eventusupt.R;
import com.desarrollo.eventusupt.adapters.EventAdapter;
import com.desarrollo.eventusupt.models.EventModel;

import java.util.ArrayList;


public class HomeFragment extends Fragment {

    private ArrayList<EventModel> listEvents;
    private RecyclerView recyclerView;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setEvents();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.events_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        EventAdapter eventAdapter = new EventAdapter(listEvents);
        recyclerView.setAdapter(eventAdapter);
    }

    private void setEvents(){
        listEvents = new ArrayList<>();
        listEvents.add(new EventModel(1, "Dirección de Educación Continua", "Charla virtual: Talleres de apoyo a tesis para optar al título profesional en Ingeniería Industrial 2020-1", R.drawable.eventimage, "Martes 16 de junio | 9:30 - 10:30 am" ));
        listEvents.add(new EventModel(2, "Dirección de Educación Continua", "Charla virtual: Talleres de apoyo a tesis para optar al título profesional en Ingeniería Industrial 2020-1", R.drawable.eventimage2, "Martes 16 de junio | 9:30 - 10:30 am" ));
        listEvents.add(new EventModel(3, "Dirección de Educación Continua", "Charla virtual: Talleres de apoyo a tesis para optar al título profesional en Ingeniería Industrial 2020-1", R.drawable.eventimage, "Martes 16 de junio | 9:30 - 10:30 am" ));
        listEvents.add(new EventModel(4, "Dirección de Educación Continua", "Charla virtual: Talleres de apoyo a tesis para optar al título profesional en Ingeniería Industrial 2020-1", R.drawable.eventimage2, "Martes 16 de junio | 9:30 - 10:30 am" ));
        listEvents.add(new EventModel(5, "Dirección de Educación Continua", "Charla virtual: Talleres de apoyo a tesis para optar al título profesional en Ingeniería Industrial 2020-1", R.drawable.eventimage, "Martes 16 de junio | 9:30 - 10:30 am" ));
        listEvents.add(new EventModel(6, "Dirección de Educación Continua", "Charla virtual: Talleres de apoyo a tesis para optar al título profesional en Ingeniería Industrial 2020-1", R.drawable.eventimage2, "Martes 16 de junio | 9:30 - 10:30 am" ));
        listEvents.add(new EventModel(7, "Dirección de Educación Continua", "Charla virtual: Talleres de apoyo a tesis para optar al título profesional en Ingeniería Industrial 2020-1", R.drawable.eventimage, "Martes 16 de junio | 9:30 - 10:30 am" ));
        listEvents.add(new EventModel(8, "Dirección de Educación Continua", "Charla virtual: Talleres de apoyo a tesis para optar al título profesional en Ingeniería Industrial 2020-1", R.drawable.eventimage2, "Martes 16 de junio | 9:30 - 10:30 am" ));
        listEvents.add(new EventModel(9, "Dirección de Educación Continua", "Charla virtual: Talleres de apoyo a tesis para optar al título profesional en Ingeniería Industrial 2020-1", R.drawable.eventimage, "Martes 16 de junio | 9:30 - 10:30 am" ));
        listEvents.add(new EventModel(10, "Dirección de Educación Continua", "Charla virtual: Talleres de apoyo a tesis para optar al título profesional en Ingeniería Industrial 2020-1", R.drawable.eventimage2, "Martes 16 de junio | 9:30 - 10:30 am" ));
        listEvents.add(new EventModel(11, "Dirección de Educación Continua", "Charla virtual: Talleres de apoyo a tesis para optar al título profesional en Ingeniería Industrial 2020-1", R.drawable.eventimage, "Martes 16 de junio | 9:30 - 10:30 am" ));
        listEvents.add(new EventModel(12, "Dirección de Educación Continua", "Charla virtual: Talleres de apoyo a tesis para optar al título profesional en Ingeniería Industrial 2020-1", R.drawable.eventimage2, "Martes 16 de junio | 9:30 - 10:30 am" ));
        listEvents.add(new EventModel(13, "Dirección de Educación Continua", "Charla virtual: Talleres de apoyo a tesis para optar al título profesional en Ingeniería Industrial 2020-1", R.drawable.eventimage, "Martes 16 de junio | 9:30 - 10:30 am" ));
        listEvents.add(new EventModel(14, "Dirección de Educación Continua", "Charla virtual: Talleres de apoyo a tesis para optar al título profesional en Ingeniería Industrial 2020-1", R.drawable.eventimage2, "Martes 16 de junio | 9:30 - 10:30 am" ));
        listEvents.add(new EventModel(15, "Dirección de Educación Continua", "Charla virtual: Talleres de apoyo a tesis para optar al título profesional en Ingeniería Industrial 2020-1", R.drawable.eventimage, "Martes 16 de junio | 9:30 - 10:30 am" ));
        listEvents.add(new EventModel(16, "Dirección de Educación Continua", "Charla virtual: Talleres de apoyo a tesis para optar al título profesional en Ingeniería Industrial 2020-1", R.drawable.eventimage2, "Martes 16 de junio | 9:30 - 10:30 am" ));
        listEvents.add(new EventModel(17, "Dirección de Educación Continua", "Charla virtual: Talleres de apoyo a tesis para optar al título profesional en Ingeniería Industrial 2020-1", R.drawable.eventimage, "Martes 16 de junio | 9:30 - 10:30 am" ));
        listEvents.add(new EventModel(18, "Dirección de Educación Continua", "Charla virtual: Talleres de apoyo a tesis para optar al título profesional en Ingeniería Industrial 2020-1", R.drawable.eventimage2, "Martes 16 de junio | 9:30 - 10:30 am" ));
        listEvents.add(new EventModel(19, "Dirección de Educación Continua", "Charla virtual: Talleres de apoyo a tesis para optar al título profesional en Ingeniería Industrial 2020-1", R.drawable.eventimage, "Martes 16 de junio | 9:30 - 10:30 am" ));
        listEvents.add(new EventModel(20, "Dirección de Educación Continua", "Charla virtual: Talleres de apoyo a tesis para optar al título profesional en Ingeniería Industrial 2020-1", R.drawable.eventimage2, "Martes 16 de junio | 9:30 - 10:30 am" ));
    }
}

package com.desarrollo.eventusupt.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.desarrollo.eventusupt.R;
import com.desarrollo.eventusupt.models.EventModel;

import java.util.ArrayList;

public class MyEventAdapter extends RecyclerView.Adapter<MyEventAdapter.ViewHolderMyEvents> {

    private ArrayList<EventModel> listEvents;

    public MyEventAdapter(ArrayList<EventModel> listEvents) {
        this.listEvents = listEvents;
    }

    @NonNull
    @Override
    public ViewHolderMyEvents onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_event, null, false);
        return new ViewHolderMyEvents(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderMyEvents holder, int position) {
        holder.assignData(listEvents.get(position));
    }

    @Override
    public int getItemCount() {
        return listEvents.size();
    }

    static class ViewHolderMyEvents extends RecyclerView.ViewHolder {

        ImageView image;
        TextView event_date;
        TextView event_title;

        ViewHolderMyEvents(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.my_event_image);
            event_date = itemView.findViewById(R.id.my_event_date);
            event_title = itemView.findViewById(R.id.my_event_title);
        }

        void assignData(EventModel eventModel) {
            image.setImageResource(eventModel.getImage());
            event_date.setText(eventModel.getDateString());
            event_title.setText(eventModel.getTitle());
        }
    }
}

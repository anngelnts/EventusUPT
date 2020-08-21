package com.desarrollo.eventusupt.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.desarrollo.eventusupt.R;
import com.desarrollo.eventusupt.models.EventModel;

import java.util.ArrayList;

public class EventOrganizerAdapter extends RecyclerView.Adapter<EventOrganizerAdapter.ViewHolderMyEvents>
        implements View.OnClickListener{

    private ArrayList<EventModel> listEvents;
    private View.OnClickListener listener;

    public EventOrganizerAdapter(ArrayList<EventModel> listEvents) {
        this.listEvents = listEvents;
    }

    @NonNull
    @Override
    public EventOrganizerAdapter.ViewHolderMyEvents onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event_organizer, null, false);

        view.setOnClickListener(this);

        return new EventOrganizerAdapter.ViewHolderMyEvents(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventOrganizerAdapter.ViewHolderMyEvents holder, int position) {
        holder.assignData(listEvents.get(position));
    }

    @Override
    public int getItemCount() {
        return listEvents.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener=listener;
    }

    @Override
    public void onClick(View view) {
        if (listener!=null){
            listener.onClick(view);
        }
    }

    static class ViewHolderMyEvents extends RecyclerView.ViewHolder {

        ImageView image;
        TextView event_date;
        TextView event_title;

        ViewHolderMyEvents(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.my_event_organizer_image);
            event_date = itemView.findViewById(R.id.my_event__organizer_date_time);
            event_title = itemView.findViewById(R.id.my_event_organizer_title);
        }

        void assignData(EventModel eventModel) {
            Glide.with(image.getContext()).load(eventModel.getImage()).into(image);
            event_date.setText(eventModel.getDateString());
            event_title.setText(eventModel.getTitle());
        }
    }
}

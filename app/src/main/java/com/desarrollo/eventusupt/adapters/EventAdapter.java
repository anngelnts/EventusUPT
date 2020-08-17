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

public class EventAdapter
        extends RecyclerView.Adapter<EventAdapter.ViewHolderEvents>
        implements View.OnClickListener{


    private ArrayList<EventModel> listEvents;
    private View.OnClickListener listener;

    public EventAdapter(ArrayList<EventModel> listEvents) {
        this.listEvents = listEvents;
    }

    @NonNull
    @Override
    public ViewHolderEvents onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event, null, false);

        view.setOnClickListener(this);

        return new ViewHolderEvents(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderEvents holder, int position) {
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

    static class ViewHolderEvents extends RecyclerView.ViewHolder {

        ImageView image;
        TextView event_provider;
        TextView event_title;
        TextView event_date;

        ViewHolderEvents(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.event_image);
            event_provider = itemView.findViewById(R.id.event_provider);
            event_title = itemView.findViewById(R.id.event_title);
            event_date = itemView.findViewById(R.id.event_date);
        }

        void assignData(EventModel event) {
            image.setImageResource(event.getImage());
            event_provider.setText(event.getProvider());
            event_title.setText(event.getTitle());
            event_date.setText(event.getDateString());
        }
    }
}

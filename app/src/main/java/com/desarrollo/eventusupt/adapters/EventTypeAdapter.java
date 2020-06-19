package com.desarrollo.eventusupt.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.desarrollo.eventusupt.R;
import com.desarrollo.eventusupt.models.EventTypeModel;

import java.util.ArrayList;

public class EventTypeAdapter extends RecyclerView.Adapter<EventTypeAdapter.ViewHolderEventTypes> {

    private ArrayList<EventTypeModel> listEventTypes;

    public EventTypeAdapter(ArrayList<EventTypeModel> listEventTypes) {
        this.listEventTypes = listEventTypes;
    }

    @NonNull
    @Override
    public ViewHolderEventTypes onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event_type, null, false);
        return new ViewHolderEventTypes(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderEventTypes holder, int position) {
        holder.assignData(listEventTypes.get(position));
    }

    @Override
    public int getItemCount() {
        return listEventTypes.size();
    }

    static class ViewHolderEventTypes extends RecyclerView.ViewHolder {

        TextView event_type_name;

        ViewHolderEventTypes(@NonNull View itemView) {
            super(itemView);
            event_type_name = itemView.findViewById(R.id.event_type_name);
        }

        void assignData(EventTypeModel eventTypeModel) {
            event_type_name.setText(eventTypeModel.getName());
        }
    }
}

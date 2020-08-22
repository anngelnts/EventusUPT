package com.desarrollo.eventusupt.adapters;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.desarrollo.eventusupt.R;
import com.desarrollo.eventusupt.models.ParticipantModel;

import java.util.ArrayList;

public class ParticipantsAdapter extends RecyclerView.Adapter<ParticipantsAdapter.ViewHolderEventTypes> {

    private ArrayList<ParticipantModel> listParticipants;

    public ParticipantsAdapter(ArrayList<ParticipantModel> listParticipants) {
        this.listParticipants = listParticipants;
    }

    @NonNull
    @Override
    public ViewHolderEventTypes onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event_participant, null, false);
        return new ViewHolderEventTypes(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderEventTypes holder, int position) {
        holder.assignData(listParticipants.get(position));
    }

    @Override
    public int getItemCount() {
        //return listParticipants.size();
        return (listParticipants == null) ? 0 : listParticipants.size();
    }

    static class ViewHolderEventTypes extends RecyclerView.ViewHolder {

        TextView txt_participant_name;
        TextView txt_participant_assistant;
        TextView txt_participant_assistant_time;

        ViewHolderEventTypes(@NonNull View itemView) {
            super(itemView);
            txt_participant_name = itemView.findViewById(R.id.txt_participant_name);
            txt_participant_assistant = itemView.findViewById(R.id.txt_participant_assistant);
            txt_participant_assistant_time = itemView.findViewById(R.id.txt_participant_assistant_time);
        }

        void assignData(ParticipantModel participantModel) {
            String assistance = participantModel.getAssistance()==1 ? "Asistió" : "No Asistió";
            txt_participant_name.setText(participantModel.getName());
            txt_participant_assistant.setText(assistance);
            txt_participant_assistant_time.setText(participantModel.getAssistanceTime());
        }
    }
}
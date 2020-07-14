package ru.gushchin.politexmark;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.SubjectViewHolder> {
    private List<Subject> subjectList;

    public SubjectAdapter(List<Subject> list) {
        this.subjectList = list;
    }

    @NonNull
    @Override
    public SubjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mark_item,parent,false);
        return new SubjectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubjectViewHolder holder, int position) {
        holder.bind(subjectList.get(position));
    }



    @Override
    public int getItemCount() {
        return subjectList.size();
    }


    static final class SubjectViewHolder extends RecyclerView.ViewHolder{

        private final TextView textViewAdapterNameSubject;
        private final TextView textViewAdapterFirstKnMark;
        private final TextView textViewAdapterFirstKnPass;
        private final TextView textViewAdapterSecondKnMark;
        private final TextView textViewAdapterSecondKnPass;
        private final TextView textViewAdapterFinalMark;

        public SubjectViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewAdapterNameSubject = itemView.findViewById(R.id.textViewAdapterNameSubject);
            textViewAdapterFirstKnMark = itemView.findViewById(R.id.textViewAdapterFirstKnMark);
            textViewAdapterFirstKnPass = itemView.findViewById(R.id.textViewAdapterFirstKnPass);
            textViewAdapterSecondKnMark = itemView.findViewById(R.id.textViewAdapterSecondKnMark);
            textViewAdapterSecondKnPass = itemView.findViewById(R.id.textViewAdapterSecondKnPass);
            textViewAdapterFinalMark = itemView.findViewById(R.id.textViewAdapterFinalMark);
        }

        private void bind(@NonNull Subject subject){

            textViewAdapterNameSubject.setText(subject.name);
            textViewAdapterFirstKnMark.setText(subject.first_knMark);
            textViewAdapterFirstKnPass.setText(subject.first_knpass);
            textViewAdapterSecondKnMark.setText(subject.second_knMark);
            textViewAdapterSecondKnPass.setText(subject.second_knpass);
            textViewAdapterFinalMark.setText(subject.mark);
        }

    }
}

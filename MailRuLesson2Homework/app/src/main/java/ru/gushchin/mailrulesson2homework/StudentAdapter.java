package ru.gushchin.mailrulesson2homework;

import android.net.sip.SipSession;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {

    private List<Student> students;

    private final Listener onStudentClickListener;

    public StudentAdapter(List<Student> students, Listener onStudentClickListener) {
        this.students = students;
        this.onStudentClickListener = onStudentClickListener;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_item, parent, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           // onStudentClickListener.onStudentClick((Student) v.getTag());
            onStudentClickListener.onStudentClick((Student) v.getTag());
            }
        });
        return new StudentViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        holder.bind(students.get(position));
        holder.itemView.setTag(students.get(position));
    }

    @Override
    public int getItemCount() {
        return students.size();
    }


    interface Listener {
        void onStudentClick(Student student);
    }

   static final class StudentViewHolder extends RecyclerView.ViewHolder {
        private final TextView descriptionTextView;
        private final TextView nameTextView;
        private final ImageView studentImage;

        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            descriptionTextView = itemView.findViewById(R.id.textView_student_description);
            nameTextView = itemView.findViewById(R.id.textView_student_name);
            studentImage = itemView.findViewById(R.id.imageView_student_photo);
        }

        private void bind(@NonNull Student student){
          descriptionTextView.setText(student.description);
          nameTextView.setText(student.name);
          studentImage.setImageResource(student.image);

        }

    }

}

package ru.gushchin.mailrulesson2homework;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Student> students;

    private ImageView studentImageView;

    private EditText editTextNameStudent;
    private EditText editTextDescription;

    private Button ButtonAddNewStudent;
    private Button ButtonDeleteStudent;
    private Button ButtonSaveNewStudent;

    private String nameEdit;
    private String descEdit;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.recycleView);
        students = generateStudents();

        studentImageView = (ImageView) findViewById(R.id.imageView_student_photo);
        editTextDescription = findViewById(R.id.editText_description);
        editTextNameStudent = findViewById(R.id.editText_name);

        final StudentAdapter studentAdapter = new StudentAdapter(students, new StudentAdapter.Listener() {
            @Override
            public void onStudentClick(Student student) {
                Toast.makeText(MainActivity.this, student.name , Toast.LENGTH_SHORT).show();
                editTextDescription.setText(student.description);
                editTextNameStudent.setText(student.name);
                studentImageView.setImageResource(student.image);
                nameEdit = student.name;
                descEdit = student.description;
            }
        });
        recyclerView.setAdapter(studentAdapter);
        LinearLayoutManager mlinearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mlinearLayoutManager);

        ButtonAddNewStudent = findViewById(R.id.button_add_new_student);
        ButtonDeleteStudent = findViewById(R.id.button_delete_student);
        ButtonSaveNewStudent = findViewById(R.id.button_save_student);

        ButtonAddNewStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name;
                String description;

                name = editTextNameStudent.getText().toString();
                description = editTextDescription.getText().toString();
                if (description.equals("Nice girl")) {
                    students.add(new Student("female", name, description, R.drawable.femalestudent));
                    studentImageView.setImageResource(R.drawable.femalestudent);
                } else {
                    students.add(new Student("male", name, description, R.drawable.malestudent));
                    studentImageView.setImageResource(R.drawable.malestudent);

                }
                studentAdapter.notifyDataSetChanged();
            }
        });

        ButtonSaveNewStudent.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                String name1;
                String description1;
                boolean flag = false;
                name1 = editTextNameStudent.getText().toString();
                description1 = editTextDescription.getText().toString();

                Iterator<Student> itr = students.iterator();
                String info = "";
                while (itr.hasNext()){
                    Student student = itr.next();
                    if (student.name.equals(nameEdit) && student.description.equals(descEdit)){
                       info = student.description;
                        itr.remove();
                       flag = true;
                    }
                }
                studentAdapter.notifyDataSetChanged();
                if (flag) {
                    if(info.equals("Nice girl")) {
                        students.add(new Student("male", editTextNameStudent.getText().toString(), editTextDescription.getText().toString(), R.drawable.femalestudent));
                    } else {
                        students.add(new Student("female", editTextNameStudent.getText().toString(), editTextDescription.getText().toString(), R.drawable.malestudent));
                    }
                    Toast.makeText(MainActivity.this, "Unfortianaly we coudnt find this guy", Toast.LENGTH_SHORT).show();
                }
                studentAdapter.notifyDataSetChanged();

            }
        });

        ButtonDeleteStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name;
                String description;
                name = editTextNameStudent.getText().toString();
                description = editTextDescription.getText().toString();
                Iterator<Student> itr = students.iterator();
                while (itr.hasNext()){
                    Student student = itr.next();
                    if (student.name.equals(name) && student.description.equals(description)){
                        itr.remove();
                    }
                }



                studentAdapter.notifyDataSetChanged();


            }
        });

    }

    public List<Student> generateStudents(){
        List<Student> students = new ArrayList<>();
        students.add(new Student("male", "Ilya", "Nice guy", R.drawable.malestudent) );
        students.add(new Student("female", "Maria", "Nice girl", R.drawable.femalestudent) );
        students.add(new Student("female", "Nina", "Nice girl", R.drawable.femalestudent) );
        students.add(new Student("male", "Pavel", "Nice guy", R.drawable.malestudent) );
        return  students;
    }

}

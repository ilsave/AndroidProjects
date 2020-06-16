package com.example.studystudy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class TeacherRegisterActivity extends AppCompatActivity {

    private FirebaseFirestore db;

    private TextView textViewIntroduction;
    private TextView textViewEnterPlease;
    private TextView textViewGenderUser;
    private TextView textViewDate;
    private TextView textViewContactPhone;
    private TextView textViewJobType;
    private TextView textViewKafedra;

    private EditText editTextName;
    private EditText editTextDateBirthday;
    private EditText editTextPhone;
    private EditText editTextJobType;
    private EditText editTextKafedra;
    private EditText editTextFacultaty;

    private RadioButton radioButton;

    public ArrayList<UserTeacher> teacherList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_register);
        db = FirebaseFirestore.getInstance();

        textViewIntroduction = findViewById(R.id.textViewIntroduction);
        textViewEnterPlease = findViewById(R.id.textViewEnterPlease);
        textViewGenderUser = findViewById(R.id.textViewGenderUser);
        textViewDate = findViewById(R.id.textViewDate);
        textViewContactPhone = findViewById(R.id.textViewContactPhone);
        textViewJobType = findViewById(R.id.textViewJobType);
        textViewKafedra = findViewById(R.id.textViewKafedra);

        editTextName = findViewById(R.id.editTextName);
        editTextDateBirthday = findViewById(R.id.editTextDateBirthday);
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextJobType = findViewById(R.id.editTextDirectionEducation);
        editTextKafedra = findViewById(R.id.editTextKafedra);
        editTextFacultaty = findViewById(R.id.editTextFacultaty);

        radioButton = findViewById(R.id.radioButtonMen);
    }

    public void onClickRegister(View view) {
        final String gender;
        int id = radioButton.getId();
        if (id == R.id.radioButtonMen){
           gender = "Мужской";
        }else{
            gender = "Женский";
        }

        Intent intent = getIntent();
        final String email = intent.getStringExtra("email");
        final String password = intent.getStringExtra("password");
        Toast.makeText(this, "email"+email + password, Toast.LENGTH_SHORT).show();
        String info = "info";

        //ArrayList<UserStudent> studentList = new ArrayList<>();
        //final ArrayList<UserTeacher> teacherList = new ArrayList<>();


        db.collection("teachers").add(new UserTeacher(email,password,editTextName.getText().toString(),gender,editTextPhone.getText().toString(), editTextDateBirthday.getText().toString(), editTextJobType.getText().toString(), editTextKafedra.getText().toString(), editTextFacultaty.getText().toString(), System.currentTimeMillis()))
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(TeacherRegisterActivity.this, "Вы зарегистрировались", Toast.LENGTH_SHORT).show();
                     //teacherList.add(new UserTeacher(email,password,editTextName.getText().toString(),gender,editTextPhone.getText().toString(), editTextDateBirthday.getText().toString(), editTextJobType.getText().toString(), editTextKafedra.getText().toString(), editTextFacultaty.getText().toString()));
                        Intent intent = new Intent(TeacherRegisterActivity.this, MainActivity.class);
                        //intent.putExtra("teacherList", teacherList);
                        //intent.putExtra("teacher","1");
                        startActivity(intent);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(TeacherRegisterActivity.this, "Вы не смогли зарегистрироваться", Toast.LENGTH_SHORT).show();
            }
        });

    }
}

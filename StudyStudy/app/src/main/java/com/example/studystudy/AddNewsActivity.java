package com.example.studystudy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddNewsActivity extends AppCompatActivity {

    private FirebaseFirestore db;

    private EditText editTextTitle;
    private EditText editTextDescription;
    private Spinner spinnerDaysOfWeek;
    private RadioGroup radioGroupPriority;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_news);
        editTextTitle = findViewById(R.id.editTextTitle);
        editTextDescription = findViewById(R.id.editTextDescription);
        spinnerDaysOfWeek = findViewById(R.id.spinner);
        radioGroupPriority = findViewById(R.id.RadioGroupPriority);
    }


    public void onClickSaveNews(View view) {
        String title = editTextTitle.getText().toString().trim();
        String description = editTextDescription.getText().toString().trim();
        int dayOfWeek = spinnerDaysOfWeek.getSelectedItemPosition();
        int radioButtonId = radioGroupPriority.getCheckedRadioButtonId();
        RadioButton radioButton = findViewById(radioButtonId);
        int priority = Integer.parseInt(radioButton.getText().toString());//получаем приоритет
        Log.i("added","added");
        Note note = new Note(title, description, dayOfWeek, priority);//id азначится автоматически
        //viewModel.insertNote(note);
        Intent intent = new Intent (this, MainActivity.class);
        startActivity(intent);
        db.collection("notes").add(new Note(title, description, dayOfWeek, priority ))
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.i("added","added");
                        Toast.makeText(AddNewsActivity.this, "Вы добавили запись", Toast.LENGTH_SHORT).show();
                        //Intent intent = new Intent(AddNewsActivity.this, MainActivity.class);
                        //intent.putExtra("teacher","0");
                        //startActivity(intent);
                        //studentList.add(new UserStudent(email, password, editTextName.getText().toString(),gender,editTextPhone.getText().toString(), editTextDateBirthday.getText().toString(), editTextDirectionEducation.getText().toString(), editTextKafedra.getText().toString(), editTextFacultaty.getText().toString(),info ));
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddNewsActivity.this, "Вы не смогли добавить", Toast.LENGTH_SHORT).show();
                Log.i("Noadded","NOadded");
            }
        });
    }
}

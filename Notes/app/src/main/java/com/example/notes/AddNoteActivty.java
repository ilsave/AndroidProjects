package com.example.notes;

import android.arch.lifecycle.ViewModelProviders;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

public class AddNoteActivty extends AppCompatActivity {
    private EditText editTextTitle;
    private EditText editTextDescription;
    private Spinner spinnerDaysOfWeek;
    private RadioGroup radioGroupPriority;

    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note_activty);
        editTextTitle = findViewById(R.id.editTextTitle);
        editTextDescription = findViewById(R.id.editTextDescription);
        spinnerDaysOfWeek = findViewById(R.id.spinner);
        radioGroupPriority = findViewById(R.id.RadioGroupPriority);
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
    }

    public void onClickSaveNote(View view) {
        String title = editTextTitle.getText().toString().trim();
        String description = editTextDescription.getText().toString().trim();
        int dayOfWeek = spinnerDaysOfWeek.getSelectedItemPosition();
        int radioButtonId = radioGroupPriority.getCheckedRadioButtonId();
        RadioButton radioButton = findViewById(radioButtonId);
        int priority = Integer.parseInt(radioButton.getText().toString());//получаем приоритет
        if (isFilled(title, description)){
            Note note = new Note(title, description, dayOfWeek, priority);//id азначится автоматически
            viewModel.insertNote(note);
            Intent intent = new Intent (this, MainActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, R.string.warning, Toast.LENGTH_SHORT).show();
        }

    }
    private boolean isFilled(String title, String description){//метод заполнены ли все поля?
        return !title.isEmpty() && !description.isEmpty();
    }
}

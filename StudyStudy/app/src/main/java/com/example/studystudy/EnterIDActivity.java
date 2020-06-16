package com.example.studystudy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EnterIDActivity extends AppCompatActivity {
    private EditText editTextIdUSer;
    private Button buttonEnterToFullRegistration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_id);
        editTextIdUSer = findViewById(R.id.editTextIdUSer);
        buttonEnterToFullRegistration = findViewById(R.id.buttonEnterToFullRegistration);
    }

    public void onClickToFullRegistration(View view) {
        Intent intent = getIntent();
        String email = intent.getStringExtra("email");
        String password = intent.getStringExtra("password");

        if (editTextIdUSer.getText().toString().equals("111")){
            Toast.makeText(this, "eto 111", Toast.LENGTH_SHORT).show();
            Intent intent1 = new Intent(this, TeacherRegisterActivity.class);
            intent1.putExtra("email", email);
            intent1.putExtra("password", password);
            startActivity(intent1);
        }else{
            if (editTextIdUSer.getText().toString().equals("222")){
                Toast.makeText(this, "eto ne 111", Toast.LENGTH_SHORT).show();
                Intent intent2 = new Intent(this, StudentRegisterActivity.class);
                intent2.putExtra("email", email);
                intent2.putExtra("password", password);
                startActivity(intent2);
            }
        }
    }
}

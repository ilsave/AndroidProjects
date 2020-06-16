package ru.gushchin.trytodrawgraph;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class LogoActivity extends AppCompatActivity {

    private Animation animLogo, animButtonLogo, animText;
    private Button buttonAnim;
    private ImageView imageViewLogo;
    private TextView textViewPower;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);
        init();
    }

    private void init(){

        animLogo = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.logo_anim);
        animButtonLogo = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.button_anim);
        animText = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.alpha_anim);

        imageViewLogo = findViewById(R.id.imageView_logo_london);
        buttonAnim = findViewById(R.id.button_anim);
        textViewPower = findViewById(R.id.textView2);

        textViewPower.startAnimation(animText);
        imageViewLogo.startAnimation(animLogo);
        buttonAnim.startAnimation(animButtonLogo);

    }

    public void onClickButton(View view) {
        Intent intent = new Intent(LogoActivity.this, InfoActivity.class);
        startActivity(intent);
    }
}
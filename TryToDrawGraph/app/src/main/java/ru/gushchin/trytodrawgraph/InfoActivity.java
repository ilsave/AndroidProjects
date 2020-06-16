package ru.gushchin.trytodrawgraph;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class InfoActivity extends AppCompatActivity {

    private Animation animationImage;
    private ImageView imageViewTrain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        init();
    }

    void init(){

        animationImage = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.train_anim);

        imageViewTrain = findViewById(R.id.imageViewTrain);

        imageViewTrain.startAnimation(animationImage);

    }

    public void onClickButtonReadInfo(View view) {
        Intent intent = new Intent(InfoActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
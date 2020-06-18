package ru.gushchin.politexmark;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;

import org.jsoup.select.Elements;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText editText_FirstStudentName;
    private EditText editText_SecondStudentName;
    private EditText editText_ThirdStudentName;
    private EditText editText_StudentId;
    private Spinner spinner_TypeEducation;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText_FirstStudentName = findViewById(R.id.editTextText_FirstStudentName);
        editText_SecondStudentName = findViewById(R.id.editTextText_SecondStudentName);
        editText_ThirdStudentName = findViewById(R.id.editTextText_ThirdStudentName);
        editText_StudentId = findViewById(R.id.editTextText_StudentId);
        spinner_TypeEducation = findViewById(R.id.spinnerTypeEducation);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

    }

    private void init(){
        final NetworkUtils networkUtils = new NetworkUtils(this);
        new Thread(new Runnable() {
            @Override
            public void run() {
               // networkUtils.getWeb(textViewKyrs);
            }
        }).start();
    }

    public void onButtonReadyClicked(View view) {
        final NetworkUtils networkUtils = new NetworkUtils(this);

        new PolitechQueryTask().execute();

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                String line;
//               line =  networkUtils.getWeb(editText_FirstStudentName.getText().toString(),
//                         editText_SecondStudentName.getText().toString(),
//                         editText_ThirdStudentName.getText().toString(),
//                         spinner_TypeEducation.getSelectedItem().toString(),
//                         editText_StudentId.getText().toString());
//                Intent intent = new Intent(MainActivity.this, MarkActivity.class);
//                intent.putExtra("line", line);
//                startActivity(intent);
//            }
//        }).start();
    }

    class PolitechQueryTask extends AsyncTask<Void, Void, Elements>{


        @Override
        protected Elements doInBackground(Void... params) {
            Elements response;
               response =  NetworkUtils.getWeb(editText_FirstStudentName.getText().toString(),
                         editText_SecondStudentName.getText().toString(),
                         editText_ThirdStudentName.getText().toString(),
                         spinner_TypeEducation.getSelectedItem().toString(),
                         editText_StudentId.getText().toString());
            return response;
        }

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(Elements response) {
            super.onPostExecute(response);
            List<String> list = ParseInfo.getBaseInfo(response);
            progressBar.setVisibility(View.INVISIBLE);
            Intent intent = new Intent(MainActivity.this, MarkActivity.class);
            intent.putExtra("line0", list.get(0));
            intent.putExtra("line1", list.get(1));
            intent.putExtra("line2", list.get(2));
            startActivity(intent);
        }
    }

}
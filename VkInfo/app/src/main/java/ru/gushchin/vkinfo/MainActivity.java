package ru.gushchin.vkinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

import static ru.gushchin.vkinfo.utils.NetworkUtils.generateURL;
import static ru.gushchin.vkinfo.utils.NetworkUtils.getResponseFromUrl;

public class MainActivity extends AppCompatActivity {

    private EditText searchField;
    private Button searchButton;
    private TextView textViewResult;
    private TextView textViewErrorMessage;
    private static ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        searchField = findViewById(R.id.et_search_field);
        searchButton = findViewById(R.id.b_search_vk);
        textViewResult = findViewById(R.id.tv_result);
        textViewErrorMessage = findViewById(R.id.tv_error_message);
        progressBar = findViewById(R.id.progress_bar);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                URL generateURL = generateURL(searchField.getText().toString());
                Log.d("myLog", generateURL.toString());
                new VKQueryTask().execute(generateURL);
            }
        };

        searchButton.setOnClickListener(onClickListener);

    }

    class VKQueryTask extends AsyncTask<URL, Void, String> {

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(URL... urls) {
            String response = null;

            try {
                response = getResponseFromUrl(urls[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String response) {
            String firstname = null;
            String lastname = null;
            if (response != null && !response.equals("")) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    JSONArray jsonArray = jsonResponse.getJSONArray("response");
                    StringBuilder resultString = new StringBuilder();
                    for (int i = 0; i < jsonArray.length(); i++){
                        JSONObject userInfo = jsonArray.getJSONObject(i);
                        firstname = userInfo.getString("first_name");
                        lastname = userInfo.getString("last_name");
                        resultString.append("Имя: ").append(firstname).append("\n").append("Фамилия: ").append(lastname).append("\n\n");
                    }
                    textViewResult.setText(resultString);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                showResultTextView();
            } else {
                showErrorTextView();
            }
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    private void showResultTextView() {

        textViewResult.setVisibility(View.VISIBLE);
        textViewErrorMessage.setVisibility(View.INVISIBLE);
    }

    private void showErrorTextView() {
        textViewResult.setVisibility(View.INVISIBLE);
        textViewErrorMessage.setVisibility(View.VISIBLE);
    }
}
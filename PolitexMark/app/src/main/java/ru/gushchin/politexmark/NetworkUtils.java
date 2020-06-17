package ru.gushchin.politexmark;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.lang.annotation.Documented;
import java.net.MalformedURLException;
import java.net.URL;

class NetworkUtils {

    Context context;

    public NetworkUtils(Context context) {
        this.context = context;
    }

    Document doc;

    private static final String MARK_BASE_URL = "https://www.nntu.ru/frontend/web/student_info.php?";
    //public static final String MARK_

    public static final String PARAM_USER_LASTNAME = "last_name";
    public static final String PARAM_USER_FIRTSNAME = "first_name";
    public static final String PARAM_USER_FATHERNAME = "otc";
    public static final String PARAM_USER_NUMBER= "n_zach";
    public static final String PARAM_USER_EDUSTYLE = "learn_type";

    public static URL generateURL (String firstname, String lastname, String fathername, String usernumber,String edustyle, String number ){
        Uri builtUri = Uri.parse(MARK_BASE_URL)
                .buildUpon()
                .appendQueryParameter(PARAM_USER_LASTNAME, "Гущин")
                .appendQueryParameter(PARAM_USER_FIRTSNAME,"Илья")
                .appendQueryParameter(PARAM_USER_FATHERNAME, "Вячеславович")
                .appendQueryParameter(PARAM_USER_EDUSTYLE,"bak_spec")
                .appendQueryParameter(PARAM_USER_NUMBER,"182979")
                .build();
        URL url =  null;

        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public void getWeb(final TextView textView){
        Document doc;
        try {
            doc = Jsoup.connect("https://www.nntu.ru/frontend/web/student_info.php?last_name=%D0%93%D1%83%D1%89%D0%B8%D0%BD&first_name=%D0%98%D0%BB%D1%8C%D1%8F&otc=%D0%92%D1%8F%D1%87%D0%B5%D1%81%D0%BB%D0%B0%D0%B2%D0%BE%D0%B2%D0%B8%D1%87&n_zach=182979&learn_type=bak_spec").get();
            Elements tables = doc.getElementsByTag("tbody");
            final Element facultet = tables.get(0);
            Elements facultetchildern = facultet.children();
            Element finalfuc = facultetchildern.get(0);

            Log.d("Mymarklog", "hey there "+facultet.text());
            Log.d("Mymarklog", "hey not there " + facultet.child(1).child(0).text());

            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    textView.setText(facultet.child(1).child(0).text());
                }
            });




        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

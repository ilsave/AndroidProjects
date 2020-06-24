package ru.gushchin.politexmark;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

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

    public static URL generateURL (String firstname, String lastname, String fathername,String edustyle, String number ){
        Uri builtUri = Uri.parse(MARK_BASE_URL)
                .buildUpon()
                .appendQueryParameter(PARAM_USER_LASTNAME, lastname)
                .appendQueryParameter(PARAM_USER_FIRTSNAME,firstname)
                .appendQueryParameter(PARAM_USER_FATHERNAME, fathername)
                .appendQueryParameter(PARAM_USER_EDUSTYLE,"bak_spec")
                .appendQueryParameter(PARAM_USER_NUMBER,number)
                .build();
        URL url =  null;

        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static Elements getWeb(String firstname, String lastname, String fathername, String edustyle, String number){
        String info="";
        Elements tables = null;
        URL url = generateURL(firstname, lastname, fathername, edustyle, number);
        Log.d("MyUrlLog", "hey not there "+ url);
        Document doc;
        try {
            doc = Jsoup.connect(url.toString()).get();
                tables = doc.getElementsByTag("tbody");
                final Element facultet = tables.get(0);
                Elements facultetchildern = facultet.children();
                Element finalfuc = facultetchildern.get(0);
                info = tables.text();
                Log.d("Mymarklog", "hey there " + info);
                Log.d("mymarklog", "hey not there " + tables.get(3).child(2).child(6).ownText());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return tables;
    }

}

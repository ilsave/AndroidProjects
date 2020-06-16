package ru.gushchin.vkinfo.utils;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Scanner;

public class NetworkUtils {

    private static final String VK_API_BASE_URL = "https://api.vk.com";
    private static final String VK_USER_GET = "/method/users.get";
    private static final String VK_KEY = "9715342a9715342a9715342a6497676536997159715342ac9c64b28ed4101a48a1cbb51";

    private static final String PARAM_USER_ID = "user_ids";
    private static final String PARAM_VERSION = "v";
    private static final String PARAM_KEY = "access_token";

    public static URL generateURL (String userIds){
        Uri builtUri = Uri.parse(VK_API_BASE_URL + VK_USER_GET)
                .buildUpon()
                .appendQueryParameter(PARAM_USER_ID, userIds)
                .appendQueryParameter(PARAM_KEY, "9715342a9715342a9715342a6497676536997159715342ac9c64b28ed4101a48a1cbb51")
                .appendQueryParameter(PARAM_VERSION,"5.107")
                .build();
        URL url =  null;

        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static String getResponseFromUrl(URL url) throws IOException{
        HttpURLConnection urlConnection = ( HttpURLConnection) url.openConnection(); //открываем соединение
        try {
            InputStream in = urlConnection.getInputStream(); //открываем поток для считывания данных

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");      // то что приходит мы делим посторочно а не по пробелам
            boolean hasInput = scanner.hasNext();

            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } catch (UnknownHostException e){
            e.printStackTrace();
            return null;
        }finally {
            urlConnection.disconnect();
        }

    }
}

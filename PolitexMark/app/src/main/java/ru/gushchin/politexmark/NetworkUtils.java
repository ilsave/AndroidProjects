package ru.gushchin.politexmark;

import android.net.Uri;

import java.net.URL;

class NetworkUtils {

    private static final String MARK_BASE_URL = "https://www.nntu.ru/frontend/web/student_info.php?";
    //public static final String MARK_

    public static final String PARAM_USER_LASTNAME = "last_name";
    public static final String PARAM_USER_FIRTSNAME = "first_name";
    public static final String PARAM_USER_FATHERNAME = "otc";
    public static final String PARAM_USER_NUMBER= "n_zach";
    public static final String PARAM_USER_EDUSTYLE = "bak_spec";

    public static URL generateURL (String firstname, String lastname, String fathername, String usernumber, ){
        Uri builtUri = Uri.parse(MARK_BASE_URL)
                .buildUpon()
                .appendQueryParameter(PARAM_USER_LASTNAME, "Гущин")
                .appendQueryParameter(PARAM_USER_FATHERNAME, "Вячеславович")
                .
    }


}

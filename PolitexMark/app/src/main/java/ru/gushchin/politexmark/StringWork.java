package ru.gushchin.politexmark;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class StringWork {

    static String check(String line){
        if (line.equals("")){
            return "~";
        }else {
            return line;
        }
    }

    static String changeName(String line){
        if (line.length() > 8 ){
         //   line = line.substring(0,8);
            String str = line;
            String[] splitStr = str.trim().split("\\s+");
            List<String> arrayList = new ArrayList<String>(Arrays.asList(splitStr));
            int listSize = arrayList.size();
            StringBuilder a = new StringBuilder();

            if (listSize == 2){
                for (String forline : arrayList){
                    if (!forline.equals("и")){
                        a.append(forline.substring(0,4)+". ");
                    }else {
                        a.append("и ");
                    }
                }
                return a.toString();
            }

            if (listSize == 3){
                for (String forline : arrayList){
                    if (!forline.equals("и")){
                        a.append(forline.substring(0,2)+". ");
                    }else {
                        a.append("и ");
                    }
                }
                return a.toString();
            }

            if (listSize == 5){
                for (String forline : arrayList){
                    if (!forline.equals("и")){
                        a.append(forline.substring(0,1)+". ");
                    }else {
                        a.append("и ");
                    }
                }
                return a.toString();
            }

            for (Iterator<String> iter = arrayList.listIterator(); iter.hasNext(); ) {
                String forline = iter.next();
                if (forline.equals("и")) {
                    iter.remove();
                }
            }



            for (String forline : arrayList){
                if (forline.length() > 4){
                    a.append(forline.substring(0,1)+". ");
                }

                Log.d("mylog",forline);
            }



            if (listSize == 1){
                return line.substring(0,8)+".";
            }

            return a.toString();


          //  return line;
        }else
            return line;
    }

}

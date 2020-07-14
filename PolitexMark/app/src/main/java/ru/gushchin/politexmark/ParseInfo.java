package ru.gushchin.politexmark;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import org.jsoup.internal.StringUtil;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

public class ParseInfo {
    static  StringBuilder stringBuilder;
    static List<String> getBaseInfo(Elements response){
        List<String> list = new LinkedList<>();
        final Element facultet = response.get(0);
        Elements facultetchildern = facultet.children();
        Element finalfuc = facultetchildern.get(0);
        list.add(facultet.child(1).child(1).text());
        list.add(facultet.child(2).child(1).text());
        list.add(facultet.child(3).child(1).text());

//        Log.d("Mymarklog", "hey there "+ info);
//        Log.d("Mymarklog", "hey not there " + facultet.child(1).child(1).text()); - shows irit
//        Log.d("iwillfindu", "" + facultet.text());
     return list;
    }

    static String getAllMarks(Elements response){
        List<Integer> marks = new LinkedList<>();
        StringBuilder answer = new StringBuilder();
        Element table;
        answer.append(" семестр     " + "      Ваша оценка          " +  " 1 Кн " + " 2 Кн ");
        for (int i = 2; i < response.size()-4; i++){
            answer.append("\n" + (i-1) + " семестр" + "\n");
            for (int j = 2; j < response.get(i).childrenSize(); j++){
                answer.append("     "+response.get(i).child(j).child(0).ownText().substring(0,6) + ".     ");
                answer.append(response.get(i).child(j).child(response.get(i).child(j).childrenSize()-2).ownText());
                if (StringUtil.isNumeric(response.get(i).child(j).child(response.get(i).child(j).childrenSize()-2).ownText())){
                    marks.add(Integer.parseInt(response.get(i).child(j).child(response.get(i).child(j).childrenSize()-2).ownText()));
                }
                if (response.get(i).child(j).child(response.get(i).child(j).childrenSize()-2).ownText().equals("")){
                    answer.append("Н/И");
                }
                if (response.get(i).child(j).child(1).ownText().equals("")) {
                    answer.append("               n/i");
                }else {
                    answer.append("               ").append(response.get(i).child(j).child(1).ownText());
                }
                if (response.get(i).child(j).child(3).ownText().equals("")) {
                    answer.append("               n/i");
                }else {
                    answer.append("               ").append(response.get(i).child(j).child(3).ownText());
                }

                answer.append("\n");
            }
        }
        answer.append("Мы тут подсчитали вашу среднюю оценку : ").append(calculateAverage(marks)).append("\n");
       return answer.toString();
    }


    static List<Subject> getSubjectList(Elements response, String kyrsNumber, Context context){
        stringBuilder = new StringBuilder();
        List<Subject> list = new ArrayList<>();
        int k = response.size()-(response.size()-2-2*Integer.parseInt(kyrsNumber));
        for (int i = 2; i < k; i++){
            list.add(new Subject(i-1+" семестр ","","","","",""));
            stringBuilder.append(i-1+" семестр \n");
            for (int j = 2; j < response.get(i).childrenSize(); j++) {
                stringBuilder.append(response.get(i).child(j).child(0).ownText()+ "\n");
                list.add(new Subject(
                        StringWork.changeName(response.get(i).child(j).child(0).ownText()),
                        StringWork.check(response.get(i).child(j).child(1).ownText()),
                        StringWork.check(response.get(i).child(j).child(2).ownText()),
                        StringWork.check(response.get(i).child(j).child(3).ownText()),
                        StringWork.check(response.get(i).child(j).child(4).ownText()),
                        StringWork.check(response.get(i).child(j).child(response.get(i).child(j).childrenSize()-2).ownText())
                ));
            }
            stringBuilder.append("\n");
            list.add(new Subject("","","","","",""));
        }
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().putString("allSubjects",stringBuilder.toString()).apply();
        return list;
    }

    static String getAverageMark(Elements response){
        String line;
        boolean flag;
        List<Integer> marks = new LinkedList<>();
        Pattern ocenka = Pattern.compile("\\w+()");
        for (int i = 2; i < response.size(); i++){
            for (int j = 2; j < response.get(i).childrenSize(); j++){
                if (StringUtil.isNumeric(response.get(i).child(j).child(response.get(i).child(j).childrenSize()-2).ownText())){
                    marks.add(Integer.parseInt(response.get(i).child(j).child(response.get(i).child(j).childrenSize()-2).ownText()));
                }else{
                    line = response.get(i).child(j).child(response.get(i).child(j).childrenSize()-2).ownText();
                    flag = line.contains("(3)");
                    if (line.contains("(3)") || (line.contains("(2)")) || (line.contains("(4)")) || (line.contains("(5)"))) {
                        marks.add(Integer.parseInt(line.substring(line.length()-2,line.length()-1)));
                    }
                }
            }
        }
        int b =0;
        for (int a : marks){

            if (a == 5){
                b++;
            }
        }

          double mark = round(calculateAverage(marks), 2);
        Log.d("b = ",b+"");
        return  String.valueOf(mark);
    }

    private static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

     static double calculateAverage(List<Integer> marks) {
        Integer sum = 0;
        if(!marks.isEmpty()) {
            for (Integer mark : marks) {
                sum += mark;
            }
            return sum.doubleValue() / marks.size();
        }
        return sum;
    }
}

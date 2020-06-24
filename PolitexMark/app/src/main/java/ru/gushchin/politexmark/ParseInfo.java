package ru.gushchin.politexmark;

import org.jsoup.internal.StringUtil;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.LinkedList;
import java.util.List;

public class ParseInfo {
    static List<String> getBaseInfo(Elements response){
        List<String> list = new LinkedList<>();
        final Element facultet = response.get(0);
        Elements facultetchildern = facultet.children();
        Element finalfuc = facultetchildern.get(0);
        list.add(facultet.child(1).child(1).text());
        list.add(facultet.child(2).child(1).text());
        list.add(facultet.child(3).child(1).text());
        //list.add(response.);

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
        for (int i = 2; i < response.size(); i++){
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
                answer.append("\n");
            }
        }
        answer.append("Мы тут подсчитали вашу среднюю оценку : " + calculateAverage(marks) + "\n");
       return answer.toString();
    }

    static String getAverageMark(Elements response){

        List<Integer> marks = new LinkedList<>();

        for (int i = 2; i < response.size(); i++){
            for (int j = 2; j < response.get(i).childrenSize(); j++){
                if (StringUtil.isNumeric(response.get(i).child(j).child(response.get(i).child(j).childrenSize()-2).ownText())){
                    marks.add(Integer.parseInt(response.get(i).child(j).child(response.get(i).child(j).childrenSize()-2).ownText()));
                }
            }
        }
          double mark =  calculateAverage(marks);
        return  String.valueOf(mark);
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

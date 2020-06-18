package ru.gushchin.politexmark;

import android.util.Log;

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

//        Log.d("Mymarklog", "hey there "+ info);
//        Log.d("Mymarklog", "hey not there " + facultet.child(1).child(1).text()); - shows irit
//        Log.d("iwillfindu", "" + facultet.text());
     return list;
    }
}

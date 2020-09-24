package ru.gushchin.politexmark.other;

public class Subject {
    String name;
    String first_knMark;
    String first_knpass;
    String second_knMark;
    String second_knpass;
    String mark;

    public Subject(String name, String first_knMark, String first_knpass, String second_knMark, String second_knpass, String mark) {
        this.name = name;
        this.first_knMark = first_knMark;
        this.first_knpass = first_knpass;
        this.second_knMark = second_knMark;
        this.second_knpass = second_knpass;
        this.mark = mark;
    }
}

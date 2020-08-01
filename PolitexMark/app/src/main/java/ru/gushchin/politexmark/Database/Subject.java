package ru.gushchin.politexmark.Database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(tableName = "subject_table")
public class Subject {

    @Override
    public String toString() {
        return "Subject{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", first_knMark='" + first_knMark + '\'' +
                ", first_knpass='" + first_knpass + '\'' +
                ", second_knMark='" + second_knMark + '\'' +
                ", second_knpass='" + second_knpass + '\'' +
                ", mark='" + mark + '\'' +
                "} \n";
    }

    public Subject(String name, String first_knMark, String first_knpass, String second_knMark, String second_knpass, String mark) {
        this.name = name;
        this.first_knMark = first_knMark;
        this.first_knpass = first_knpass;
        this.second_knMark = second_knMark;
        this.second_knpass = second_knpass;
        this.mark = mark;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getFirst_knMark() {
        return first_knMark;
    }

    public String getFirst_knpass() {
        return first_knpass;
    }

    public String getSecond_knMark() {
        return second_knMark;
    }

    public String getSecond_knpass() {
        return second_knpass;
    }

    public String getMark() {
        return mark;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFirst_knMark(String first_knMark) {
        this.first_knMark = first_knMark;
    }

    public void setFirst_knpass(String first_knpass) {
        this.first_knpass = first_knpass;
    }

    public void setSecond_knMark(String second_knMark) {
        this.second_knMark = second_knMark;
    }

    public void setSecond_knpass(String second_knpass) {
        this.second_knpass = second_knpass;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "subject_id")
    private int id;

    @ColumnInfo(name = "subject_name")
    private String name;

    @ColumnInfo(name = "first_knMark")
    private String first_knMark;

    @ColumnInfo(name = "first_knpass")
    private String first_knpass;

    @ColumnInfo(name = "second_knMark")
    private String second_knMark;

    @ColumnInfo(name = "second_knpass")
    private String second_knpass;

    @ColumnInfo(name = "mark")
    private String mark;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Subject)) return false;
        Subject subject = (Subject) o;
        return
                this.getName().equals(subject.getName()) &&
                this.getFirst_knMark().equals(subject.getFirst_knMark()) &&
                this.getFirst_knpass().equals(subject.getFirst_knpass()) &&
                this.getSecond_knMark().equals(subject.getSecond_knMark()) &&
                this.getSecond_knpass().equals(subject.getSecond_knpass()) &&
                this.getMark().equals(subject.getMark());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getFirst_knMark(), getFirst_knpass(), getSecond_knMark(), getSecond_knpass(), getMark());
    }
}

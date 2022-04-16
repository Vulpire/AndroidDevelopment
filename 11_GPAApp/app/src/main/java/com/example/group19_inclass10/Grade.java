package com.example.group19_inclass10;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "grades")
public class Grade {
    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo
    public int classAbv;

    @ColumnInfo
    public String className;

    @ColumnInfo
    public char grade;

    @ColumnInfo
    public int creditHours;

    public Grade() {
    }

    public Grade(String className, char grade) {
        this.className = className;
        this.grade = grade;
    }

    public Grade(int classAbv, String className, char grade, int creditHours) {
        this.classAbv = classAbv;
        this.className = className;
        this.grade = grade;
        this.creditHours = creditHours;
    }

    public Grade(long id, String className, char grade) {
        this.id = id;
        this.className = className;
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "Grade{" +
                "id=" + id +
                ", className='" + className + '\'' +
                ", grade=" + grade +
                '}';
    }
}



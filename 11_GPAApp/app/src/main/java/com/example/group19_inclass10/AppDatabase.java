package com.example.group19_inclass10;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Grade.class} ,version = 4)
public abstract class AppDatabase extends RoomDatabase {
    public abstract GradeDao gradeDao();
}

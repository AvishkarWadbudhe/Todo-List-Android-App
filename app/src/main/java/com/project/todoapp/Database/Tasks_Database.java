package com.project.todoapp.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.project.todoapp.Model.DataModel;
import com.project.todoapp.Model.Task_Dao;

@Database(entities = DataModel.class,version = 2)
public abstract  class Tasks_Database extends RoomDatabase {


    private static Tasks_Database instance;
    public abstract Task_Dao task_dao();
    public static synchronized Tasks_Database getInstance(Context context)
    {
        if(instance==null)
        {
            instance = Room.databaseBuilder(context.getApplicationContext()
            ,Tasks_Database.class,"Todo_Database").fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

}

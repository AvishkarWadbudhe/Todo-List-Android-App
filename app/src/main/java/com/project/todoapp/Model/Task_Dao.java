package com.project.todoapp.Model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;


@Dao
public interface Task_Dao {

    @Insert
    public void insertTask(DataModel data);

    @Update
    public void updateTask(DataModel data);

    @Delete
    public void deleteTask(DataModel data);

    @Query("SELECT * FROM my_tasks")
    public LiveData<List<DataModel>> showTask();

    @Query("SELECT * FROM my_tasks")
    public LiveData<List<DataModel>> showCompletedTask();


}

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
    void insertTask(DataModel data);

    @Update
    void updateTask(DataModel data);

    @Delete
    void deleteTask(DataModel data);

    @Query("SELECT * FROM my_tasks WHERE isCompleted=FALSE")
    LiveData<List<DataModel>> showTask();

    @Query("SELECT * FROM my_tasks WHERE isCompleted=TRUE")
    LiveData<List<DataModel>> showCompletedTask();


    @Query("DELETE FROM my_tasks WHERE isCompleted = TRUE")
    void deleteCompletedTasks();



}

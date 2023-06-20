package com.project.todoapp.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.project.todoapp.Model.DataModel;
import com.project.todoapp.Model.Task_Repo;

import java.util.List;

public class Task_viewModel extends AndroidViewModel {
    private final Task_Repo taskRepo;
    private final LiveData<List<DataModel>> taskList;
    private final LiveData<List<DataModel>> completedTaskList;

    public Task_viewModel(@NonNull Application application) {
        super(application);
        taskRepo = new Task_Repo(application);
        taskList = taskRepo.showTask();
        completedTaskList = taskRepo.showCompletedTask();
    }
    public void insert(DataModel dataModel){
        taskRepo.insertData(dataModel);
    }
    public void update(DataModel dataModel){
        dataModel.setCompleted(true); // Update the isCompleted value

        taskRepo.updateData(dataModel);
    }
    public void delete(DataModel dataModel){
        taskRepo.deleteData(dataModel);
    }
    public LiveData<List<DataModel>> getTaskList()
    {
        return taskList;
    }
    public LiveData<List<DataModel>> getCompletedTaskList()
    {
        return completedTaskList;
    }


}

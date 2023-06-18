package com.project.todoapp.Model;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.project.todoapp.Database.Tasks_Database;

import java.util.List;

public class Task_Repo {
    private Task_Dao task_dao;
    private LiveData<List<DataModel>> taskList;
    private LiveData<List<DataModel>> completedTask;

    public Task_Repo(Application application) {
        Tasks_Database tasks_database=Tasks_Database.getInstance(application);
        task_dao = tasks_database.task_dao();
        taskList = task_dao.showTask();
        completedTask =task_dao.showCompletedTask();

    }
public void insertData(DataModel dataModel){new InsertTask(task_dao).execute(dataModel);}
    public void updateData(DataModel dataModel){new UpdateTask(task_dao).execute(dataModel);}
    public void deleteData(DataModel dataModel){new DeleteTask(task_dao).execute(dataModel);}

    public LiveData<List<DataModel>> showTask()
    {
        return taskList;
    }
    public LiveData<List<DataModel>> showCompletedTask()
    {
        return completedTask;
    }

    private static class InsertTask extends AsyncTask<DataModel,Void,Void>{

        public InsertTask(Task_Dao task_dao) {
            this.task_dao = task_dao;
        }

        private Task_Dao task_dao;
        @Override
        protected Void doInBackground(DataModel... dataModels) {
            task_dao.insertTask(dataModels[0]);
            return null;
        }
    }
    private static class UpdateTask extends AsyncTask<DataModel,Void,Void>{

        public UpdateTask(Task_Dao task_dao) {
            this.task_dao = task_dao;
        }

        private Task_Dao task_dao;
        @Override
        protected Void doInBackground(DataModel... dataModels) {
            task_dao.updateTask(dataModels[0]);
            return null;
        }
    }
    private static class DeleteTask extends AsyncTask<DataModel,Void,Void>{

        public DeleteTask(Task_Dao task_dao) {
            this.task_dao = task_dao;
        }

        private Task_Dao task_dao;
        @Override
        protected Void doInBackground(DataModel... dataModels) {
            task_dao.deleteTask(dataModels[0]);
            return null;
        }
    }
}


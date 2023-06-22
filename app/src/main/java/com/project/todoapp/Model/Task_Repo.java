package com.project.todoapp.Model;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.project.todoapp.Database.Tasks_Database;

import java.util.List;

public class Task_Repo {
    private final Task_Dao task_dao;
    private final LiveData<List<DataModel>> taskList;
    private final LiveData<List<DataModel>> completedTask;

    public Task_Repo(Application application) {
        Tasks_Database tasks_database=Tasks_Database.getInstance(application);
        task_dao = tasks_database.task_dao();
        taskList = task_dao.showTask();
        completedTask =task_dao.showCompletedTask();

    }
public void insertData(DataModel dataModel){new InsertTask(task_dao).execute(dataModel);}
    public void updateData(DataModel dataModel){new UpdateTask(task_dao).execute(dataModel);}
    public void deleteData(DataModel dataModel){new DeleteTask(task_dao).execute(dataModel);}

    public void deleteCompletedTasks() {
        new DeleteCompletedTasksTask(task_dao).execute();
    }



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

        private final Task_Dao task_dao;
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

        private final Task_Dao task_dao;
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

        private final Task_Dao task_dao;
        @Override
        protected Void doInBackground(DataModel... dataModels) {
            task_dao.deleteTask(dataModels[0]);
            return null;
        }
        public void deleteCompletedTasks() {
            task_dao.deleteCompletedTasks();

        }
    }
    private static class DeleteCompletedTasksTask extends AsyncTask<Void, Void, Void> {

        private final Task_Dao task_dao;

        public DeleteCompletedTasksTask(Task_Dao task_dao) {
            this.task_dao = task_dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            task_dao.deleteCompletedTasks();
            return null;
        }
    }
}


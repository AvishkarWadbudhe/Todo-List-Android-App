package com.project.todoapp.Model;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "my_tasks")
public class DataModel {

    public void setId(int id) {
        this.id = id;
    }

    @PrimaryKey(autoGenerate = true)
    private int id;

        private String title;
        private String description;
        private Date dueDate;
        private Date dueTime;
        private boolean isCompleted;

        public DataModel(String title, String description, Date dueDate, Date dueTime, boolean isCompleted) {

            this.title = title;
            this.description = description;
            this.dueDate = dueDate;
            this.dueTime = dueTime;
            this.isCompleted = isCompleted;
        }



        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }

        public Date getDueDate() {
            return dueDate;
        }

        public boolean isCompleted() {
            return isCompleted;
        }

    public Date getDueTime() {
        return dueTime;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public void setDueTime(Date dueTime) {
        this.dueTime = dueTime;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
}


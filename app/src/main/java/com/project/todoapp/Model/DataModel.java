package com.project.todoapp.Model;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "my_tasks")
public class DataModel {

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @PrimaryKey(autoGenerate = true)
    private int id;

        private String title;
        private String description;
        private String Date;
        private String Time;
        private boolean isCompleted;

        public DataModel(String title, String description, String Date, String Time, boolean isCompleted) {

            this.title = title;
            this.description = description;
            this.Date = Date;
            this.Time = Time;
            this.isCompleted = isCompleted;
        }



        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }

        public String getDate() {
            return Date;
        }

        public boolean isCompleted() {
            return isCompleted;
        }

    public String getTime() {
        return Time;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDueDate(String Date) {
        this.Date = Date;
    }

    public void setTime(String Time) {
        this.Time = Time;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
}


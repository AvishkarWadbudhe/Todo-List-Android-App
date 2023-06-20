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

        private boolean isCompleted;

        public DataModel(String title, String description, boolean isCompleted) {

            this.title = title;
            this.description = description;

            this.isCompleted = isCompleted;
        }

        public String getTitle() {
            return title;
        }
        public String getDescription() {
            return description;
        }
        public boolean isCompleted() {
            return isCompleted;
        }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
}


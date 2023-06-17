package com.project.todoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.project.todoapp.TaskActivity.CreateTasks;
import com.project.todoapp.TaskActivity.Task_History;
import com.project.todoapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setListener();

    }
    private void setListener()
    {
        binding.newTask.setOnClickListener(v ->
                startActivity(new Intent(getApplicationContext(), CreateTasks.class)));

        //history
        binding.btnHistory.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), Task_History.class)));
    }

}
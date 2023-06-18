package com.project.todoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.project.todoapp.Adapter.Tasks_Adapter;
import com.project.todoapp.Model.DataModel;
import com.project.todoapp.TaskActivity.CreateTasks;
import com.project.todoapp.TaskActivity.Task_History;
import com.project.todoapp.ViewModel.Task_viewModel;
import com.project.todoapp.databinding.ActivityMainBinding;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    private Task_viewModel taskViewModel;
    Tasks_Adapter tasksAdapter = new Tasks_Adapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        taskViewModel = new ViewModelProvider((ViewModelStoreOwner) this, (ViewModelProvider.Factory) ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication()))
                .get(Task_viewModel.class);

        setListener();
        setRecycleView();

    }
    private void setListener()
    {
        binding.newTask.setOnClickListener(v ->
                startActivity(new Intent(getApplicationContext(), CreateTasks.class)));

        //history
        binding.btnHistory.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), Task_History.class)));





    }
    private void setRecycleView()
    {

        binding.taskRecycler.setLayoutManager(new LinearLayoutManager(this));
        binding.taskRecycler.setHasFixedSize(true);
        binding.taskRecycler.setAdapter(tasksAdapter);



        taskViewModel.getTaskList().observe(this, new Observer<List<DataModel>>() {
            @Override
            public void onChanged(List<DataModel> dataModels) {
                tasksAdapter.submitList(dataModels);
            }
        });
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Is Task Completed?")
                        .setMessage("Did you completed the task?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                taskViewModel.update(tasksAdapter.getTask(viewHolder.getAbsoluteAdapterPosition()));


                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                tasksAdapter.notifyItemChanged(viewHolder.getAbsoluteAdapterPosition());
                            }
                        })
                        .show();
            }
        }).attachToRecyclerView(binding.taskRecycler);
    }

}
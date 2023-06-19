package com.project.todoapp.TaskActivity;

import android.app.AlertDialog;
import android.graphics.Canvas;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.project.todoapp.Adapter.Task_History_Adapter;
import com.project.todoapp.R;
import com.project.todoapp.ViewModel.Task_viewModel;
import com.project.todoapp.databinding.ActivityTaskHistoryBinding;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class Task_History extends AppCompatActivity {
    ActivityTaskHistoryBinding binding;
    private Task_viewModel taskViewModel;
    Task_History_Adapter tasksAdapter = new Task_History_Adapter();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTaskHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        taskViewModel = new ViewModelProvider(this, (ViewModelProvider.Factory) ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication()))
                .get(Task_viewModel.class);

        setListener();
        setRecycleView();

    }
    private void setListener() {
        binding.btnBack.setOnClickListener(v -> onBackPressed());

    }
    private void setRecycleView()
    {

        binding.taskRecycler.setLayoutManager(new LinearLayoutManager(this));
        binding.taskRecycler.setHasFixedSize(true);
        binding.taskRecycler.setAdapter(tasksAdapter);



        taskViewModel.getCompletedTaskList().observe(this, dataModels -> tasksAdapter.submitList(dataModels));
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Task_History.this);
                builder.setTitle("Confirmation")
                        .setMessage("Are you sure you want to delete this task?")
                        .setPositiveButton("Delete", (dialog, which) -> {

                            taskViewModel.delete(tasksAdapter.getTask(viewHolder.getAbsoluteAdapterPosition()));
                            showToast("Task Deleted");
                        })
                        .setNegativeButton("Cancel", (dialog, which) -> tasksAdapter.notifyItemChanged(viewHolder.getAbsoluteAdapterPosition()))
                        .show();

            }
            public void onChildDraw (Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive){

                new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                        .addSwipeRightBackgroundColor(ContextCompat.getColor(Task_History.this,R.color.colorRed))
                        .addSwipeRightActionIcon(R.drawable.ic_delete)
                        .addSwipeRightLabel("Delete").setSwipeRightLabelColor(ContextCompat.getColor(Task_History.this,R.color.white))
                        .addSwipeLeftBackgroundColor(ContextCompat.getColor(Task_History.this,R.color.colorRed))
                        .addSwipeLeftActionIcon(R.drawable.ic_delete)
                        .addSwipeLeftLabel("Delete").setSwipeLeftLabelColor(ContextCompat.getColor(Task_History.this,R.color.white))
                        .create()
                        .decorate();

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }


        }).attachToRecyclerView(binding.taskRecycler);


    }


    private void showToast(String message){
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();

    }
}
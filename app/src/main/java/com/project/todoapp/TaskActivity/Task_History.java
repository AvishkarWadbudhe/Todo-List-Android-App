package com.project.todoapp.TaskActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.project.todoapp.Adapter.Task_History_Adapter;
import com.project.todoapp.Adapter.Tasks_Adapter;
import com.project.todoapp.Model.DataModel;
import com.project.todoapp.ViewModel.Task_viewModel;
import com.project.todoapp.databinding.ActivityTaskHistoryBinding;

import java.util.List;

public class Task_History extends AppCompatActivity {
    ActivityTaskHistoryBinding binding;
    private Task_viewModel taskViewModel;
    Task_History_Adapter tasksAdapter = new Task_History_Adapter();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTaskHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        taskViewModel = new ViewModelProvider((ViewModelStoreOwner) this, (ViewModelProvider.Factory) ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication()))
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



        taskViewModel.getCompletedTaskList().observe(this, new Observer<List<DataModel>>() {
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
                AlertDialog.Builder builder = new AlertDialog.Builder(Task_History.this);
                builder.setTitle("Confirmation")
                        .setMessage("Are you sure you want to delete this task?")
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                taskViewModel.delete(tasksAdapter.getTask(viewHolder.getAbsoluteAdapterPosition()));
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                tasksAdapter.notifyItemChanged(viewHolder.getAbsoluteAdapterPosition());
                            }
                        })
                        .show();
            }
        }).attachToRecyclerView(binding.taskRecycler);
    }


    private void showToast(String message){
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();

    }
}
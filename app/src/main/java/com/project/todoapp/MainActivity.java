package com.project.todoapp;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.project.todoapp.Adapter.Tasks_Adapter;
import com.project.todoapp.CustomTools.Custom_SnackBar;
import com.project.todoapp.Model.DataModel;
import com.project.todoapp.TaskActivity.Task_History;
import com.project.todoapp.ViewModel.Task_viewModel;
import com.project.todoapp.databinding.ActivityMainBinding;
import com.project.todoapp.databinding.CreateTaskDialogBoxBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    private Task_viewModel taskViewModel;
    Tasks_Adapter tasksAdapter = new Tasks_Adapter();

    private DatePickerDialog datePickerDialog;

    private AlertDialog alertDialog;
    private CreateTaskDialogBoxBinding dialogBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());




        taskViewModel = new ViewModelProvider(this, (ViewModelProvider.Factory) ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication()))
                .get(Task_viewModel.class);

        setListener();
        setRecycleView();

    }
    private void setListener()
    {
        binding.addTaskFloatingBtn.setOnClickListener(v -> {
            // Open the dialog box
            openDialogBox();
        });

        //history
        binding.btnHistory.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), Task_History.class)));

        Date currentDate = new Date();

        // Format the date as "June 20, 2023"
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault());
        String formattedDate = dateFormat.format(currentDate);

        // Set the formatted date in the TextView
        binding.dateTextView.setText(formattedDate);

    }
    private void setRecycleView()
    {

        binding.recyclerViewIncomplete.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerViewIncomplete.setHasFixedSize(true);
        binding.recyclerViewIncomplete.setAdapter(tasksAdapter);
        taskViewModel.getTaskList().observe(this, dataModels -> tasksAdapter.submitList(dataModels));
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                if (direction == ItemTouchHelper.RIGHT) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Task Completion")
                            .setMessage("Would you like to mark this task as completed?")
                            .setPositiveButton("Yes", (dialog, which) -> {
                                taskViewModel.update(tasksAdapter.getTask(viewHolder.getAbsoluteAdapterPosition()));
                                Custom_SnackBar.showSnackbar(MainActivity.this, findViewById(android.R.id.content), "Task completed");
                            })
                            .setNegativeButton("No", (dialog, which) -> tasksAdapter.notifyItemChanged(viewHolder.getAbsoluteAdapterPosition()))
                            .show();
                } else if (direction == ItemTouchHelper.LEFT) {
                    // Delete the task
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Delete Task")
                            .setMessage("Are you sure you want to delete this task?")
                            .setPositiveButton("Delete", (dialog, which) -> {
                                // Delete the task
                                taskViewModel.delete(tasksAdapter.getTask(viewHolder.getAbsoluteAdapterPosition()));
                                Custom_SnackBar.showSnackbar(MainActivity.this, findViewById(android.R.id.content), "Task deleted");
                            })
                            .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                            .show();

                }
            }


            public void onChildDraw (Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive){

                new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                        .addSwipeRightBackgroundColor(ContextCompat.getColor(MainActivity.this,R.color.green                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               ))
                        .addSwipeRightActionIcon(R.drawable.ic_complete)
                        .addSwipeRightLabel("Completed").setSwipeRightLabelColor(ContextCompat.getColor(MainActivity.this,R.color.white))
                        .addSwipeLeftBackgroundColor(ContextCompat.getColor(MainActivity.this,R.color.colorRed))
                        .addSwipeLeftActionIcon(R.drawable.ic_delete)
                        .addSwipeLeftLabel("Deleted").setSwipeLeftLabelColor(ContextCompat.getColor(MainActivity.this,R.color.white))


                        .create()
                        .decorate();

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }

        }).attachToRecyclerView(binding.recyclerViewIncomplete);


    }

    private void openDialogBox() {
        // Create the dialog box
        dialogBinding = CreateTaskDialogBoxBinding.inflate(LayoutInflater.from(MainActivity.this));
        View dialogView = dialogBinding.getRoot();


        // Create the dialog box
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setView(dialogView);

        EditText titleEditText = dialogBinding.editTextTitle;
        EditText descriptionEditText = dialogBinding.editTextRelated;
        Button createButton = dialogBinding.btnCreate;
        Button cancelButton = dialogBinding.btnCancel;

        // Set click listener for the create button
        createButton.setOnClickListener(v -> {
            // Validate the fields
            if (validateFields(titleEditText, descriptionEditText)) {
                try {
                    // Create the task
                    createTask(
                            titleEditText.getText().toString(),
                            descriptionEditText.getText().toString()

                    );
                    showToast("Task Created");
                    // Close the dialog box
                    alertDialog.dismiss();
                } catch (ParseException e) {
                    showToast("Failed to create task");
                }
            }
        });

        // Set click listener for the cancel button
        cancelButton.setOnClickListener(v -> {
            // Close the dialog box
            alertDialog.dismiss();
        });

        // Show the dialog box
        alertDialog = builder.create();
        alertDialog.show();
    }

    private void createTask(String title, String description) throws ParseException {
        DataModel dataModel = new DataModel(title, description, false);
        taskViewModel.insert(dataModel);
    }

    private boolean validateFields(EditText titleEditText, EditText descriptionEditText) {
        if (titleEditText.getText().toString().isEmpty()) {
            showToast("Enter Task Title");
            return false;
        } else if (descriptionEditText.getText().toString().isEmpty()) {
            showToast("Enter Description");
            return false;
        } else {
            return true;
        }
    }

    private void showToast(String message) {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}
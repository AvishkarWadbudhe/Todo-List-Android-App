package com.project.todoapp;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import java.util.Calendar;
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

    }
    private void setRecycleView()
    {

        binding.taskRecycler.setLayoutManager(new LinearLayoutManager(this));
        binding.taskRecycler.setHasFixedSize(true);
        binding.taskRecycler.setAdapter(tasksAdapter);
        taskViewModel.getTaskList().observe(this, dataModels -> tasksAdapter.submitList(dataModels));
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
                        .setPositiveButton("Yes", (dialog, which) -> {
                            taskViewModel.update(tasksAdapter.getTask(viewHolder.getAbsoluteAdapterPosition()));
                            Custom_SnackBar.showSnackbar(MainActivity.this, findViewById(android.R.id.content), "Task completed");


                        })
                        .setNegativeButton("No", (dialog, which) -> tasksAdapter.notifyItemChanged(viewHolder.getAbsoluteAdapterPosition()))
                        .show();
            }

            public void onChildDraw (Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive){

                new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                        .addSwipeRightBackgroundColor(ContextCompat.getColor(MainActivity.this,R.color.green                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               ))
                        .addSwipeRightActionIcon(R.drawable.ic_complete)
                        .addSwipeRightLabel("Completed").setSwipeRightLabelColor(ContextCompat.getColor(MainActivity.this,R.color.white))
                        .addSwipeLeftBackgroundColor(ContextCompat.getColor(MainActivity.this,R.color.green))
                        .addSwipeLeftActionIcon(R.drawable.ic_complete)
                        .addSwipeLeftLabel("Completed").setSwipeLeftLabelColor(ContextCompat.getColor(MainActivity.this,R.color.white))
                        .create()
                        .decorate();

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }




        }).attachToRecyclerView(binding.taskRecycler);
    }
    private void openDialogBox() {
        // Create the dialog box
        dialogBinding = CreateTaskDialogBoxBinding.inflate(LayoutInflater.from(MainActivity.this));
        View dialogView = dialogBinding.getRoot();

        // Create the dialog box
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setView(dialogView);

        dialogBinding.editTextDate.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                v.requestFocus(); // Request focus on the EditText
                showDatePickerDialog();
            }
            return false;
        });
//
        dialogBinding.editTextTime.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                v.requestFocus(); // Request focus on the EditText
                showTimePickerDialog();
            }
            return false;
        });



        EditText titleEditText = dialogBinding.editTextTitle;
        EditText descriptionEditText = dialogBinding.editTextDescription;
        EditText dateEditText = dialogBinding.editTextDate;
        EditText timeEditText = dialogBinding.editTextTime;
        Button createButton = dialogBinding.btnCreate;
        Button cancelButton = dialogBinding.btnCancel;

        // Set click listener for the create button
        createButton.setOnClickListener(v -> {
            // Validate the fields
            if (validateFields(titleEditText, descriptionEditText, dateEditText, timeEditText)) {
                try {
                    // Create the task
                    createTask(
                            titleEditText.getText().toString(),
                            descriptionEditText.getText().toString(),
                            dateEditText.getText().toString(),
                            timeEditText.getText().toString()
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

    private void createTask(String title, String description, String date, String time) throws ParseException {
        DataModel dataModel = new DataModel(title, description, date, time, false);
        taskViewModel.insert(dataModel);
    }

    private boolean validateFields(EditText titleEditText, EditText descriptionEditText, EditText dateEditText, EditText timeEditText) {
        if (titleEditText.getText().toString().isEmpty()) {
            showToast("Enter Task Title");
            return false;
        } else if (descriptionEditText.getText().toString().isEmpty()) {
            showToast("Enter Description");
            return false;
        } else if (dateEditText.getText().toString().isEmpty()) {
            showToast("Enter Date");
            return false;
        } else if (timeEditText.getText().toString().isEmpty()) {
            showToast("Enter Time");
            return false;
        } else {
            return true;
        }
    }
    private void showToast(String message) {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
    }
    private void showDatePickerDialog() {
        // Get current date
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Create a DatePickerDialog
        datePickerDialog = new DatePickerDialog(this, (view, selectedYear, selectedMonth, selectedDay) -> {
            // Format the chosen date
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
            Calendar selectedDate = Calendar.getInstance();
            selectedDate.set(selectedYear, selectedMonth, selectedDay);
            String formattedDate = dateFormat.format(selectedDate.getTime());

            // Set the chosen date in the EditText
            dialogBinding.editTextDate.setText(formattedDate);
        }, year, month, day);

        // Show the DatePickerDialog
        datePickerDialog.show();
    }
    private void showTimePickerDialog() {
        // Get current time
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        // Create a TimePickerDialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, (view, selectedHour, selectedMinute) -> {
            // Format the chosen time
            String formattedTime = String.format(Locale.getDefault(), "%02d:%02d", selectedHour, selectedMinute);

            // Set the chosen time in the EditText
            dialogBinding.editTextTime.setText(formattedTime);
        }, hour, minute, false);

        // Show the TimePickerDialog
        timePickerDialog.show();
    }

}
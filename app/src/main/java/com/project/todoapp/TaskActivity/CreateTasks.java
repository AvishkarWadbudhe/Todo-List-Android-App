package com.project.todoapp.TaskActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.Toast;

import com.project.todoapp.databinding.ActivityCreateTasksBinding;
import com.project.todoapp.databinding.ActivityMainBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CreateTasks extends AppCompatActivity {

    ActivityCreateTasksBinding binding;

    private DatePickerDialog datePickerDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateTasksBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setListener();

    }
    @SuppressLint("ClickableViewAccessibility")
    private void setListener() {
        //BACK BUTTON
        binding.imageBackButton.setOnClickListener(v -> onBackPressed());


        binding.taskDate.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                v.requestFocus(); // Request focus on the EditText
                showDatePickerDialog();
            }
            return false;
        });
//
        binding.taskTime.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                v.requestFocus(); // Request focus on the EditText
                showTimePickerDialog();
            }
            return false;
        });

        binding.btnAddTask.setOnClickListener(v -> {

        });


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
            binding.taskDate.setText(formattedDate);
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
            binding.taskTime.setText(formattedTime);
        }, hour, minute, false);

        // Show the TimePickerDialog
        timePickerDialog.show();
    }



    public boolean validateFields() {
        if(binding.addTaskTitle.getText().toString().equalsIgnoreCase("")) {
            showToast("Enter Task Title");
            return false;
        }
        else if(binding.addTaskDescription.getText().toString().equalsIgnoreCase("")) {
          showToast("Enter Description");
            return false;
        }
        else if(binding.taskDate.getText().toString().equalsIgnoreCase("")) {
           showToast("Enter Date");
            return false;
        }
        else if(binding.taskTime.getText().toString().equalsIgnoreCase("")) {
          showToast("Enter Time");
            return false;
        }

        else {
            return true;
        }
    }
    private void showToast(String message){
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();

    }
}
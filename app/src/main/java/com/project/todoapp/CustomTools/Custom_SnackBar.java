package com.project.todoapp.CustomTools;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.project.todoapp.R;

public class Custom_SnackBar {
    public static void showSnackbar(Context context, View view, String message) {
        Snackbar snackbar = Snackbar.make(view, "", Snackbar.LENGTH_LONG);

        // Get the Snackbar view
        View snackbarView = snackbar.getView();

        // Inflate the custom Snackbar layout
        LayoutInflater inflater = LayoutInflater.from(context);
        View customSnackbarLayout = inflater.inflate(R.layout.custom_snackbar, null);

        // Set the message text
        TextView textViewMessage = customSnackbarLayout.findViewById(R.id.message);
        textViewMessage.setText(message);

        // Set the icon
        ImageView imageViewIcon = customSnackbarLayout.findViewById(R.id.icon);
        imageViewIcon.setImageResource(R.drawable.ic_check);

        // Add the custom layout to the Snackbar's content view
        Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) snackbarView;
        snackbarLayout.addView(customSnackbarLayout, 0);
        snackbar.getView().setBackgroundResource(R.drawable.snackbar_background);
        // Show the Snackbar
        snackbar.show();
    }
}
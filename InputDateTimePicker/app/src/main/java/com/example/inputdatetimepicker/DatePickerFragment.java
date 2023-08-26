package com.example.inputdatetimepicker;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    public static final String TAG = "DatePickerFragment";

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "inside of onCreateDialog for DatePickerFragment");

        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        Log.d(TAG, "end of onCreateDialog for DatePickerFragment");
        return new DatePickerDialog(getActivity(), this, year, month, dayOfMonth);
        //return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Log.d(TAG, "inside of onDateSet for TimePickerFragment");

        MainActivity activity = (MainActivity)getActivity();//use method from MainActivity to process the date picked
        activity.processDatePickerResult(year,month,dayOfMonth);

        Log.d(TAG, "end of onDateSet for TimePickerFragment");
    }
}
package com.light1.ui.time_management;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.light1.databinding.FragmentTimeManagementBinding;

import java.util.Locale;

public class TimeManager extends Fragment {

    private Button mTimeButton;
    private Button mDateButton;

    protected int hour, minute;
    protected int year, month, date;

    private FragmentTimeManagementBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        DashboardViewModel dashboardViewModel =
//                new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(DashboardViewModel.class);

        binding = FragmentTimeManagementBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        initParameters();

//        final TextView textView = binding.textDashboard;
//        dashboardViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void initParameters(){
        mTimeButton = binding.timeButton;
        mDateButton = binding.dateButton;
        mTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popTimePicker(view);
            }
        });
        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popDatePicker(view);
            }
        });
    }



    public void popTimePicker(View view)
    {
//        int style = TimePickerDialog.THEME_HOLO_LIGHT;

        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectHour, int selectMinute) {
                hour = selectHour;
                minute = selectMinute;
                mTimeButton.setText(String.format(Locale.getDefault(),"%02d:%02d",hour,minute));
            }
        };

        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), onTimeSetListener, hour, minute, true);

        timePickerDialog.setTitle("设置时间");
        timePickerDialog.show();
    }

    public void popDatePicker(View view)
    {
        DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int selectYear, int selectMonth, int selectDate) {
                year = selectYear;
                month = selectMonth;
                date = selectDate;
            }
        };

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), onDateSetListener, year, month, date);
    }
}
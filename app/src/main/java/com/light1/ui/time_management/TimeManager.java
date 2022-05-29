package com.light1.ui.time_management;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.light1.databinding.FragmentTimeManagementBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

// TODO: 2022/3/27 显示远端时间
// TODO: 2022/3/28 显示远端温度

public class TimeManager extends Fragment {

    private Button mTimeButton;
    private Button mDateButton;
    private Button mGetSystemTimeButton;
    private SeekBar mAdjustBrightnessSeekBar;
    private SeekBar mAdjustVolumeSeekBar;
    private TextView mTextViewCurrentBrightness;

    SimpleDateFormat simpleDateFormat;
    Calendar calendar;
    java.util.Date mDate;

    private static int hour, minute, seconds = 0;
    private static int year, month, dayOfMonth;
    private int defaultYear = 2022, defaultMonth = 4, defaultDayOfMonth = 1;
    private static int brightness = 5;
    private static int volume = 8;

    public static int getHour() {
        return hour;
    }

    public static int getBrightness() {
        return brightness;
    }

    public static int getVolume() {
        return volume;
    }

    public static Date getSetTime() {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.clear();
        calendar1.set(year, month, dayOfMonth, hour, minute, seconds);
        return calendar1.getTime();
    }

    private FragmentTimeManagementBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        DashboardViewModel dashboardViewModel =
//                new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(DashboardViewModel.class);

        binding = FragmentTimeManagementBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        final TextView textView = binding.textDashboard;
//        dashboardViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        initParameters();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void initParameters(){
        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

        mTimeButton = binding.timeButton;
        mDateButton = binding.dateButton;
        mAdjustBrightnessSeekBar = binding.adjustBrightnessSeekBar;
        mAdjustVolumeSeekBar = binding.adjustVolumeSeekBar;
        mTextViewCurrentBrightness = binding.textViewCurrentBrightness;
//        brightness = 5;
//        volume = 8;
        mTextViewCurrentBrightness.setText("当前亮度：" + String.valueOf(brightness));

        mGetSystemTimeButton = binding.getSystemTimeButton;
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

        mGetSystemTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                acquireSystemTimeAndSet();
            }
        });

        mAdjustBrightnessSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                brightness = progress;
                mTextViewCurrentBrightness.setText("当前亮度：" + String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO: 2022/3/28 调节屏幕亮度使之更直观
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO: 2022/3/28 发送brightness数据

            }

        });

        mAdjustVolumeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                volume = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    public void acquireSystemTimeAndSet() {
        String dateTime = simpleDateFormat.format(calendar.getTime());
        String[] dateTimeArr;
        dateTimeArr = dateTime.split("-");
        year = Integer.parseInt(dateTimeArr[0]);
        month = Integer.parseInt(dateTimeArr[1]);
        dayOfMonth = Integer.parseInt(dateTimeArr[2]);
        hour = Integer.parseInt(dateTimeArr[3]);
        minute = Integer.parseInt(dateTimeArr[4]);
        seconds = Integer.parseInt(dateTimeArr[5]);
        mTimeButton.setText(String.format(Locale.getDefault(),"%02d点%02d分%02d秒",hour,minute,seconds));
        mDateButton.setText(String.format(Locale.getDefault(),"%02d年%02d月%02d号",year,month, dayOfMonth));

//        mTestTextView.setText(Date);
    }

    public void popTimePicker(View view)
    {
//        int style = TimePickerDialog.THEME_HOLO_LIGHT;

        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectHour, int selectMinute) {
                hour = selectHour;
                minute = selectMinute;
                mTimeButton.setText(String.format(Locale.getDefault(),"%02d点%02d分",hour,minute));
            }
        };

        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), onTimeSetListener, hour, minute, true);

        timePickerDialog.setTitle("设置时间");
        timePickerDialog.show();
    }

    public void popDatePicker(View view)
    {
        DatePickerDialog.OnDateSetListener onDateSetListener = (datePicker, selectYear, selectMonth, selectDate) -> {
            year = selectYear;
            month = selectMonth;
            dayOfMonth = selectDate;
            mDateButton.setText(String.format(Locale.getDefault(),"%02d年%02d月%02d号", year, month + 1, dayOfMonth)); //month需要加1否则会少一个月
        };

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), onDateSetListener, defaultYear, defaultMonth, defaultDayOfMonth);
//        datePickerDialog.updateDate(defaultYear, defaultMonth - 1, defaultDayOfMonth);
//        datePickerDialog.init
        datePickerDialog.setTitle("设置日期");
        datePickerDialog.show();
    }


}
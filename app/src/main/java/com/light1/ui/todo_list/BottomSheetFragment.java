package com.light1.ui.todo_list;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.Group;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.chip.Chip;
import com.google.android.material.snackbar.Snackbar;
import com.light1.R;
import com.light1.adapter.util.Utils;
import com.light1.model.Priority;
import com.light1.model.SharedViewModel;
import com.light1.model.Task;
import com.light1.model.TaskViewModel;

import java.util.Calendar;
import java.util.Date;

public class BottomSheetFragment extends BottomSheetDialogFragment implements View.OnClickListener {
    private EditText enterTodo;
    private ImageButton calendarButton;
    private ImageButton priorityButton;
    private ImageButton alarmButton;
    private ImageButton timeButton;
    private RadioGroup priorityRadioGroup;
    private RadioButton selectedPriorityRadioButton;
    private RadioGroup alarmRadioGroup;
    private RadioButton selectedAlarmRadioButton;
    private int selectedPriorityButtonId;
    private int selectedAlarmButtonId;
    private ImageButton saveButton;
    private CalendarView calendarView;
    private Group calendarGroup;
    private Date dueDate;
    Calendar calendar = Calendar.getInstance();
    private SharedViewModel sharedViewModel;
    private boolean isEdit;
    private Priority priority = Priority.LOW;
    private int alarmSound = 0;

    private int year, month, dayOfMonth, hour, minute;

    public BottomSheetFragment() {
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        View view = inflater.inflate(R.layout.bottom_sheet, container, false);
        calendarGroup = view.findViewById(R.id.calendar_group);
        calendarView = view.findViewById(R.id.calendar_view);
        calendarButton = view.findViewById(R.id.today_calendar_button);
        timeButton = view.findViewById(R.id.time_button);
        enterTodo = view.findViewById(R.id.enter_todo_et);
        saveButton = view.findViewById(R.id.save_todo_button);
        priorityButton = view.findViewById(R.id.priority_todo_button);
        priorityRadioGroup = view.findViewById(R.id.radioGroup_priority);
        alarmButton = view.findViewById(R.id.alarm_button);
        alarmRadioGroup = view.findViewById(R.id.radioGroup_alarm);

        Chip todayChip = view.findViewById(R.id.today_chip);
        todayChip.setOnClickListener(this);
        Chip tomorrowChip = view.findViewById(R.id.tomorrow_chip);
        tomorrowChip.setOnClickListener(this);
        Chip newtWeekChip = view.findViewById(R.id.next_week_chip);
        newtWeekChip.setOnClickListener(this);

        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR);
        minute = calendar.get(Calendar.MINUTE);

        return view;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharedViewModel = new ViewModelProvider(requireActivity())
                .get(SharedViewModel.class);

        calendarButton.setOnClickListener(view12 -> {
            calendarGroup.setVisibility(
                    calendarGroup.getVisibility() == View.GONE ? View.VISIBLE : View.GONE
            );
            Utils.hideSoftKeyboard(view12);

        });

        timeButton.setOnClickListener(view14 -> {
            calendar.clear();

            TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int selectHour, int selectMinute) {
                    hour = selectHour;
                    minute = selectMinute;
                }
            };

            TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), onTimeSetListener, Calendar.getInstance().get(Calendar.HOUR), Calendar.getInstance().get(Calendar.MINUTE), true);

            timePickerDialog.setTitle("设置到期时间");
            timePickerDialog.show();

        });

        calendarView.setOnDateChangeListener((calendarView, year, month, dayOfMonth) -> {
            calendar.clear();
            this.year = year;
            this.month = month;
            this.dayOfMonth = dayOfMonth;
//            calendar.clear();
//            calendar.set(year, month, dayOfMonth);
//            dueDate = calendar.getTime();

        });

        priorityButton.setOnSystemUiVisibilityChangeListener(i -> {
            Utils.hideSoftKeyboard(this.getView());
        });

        alarmButton.setOnSystemUiVisibilityChangeListener(i -> {
            Utils.hideSoftKeyboard(this.getView());
        });

        priorityButton.setOnClickListener(view13 -> {
            Utils.hideSoftKeyboard(view13);
            priorityRadioGroup.setVisibility(
                    priorityRadioGroup.getVisibility() == View.GONE ? View.VISIBLE : View.GONE
            );
            priorityRadioGroup.setOnCheckedChangeListener((radioGroup, checkedId) -> {
                if (priorityRadioGroup.getVisibility() == View.VISIBLE) {
                    selectedPriorityButtonId = checkedId;
                    selectedPriorityRadioButton = view.findViewById(selectedPriorityButtonId);
                    if (selectedPriorityRadioButton.getId() == R.id.radioButton_high) {
                        priority = Priority.HIGH;
                    } else if (selectedPriorityRadioButton.getId() == R.id.radioButton_med) {
                        priority = Priority.MEDIUM;
                    } else if (selectedPriorityRadioButton.getId() == R.id.radioButton_low) {
                        priority = Priority.LOW;
                    } else {
                        priority = Priority.LOW;
                    }
                } else {
                    priority = Priority.LOW;
                }

            });

        });

        alarmButton.setOnClickListener(v -> {
            Utils.hideSoftKeyboard(v);
            alarmRadioGroup.setVisibility(
                    alarmRadioGroup.getVisibility() == View.GONE ? View.VISIBLE : View.GONE
            );
            alarmRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
                if (alarmRadioGroup.getVisibility() == View.VISIBLE) {
                    selectedAlarmButtonId = checkedId;
                    selectedAlarmRadioButton = view.findViewById(selectedAlarmButtonId);
                    if (selectedAlarmRadioButton.getId() == R.id.radioButton_alarm_1) {
                        alarmSound = 1;
                    }
                    else if(selectedAlarmRadioButton.getId() == R.id.radioButton_alarm_2) {
                        alarmSound = 2;
                    }
                    else if (selectedAlarmRadioButton.getId() == R.id.radioButton_alarm_3) {
                        alarmSound = 3;
                    }
                    else if (selectedAlarmRadioButton.getId() == R.id.radioButton_alarm_4) {
                        alarmSound = 4;
                    }
                    else {
                        alarmSound = 0; //发送0代表未进行设置错误，在单片机端采用默认铃声
                    }
                }
            });
        });

        saveButton.setOnClickListener(view1 -> {
            Utils.hideSoftKeyboard(view1);
            String taskName = enterTodo.getText().toString().trim();

            calendar.clear();
            calendar.set(year, month, dayOfMonth, hour, minute);
            dueDate = calendar.getTime();

            if (!TextUtils.isEmpty(taskName) && dueDate != null && priority != null) {
                Task myTask = new Task(taskName, priority,
                        dueDate, Calendar.getInstance().getTime(),
                        false, alarmSound);
                if (isEdit == false)
                    TaskViewModel.insert(myTask);
                else {
                    Task updateTask = sharedViewModel.getSelectedItem().getValue();
                    updateTask.setTask(taskName);
                    updateTask.setDateCreated(Calendar.getInstance().getTime());
                    updateTask.setPriority(priority);
                    updateTask.setDueDate(dueDate);
                    updateTask.setAlarmSound(alarmSound);
                    TaskViewModel.update(updateTask);
                    sharedViewModel.setIsEdit(false);
                }
                enterTodo.setText("");
                if (this.isVisible()) {
                    this.dismiss();
                }
            } else {
                Snackbar.make(saveButton, R.string.empty_field, Snackbar.LENGTH_LONG)
                        .show();
            }

        });

    }

    @Override
    public void onResume() {
        super.onResume();
        if (sharedViewModel.getSelectedItem().getValue() != null) {
            isEdit = sharedViewModel.getIsEdit();
            Task task = sharedViewModel.getSelectedItem().getValue();
            enterTodo.setText(task.getTask());
        } else {
            enterTodo.setText("");
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        sharedViewModel.selectItem(null);
        sharedViewModel.setIsEdit(false);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.today_chip:
                calendar.add(Calendar.DAY_OF_YEAR, 0);
                dueDate = calendar.getTime();
            case R.id.tomorrow_chip:
                calendar.add(Calendar.DAY_OF_YEAR, 1);
                dueDate = calendar.getTime();
            case R.id.next_week_chip:
                calendar.add(Calendar.DAY_OF_YEAR, 7);
                dueDate = calendar.getTime();
            default:
                dueDate = calendar.getTime();
        }
    }
}
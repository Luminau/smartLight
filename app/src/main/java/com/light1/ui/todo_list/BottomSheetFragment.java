package com.light1.ui.todo_list;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableLayout;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.Group;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.chip.Chip;
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
    private RadioGroup priorityRadioGroup;
    private RadioButton selectedRadioButton;
    private int selectedButtonId;
    private ImageButton saveButton;
    private CalendarView calendarView;
    private Group calendarGroup;
    private Date dueDate;
    Calendar calendar = Calendar.getInstance();
    private SharedViewModel sharedViewModel;
    private boolean isEdit;

    public BottomSheetFragment(){}

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        View view = inflater.inflate(R.layout.bottom_sheet, container, false);
        calendarGroup = view.findViewById(R.id.calendar_group);
        calendarView = view.findViewById(R.id.calendar_view);
        calendarButton = view.findViewById(R.id.today_calendar_button);
        enterTodo = view.findViewById(R.id.enter_todo_et);
        saveButton = view.findViewById(R.id.save_todo_button);
        priorityButton = view.findViewById(R.id.priority_todo_button);
        priorityRadioGroup = view.findViewById(R.id.radioGroup_priority);

        Chip todayChip = view.findViewById(R.id.today_chip);
        todayChip.setOnClickListener(this);
        Chip tomorrowChip = view.findViewById(R.id.tomorrow_chip);
        tomorrowChip.setOnClickListener(this);
        Chip newtWeekChip = view.findViewById(R.id.next_week_chip);
        newtWeekChip.setOnClickListener(this);


        return view;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharedViewModel = new ViewModelProvider(requireActivity())
                .get(SharedViewModel.class);

        calendarButton.setOnClickListener(view12 -> {
            calendarGroup.setVisibility(calendarGroup.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);

        });

        calendarView.setOnDateChangeListener((calendarView, year, month, dayOfMonth) -> {
            calendar.clear();
            calendar.set(year, month, dayOfMonth);
            dueDate = calendar.getTime();

        });

        saveButton.setOnClickListener(view1 -> {
            String taskName = enterTodo.getText().toString().trim();
            if(!TextUtils.isEmpty(taskName) && dueDate != null){
                Task myTask = new Task(taskName, Priority.HIGH,
                        dueDate, Calendar.getInstance().getTime(),
                        false);
                if(isEdit == false)
                    TaskViewModel.insert(myTask);
                else{
                    Task updateTask = sharedViewModel.getSelectedItem().getValue();
                    updateTask.setTask(taskName);
                    updateTask.setDateCreated(Calendar.getInstance().getTime());
                    updateTask.setPriority(Priority.HIGH);
                    updateTask.setDueDate(dueDate);
                    TaskViewModel.update(updateTask);
                    sharedViewModel.setIsEdit(false);
                }

            }

        });

    }

    @Override
    public void onResume() {
        super.onResume();
        if(sharedViewModel.getSelectedItem().getValue() != null){
            isEdit = sharedViewModel.getIsEdit();
            Task task = sharedViewModel.getSelectedItem().getValue();
            enterTodo.setText(task.getTask());
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch(id){
            case R.id.today_chip:
                calendar.add(Calendar.DAY_OF_YEAR, 0);
                dueDate = calendar.getTime();
            case R.id.tomorrow_chip:
                calendar.add(Calendar.DAY_OF_YEAR, 1);
                dueDate = calendar.getTime();
            case R.id.next_week_chip:
                calendar.add(Calendar.DAY_OF_YEAR, 7);
                dueDate = calendar.getTime();
        }
    }
}
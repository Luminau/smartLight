package com.light1.ui.todo_list;

import android.app.Application;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.light1.databinding.FragmentTodoListManagementBinding;
import com.light1.model.Priority;
import com.light1.model.Task;
import com.light1.model.TaskViewModel;

import java.util.Calendar;

public class todoListManager extends Fragment {

    private TaskViewModel taskViewModel;
    private FloatingActionButton fab;
    private FragmentTodoListManagementBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentTodoListManagementBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        initParameters();
        return root;
    }

    protected void initParameters() {
        fab = binding.fab;
        fab.setOnClickListener(view -> {
            Task task = new Task("Todo", Priority.HIGH, Calendar.getInstance().getTime(),
                    Calendar.getInstance().getTime(), false);
            TaskViewModel.insert(task);

        });

        taskViewModel = new ViewModelProvider.AndroidViewModelFactory(
                getActivity().getApplication())
                .create(TaskViewModel.class);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
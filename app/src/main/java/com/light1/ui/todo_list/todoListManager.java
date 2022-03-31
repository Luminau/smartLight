package com.light1.ui.todo_list;

import android.app.Application;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.light1.adapter.RecyclerViewAdapter;
import com.light1.databinding.FragmentTodoListManagementBinding;
import com.light1.model.Priority;
import com.light1.model.Task;
import com.light1.model.TaskViewModel;

import java.util.Calendar;
import java.util.List;

public class todoListManager extends Fragment {

    public static final String TAG = "ITEM";

    private TaskViewModel taskViewModel;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;

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
        recyclerView = binding.recyclerView;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        fab = binding.fab;
        fab.setOnClickListener(view -> {
//            Task task = new Task("Todoo", Priority.HIGH, Calendar.getInstance().getTime(),
//                    Calendar.getInstance().getTime(), false);
//            TaskViewModel.insert(task);
            callUpSettingTaskMenu();

        });

        taskViewModel = new ViewModelProvider.AndroidViewModelFactory(
                getActivity().getApplication())
                .create(TaskViewModel.class);

        taskViewModel.getAllTasks().observe(getViewLifecycleOwner(), tasks -> {
            recyclerViewAdapter = new RecyclerViewAdapter(tasks);
            recyclerView.setAdapter(recyclerViewAdapter);
//            for (Task task :tasks){
//                Log.d(TAG, "onCreate: " + task.getTaskId());
//            }

        });
    }

    private void callUpSettingTaskMenu() {
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
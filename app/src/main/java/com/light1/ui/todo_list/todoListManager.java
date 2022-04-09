package com.light1.ui.todo_list;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.light1.MainActivity;
import com.light1.adapter.OnTodoClickListener;
import com.light1.adapter.RecyclerViewAdapter;
import com.light1.databinding.FragmentTodoListManagementBinding;
import com.light1.model.Task;
import com.light1.model.TaskViewModel;

public class todoListManager extends Fragment implements OnTodoClickListener {

    public static final String TAG = "ITEM";

    private TaskViewModel taskViewModel;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private FloatingActionButton fab;
    private FragmentTodoListManagementBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentTodoListManagementBinding.inflate(inflater, container, false);

        fab = binding.fab;

        recyclerView = binding.recyclerView;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        taskViewModel = new ViewModelProvider.AndroidViewModelFactory(
                getActivity().getApplication())
                .create(TaskViewModel.class);

        taskViewModel.getAllTasks().observe(getViewLifecycleOwner(), tasks -> {
            recyclerViewAdapter = new RecyclerViewAdapter(tasks, this);
            recyclerView.setAdapter(recyclerViewAdapter);
//            for (Task task :tasks){
//                Log.d(TAG, "onCreate: " + task.getTaskId());
//            }
        });

        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initParameters();
    }

    protected void initParameters() {
        ((MainActivity)getActivity()).initBottomSheetFragment();
        fab.setOnClickListener(view -> {
            callUPBottomSheetFragment();
        });
    }

    private void callUPBottomSheetFragment() {
        ((MainActivity)getActivity()).callUPBottomSheetFragment();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onTodoClick(int adapterPosition, Task task) {
        Log.d("Click", "onTodoClick: " + adapterPosition + "taskName" + task.getTask());

    }
}
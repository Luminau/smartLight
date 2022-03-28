package com.light1.ui.todo_list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.arch.lifecycle.ViewModelProvider;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.light1.databinding.FragmentTodoListManagementBinding;

public class todoListManager extends Fragment {

    private FloatingActionButton fab;

    private FragmentTodoListManagementBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        NotificationsViewModel notificationsViewModel =
//                new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(NotificationsViewModel.class);

        binding = FragmentTodoListManagementBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        initParameters();

//        final TextView textView = binding.textNotifications;
//        notificationsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    protected void initParameters() {
        fab = binding.fab;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
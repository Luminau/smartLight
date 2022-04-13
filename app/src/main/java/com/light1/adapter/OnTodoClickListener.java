package com.light1.adapter;

import com.light1.model.Task;

public interface OnTodoClickListener {
    void onTodoClick(Task task);
    void onTodoRadioButtonClick(Task task);
    void onTodoRowChipClick(Task task);
}

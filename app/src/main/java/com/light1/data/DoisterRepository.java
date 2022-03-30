package com.light1.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.light1.model.Task;
import com.light1.util.TaskRoomDatabase;

import java.util.List;

public class DoisterRepository {
    private final TaskDao taskDao;
    private final LiveData<List<Task>> allTasks;

    public DoisterRepository(Application application) {
        TaskRoomDatabase database = TaskRoomDatabase.getDatabase(application);
        taskDao = database.taskDao();
        allTasks = taskDao.getTask();
    }

    public LiveData<List<Task>> getAllTasks(){
        return allTasks;
    }

    public void insert(Task task) {
        TaskRoomDatabase.databaseWriterExcutor.execute(() -> taskDao.insertTask(task));
    }

    public LiveData<Task> get(long id){return taskDao.get(id);}

    public void update(Task task){
        TaskRoomDatabase.databaseWriterExcutor.execute(() -> taskDao.update(task));
    }

    public void delete(Task task){
        TaskRoomDatabase.databaseWriterExcutor.execute(() -> taskDao.delete(task));
    }
}

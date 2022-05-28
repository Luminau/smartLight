package com.light1.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "task_table")
public class Task {
    @ColumnInfo(name = "task_id")
    @PrimaryKey(autoGenerate = true)
    public long taskId;

    public String task;

    public Priority priority;

    @ColumnInfo(name = "due_date")
    public Date dueDate;

    @ColumnInfo(name = "created_at")
    public Date dateCreated;

    @ColumnInfo(name = "is_done")
    public boolean isDone;

    @ColumnInfo(name = "alarm_sound")
    public int alarmSound;

    public Task(String task, Priority priority, Date dueDate, Date dateCreated, boolean isDone, int alarmSound) {
//        this.taskId = taskId;
        this.task = task;
        this.priority = priority;
        this.dueDate = dueDate;
        this.dateCreated = dateCreated;
        this.isDone = isDone;
        this.alarmSound = alarmSound;
    }

    public long getTaskId() {
        return taskId;
    }

    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public Priority getPriority() {
        return priority;
    }

    public int getNumPriority() {
        if (priority == Priority.VERYHIGH) {
            return 4;
        } else if (priority == Priority.HIGH) {
            return 3;
        } else if (priority == Priority.MEDIUM) {
            return 2;
        } else if (priority == Priority.LOW) {
            return 1;
        } else return 0;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public int getAlarmSound() {
        return alarmSound;
    }

    public void setAlarmSound(int alarmSound) {
        this.alarmSound = alarmSound;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    @Override
    public String toString() {
        return taskId + '\'' +
                task + '\'' +
                priority + '\'' +
                dueDate + '\'' +
                dateCreated + '\'' +
                isDone + "$"
                ;
    }


}

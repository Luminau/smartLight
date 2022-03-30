package com.light1.util;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.light1.data.TaskDao;
import com.light1.model.Task;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Task.class}, version = 1, exportSchema = false)
@TypeConverters({Converter.class})
public abstract class TaskRoomDatabase extends RoomDatabase {
    public static final int NUMBER_OF_THREADS = 4;
    public static final String DATASE_NAME = "todolister_database";

    public static final RoomDatabase.Callback sRoomDatabaseCallback
            = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            databaseWriterExcutor.execute(() -> {
                // TODO: 2022/3/29 invoke dao and write
                TaskDao taskDao = INSTANCE.taskDao();
                taskDao.deleteAll(); //清楚数据库内所有已有数据
            });
        }
    };

    private static volatile TaskRoomDatabase INSTANCE;
    public static final ExecutorService databaseWriterExcutor
            = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    public static TaskRoomDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (TaskRoomDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TaskRoomDatabase.class, DATASE_NAME)
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract TaskDao taskDao();

}

package com.light1.util;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import androidx.annotation.NonNull;

import com.light1.data.TaskDao;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

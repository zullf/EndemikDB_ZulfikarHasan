package com.example.endemikdb_zulfikarhasan.data.local.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.endemikdb_zulfikarhasan.data.model.Endemik;
import com.example.endemikdb_zulfikarhasan.data.model.Favorite;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Endemik.class, Favorite.class}, version = 2, exportSchema = false)
public abstract class EndemikDatabase extends RoomDatabase {
    public abstract EndemikDao endemikDao();

    private static volatile EndemikDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static EndemikDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (EndemikDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    EndemikDatabase.class, "endemik_database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}

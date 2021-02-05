package com.astech.movsee.data.source.local.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.astech.movsee.data.source.local.entity.MovieDetailEntity;
import com.astech.movsee.data.source.local.entity.TvDetailEntity;

@Database(entities = {MovieDetailEntity.class, TvDetailEntity.class}, version = 2, exportSchema = false)
public abstract class MovSeeDatabase extends RoomDatabase {
    private static volatile MovSeeDatabase INSTANCE;

    public abstract MovSeeDao movSeeDao();

    public static MovSeeDatabase getInstance(Context context){
        if (INSTANCE == null){
            synchronized (MovSeeDatabase.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),MovSeeDatabase.class,"MovSee.db").build();
                }
            }
        }

        return INSTANCE;
    }


}

package com.nevdiaz.iterate;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database (entities = {Algorithm.class, Image.class, Iteration.class}, version = 1)
public abstract class IterateDatabase extends RoomDatabase {
  
    private static IterateDatabase INSTANCE;

    public static IterateDatabase getInstance(Context context){
      if (INSTANCE == null){
        INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
           IterateDatabase.class, "iterate_room").build();
      }
      return INSTANCE;
    }

  }


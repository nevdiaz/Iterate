package com.nevdiaz.iterate;

import android.content.Context;
import android.os.AsyncTask;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import com.nevdiaz.iterate.dao.AlgorithmDao;
import com.nevdiaz.iterate.dao.ImageDao;
import com.nevdiaz.iterate.dao.IterationDao;
import com.nevdiaz.iterate.entities.Algorithm;
import com.nevdiaz.iterate.entities.Image;
import com.nevdiaz.iterate.entities.Iteration;

@Database(entities = {Algorithm.class, Image.class, Iteration.class}, version = 1)
public abstract class IterateDatabase extends RoomDatabase {

  public abstract AlgorithmDao getAlgorithmDao();

  public abstract ImageDao getImageDao();

  public abstract IterationDao getIterationDao();


  private static IterateDatabase INSTANCE;

  public static IterateDatabase getInstance(Context context) {
    if (INSTANCE == null) {
      synchronized (IterateDatabase.class) {
        if (INSTANCE == null) {
          INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
              IterateDatabase.class, "iterate_room")
              .fallbackToDestructiveMigration()
              .addCallback(new Callback() {
                @Override
                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                  super.onCreate(db);
                  new PopulateDbTask(INSTANCE).execute();
                }
              })

              .build();
        }
      }
    }
    return INSTANCE;
  }

  private static class PopulateDbTask extends AsyncTask<Void, Void, Void> {

    private final IterateDatabase db;

    public PopulateDbTask(IterateDatabase db) {
      this.db = db;
    }

    @Override
    protected Void doInBackground(Void... voids) {

      Algorithm algorithm = new Algorithm();
      algorithm.setName("fibonacci");
      algorithm.setFormula("com.nevdiaz.iterate.Fibonacci");
      db.getAlgorithmDao().insert(algorithm);
      return null;
    }
  }
}


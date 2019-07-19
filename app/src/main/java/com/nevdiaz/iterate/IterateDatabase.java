package com.nevdiaz.iterate;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
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
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executors;

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
    public void onCreate(@android.support.annotation.NonNull SupportSQLiteDatabase db) {
      super.onCreate(db);
      Executors.newSingleThreadScheduledExecutor().execute(new Runnable() {
        @Override
        public void run() {

          try (
              InputStream input = IterateApplication.getInstance().getResources()
                  .openRawResource(R.raw.transforms);
              Reader reader = new InputStreamReader(input);
              CSVParser parser = new CSVParser(reader, CSVFormat.DEFAULT.withTrim())
          ) {
            List<Iteration> iterations = new LinkedList<>();
            for (CSVRecord record : parser) {
              Iteration iteration = new iteration();
              iteration.setName(record.get(0));
              iteration.setFormula(record.get(1).isEmpty() ? null : record.get(1));
              transforms.add(iteration);
            }
            getInstance().getIterationDao().insert(iterations);
          } catch (IOException e) {
            Log.e("Something went wrong!", getClass().getSimpleName());
          }
        }
      });
    }

    @Override
    public void onOpen(@android.support.annotation.NonNull SupportSQLiteDatabase db) {
      super.onOpen(db);
    }

  }

//
//    @Override
//    protected Void doInBackground(Void... voids) {
//
//      Algorithm algorithm = new Algorithm();
//      algorithm.setName("fibonacci");
//      algorithm.setFormula("com.nevdiaz.iterate.Fibonacci");
//      db.getAlgorithmDao().insert(algorithm);
//      return null;
//    }
//  }
}


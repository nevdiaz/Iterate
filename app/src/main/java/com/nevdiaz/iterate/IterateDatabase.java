package com.nevdiaz.iterate;

import android.content.Context;
import android.util.Log;
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
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

@Database(entities = {Algorithm.class, Image.class, Iteration.class}, version = 1)
public abstract class IterateDatabase extends RoomDatabase {

  private static final String DB_NAME = "iterate_room";

  private static Context context;

  public static void setContext(Context context) {
    IterateDatabase.context = context;
  }

  /**
   * Returns the single instance of {@link IterateDatabase} for the current application context.
   *
   * @return single {@link IterateDatabase} instance reference.
   */
  public static IterateDatabase getInstance() {
    return InstanceHolder.INSTANCE;

  }

  public abstract AlgorithmDao getAlgorithmDao();

  public abstract ImageDao getImageDao();

  public static IterationDao getIterationDao() {
    return null;
  }

  private static class InstanceHolder {

    private static final IterateDatabase INSTANCE = Room.databaseBuilder(
        context.getApplicationContext(),
        IterateDatabase.class, DB_NAME)
        .addCallback(new Prepopulate())
        .build();

  }



    private static class Prepopulate extends Callback {

      /**
       * This method pre-populates the Algorithm database with id, name, and class name.
       *
       * @param db database to which pre-load items will be added
       */

      @Override
      public void onCreate(@android.support.annotation.NonNull SupportSQLiteDatabase db) {
        super.onCreate(db);
        Executors.newSingleThreadScheduledExecutor().execute(new Runnable() {
          @Override
          public void run() {

            try (
                InputStream input = context.getResources()
                    .openRawResource(R.raw.algorithm);
                Reader reader = new InputStreamReader(input);
                CSVParser parser = new CSVParser(reader, CSVFormat.DEFAULT.withTrim())
            ) {
              List<Algorithm> algorithms = new LinkedList<>();
              for (CSVRecord record : parser) {
                Algorithm algorithm = new Algorithm();
                algorithm.setName(record.get(0));
                algorithm.setFormula(record.get(1).isEmpty() ? null : record.get(1));
                algorithms.add(algorithm);
              }
              getInstance().getAlgorithmDao().insert(algorithms);
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



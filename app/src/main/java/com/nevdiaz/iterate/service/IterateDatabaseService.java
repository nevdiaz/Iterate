package com.nevdiaz.iterate.service;

import android.support.annotation.Nullable;
import com.nevdiaz.iterate.IterateDatabase;
import com.nevdiaz.iterate.entities.Algorithm;
import com.nevdiaz.iterate.entities.Iteration;
import edu.cnm.deepdive.android.BaseFluentAsyncTask;
import edu.cnm.deepdive.android.FluentAsyncTask;
import java.sql.Date;
import java.util.LinkedList;
import java.util.List;

public class IterateDatabaseService {

  private IterateDatabaseService() {
  }

  /**
   * Implements an asynchronous <code>INSERT</code> of one or more {@link Algorithm} instances, with
   * related {@link Iteration} instances, into the local database.
   */
  public static class InsertApodTask
      extends FluentAsyncTask<Algorithm, Void, List<Long>, List<Long>> {

    private boolean foreground;

    /**
     * Initializes <code>INSERT</code> task with <code>foreground</code> indicating intention to
     * present Iteration immediately in user interface
     *
     * @param foreground <code>true</code> if Iteration will be displayed immediately;
     * <code>false</code> otherwise.
     */
    public InsertApodTask(boolean foreground) {
      this.foreground = foreground;
    }

    @Override
    protected List<Long> perform(Algorithm... Algorithms) {
      List<Long> AlgorithmIds = IterateDatabase.getInstance().getAlgorithmDao().insert(Algorithms);
      if (foreground) {
        List<Iteration> accesses = new LinkedList<>();
        for (long id : AlgorithmIds) {
          Iteration access = new Iteration();
          access.setAlgorithmId(id);
          accesses.add(access);
        }
        IterateDatabase.getInstance().getIterationDao().insert(accesses);
      }
      return AlgorithmIds;
    }

  }

  /**
   * Implements an asynchronous <code>SELECT</code> of a single {@link Algorithm} instance, and an
   * <code>INSERT</code> of a related {@link Iteration} instance, in the local database.
   */
  public static class SelectApodTask extends FluentAsyncTask<Date, Void, Algorithm, Algorithm> {

  }

  /**
   * Implements an asynchronous <code>SELECT</code> of all {@link Algorithm} instances (sorted in
   * descending date order) from the local database.
   */
  public static class SelectAllAlgorithmTask
      extends FluentAsyncTask<Void, Void, List<Algorithm>, List<Algorithm>> {

    @Override
    protected List<Algorithm> perform(Void... voids) {
      return IterateDatabase.getInstance().getAlgorithmDao().findAll();
    }

  }

  /**
   * Implements an asynchronous <code>DELETE</code> of one or more {@link Iteration} instances from the
   * local database.
   */
  public static class DeleteAlgorithmTask extends FluentAsyncTask<Algorithm, Void, Void, Void> {

    @Nullable
    @Override
    protected Void perform(Algorithm... Algorithms) throws TaskException {
      IterateDatabase.getInstance().getAlgorithmDao().delete(Algorithms);
      return null;
    }

  }

  /**
   * Implements an asynchronous <code>INSERT</code> of one or more {@link Iteration} instances into the
   * local database.
   */
  public static class GetHistoryTask
      extends FluentAsyncTask<Iteration, Void, List<Iteration>, List<Iteration>> {

    @Nullable
    @Override
    protected List<Iteration> perform(Iteration... Iterations) throws TaskException {
      return IterateDatabase.getInstance().getIterationDao().findHistory(50);
    }

  }

}




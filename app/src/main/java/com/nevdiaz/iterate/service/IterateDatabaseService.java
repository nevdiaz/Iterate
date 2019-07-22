//package com.nevdiaz.iterate.service;
//
//import android.support.annotation.Nullable;
//import androidx.lifecycle.LiveData;
//import com.nevdiaz.iterate.IterateDatabase;
//import com.nevdiaz.iterate.entities.Algorithm;
//import com.nevdiaz.iterate.entities.Iteration;
//import edu.cnm.deepdive.android.FluentAsyncTask;
//import java.sql.Date;
//import java.util.LinkedList;
//import java.util.List;
//
//public class IterateDatabaseService {
//
//  private IterateDatabaseService() {
//  }
//
//  /**
//   * Implements an asynchronous <code>INSERT</code> of one or more {@link Algorithm} instances, with
//   * related {@link Iteration} instances, into the local database.
//   */
//  public static class InsertAlgorithmTask
//      extends FluentAsyncTask<Algorithm, List<Long>, List<Long>> {
//
//    private boolean foreground;
//
//    /**
//     * Initializes <code>INSERT</code> task with <code>foreground</code> indicating intention to
//     * present Iteration immediately in user interface
//     *
//     * @param foreground <code>true</code> if Iteration will be displayed immediately;
//     * <code>false</code> otherwise.
//     */
//    public InsertAlgorithmTask(boolean foreground) {
//      this.foreground = foreground;
//    }
//
//    @Override
//    protected List<Long> perform(Algorithm... Algorithms) {
//      List<Long> AlgorithmIds = IterateDatabase.getInstance().getAlgorithmDao().insert(Algorithms);
//      if (foreground) {
//        List<Iteration> accesses = new LinkedList<>();
//        for (long id : AlgorithmIds) {
//          Iteration access = new Iteration();
//          access.setAlgorithmId(id);
//          accesses.add(access);
//        }
//        IterateDatabase.getInstance().getIterationDao().insert();
//      }
//      return AlgorithmIds;
//    }
//
//  }
//
//  /**
//   * Implements an asynchronous <code>SELECT</code> of a single {@link Algorithm} instance, and an
//   * <code>INSERT</code> of a related {@link Iteration} instance, in the local database.
//   */
//  public static class SelectAlgorithmTask extends FluentAsyncTask<Date, Algorithm, Algorithm> {
//
//  }
//
//  /**
//   * Implements an asynchronous <code>SELECT</code> of all {@link Algorithm} instances (sorted in
//   * descending date order) from the local database.
//   */
//  public static class SelectAllAlgorithmTask
//      extends FluentAsyncTask<Void,List<Algorithm>, List<Algorithm>> {
//
//    @Override
//    protected LiveData<List<Algorithm>> perform(Void... voids) {
//      return IterateDatabase.getInstance().getAlgorithmDao().getAll();
//    }
//
//  }
//
//  /**
//   * Implements an asynchronous <code>DELETE</code> of one or more {@link Iteration} instances from the
//   * local database.
//   */
//  public static class DeleteAlgorithmTask extends FluentAsyncTask<Algorithm, Void, Void> {
//
//    @Nullable
//    @Override
//    protected Void perform(Algorithm... Algorithms)  {
//      IterateDatabase.getInstance().getAlgorithmDao().delete(Algorithms);
//      return null;
//    }
//
//  }
//
//  /**
//   * Implements an asynchronous <code>INSERT</code> of one or more {@link Iteration} instances into the
//   * local database.
//   */
//  public static class GetHistoryTask
//      extends FluentAsyncTask<Iteration, List<Iteration>, List<Iteration>> {
//
//    @Nullable
//    @Override
//    protected List<Iteration> perform(Iteration... Iterations){
//      return IterateDatabase.getInstance().getIterationDao().findHistory(50);
//    }
//
//  }
//
//}
//
//
//

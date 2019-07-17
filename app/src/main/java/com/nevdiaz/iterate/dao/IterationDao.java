package com.nevdiaz.iterate.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import com.nevdiaz.iterate.entities.Iteration;
import java.util.List;

@Dao
public interface IterationDao {

  @Insert
  void insert(Iteration iteration);

  @Query("SELECT * FROM algorithm INNER JOIN Iteration ON " +
      "algorithm.id = iteration.algorithm_id WHERE " +
      "iteration.image_id = :imageId")
  LiveData<List<>> getAlgorithmForImage(long imageId);

  @Delete
  int delete(Iteration... iterations);
}

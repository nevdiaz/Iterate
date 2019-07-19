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

  @Insert
  List<Long> insert(Iteration... iterations);

  @Query("SELECT * FROM algorithm INNER JOIN Iteration ON " +
      "algorithm.id = iteration.algorithm_id WHERE " +
      "iteration.image_id = :imageId")
  LiveData<List<Iteration>> getIterationForImage(long imageId);


  @Query("SELECT * FROM Iteration WHERE algorithm_id= :id ")
  Iteration findById(Long id);

  @Query("SELECT * FROM Iteration ORDER BY algorithm_id DESC")
  List<Iteration> findAll();

  @Query("SELECT * FROM Iteration ORDER BY timestamp DESC LIMIT :limit")
  List<Iteration> findHistory(long limit);

  @Query("SELECT * FROM Iteration WHERE algorithm_id = :algorithm")
  List<Iteration> findGroupByAlgorithm(long algorithm);


  @Query("SELECT * FROM Iteration WHERE image_id = :image")
  List<Iteration> findGroupByImage(long image);


  @Delete
  int delete(Iteration... iterations);
}

package com.nevdiaz.iterate.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import com.nevdiaz.iterate.entities.Algorithm;
import java.util.List;

@Dao
public interface AlgorithmDao {

  @Insert
  void insert(Algorithm algorithm);

  @Query("SELECT * FROM algorithm")
  LiveData<List<Algorithm>> getAll();

  @Query("SELECT * FROM algorithm WHERE id = :id")
  LiveData<Algorithm> findById(Long id);

  @Delete
  int delete (Algorithm algorithm);

}

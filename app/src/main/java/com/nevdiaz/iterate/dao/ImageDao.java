package com.nevdiaz.iterate.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import com.nevdiaz.iterate.entities.Image;
import java.util.List;

@Dao
public interface ImageDao {


  @Insert
  long insert(Image image);

  @Query("SELECT * FROM image")
  LiveData<List<Image>> getAll();

  @Delete
  int delete(Image... images);


}

//    Copyright 2019 Jennifer Nevares-Diaz
//
//    Licensed under the Apache License, Version 2.0 (the "License");
//    you may not use this file except in compliance with the License.
//    You may obtain a copy of the License at
//
//    http://www.apache.org/licenses/LICENSE-2.0
//
//    Unless required by applicable law or agreed to in writing, software
//    distributed under the License is distributed on an "AS IS" BASIS,
//    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//    See the License for the specific language governing permissions and
//    limitations under the License.

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
  long insert(Algorithm algorithm);

  @Insert
  List<Long> insert(Algorithm... algorithms);

  @Insert
  List<Long>  insert(List<Algorithm> algorithms);

  @Query("SELECT * FROM algorithm")
 LiveData<List<Algorithm>> getAll();

  @Query("SELECT * FROM algorithm WHERE id = :id")
  LiveData<Algorithm> findById(Long id);

  @Delete
  int delete(Algorithm... algorithms);

}

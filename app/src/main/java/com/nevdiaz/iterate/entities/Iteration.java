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

package com.nevdiaz.iterate.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
import com.nevdiaz.iterate.service.TimestampConverter;
import java.util.Date;

@Entity(foreignKeys = {
    @ForeignKey(
        entity = Image.class,
        parentColumns = "id",
        childColumns = "image_id"
    ),

    @ForeignKey(
        entity = Algorithm.class,
        parentColumns = "id",
        childColumns = "algorithm_id"
    )},
    indices = {@Index("iteration_id"), @Index(value = {"image_id", "algorithm_id"})})

public class Iteration {

  @PrimaryKey
  @ColumnInfo(name = "iteration_id")
  private long id;

  @ColumnInfo(name = "algorithm_id")
  private long algorithmId;


  @NonNull
  @ColumnInfo(name = "image_id")
  private long imageId;

  @ColumnInfo(name = "timestamp", index = true)
  @TypeConverters({TimestampConverter.class})
  private Date timestamp;

  @ColumnInfo(name = "iterations")
  private String name;

  public Date getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Date timestamp) {
    this.timestamp = timestamp;
  }

//Date timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Timestamp());


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public long getAlgorithmId() {
    return algorithmId;
  }

  public void setAlgorithmId(long algorithmId) {
    this.algorithmId = algorithmId;
  }

  public long getImageId() {
    return imageId;
  }

  public void setImageId(long imageId) {
    this.imageId = imageId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}

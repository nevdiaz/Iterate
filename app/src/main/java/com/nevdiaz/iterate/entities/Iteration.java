package com.nevdiaz.iterate.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(foreignKeys={
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
    indices = {@Index("id"), @Index(value = {"image_id","algorithm_id"})})

public class Iteration {

@PrimaryKey
  private  long id;

  @ColumnInfo(name = "image_id")
  private long imageId;

  @ColumnInfo(name = "algorithm_id")
  private long algorithmId;

  private String timeStamp;



  public String getTimeStamp() {
//    String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Timestamp());
    return timeStamp;
  }

  public void setTimeStamp(String timeStamp) {
    this.timeStamp = timeStamp;
  }


  public  long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public long getImageId() {
    return imageId;
  }

  public void setImageId(long imageId) {
    this.imageId = imageId;
  }

  public long getAlgorithmId() {
    return algorithmId;
  }

  public void setAlgorithmId(long algorithmId) {
    this.algorithmId = algorithmId;
  }


}

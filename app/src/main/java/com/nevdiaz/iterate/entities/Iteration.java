package com.nevdiaz.iterate.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import java.security.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity(foreignKeys = {
    @ForeignKey(
        entity = Algorithm.class,
        parentColumns = "id",
        childColumns = "algorithm_id"
    )},
    indices = {@Index("id"), @Index(value = {"algorithm_id"})})

public class Iteration {

  @PrimaryKey
  private long id;

  @ColumnInfo(name = "algorithm_id")
  private long algorithmId;

  private Date timeStamp;

  public Date getTimeStamp() {
    return timeStamp;
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


}

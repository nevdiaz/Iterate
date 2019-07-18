package com.nevdiaz.iterate.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
import com.nevdiaz.iterate.service.TimestampConverter;
import java.util.Date;

@Entity
public class Image {

  @PrimaryKey
  private long id;

  @ColumnInfo(name = "filename")
  private String filename;

  @ColumnInfo(name = "timestamp", index = true)
  @TypeConverters({TimestampConverter.class})
  private Date timestamp;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getFilename() {
    return filename;
  }

  public void setFilename(String filename) {
    this.filename = filename;
  }

  public Date getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Date timestamp) {
    this.timestamp = timestamp;
  }
}
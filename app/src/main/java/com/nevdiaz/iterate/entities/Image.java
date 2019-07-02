package com.nevdiaz.iterate.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Image {

  private String filename;
  @PrimaryKey
  private long id;

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


}


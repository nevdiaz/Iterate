package com.nevdiaz.iterate;

@Entity
public class Image  {

  @PrimaryKey
  private String filename;
  private final long id = 1L ;

  public long getId() {
    return id;
  }

  public String getFilename() {
    return filename;
  }

  public void setFilename(String filename) {
    this.filename = filename;
  }


}


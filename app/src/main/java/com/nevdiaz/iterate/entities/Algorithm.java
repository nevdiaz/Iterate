package com.nevdiaz.iterate.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Algorithm {

  @PrimaryKey(autoGenerate = true)
  private long id;

  @android.support.annotation.NonNull
  @ColumnInfo(name = "name", index = true)
  private String name = "";

  @ColumnInfo(name = "detail", index = true, collate = ColumnInfo.NOCASE)
  private String detail;// this may become several columns)

  private String example;// this can be a drawable resource or it could be a url.

  private String formula;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getFormula() {
    return formula;
  }

  public void setFormula(String formula) {
    this.formula = formula;
  }

  public String getDetail() {
    return detail;
  }

  public void setDetail(String detail) {
    this.detail = detail;
  }

  public String getExample() {
    return example;
  }

  public void setExample(String example) {
    this.example = example;
  }

  @Override
  public String toString() {
    return name;
  }
}

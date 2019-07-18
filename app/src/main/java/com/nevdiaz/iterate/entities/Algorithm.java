package com.nevdiaz.iterate.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Algorithm {

  @PrimaryKey(autoGenerate = true)
  private long id;

  @ColumnInfo(index = true)
  private String name;

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

  @NonNull
  @Override
  public String toString() {
    return name;
  }
  //what the hell is this jennifer?

}

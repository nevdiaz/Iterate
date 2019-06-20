package com.nevdiaz.iterate;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Algorithm {
  private String formula;

  @PrimaryKey
  private  long id;

  public  long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getFormula() {
    return formula;
  }

  public void setFormula(String formula) {
    this.formula = formula;
  }
}

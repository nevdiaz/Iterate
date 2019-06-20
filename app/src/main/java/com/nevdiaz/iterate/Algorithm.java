package com.nevdiaz.iterate;

@Entity
public class Algorithm {
  @PrimaryKey
  private static long id = 1L;
  private String formula;

  public static long getId() {
    return id;
  }

  public static void setId(long id) {
    Algorithm.id = id;
  }

  public String getFormula() {
    return formula;
  }

  public void setFormula(String formula) {
    this.formula = formula;
  }
}

package com.nevdiaz.iterate;

@Entity
public class Iteration {

  @PrimaryKey
  private static long id = 1L;
  private static long inputId = 1L;
  private static long outputId = 1L;
  private static long algorithmId=1L;
//  private timestamp = date;
  private String Parameter;

  public static long getId() {
    return id;
  }

  public static void setId(long id) {
    Iteration.id = id;
  }

  public static long getInputId() {
    return inputId;
  }

  public static void setInputId(long inputId) {
    Iteration.inputId = inputId;
  }

  public static long getOutputId() {
    return outputId;
  }

  public static void setOutputId(long outputId) {
    Iteration.outputId = outputId;
  }

  public static long getAlgorithmId() {
    return algorithmId;
  }

  public static void setAlgorithmId(long algorithmId) {
    Iteration.algorithmId = algorithmId;
  }

  public String getParameter() {
    return Parameter;
  }

  public void setParameter(String parameter) {
    Parameter = parameter;
  }
}

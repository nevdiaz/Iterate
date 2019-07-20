package com.nevdiaz.iterate.service;

import android.graphics.Bitmap;
import android.view.View;
import java.io.Serializable;

public class IterateOperation implements Serializable {

  private static final long serialVersionUID = 2455031342366739846L;

  /**
   * This method provides {@link Bitmap} and the {@link View} necessary for a particular transform
   * operation to transform.
   *
   * @param src the Bitmap to be transformed.
   * @param view the needed user interface components.
   * @return a bitmap
   */
  public Bitmap algorithm(Bitmap src, View view) {

    return src;

  }

  /**
   * Returns layout id so that the appropriate transform type layout is called.
   *
   * @return an integer representing a layout id.
   */
  public int getLayout() {
    return 0;
  }

}




package com.nevdiaz.iterate.service;

import android.graphics.Bitmap;

public interface ImageAlgorithm {

  ImageAlgorithm setSource(Bitmap bitmap);

  void process();

  Bitmap getInProcess();

  ImageAlgorithm setOnCompletionListener(OnCompletionListener listener);

  interface OnCompletionListener {

    void handle(Bitmap bitmap);
  }


}

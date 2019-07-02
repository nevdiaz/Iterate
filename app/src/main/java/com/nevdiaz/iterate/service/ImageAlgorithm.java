package com.nevdiaz.iterate.service;

import android.graphics.Bitmap;
import android.media.MediaPlayer.OnCompletionListener;

public interface ImageAlgorithm {

  ImageAlgorithm setSource (Bitmap bitmap);

  void process();

  Bitmap getInProcess();

  ImageAlgorithm setOnCompletionListener (OnCompletionListener listener);

  interface OnCompletionListener{
    void handle (Bitmap bitmap);
  }


}

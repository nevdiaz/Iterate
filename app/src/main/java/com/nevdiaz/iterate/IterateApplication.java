package com.nevdiaz.iterate;

import android.app.Application;
import com.facebook.stetho.Stetho;

public class IterateApplication extends Application {

  @Override
  public void onCreate() {
    super.onCreate();
    Stetho.initializeWithDefaults(this);
    new Thread(() -> {
      IterateDatabase.getInstance(this).getAlgorithmDao();

    }).start();
  }
}

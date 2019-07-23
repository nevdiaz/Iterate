package com.nevdiaz.iterate;

import android.app.Application;
import com.facebook.stetho.Stetho;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.nevdiaz.iterate.service.GoogleSignInService;

public class IterateApplication extends Application {

  private GoogleSignInClient client;
  private GoogleSignInAccount account;

  public static IterateApplication instance = null;

  @Override
  public void onCreate() {
    super.onCreate();
    instance = this;
    Stetho.initializeWithDefaults(this);

    IterateDatabase.setContext(this);
    new Thread(() -> {
      IterateDatabase.getInstance().getAlgorithmDao().delete();

    }).start();
    GoogleSignInService.setContext(this);
  }

}

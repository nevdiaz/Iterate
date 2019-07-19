package com.nevdiaz.iterate;

import android.app.Application;
import com.facebook.stetho.Stetho;
import com.google.android.gms.auth.AccountChangeEventsRequest;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;

public class IterateApplication extends Application {

  private GoogleSignInClient client;
  private GoogleSignInAccount account;

  public static IterateApplication instance = null;

  @Override
  public void onCreate() {
    super.onCreate();
    instance = this;
    Stetho.initializeWithDefaults(this);
    new Thread(() -> {
      IterateDatabase.getInstance(this).getAlgorithmDao().delete();

    }).start();
  }

  public static IterateApplication getInstance() {
    return instance;
  }

  public GoogleSignInClient getClient() {
    return client;
  }

  public void setClient(GoogleSignInClient client) {
    this.client = client;
  }

  public GoogleSignInAccount getAccount() {
    return account;
  }

  public void setAccount(GoogleSignInAccount account) {
    this.account = account;
  }

}

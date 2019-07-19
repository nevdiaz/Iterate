package com.nevdiaz.iterate.service;

import android.Manifest;
import android.Manifest.permission;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class Utility {
  public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;

  /**
   * This method will check permission at runtime for Marshmallow %26 greater than Marshmallow
   * version.
   *
   * If current API version is less than Marshmallow, then checkPermission() will return true, which
   * means permission is granted (in Manifest file, no support for runtime permission).
   *
   * If current API version is Marshmallow or greater, and if permission is already granted then the
   * method returns true. Otherwise, the method returns false and will show a dialog box to a user
   * with allow or deny options.
   * @param context provides the {@link Context}
   *
   * @return boolean returns true or false depending on permission.
   */
  @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
  public static boolean checkPermission(final Context context) {
    int currentAPIVersion = Build.VERSION.SDK_INT;
    if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
      if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)
          != PackageManager.PERMISSION_GRANTED) {
        if (ActivityCompat
            .shouldShowRequestPermissionRationale((Activity) context,
                Manifest.permission.READ_EXTERNAL_STORAGE)) {
          AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
          alertBuilder.setCancelable(true);
          alertBuilder.setTitle("Permission necessary");
          alertBuilder.setMessage("External storage permission is necessary");
          alertBuilder
              .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                public void onClick(DialogInterface dialog, int which) {
                  ActivityCompat.requestPermissions((Activity) context,
                      new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                      MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                }
              });
          AlertDialog alert = alertBuilder.create();
          alert.show();
        } else {
          ActivityCompat.requestPermissions((Activity) context,
              new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
              MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
        }
        return false;
      } else {
        if (ContextCompat.checkSelfPermission(context, permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED){
          ActivityCompat.requestPermissions((Activity) context,
              new String[]{permission.CAMERA},
              MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
          return false;
        }else{
          return true;
        }
      }
    } else {
      return true;
    }
  }

}

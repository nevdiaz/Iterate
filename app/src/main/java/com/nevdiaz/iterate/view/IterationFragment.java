//    Copyright 2019 Jennifer Nevares-Diaz
//
//    Licensed under the Apache License, Version 2.0 (the "License");
//    you may not use this file except in compliance with the License.
//    You may obtain a copy of the License at
//
//    http://www.apache.org/licenses/LICENSE-2.0
//
//    Unless required by applicable law or agreed to in writing, software
//    distributed under the License is distributed on an "AS IS" BASIS,
//    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//    See the License for the specific language governing permissions and
//    limitations under the License.

package com.nevdiaz.iterate.view;


import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import com.nevdiaz.iterate.IterateDatabase;
import com.nevdiaz.iterate.R;
import com.nevdiaz.iterate.entities.Algorithm;
import com.nevdiaz.iterate.entities.Iteration;
import com.nevdiaz.iterate.service.ImageAlgorithm;
import com.nevdiaz.iterate.service.Utility;
import com.nevdiaz.iterate.viewmodel.IterationViewModel;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class IterationFragment extends Fragment implements ImageAlgorithm.OnCompletionListener {

  private IterationViewModel mViewModel;
  private Spinner spinner;
  private ImageView imageView;
  private String userChosenTask;
  private ImageButton imageButton;


  /**
   * This field set when user chooses to access an image file.
   */
  private static final int SELECT_FILE = 1;

  /**
   * This field set when user chooses to access the camera.
   */
  private static final int REQUEST_CAMERA = 2;

  /**
   * List which holds instances of image transform algorithms.
   */
  private List<Algorithm> algorithms;

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.iteration_fragment, container, false);

    imageView = view.findViewById(R.id.imageView);
    spinner = view.findViewById(R.id.spinner);
    spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        Algorithm algorithm = (Algorithm) adapterView.getItemAtPosition(position);
        if (algorithm.getId() != 0
            && algorithm.getFormula() != null
            && imageView.getDrawable() != null) {
          try {
            Class<? extends ImageAlgorithm> formula =
                (Class<? extends ImageAlgorithm>) getClass()
                    .getClassLoader().loadClass(algorithm.getFormula());
            ImageAlgorithm operation = formula.newInstance();
            operation.setOnCompletionListener(IterationFragment.this);
            operation.setSource(((BitmapDrawable) imageView.getDrawable()).getBitmap());
            operation.process(); // Do it!!!!!!
          } catch (ClassNotFoundException e) {
            e.printStackTrace();
          } catch (IllegalAccessException e) {
            e.printStackTrace();
          } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
          }
        }
      }

      @Override
      public void onNothingSelected(AdapterView<?> adapterView) {
      }
    });

    imageButton = view.findViewById(R.id.imageButton);
    imageButton.setOnClickListener((View v) -> {
      selectImage();
    });
    return view;

  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    mViewModel = ViewModelProviders.of(this).get(IterationViewModel.class);
    mViewModel.getAlgorithms().observe(this, (algorithms) -> {
      Algorithm algorithm = new Algorithm();
      algorithm.setName("SELECT AN ALGORITHM");
      algorithms.add(0, algorithm);
      ArrayAdapter<Algorithm> adapter =
          new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, algorithms);
      spinner.setAdapter(adapter);
    });
  }

  @Override
  public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
    super.onCreateOptionsMenu(menu, inflater);
  }

//
//  /**
//   * Sets the popup menu to display choices of transform operations.  Inflates the chosen {@link
//   * AlgorithmPickerDialogFragment} alert dialog so that user can enter information needed for the
//   * chosen transform type.
//   *
//   * @param v current {@link View} instance of popup menu.
//   */
//  private void showPopup(View v) {
//    PopupMenu popup = new PopupMenu(getContext(), v);
//    // TODO read from database instead of inflating.
//    MenuInflater inflater = popup.getMenuInflater();
//    inflater.inflate(R.menu.algorithm_options, popup.getMenu());
//
//    for (Algorithm algorithm: algorithms) {
//
//      popup.getMenu()
//          .add(algorithm.getName())
//          .setOnMenuItemClickListener((item) -> {
//            try {
//              if (algorithm.getFormula()!=null) {
//                Class<? extends ImageAlgorithm> formula =
//                    (Class<? extends ImageAlgorithm>) getClass()
//                        .getClassLoader().loadClass(algorithm.getFormula());
//                ImageAlgorithm operation = formula.newInstance();
//                operation.setOnCompletionListener(this);
//                operation.process(); // Do it!!!!!!
////                operation.setSource(/* some bitmap */); // here add winston
//              }
//              return true;
//            } catch (ClassNotFoundException | IllegalAccessException | java.lang.InstantiationException e) {
//              e.printStackTrace();
//              return false;
//            }
//          });
//    }
//
//    popup.setOnMenuItemClickListener(menuItem -> {
//      Bitmap result = operation.algorithm(access.getBitmap(), view);
//      // TODO Update database, local storage.
//      access.setBitmap(result, getArguments().getLong(TRANSFORM_ID_KEY));
//      float stDev = standardDeviation.getProgress();
//      preferences = getActivity().getSharedPreferences(" ", MODE_PRIVATE);
//      final SharedPreferences.Editor editor = preferences.edit();
//      editor.putFloat("Blur", stDev);
//      editor.apply();
//      standardDeviation.setProgress(preferences.getInt(PROGRESS, 0));
//    });
////        .setNegativeButton(R.string.algorithms_cancel,
////            (dialog, which) -> {
////            });
////      showNoticeDialog("Iteration1");
////      return true;
////    });
//    popup.show();
//  }

//  @Override
//  public Bitmap getBitmap() {
//    return ((BitmapDrawable) imageView.getDrawable()).getBitmap();
//  }
//
//  /**
//   * This method makes it easy to load a bitmap of arbitrarily large size into a file.  This method
//   * is also used to set data regarding the {@link Image} entity into the data base.
//   *
//   * @param bitmap bitmap to be compressed and stored in the gallery file and in the database.
//   * @param transId the id of the type of transform applied to the image.
//   */
//  @Override
//  public void setBitmap(Bitmap bitmap, long transId) {
//    imageView.setImageBitmap(bitmap);
//
//    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//    bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
//    if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
//    }
//    File destination = new File(getExternalFilesDir(null),
//        System.currentTimeMillis() + ".jpg");
//    FileOutputStream fo;
//    try {
//      destination.createNewFile();
//      destination.toURL();
//      fo = new FileOutputStream(destination);
//      fo.write(bytes.toByteArray());
//      fo.close();
//    } catch (FileNotFoundException e) {
//      e.printStackTrace();
//    } catch (IOException e) {
//      e.printStackTrace();
//    }
//    Iteration iteration = new Iteration();
//    iteration.setTimestamp(new Date());
//    iteration.setInternalURL(destination.toString());
//    iteration.setTransformId(transId);
//    new BaseFluentAsyncTask<Void, Void, Long, Long>()
//        .setPerformer((ignore) -> IterateDatabase.getIterationDao().insert(iteration))
//        .execute();
//  }


  /**
   * Alert dialog which invokes {@link #galleryIntent()} or {@link #cameraIntent()} or cancels the
   * dialog depending on the choice of the user.
   */
  private void selectImage() {
    final CharSequence[] items = {"Take Photo", "Choose from Library",
        "Cancel"};

    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
    builder.setTitle("Add Photo!");
    builder.setItems(items, (dialog, item) -> {
      boolean result = Utility.checkPermission(getContext());

      if (items[item].equals("Take Photo")) {
        userChosenTask = "Take Photo";
        if (result) {
          cameraIntent();
        }

      } else if (items[item].equals("Choose from Library")) {
        userChosenTask = "Choose from Library";
        if (result) {
          galleryIntent();
        }

      } else if (items[item].equals("Cancel")) {
        dialog.dismiss();
      }
    });
    builder.show();
  } // end selectImage()

  /**
   * This method, when invoked, activates an intent which provides access to the camera.
   */
  private void cameraIntent() {
    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    startActivityForResult(intent, REQUEST_CAMERA);
  }

  /**
   * This method, when invoked, activates an intent which provides access to the image gallery
   * files.
   */
  private void galleryIntent() {
    Intent intent = new Intent();
    intent.setType("image/*");
    intent.setAction(Intent.ACTION_GET_CONTENT);//
    startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);

  }

  /**
   * onRequestPermissionsResult() is inbuilt method which receives a callback of this dialog action
   * for particular activity from which checkPermssion() has been called.If permission has been
   * granted then the value of grantResults[0] would be PERMISSION_GRANTED. And if permission has
   * been revoked then the value of grantResults[0] would be PERMISSION_DENIED.
   *
   * Here, if permission has been granted, then the method for the specific {@link Intent} is
   * invoked according to the value of the userChosenTask variable.
   */
  @Override
  public void onRequestPermissionsResult(int requestCode,
      @android.support.annotation.NonNull String[] permissions,
      @android.support.annotation.NonNull int[] grantResults) {
    switch (requestCode) {
      case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
          if (userChosenTask.equals("Take Photo")) {
            cameraIntent();
          } else if (userChosenTask.equals("Choose from Library")) {
            galleryIntent();
          }
        } else {
          //code for deny
        }
        break;
    }
  }

  /**
   * The image either from camera or from gallery, is handled here. Handle the result received by
   * calling startActivityForResult() Method.
   *
   * @param requestCode The request code is what has been passed to startActivityForResult(). In
   * this case it is REQUEST_CAMERA.
   * @param resultCode RESULT_OK if the operation was successful or RESULT_CANCEL if the operation
   * was somehow cancelled or unsuccessful.
   * @param data The intent carries the result data – in this case it is the image captured from the
   * camera.
   */
  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (resultCode == Activity.RESULT_OK) {
      if (requestCode == SELECT_FILE) {
        onSelectFromGalleryResult(data);
      } else if (requestCode == REQUEST_CAMERA) {
        onCaptureImageResult(data);
      }
    }
  }

  /**
   * Sets a Bitmap as the content of this ImageView when the user has chosen to select a file from
   * the gallery.
   *
   * @param data Intent passed to this method when invoked in {@link #onActivityResult(int, int,
   * Intent)} containing information regarding the particular image file chosen.
   */
  private void onSelectFromGalleryResult(Intent data) {
    Bitmap bm = null;
    if (data != null) {
      try {
        bm = MediaStore.Images.Media
            .getBitmap(getContext().getContentResolver(), data.getData());
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    imageView.setImageBitmap(bm);
  }


  private void onCaptureImageResult(Intent data) {
    Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    thumbnail.compress(Bitmap.CompressFormat.JPEG, 75, bytes);
    File destination = new File(Environment.getExternalStorageDirectory(),
        System.currentTimeMillis() + ".jpg");
    FileOutputStream fo;
    try {
      destination.createNewFile();
      destination.toURL();
      Iteration iteration = new Iteration();
      iteration.setName(destination.toString());
      IterateDatabase.getIterationDao().insert(iteration);
      fo = new FileOutputStream(destination);
      fo.write(bytes.toByteArray());
      fo.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    imageView
        .setImageBitmap(thumbnail);
  }

  @Override
  public void handle(Bitmap bitmap) {
    //TODO save the bitmap update the database
    imageView.setImageBitmap(bitmap);
  }

}

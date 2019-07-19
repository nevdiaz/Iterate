package com.nevdiaz.iterate.view;


import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import com.nevdiaz.iterate.IterateDatabase;
import com.nevdiaz.iterate.R;
import com.nevdiaz.iterate.entities.Algorithm;
import com.nevdiaz.iterate.entities.Iteration;
import com.nevdiaz.iterate.service.IterateOperation;
import com.nevdiaz.iterate.service.Utility;
import com.nevdiaz.iterate.viewmodel.IterationViewModel;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public class IterationFragment extends Fragment {

  private IterationViewModel mViewModel;
  private Spinner spinner;
  private ImageView imageView;
  private Button iterateButton;
  private String userChosenTask;
  private static IterateDatabase db;



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


  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.iteration_fragment, container, false);
//    db = IterateDatabase.getInstance();

    iterateButton = view.findViewById(R.id.iterate_button);
    imageView = view.findViewById(R.id.imageView);
    spinner = view.findViewById(R.id.spinner);

//    final ImageButton startCamera = view.findViewById(R.id.imageButton);
//    startCamera.setOnClickListener(new OnClickListener() {
//      @Override
//      public void onClick(View v) {
//        Fragment fragment = new Camera2BasicFragment();
//        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id.container, fragment);
//        fragmentTransaction.addToBackStack(null);
//        fragmentTransaction.commit();
//      }
//    });
//    return view;

    final ImageButton startCamera = view.findViewById(R.id.imageButton);
    startCamera.setOnClickListener((View v) -> {
      selectImage();
    });
    return view;

  }


  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    return super.onCreateOptionsMenu(menu);
  }

  /**
   * Sets the popup menu to display choices of transform operations.  Inflates the chosen {@link
   * TransformPickerDialogFragment} alert dialog so that user can enter information needed for the
   * chosen transform type.
   *
   * @param v current {@link View} instance of popup menu.
   */
  private void showPopup(View v) {
    PopupMenu popup = new PopupMenu(this, v);
    // TODO read from database instead of inflating.
    MenuInflater inflater = popup.getMenuInflater();
    inflater.inflate(R.menu.transform_options, popup.getMenu());

    for (Transform transform : transforms) {

      popup.getMenu()
          .add(transform.getName())
          .setOnMenuItemClickListener((item) -> {
            try {
              if (transform.getFormula()!=null) {
                Class<? extends IterateOperation> clazz =
                    (Class<? extends IterateOperation>) getClass()
                        .getClassLoader().loadClass(transform.getClazz());
                IterateOperation operation = clazz.newInstance();
                DialogFragment dialogFragment = TransformPickerDialogFragment
                    .newInstance(operation, transform.getId());
                dialogFragment.show(getSupportFragmentManager(),
                    dialogFragment.getClass().getSimpleName());
              }
              return true;
            } catch (ClassNotFoundException | IllegalAccessException | java.lang.InstantiationException e) {
              e.printStackTrace();
              return false;
            }
          });
    }

    popup.setOnMenuItemClickListener(menuItem -> {
      showNoticeDialog("Iteration1");
      return true;
    });
    popup.show();
  }

  /**
   * Create an instance of the dialog fragment and show it.
   */
  public void showNoticeDialog(String str) {

    DialogFragment dialogFragment = new TransformPickerDialogFragment();
    dialogFragment.show(getSupportFragmentManager(), "Notice Dialog Fragment");
  }

  @Override
  public boolean onMenuItemClick(MenuItem item) {
    return false;
  }

  @Override
  public Bitmap getBitmap() {
    return ((BitmapDrawable) imageView.getDrawable()).getBitmap();
  }

  /**
   * This method makes it easy to load a bitmap of arbitrarily large size into a file.  This method
   * is also used to set data regarding the {@link Image} entity into the data base.
   *
   * @param bitmap bitmap to be compressed and stored in the gallery file and in the database.
   * @param transId the id of the type of transform applied to the image.
   */
  @Override
  public void setBitmap(Bitmap bitmap, long transId) {
    imageView.setImageBitmap(bitmap);

    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
    if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
    }
    File destination = new File(getExternalFilesDir(null),
        System.currentTimeMillis() + ".jpg");
    FileOutputStream fo;
    try {
      destination.createNewFile();
      destination.toURL();
      fo = new FileOutputStream(destination);
      fo.write(bytes.toByteArray());
      fo.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    Image image = new Image();
    image.setTimestamp(new Date());
    image.setInternalURL(destination.toString());
    image.setTransformId(transId);
    new BaseFluentAsyncTask<Void, Void, Long, Long>()
        .setPerformer((ignore) -> IterateDatabase.getImageDao().insert(image))
        .execute();
  }

  private class TransformListQuery extends
      AsyncTask<Void, Void, List<Iteration>> {


    @Override
    protected List<Iteration> doInBackground(Void... voids) {
      return IterateDatabase.getInstance().getIterationDao().findAll();
    }

    @Override
    protected void onPostExecute(List<Iteration> transforms) {
      IterationFragment.this.iterates = transforms;
    }

  }

  /**
   * Alert dialog which invokes {@link #galleryIntent()} or {@link #cameraIntent()} or cancels the
   * dialog depending on the choice of the user.
   */
  private void selectImage() {
    final CharSequence[] items = {"Take Photo", "Choose from Library",
        "Cancel"};

    AlertDialog.Builder builder = new AlertDialog.Builder( this);
    builder.setTitle("Add Photo!");
    builder.setItems(items, (dialog, item) -> {
      boolean result = Utility.checkPermission(context(IterationFragment));

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
      @android.support.annotation.NonNull String[] permissions, @android.support.annotation.NonNull int[] grantResults) {
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
            .getBitmap(getApplicationContext().getContentResolver(), data.getData());
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
      iteration.setInternalURL(destination.toString());
      IterateDatabase.getAlgorithmDao().insert(iteration);
      fo = new FileOutputStream(destination);
      fo.write(bytes.toByteArray());
      fo.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    imageView
        .setImageBitmap(thumbnail);
  }

}

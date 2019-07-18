package com.nevdiaz.iterate.view;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import com.nevdiaz.iterate.R;
import com.nevdiaz.iterate.entities.Algorithm;
import com.nevdiaz.iterate.viewmodel.IterationViewModel;

public class IterationFragment extends Fragment {

  private IterationViewModel mViewModel;
  private Spinner spinner;

  public static IterationFragment newInstance() {
    return new IterationFragment();
  }

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.iteration_fragment, container, false);
    spinner = view.findViewById(R.id.spinner);

    final ImageButton startCamera = view.findViewById(R.id.imageButton);
    startCamera.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        Fragment fragment = new Camera2BasicFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
      }
    });    return view;

  }
  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    mViewModel = ViewModelProviders.of(this).get(IterationViewModel.class);
    mViewModel.getAlgorithms().observe(this, (algorithms) -> {
      ArrayAdapter<Algorithm> adapter =
          new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, algorithms);
      spinner.setAdapter(adapter);
    });
  }

// TODO  Add button


//  public static ArrayList<String> getImagesPath(Activity activity) {
//    Uri uri;
//    ArrayList<String> listOfAllImages = new ArrayList<String>();
//    Cursor cursor;
//    int column_index_data, column_index_folder_name;
//    String PathOfImage = null;
//    uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
//
//    String[] projection = { MediaColumns.DATA,
//        MediaStore.Images.Media.BUCKET_DISPLAY_NAME };
//
//    cursor = activity.getContentResolver().query(uri, projection, null,
//        null, null);
//
//    column_index_data = cursor.getColumnIndexOrThrow(MediaColumns.DATA);
//    column_index_folder_name = cursor
//        .getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
//    while (cursor.moveToNext()) {
//      PathOfImage = cursor.getString(column_index_data);
//
//      listOfAllImages.add(PathOfImage);
//    }
//    return listOfAllImages;
//  }


}

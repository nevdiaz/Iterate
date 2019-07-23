//package com.nevdiaz.iterate.view;
//
//import static android.content.Context.MODE_PRIVATE;
//
//import android.app.Dialog;
//import android.content.Context;
//import android.content.SharedPreferences;
//import android.graphics.Bitmap;
//import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.widget.SeekBar;
//import android.widget.SeekBar.OnSeekBarChangeListener;
//import androidx.appcompat.app.AlertDialog;
//import androidx.fragment.app.DialogFragment;
//import com.nevdiaz.iterate.R;
//import com.nevdiaz.iterate.service.IterateOperation;
//
//public class AlgorithmPickerDialogFragment extends DialogFragment {
//
//  private static final String ITERATE = "iteration";
//  private static final String ALGORITHM_ID = "algorithmId";
//  // Use this instance of the interface to deliver action events
//  private BitmapAccess access;
//  private IterateOperation operation;
//  private SharedPreferences preferences;
//  private static final String PROGRESS = "SEEKBAR";
//
//
//  /**
//   * Constructs an instance of the alert dialog which is tailored according to the type of transform
//   * operation chosen by the user.
//   *
//   * @param operation passes in instance of {@link IterateOperation} which contains methods
//   * responsible for getting the correct dialog layout and the associated bitmap.
//   * @param transformId the id of the transform type being applied to the image.
//   */
//  static AlgorithmPickerDialogFragment newInstance(
//      IterateOperation operation, long transformId) {
//    Bundle args = new Bundle();
//    args.putSerializable(ITERATE, operation);
//    args.putLong(ALGORITHM_ID, transformId);
//    AlgorithmPickerDialogFragment fragment = new AlgorithmPickerDialogFragment();
//    fragment.setArguments(args);
//    return fragment;
//  }
//
//  @Override
//  public void onCreate(@Nullable Bundle savedInstanceState) {
//    super.onCreate(savedInstanceState);
//    operation = (IterateOperation) getArguments()
//        .getSerializable(ITERATE);
//
//  }
//
//  /**
//   * Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
//   */
//  @Override
//  public void onAttach(Context context) {
//    super.onAttach(context);
//    access = (BitmapAccess) context;
//  }
//
//  @NonNull
//  @Override
//  public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
//
//    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//    //Get the layout inflater
//    LayoutInflater inflater = requireActivity().getLayoutInflater();
//    //Inflate and set the layout for the dialog
//    //pass null as the parent view because it is going on the dialog layout.
//    View view = inflater.inflate(operation.getLayout(), null);
//
//
//    return builder.create();
//  }
//
//  /**
//   * The activity that creates an instance of this dialog fragment must implement this interface in
//   * order to receive event callbacks. Each method passes the DialogFragment in case the host needs
//   * to query it.
//   */
//  public interface BitmapAccess {
//
//    Bitmap getBitmap();
//
//    void setBitmap(Bitmap bitmap, long aLong);
//  }
//
//}

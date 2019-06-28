package com.nevdiaz.iterate.fragments;

import android.widget.ArrayAdapter;
import android.widget.Spinner;
import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    View view =  inflater.inflate(R.layout.iteration_fragment, container, false);
    spinner = view.findViewById(R.id.spinner);
    return view;
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

}

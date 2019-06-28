package com.nevdiaz.iterate.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.nevdiaz.iterate.IterateDatabase;
import com.nevdiaz.iterate.entities.Algorithm;
import java.util.List;

public class IterationViewModel extends AndroidViewModel {

  public IterationViewModel(@NonNull Application application) {
    super(application);
  }
  // TODO: Implement the ViewModel

  public LiveData<List<Algorithm>> getAlgorithms() {
    return IterateDatabase.getInstance(getApplication()).getAlgorithmDao().getAll();
  }

}

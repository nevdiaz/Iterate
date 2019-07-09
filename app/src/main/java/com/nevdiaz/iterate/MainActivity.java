package com.nevdiaz.iterate;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.nevdiaz.iterate.fragments.Camera2BasicFragment;
import com.nevdiaz.iterate.fragments.IterationFragment;

public class MainActivity extends AppCompatActivity {

  private TextView mTextMessage;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    mTextMessage = (TextView) findViewById(R.id.message);
    BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
    NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);

    NavigationUI.setupWithNavController(navigation, navController);
  }
  private boolean loadFragment(Fragment fragment) {
    if (fragment != null) {

      getSupportFragmentManager()
          .beginTransaction()
          .replace(R.id.nav_host_fragment, fragment)
          .commit();

      return true;
    }
    return false;
  }

  public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

    Fragment fragment = null;

    switch (menuItem.getItemId()) {

      case R.id.iterationFragment:
        fragment = new IterationFragment();
        break;

      case R.id.camera2BasicFragment:
        fragment = new Camera2BasicFragment();
        break;

    }

    return loadFragment(fragment);
  }


}

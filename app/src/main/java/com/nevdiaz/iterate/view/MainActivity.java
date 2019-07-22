//Copyright 2019 Jennifer Nevares-Diaz
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

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.nevdiaz.iterate.R;
import com.nevdiaz.iterate.service.GoogleSignInService;

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

      case R.id.galleryFragment:
        fragment = new GalleryFragment();
        break;

    }

    return loadFragment(fragment);
  }

  /**
   * Creates and inflates the options menu.
   * @param menu the menu
   * @return true
   */
  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.options, menu);
    return true;
  }
  /**
   * Currently contains the only option in the options menu, to sign out.
   * @param item item selected from the {@link Menu}
   * @return {@link Boolean} if the {@link android.content.ClipData.Item} was selected
   */
  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    boolean handled = true;
    switch (item.getItemId()) {
      case R.id.sign_out:
        signOut();
        break;
      default:
        handled = super.onOptionsItemSelected(item);
    }
    return handled;
  }
  //TODO create a reset option in the options menu!

  /**
   * Initialized a GoogleSignInService and sets the account to null, i.e. signing out.
   */
  private void signOut() {
    GoogleSignInService service = GoogleSignInService.getInstance();
    service.getClient().signOut().addOnCompleteListener((task) -> {
      service.setAccount(null);
      Intent intent = new Intent(this, LoginActivity.class);
      intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
      startActivity(intent);
    });
  }

}

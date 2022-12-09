package com.solvit.mobile.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.solvit.mobile.R;

import com.solvit.mobile.databinding.ActivityNavigationDrawerBinding;
import com.solvit.mobile.ui.fragments.completed.CompletedFragment;
import com.solvit.mobile.ui.fragments.newnotification.NewNotificationFragment;
import com.solvit.mobile.ui.fragments.pending.PendingFragment;


public class NavigationDrawerActivity extends AppCompatActivity {

    // variable for role access
    private  static String collection_path;

    private static final String TAG = "Navigation Drawer";
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityNavigationDrawerBinding binding;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // check if the user is logged in
        mAuth = FirebaseAuth.getInstance();
        binding = ActivityNavigationDrawerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        setSupportActionBar(binding.appBarNavigationDrawer.toolbar);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_pending, R.id.nav_completed, R.id.nav_users, R.id.nav_newnotification, R.id.nav_profile, R.id.nav_signout)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_navigation_drawer);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        // set navigation header details
        if(mAuth.getCurrentUser() != null) {
            ((TextView) binding.navView.getHeaderView(0).findViewById(R.id.tvHeaderDisplayName)).setText(mAuth.getCurrentUser().getDisplayName());
            ((TextView) binding.navView.getHeaderView(0).findViewById(R.id.tvHeaderEmail)).setText(mAuth.getCurrentUser().getEmail());
        }

        // check the user role access hide list of users if is not admin
        SharedPreferences sharedPref = this.getSharedPreferences("loginPref", Context.MODE_PRIVATE);
        String role = sharedPref.getString("role", "TIC");
        int collectionPath = sharedPref.getInt("collectionPath", R.string.collectionIt);
        setCollection_path(getResources().getString(collectionPath));
        Log.d(TAG, "collectionspath: " + getCollection_path());
        Log.d(TAG, "role: " + sharedPref.getAll());
        if(!role.equals("ADMIN")){
            navigationView.getMenu().findItem(R.id.nav_users).setVisible(false);
        }

        // bind de fab button
        binding.appBarNavigationDrawer.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_content_navigation_drawer);

                if( navHostFragment != null){
                    Fragment currentFragment = navHostFragment.getChildFragmentManager().getFragments().get(0);

                    if (currentFragment instanceof PendingFragment) {
                        Toast.makeText(getApplicationContext(), "Home Fragment", Toast.LENGTH_SHORT).show();
                        navController.navigate(R.id.nav_newnotification);


                    } else if (currentFragment instanceof CompletedFragment) {
                        Toast.makeText(getApplicationContext(), "Slideshow Fragment", Toast.LENGTH_SHORT).show();
                        navController.navigate(R.id.nav_newnotification);


                    } else if (currentFragment instanceof NewNotificationFragment) {
                        Toast.makeText(getApplicationContext(), "Gallery Fragment", Toast.LENGTH_SHORT).show();
                        binding.appBarNavigationDrawer.fab.setVisibility(View.INVISIBLE);
                    }
                }

            }
        });

        Log.d(TAG, "onCreate: " + navigationView.getMenu().findItem(R.id.nav_signout));
        // position of sign out is 3
        navigationView.getMenu().findItem(R.id.nav_signout).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                new AlertDialog.Builder(NavigationDrawerActivity.this)
                        .setTitle("Salir")
                            .setMessage("Quieres salir de la aplicion")
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setPositiveButton("Ok", (dialogInterface, i) -> {
                                FirebaseAuth.getInstance().signOut();
                                //navController.navigate(R.id.nav_signout);
                                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();
                            })
                            .setNegativeButton("Cancel", (dialogInterface, i) -> {
                                drawer.closeDrawers();
                            })
                          .show();
                return true;
            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                // opens in a new stack avoids reapearing when the backbutton is pressed
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_navigation_drawer);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        // makes the back button go to the first fragment before exit
        DrawerLayout drawer = binding.drawerLayout;
        assert drawer != null;
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser == null ){
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public static String getCollection_path() {
        return collection_path;
    }

    public void setCollection_path(String collection_path) {
        this.collection_path = collection_path;
    }
}
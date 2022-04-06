package com.light1;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.light1.databinding.ActivityMainBinding;
import com.light1.ui.todo_list.BottomSheetFragment;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    BottomSheetFragment bottomSheetFragment;
    com.light1.ui.todo_list.todoListManager todoListManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bottomSheetFragment = new BottomSheetFragment();

        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
            .hide(bottomSheetFragment)
            .commit();

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_bluetooth_manager, R.id.navigation_time_manager, R.id.navigation_todo_list_manager)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController navController, @NonNull NavDestination navDestination, @Nullable Bundle bundle) {
                FragmentManager fm = getSupportFragmentManager();
                fm.beginTransaction()
                        .addToBackStack(null)
                        .remove(bottomSheetFragment)
                        .commit();
            }
        });

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        }

    }

    public void callUPBottomSheetFragment(){
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
            .addToBackStack(null)
            .replace(R.id.nav_host_fragment_activity_main, bottomSheetFragment)
            .show(bottomSheetFragment)
            .commit();
    }

}
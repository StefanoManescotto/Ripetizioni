package com.example.ripetizioni_android.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.ripetizioni_android.R;
import com.example.ripetizioni_android.controller.fragments.AccountFragment;
import com.example.ripetizioni_android.controller.fragments.HomeFragment;
import com.example.ripetizioni_android.controller.fragments.PrenFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigationView = findViewById(R.id.navigation_menu);
        navigationView.setOnItemSelectedListener(item -> onNavMenuClick((MenuItem) item));

        loadHomeFragment();
    }

    private boolean onNavMenuClick(MenuItem item){
        switch (item.getItemId()){
            case R.id.menu_home:
                loadHomeFragment();
                break;
            case R.id.menu_pren:
                loadPrenFragment();
                break;
            case R.id.menu_account:
                loadAccountFragment();
                break;
        }
        return true;
    }

    public void loadHomeFragment(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragmentLayout, HomeFragment.class, null).commit();
    }

    public void loadPrenFragment(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragmentLayout, PrenFragment.class, null).commit();
    }

    public void loadAccountFragment(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragmentLayout, AccountFragment.class, null).commit();
    }
}

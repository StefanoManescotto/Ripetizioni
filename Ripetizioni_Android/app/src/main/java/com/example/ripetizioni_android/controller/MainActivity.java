package com.example.ripetizioni_android.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import okhttp3.CookieJar;
import okhttp3.OkHttpClient;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.ripetizioni_android.R;
import com.example.ripetizioni_android.controller.fragments.AccountFragment;
import com.example.ripetizioni_android.controller.fragments.HomeFragment;
import com.example.ripetizioni_android.controller.fragments.LogInFragment;
import com.example.ripetizioni_android.controller.fragments.PrenFragment;
import com.example.ripetizioni_android.model.AuthenticateModel;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.concurrent.TimeUnit;


public class MainActivity extends AppCompatActivity {
    public static OkHttpClient client;

    public static final String SHARED_PREFERENCES = "sharedPrefs";
    public static final String ACCOUNT_NAME = "name";
    public static final String ACCOUNT_SURNAME = "surname";
    public static final String ACCOUNT_EMAIL = "email";
    public static final String SESSION_COOKIE = "session-cookie";
    private SharedPreferences sharedPreferences;

    private BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CookieJar cookieJar = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(this));
        client = new OkHttpClient().newBuilder()
                .cookieJar(cookieJar)
                .connectTimeout(2, TimeUnit.SECONDS)
                .build();

        CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);

        navigationView = findViewById(R.id.navigation_menu);
        navigationView.setOnItemSelectedListener(this::onNavMenuClick);

        sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, this.MODE_PRIVATE);

        loadHomeFragment();
    }

    private boolean onNavMenuClick(MenuItem item){
        switch (item.getItemId()){
            case R.id.menu_home:
                loadHomeFragment();
                break;
            case R.id.menu_pren:
                if(!sharedPreferences.getString(ACCOUNT_EMAIL, "null").equals("null")){
                    loadPrenFragment();
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage("Esegui il LogIn per visualizzare le prenotazioni effettuate")
                            .setPositiveButton("LogIn", (d, w) -> {
                                navigationView.setSelectedItemId(R.id.menu_account);
                                loadAccountFragment();
                            })
                            .setNegativeButton("Indietro",  (d, w) -> {
                                navigationView.setSelectedItemId(R.id.menu_home);
                                loadHomeFragment();
                            })
                            .show();
                }
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
        if(sharedPreferences.getString(ACCOUNT_EMAIL, "null").equals("null")){
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragmentLayout, LogInFragment.class, null).commit();
        }else{
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragmentLayout, AccountFragment.class, null).commit();
        }
    }

    public void changeViewToAccount(){
        navigationView.setSelectedItemId(R.id.menu_account);
        loadAccountFragment();
    }

    public void disableTabBar(){
        Menu menu = navigationView.getMenu();
        for(int i = 0; i < menu.size(); i++){
            menu.getItem(i).setEnabled(false);
        }
    }

    public void enableTabBar(){
        Menu menu = navigationView.getMenu();
        for(int i = 0; i < menu.size(); i++){
            menu.getItem(i).setEnabled(true);
        }
    }

    public void executeAuth(){
        (new AuthenticateModel(this)).execute(sharedPreferences.getString(MainActivity.SESSION_COOKIE, ""));
    }

    public void changeAccountView(){
        (new AuthenticateModel(this)).execute(sharedPreferences.getString(MainActivity.SESSION_COOKIE, ""));
        navigationView.setSelectedItemId(R.id.menu_home);
        loadHomeFragment();
    }

    public void setEmptySharedPreferences(){
        sharedPreferences.edit().clear().apply();
    }

    public void setSharedPreferences(String name, String surname, String email){
        sharedPreferences.edit().putString(ACCOUNT_NAME, name).apply();
        sharedPreferences.edit().putString(ACCOUNT_SURNAME, surname).apply();
        sharedPreferences.edit().putString(ACCOUNT_EMAIL, email).apply();
    }
}

package com.example.ripetizioni_android.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.ripetizioni_android.R;
import com.example.ripetizioni_android.controller.fragments.TestFragment;

public class MainActivity extends AppCompatActivity {

    private Button fragmentBtn;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.miaText);
        textView.setText("TEST TEXT");

        //fragmentBtn.setOnClickListener();
    }

    public void onClickFragment(View v){
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragmentLayout, TestFragment.class, null).commit();
    }

    public void onClickTest(View v){
        textView.setText("test click");
    }
}

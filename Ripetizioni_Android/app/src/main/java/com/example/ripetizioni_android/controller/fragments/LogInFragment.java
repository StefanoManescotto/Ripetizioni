package com.example.ripetizioni_android.controller.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ripetizioni_android.R;
import com.example.ripetizioni_android.controller.MainActivity;
import com.example.ripetizioni_android.model.LogInModel;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class LogInFragment extends Fragment {

    private SharedPreferences sharedPreferences;
    private EditText emailTxt, passwordTxt;
    private Button btnLogIn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_account_login, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        sharedPreferences = getContext().getSharedPreferences(MainActivity.SHARED_PREFERENCES, Context.MODE_PRIVATE);

        emailTxt = view.findViewById(R.id.edtTextEmail);
        passwordTxt = view.findViewById(R.id.edtTextPsw);
        btnLogIn = view.findViewById(R.id.btnLogIn);
        btnLogIn.setOnClickListener((v) -> onLogInClick());
    }

    private void onLogInClick(){
        if(emailTxt.getText().toString().equals("") || passwordTxt.getText().toString().equals("")){
            Toast.makeText(getContext(), "Riempi tutti i campi per eseguire il login", Toast.LENGTH_SHORT).show();
            return;
        }
        disableAll();
        (new LogInModel(this)).execute(emailTxt.getText().toString(), passwordTxt.getText().toString());
    }

    public void disableAll(){
        btnLogIn.setEnabled(false);
        emailTxt.setEnabled(false);
        passwordTxt.setEnabled(false);
        ((MainActivity) getActivity()).disableTabBar();
    }

    public void enableAll(){
        btnLogIn.setEnabled(true);
        emailTxt.setEnabled(true);
        passwordTxt.setEnabled(true);
        ((MainActivity) getActivity()).enableTabBar();
    }

    public void changeAccountFragment(String cookie){
        sharedPreferences.edit().putString(MainActivity.SESSION_COOKIE, cookie).commit();
        ((MainActivity) getActivity()).changeAccountView();
    }

    public void printStatus(String s){
        if(s != null){
            Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
        }
    }
}

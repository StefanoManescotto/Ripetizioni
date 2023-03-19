package com.example.ripetizioni_android.controller.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.ripetizioni_android.R;
import com.example.ripetizioni_android.controller.MainActivity;
import com.example.ripetizioni_android.model.LogOutModel;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class AccountFragment extends Fragment {

    private TextView txtName, txtSurname, txtEmail;
    private Button btnLogout;
    private SharedPreferences sharedPreferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_account, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        txtName = view.findViewById(R.id.account_name);
        txtSurname = view.findViewById(R.id.account_surname);
        txtEmail = view.findViewById(R.id.account_email);
        btnLogout = view.findViewById(R.id.btnLogOut);

        btnLogout.setOnClickListener((v) -> onLogoutClick());

        sharedPreferences = getActivity().getSharedPreferences(MainActivity.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        setAccountDetails();
    }

    private void setAccountDetails(){
        txtName.setText(sharedPreferences.getString(MainActivity.ACCOUNT_NAME, ""));
        txtSurname.setText(sharedPreferences.getString(MainActivity.ACCOUNT_SURNAME, ""));
        txtEmail.setText(sharedPreferences.getString(MainActivity.ACCOUNT_EMAIL, ""));
    }

    private void onLogoutClick(){
        (new LogOutModel(this)).execute();
    }

    public void invalidateAccountData(){
        sharedPreferences.edit().clear().commit();
        ((MainActivity)getActivity()).loadAccountFragment();
    }
}

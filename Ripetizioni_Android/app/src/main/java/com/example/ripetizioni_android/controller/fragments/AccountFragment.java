package com.example.ripetizioni_android.controller.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ripetizioni_android.R;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class AccountFragment extends Fragment {

    private TextView txtName, txtSurname, txtEmail;

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
    }

    private void setAccountDetails(String name, String surname, String email){
        txtName.setText(name);
        txtSurname.setText(surname);
        txtEmail.setText(email);
    }
}

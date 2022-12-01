package com.example.ripetizioni_android.controller.fragments;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.ripetizioni_android.R;
import com.example.ripetizioni_android.controller.adapter.RecyclerViewAdapter;
import com.example.ripetizioni_android.controller.adapter.RecyclerViewSingleRow;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView prenList;
    private ArrayList<RecyclerViewSingleRow> recyclerViewList;
    private Spinner dayPicker, timePicker, teacherPicker;
    private TextView dialogTitle;
    private Button btnBack, btnNew;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TestFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        recyclerViewList = new ArrayList<>();
        recyclerViewList.add(new RecyclerViewSingleRow("Matematica", "Mario Rossi"));
        recyclerViewList.add(new RecyclerViewSingleRow("Italiano", "Mario Rossi"));
        recyclerViewList.add(new RecyclerViewSingleRow("Informatica", "Mario Rossi"));
        recyclerViewList.add(new RecyclerViewSingleRow("Inglese", "Mario Rossi"));
        recyclerViewList.add(new RecyclerViewSingleRow("Fisica", "Mario Rossi"));
        recyclerViewList.add(new RecyclerViewSingleRow("Chimica", "Mario Rossi"));
        recyclerViewList.add(new RecyclerViewSingleRow("Telecomunicazioni", "Mario Rossi"));


        prenList = getView().findViewById(R.id.listViewPren);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(recyclerViewList, getActivity());

        prenList.setAdapter(adapter);
        prenList.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter.setOnItemClickListener((v, pos, item) -> openDialog(item));
    }

    private void openDialog(RecyclerViewSingleRow itemPressed){
        createDialogCompletePren(itemPressed.getTitle());
    }
    
    private void createDialogCompletePren(String title){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        final View createPrenView = getLayoutInflater().inflate(R.layout.dialog_complete_prenotation, null);

        dialogTitle = createPrenView.findViewById(R.id.dialog_title);
        dialogTitle.setText(title);

        ArrayList<String> daysPickArray = new ArrayList<>();
        daysPickArray.add("Lunedí");
        daysPickArray.add("Martedí");
        daysPickArray.add("Mercoledí");
        daysPickArray.add("Giovedí");
        daysPickArray.add("Venerdí");
        ArrayAdapter<String> dayPickAdapter = new ArrayAdapter<>(getActivity(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, daysPickArray);

        ArrayList<String> timePickArray = new ArrayList<>();
        timePickArray.add("15-16");
        timePickArray.add("16-17");
        timePickArray.add("17-18");
        timePickArray.add("18-19");
        ArrayAdapter<String> timePickAdapter = new ArrayAdapter<>(getActivity(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, timePickArray);

        ArrayList<String> teacherPickArray = new ArrayList<>();
        teacherPickArray.add("Mario Rossi");
        teacherPickArray.add("Marco Bianchi");
        teacherPickArray.add("Giacomo Verdi");
        ArrayAdapter<String> teacherPickAdapter = new ArrayAdapter<>(getActivity(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, teacherPickArray);

        dayPicker = createPrenView.findViewById(R.id.spinner_day_picker);
        dayPicker.setAdapter(dayPickAdapter);

        timePicker = createPrenView.findViewById(R.id.spinner_time_picker);
        timePicker.setAdapter(timePickAdapter);

        teacherPicker = createPrenView.findViewById(R.id.spinner_teacher_picker);
        teacherPicker.setAdapter(teacherPickAdapter);


        dialogBuilder.setView(createPrenView);
        AlertDialog dialog = dialogBuilder.create();

        btnBack = createPrenView.findViewById(R.id.btn_back);
        btnBack.setOnClickListener((v) -> onBackClick(dialog));
        btnNew = createPrenView.findViewById(R.id.btn_new);
        btnNew.setOnClickListener((v) -> onNewClick());

        dialog.show();
    }

    public void onBackClick(AlertDialog dialog){
        dialog.dismiss();
    }

    public void onNewClick(){
        // TODO: model connection and new pren to account
    }
}

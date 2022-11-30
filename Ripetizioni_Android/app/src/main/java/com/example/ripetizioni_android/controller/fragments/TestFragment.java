package com.example.ripetizioni_android.controller.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ripetizioni_android.R;
import com.example.ripetizioni_android.controller.adapter.RecyclerViewAdapter;
import com.example.ripetizioni_android.controller.adapter.RecyclerViewSingleRow;
import com.example.ripetizioni_android.model.Prenotazione;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TestFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class TestFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TextView test;
    private RecyclerView prenList;
    private ArrayList<RecyclerViewSingleRow> recyclerViewList;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TestFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TestFragment newInstance(String param1, String param2) {
        TestFragment fragment = new TestFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public TestFragment() {
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
        return inflater.inflate(R.layout.fragment_test, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        test = getView().findViewById(R.id.textView1); //TODO: remove when it's not needed anymnore

        recyclerViewList = new ArrayList<>();
        recyclerViewList.add(new RecyclerViewSingleRow("MATEMATICA", "Mario Rossi"));
        recyclerViewList.add(new RecyclerViewSingleRow("ITALIANO", "Mario Rossi"));
        recyclerViewList.add(new RecyclerViewSingleRow("INFORMATICA", "Mario Rossi"));
        recyclerViewList.add(new RecyclerViewSingleRow("INGLESE", "Mario Rossi"));


        prenList = getView().findViewById(R.id.listViewPren);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(recyclerViewList, getActivity());

        prenList.setAdapter(adapter);
        prenList.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter.setOnItemClickListener((v, pos, item) -> printPressed((RecyclerViewSingleRow) item));
    }

    private void printPressed(RecyclerViewSingleRow itemPressed){
        Toast.makeText(getActivity(), itemPressed.toString(), Toast.LENGTH_SHORT).show();
    }
}

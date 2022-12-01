package com.example.ripetizioni_android.controller.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.ripetizioni_android.R;
import com.example.ripetizioni_android.controller.adapter.RecyclerViewAdapter;
import com.example.ripetizioni_android.controller.adapter.RecyclerViewSingleRow;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class PrenFragment extends Fragment {

    private RecyclerView prenList;
    private ArrayList<RecyclerViewSingleRow> recyclerViewList;
    private TextView dialogTitle, dialogDay, dialogTime, dialogTeacher;
    private Button btnOk, btnDelete, btnDone;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        recyclerViewList = new ArrayList<>();
        recyclerViewList.add(new RecyclerViewSingleRow("Matematica", "Lunedi | 15-16 | Mario Rossi"));
        recyclerViewList.add(new RecyclerViewSingleRow("Italiano", "Lunedi | 15-16 | Mario Rossi"));
        recyclerViewList.add(new RecyclerViewSingleRow("Informatica", "Lunedi | 15-16 | Mario Rossi"));


        prenList = getView().findViewById(R.id.listViewPren);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(recyclerViewList, getActivity());

        prenList.setAdapter(adapter);
        prenList.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter.setOnItemClickListener((v, pos, item) -> openDialog(item));
    }

    private void openDialog(RecyclerViewSingleRow itemPressed){
        String[] prenDetails = itemPressed.getBody().split("\\|");

        createDialogCompletePren(itemPressed.getTitle(), prenDetails[0], prenDetails[1], prenDetails[2]);
    }
    
    private void createDialogCompletePren(String title, String day, String time, String teacher){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        final View createPrenView = getLayoutInflater().inflate(R.layout.dialog_cancel_prenotation, null);

        dialogTitle = createPrenView.findViewById(R.id.dialog_title);
        dialogTitle.setText(title);

        dialogDay = createPrenView.findViewById(R.id.dialog_day);
        dialogDay.setText(day);

        dialogTime = createPrenView.findViewById(R.id.dialog_time);
        dialogTime.setText(time);

        dialogTeacher = createPrenView.findViewById(R.id.dialog_teacher);
        dialogTeacher.setText(teacher);

        dialogBuilder.setView(createPrenView);
        AlertDialog dialog = dialogBuilder.create();

        btnOk = createPrenView.findViewById(R.id.btn_ok);
        btnOk.setOnClickListener((v) -> onOkClick(dialog));

        btnDelete = createPrenView.findViewById(R.id.btn_delete);
        btnDelete.setOnClickListener((v) -> onDeleteClick());

        btnDone = createPrenView.findViewById(R.id.btn_done);
        btnDone.setOnClickListener((v) -> onDoneClick());

        dialog.show();
    }

    private void onOkClick(AlertDialog dialog){
        dialog.dismiss();
    }

    private void onDoneClick(){
        // TODO: model connection and mark as 'done'
    }

    private void onDeleteClick(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Sei sicuro di voler cancellare la prenotazione?").setPositiveButton("Si", confirmationDialogListener)
                .setNegativeButton("No", confirmationDialogListener).show();
    }

    DialogInterface.OnClickListener confirmationDialogListener = (dialog, which) -> {
        switch (which){
            case DialogInterface.BUTTON_POSITIVE:
                // TODO: model connection, delete pren
                break;

            case DialogInterface.BUTTON_NEGATIVE:
                dialog.dismiss();
                break;
        }
    };
}

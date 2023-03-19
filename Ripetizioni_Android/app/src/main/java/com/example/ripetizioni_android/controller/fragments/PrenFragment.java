package com.example.ripetizioni_android.controller.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ripetizioni_android.R;
import com.example.ripetizioni_android.controller.MainActivity;
import com.example.ripetizioni_android.controller.adapter.RecyclerViewAdapter;
import com.example.ripetizioni_android.controller.adapter.RecyclerViewSingleRow;
import com.example.ripetizioni_android.model.CambiaStatoModel;
import com.example.ripetizioni_android.model.PrenotazioniModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class PrenFragment extends Fragment {
    private RecyclerViewAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ArrayList<RecyclerViewSingleRow> recyclerViewList = new ArrayList<>();
        RecyclerView prenList;

        (new PrenotazioniModel(this)).execute();

        prenList = getView().findViewById(R.id.listViewPren);
        adapter = new RecyclerViewAdapter(recyclerViewList, getActivity());

        prenList.setAdapter(adapter);
        prenList.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter.setOnItemClickListener((v, pos, item) -> openDialog(item));
    }

    private void openDialog(RecyclerViewSingleRow itemPressed){
        String[] currentState = itemPressed.getBody().split("\n");
        if(currentState[1].equals("attiva")){
            String[] prenDetails = itemPressed.getBody().split("\\|");
            createDialogCompletePren(itemPressed.getTitle(), prenDetails[0], prenDetails[1], prenDetails[2].split("\n")[0], itemPressed.getIdPren());
        }else{
            Toast.makeText(getContext(), "Non puoi cambiare lo stato di una prenotazione non attiva", Toast.LENGTH_SHORT).show();
        }
    }
    
    private void createDialogCompletePren(String title, String day, String time, String teacher, int idPren){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        final View createPrenView = getLayoutInflater().inflate(R.layout.dialog_cancel_prenotation, null);

        TextView dialogTitle = createPrenView.findViewById(R.id.dialog_title);
        dialogTitle.setText(title);

        TextView dialogDay = createPrenView.findViewById(R.id.dialog_day);
        dialogDay.setText(day);

        TextView dialogTime = createPrenView.findViewById(R.id.dialog_time);
        dialogTime.setText(time);

        TextView dialogTeacher = createPrenView.findViewById(R.id.dialog_teacher);
        dialogTeacher.setText(teacher);

        dialogBuilder.setView(createPrenView);
        AlertDialog dialog = dialogBuilder.create();

        Button btnOk = createPrenView.findViewById(R.id.btn_ok);
        btnOk.setOnClickListener((v) -> onOkClick(dialog));

        Button btnDelete = createPrenView.findViewById(R.id.btn_delete);
        btnDelete.setOnClickListener((v) -> onDeleteClick(idPren, dialog));

        Button btnDone = createPrenView.findViewById(R.id.btn_done);
        btnDone.setOnClickListener((v) -> onDoneClick(idPren, dialog));

        dialog.show();
    }

    private void onOkClick(AlertDialog dialog){
        dialog.dismiss();
    }

    private void onDoneClick(int idPren, DialogInterface dialog){
        (new CambiaStatoModel(this)).execute(String.valueOf(idPren), "effettuata");
        dialog.dismiss();
    }

    private void onDeleteClick(int idPren, DialogInterface dialog){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Sei sicuro di voler cancellare la prenotazione?")
                .setPositiveButton("Si", (d, w) -> {
                    (new CambiaStatoModel(this)).execute(String.valueOf(idPren), "cancellata");
                    dialog.dismiss();
                })
                .setNegativeButton("No",  (d, w) -> dialog.dismiss())
                .show();
    }

    public void updateAdapter(JSONArray json) throws JSONException {
        JSONObject materia;
        String body;
        for(int i = 0; i < json.length(); i++){
            materia = json.getJSONObject(i);

            body = materia.getString("data");
            body += " | " + materia.getString("ora") + "-" + (Integer.parseInt(materia.getString("ora")) + 1);
            body += " | " + materia.getString("nomeDocente") + " " + materia.getString("cognomeDocente");
            body += "\n" + materia.getString("stato");
            body = body.replace("\"", "");

            adapter.add(new RecyclerViewSingleRow(materia.getInt("idPrenotazione"), materia.getString("corso"), body));
        }
    }

    public void reload(){
        ((MainActivity) getActivity()).loadPrenFragment();
    }
}

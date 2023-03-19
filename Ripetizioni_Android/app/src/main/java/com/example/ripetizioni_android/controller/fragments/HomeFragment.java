package com.example.ripetizioni_android.controller.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ripetizioni_android.R;
import com.example.ripetizioni_android.controller.MainActivity;
import com.example.ripetizioni_android.controller.adapter.RecyclerViewAdapter;
import com.example.ripetizioni_android.controller.adapter.RecyclerViewSingleRow;
import com.example.ripetizioni_android.model.DocentiModel;
import com.example.ripetizioni_android.model.MaterieModel;
import com.example.ripetizioni_android.model.PrenotaModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class HomeFragment extends Fragment {
    private Spinner dayPicker, timePicker, teacherPicker;
    private TextView dialogTitle;
    private RecyclerViewAdapter adapter;
    private ArrayAdapter<String> dayPickAdapter, timePickAdapter, teacherPickAdapter;
    private ArrayList<Day> days = new ArrayList<>();
    private JSONArray data = new JSONArray();
    private SharedPreferences sharedPreferences;

    public HomeFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MaterieModel a = new MaterieModel(this);
        a.execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        RecyclerView prenList = getView().findViewById(R.id.listViewPren);
        adapter = new RecyclerViewAdapter(new ArrayList<>(), getActivity());
        prenList.setAdapter(adapter);
        prenList.setLayoutManager(new LinearLayoutManager(getActivity()));

        sharedPreferences = getActivity().getSharedPreferences(MainActivity.SHARED_PREFERENCES, Context.MODE_PRIVATE);

        adapter.setOnItemClickListener((v, pos, item) -> onItemClick(item));
        ((MainActivity) getActivity()).executeAuth();
    }

    private void onItemClick(RecyclerViewSingleRow itemPressed){
        if(!sharedPreferences.getString(MainActivity.ACCOUNT_EMAIL, "null").equals("null")){
            openDialog(itemPressed);
        }else{
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setMessage("Esegui il LogIn per poter prenotare")
                    .setPositiveButton("LogIn", (d, w) -> {
                        ((MainActivity)getActivity()).changeViewToAccount();
                    })
                    .setNegativeButton("Indietro",  (d, w) -> {})
                    .show();
        }
    }

    private void openDialog(RecyclerViewSingleRow itemPressed){
        DocentiModel docentiModel = new DocentiModel(this);
        docentiModel.execute(itemPressed.getTitle());

        createDialogCompletePren(itemPressed.getTitle());
    }
    
    private void createDialogCompletePren(String title){
        AdapterView.OnItemSelectedListener onItemClick = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                updateAlertView();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                updateAlertView();
            }
        };

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        final View createPrenView = getLayoutInflater().inflate(R.layout.dialog_complete_prenotation, null);

        dialogTitle = createPrenView.findViewById(R.id.dialog_title);
        dialogTitle.setText(title);

        ArrayList<String> daysPickArray = new ArrayList<>();
        daysPickArray.add("giorno");
        dayPickAdapter = new ArrayAdapter<>(getActivity(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, daysPickArray);

        ArrayList<String> timePickArray = new ArrayList<>();
        timePickArray.add("orario");
        timePickAdapter = new ArrayAdapter<>(getActivity(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, timePickArray);

        ArrayList<String> teacherPickArray = new ArrayList<>();
        teacherPickAdapter = new ArrayAdapter<>(getActivity(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, teacherPickArray);
        teacherPickArray.add("insegnante");

        dayPicker = createPrenView.findViewById(R.id.spinner_day_picker);
        dayPicker.setAdapter(dayPickAdapter);

        timePicker = createPrenView.findViewById(R.id.spinner_time_picker);
        timePicker.setAdapter(timePickAdapter);

        teacherPicker = createPrenView.findViewById(R.id.spinner_teacher_picker);
        teacherPicker.setAdapter(teacherPickAdapter);

        dayPicker.setOnItemSelectedListener(onItemClick);
        timePicker.setOnItemSelectedListener(onItemClick);
        teacherPicker.setOnItemSelectedListener(onItemClick);

        dialogBuilder.setView(createPrenView);
        AlertDialog dialog = dialogBuilder.create();

        Button btnBack = createPrenView.findViewById(R.id.btn_back);
        btnBack.setOnClickListener((v) -> onBackClick(dialog));
        Button btnNew = createPrenView.findViewById(R.id.btn_new);
        btnNew.setOnClickListener((v) -> onNewClick(dialog));

        dialog.show();

    }

    public void onBackClick(AlertDialog dialog){
        dialog.dismiss();
    }

    public void onNewClick(AlertDialog dialog){
        String corso, data, ora;
        String[] teacher;
        corso = dialogTitle.getText().toString();
        teacher = teacherPicker.getSelectedItem().toString().split(" ");
        data = dayPicker.getSelectedItem().toString();
        ora = timePicker.getSelectedItem().toString();

        if(data.equals("") || data.equals("giorno") || teacher.length < 2 || ora.equals("") || ora.equals("orario")){
            Toast.makeText(getContext(), "Riempi tutti i campi", Toast.LENGTH_SHORT).show();
            return;
        }

        (new PrenotaModel(this)).execute(corso, teacher[0], teacher[1], data, ora);
        dialog.dismiss();
    }

    public void updateAdapter(JSONArray json) throws JSONException {
        JSONObject materia;
        String teachers;
        for(int i = 0; i < json.length(); i++){
            materia = json.getJSONObject(i);

            teachers = materia.getString("insegnanti");
            teachers = teachers.substring(1, teachers.length() - 1);
            teachers = teachers.replace("\"", "");

            adapter.add(new RecyclerViewSingleRow(materia.getString("titolo"), teachers));
        }
    }

    private void updateAlertView(){
        ArrayList<String> timesFin = new ArrayList<>(), daysFin = new ArrayList<>();
        ArrayList<String> tmpTimes;

        for(int i = 0; i < data.length(); i++){
            days = getDays();

            JSONObject teacher;
            try {
                teacher = data.getJSONObject(i);
                if(teacherPicker.getSelectedItem().toString().equals("insegnante") || teacherPicker.getSelectedItem().toString().equals(teacher.getString("nome") +
                        " " + teacher.getString("cognome"))) {
                    JSONArray busyDs = teacher.getJSONArray("busyDays");
                    for (int j = 0; j < busyDs.length(); j++) {
                        JSONObject bd = busyDs.getJSONObject(j);
                        for (Day d : days) {
                            if (d.day.equals(bd.getString("day"))) {
                                d.addTime(bd.getString("time"));
                                break;
                            }
                        }
                    }

                    for(Day d : days){
                        tmpTimes = new ArrayList<>(Arrays.asList("15","16", "17","18", "19"));
                        if(dayPicker.getSelectedItem().toString().equals("giorno") || d.day.equals(dayPicker.getSelectedItem().toString())){
                            for(String s : d.busyTimes){
                                tmpTimes.remove(s);
                            }

                            for(String s2 : tmpTimes){
                                if(!timesFin.contains(s2)){
                                    timesFin.add(s2);
                                }
                            }
                        }

                        if((timePicker.getSelectedItem().toString().equals("orario") ||
                                !d.busyTimes.contains(timePicker.getSelectedItem().toString())) && !daysFin.contains(d.day)){
                            daysFin.add(d.day);
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        addDays(daysFin);
        addTimes(timesFin);
    }

    private void addDays(ArrayList<String> d){
        String selected = dayPicker.getSelectedItem().toString();
        dayPickAdapter.clear();
        dayPickAdapter.add("giorno");
        for(String s : d){
            dayPickAdapter.add(s);
        }
        int pos = dayPickAdapter.getPosition(selected) != -1 ? dayPickAdapter.getPosition(selected) : 0;
        dayPicker.setSelection(pos);
    }

    private void addTimes(ArrayList<String> t){
        String selected = timePicker.getSelectedItem().toString();
        timePickAdapter.clear();
        timePickAdapter.add("orario");
        for(String s : t){
            timePickAdapter.add(s);
        }
        int pos = timePickAdapter.getPosition(selected) != -1 ? timePickAdapter.getPosition(selected) : 0;
        timePicker.setSelection(pos);
    }

    public void updateDialogAdapter(JSONArray json) throws JSONException {
        data = json;

        for(int i = 15; i <= 19; i++){
            timePickAdapter.add(i + "-" + (i + 1));
        }

        for(int i = 0; i < json.length(); i++){
            JSONObject teacher = json.getJSONObject(i);
            teacherPickAdapter.add(teacher.getString("nome") + " " + teacher.getString("cognome"));
        }
    }

    public Calendar getNextWeek(){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        cal.add(Calendar.DATE, 7);
        return cal;
    }

    public void printServerResponse(String res){
        if(res.equals("200")){
            Toast.makeText(getContext(), "Prenotazione eseguita con successo", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getContext(), "Errore nella prenotazione", Toast.LENGTH_SHORT).show();
        }
    }

    private ArrayList<Day> getDays(){
        ArrayList<Day> days = new ArrayList<>();
        Calendar weekDays = getNextWeek();
        for(int i = 0; i < 5; i++){
            String dString = new SimpleDateFormat("yyyy-MM-dd").format(weekDays.getTime());
            dayPickAdapter.add(dString);
            days.add(new Day(dString));
            weekDays.add(Calendar.DAY_OF_WEEK, 1);
        }
        return days;
    }

    private class Day{
        public String day;
        public ArrayList<String> busyTimes = new ArrayList();

        public Day(String day){
            this.day = day;
        }

        public void addTime(String t){
            busyTimes.add(t);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || (getClass() != o.getClass() && o.getClass() != String.class)) return false;
            if(o.getClass() == String.class){
                System.out.println("HERE COMPARING TEST " + day + " " + o);
                return day.equals(o.toString());
            }
            Day day1 = (Day) o;
            return day1.day.equals(this.day);
        }

        @Override
        public String toString() {
            return "Day{" +
                    "day='" + day + '\'' +
                    ", busyTimes=" + busyTimes +
                    '}';
        }
    }
}

package com.example.practicelgty.ui.notifications;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.text.DateFormat;

import com.example.practicelgty.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class NotificationsFragment extends Fragment implements View.OnClickListener {

    RecyclerView recyclerView;
    ArrayList<Card> arrayList;
    EditText startEdit , endEdit;
    Button button;

    private DatePickerDialog.OnDateSetListener mDateSetListener;
    DateFormat dateFormat;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notifications,container , false);
        recyclerView = view.findViewById(R.id.resylerView);
        startEdit = view.findViewById(R.id.editTextDate3);
        endEdit = view.findViewById(R.id.editTextDate4);
        button = view.findViewById(R.id.button2);

        button.setOnClickListener(this::onClick);
        startEdit.setOnClickListener(this::onClick);
        endEdit.setOnClickListener(this::onClick);

        dateFormat= new SimpleDateFormat("dd.MM.yyyy");

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        internet();

        return view;
    }

    private void initializeAdapter() {

        System.out.println("initializeAdapter");
        Adapter adapterText = new Adapter(arrayList);
        recyclerView.setAdapter(adapterText);
    }


    private void internet(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    initalizeDate();

                    Activity activity = getActivity();
                    activity.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            initializeAdapter();
                        }
                    });

                } catch (IOException | ParseException e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }

    private void initalizeDate() throws ParseException, IOException {
        arrayList = new ArrayList<>();

        String startDate = startEdit.getText().toString();
        String endDate = endEdit.getText().toString();


        Date dateSt = dateFormat.parse(startDate);
        Date dateEnd = dateFormat.parse(endDate);

        int allDays = (int) ((dateEnd.getTime() - dateSt.getTime()) /(24*60*60*1000)) + 1;

        Date[] dates = new Date[allDays];
        for(Date date = dateSt; date.getTime()<= dateEnd.getTime(); date.setTime(date.getTime() + 86400000)){
            URLBuilder urlBuilder = new URLBuilder();

            String json = connections(urlBuilder.getURL(new Date(date.getTime())));
            arrayList.add(new Card(json , new Date(date.getTime())));
        }


    }

    private String connections(URL oracle) throws IOException {

        BufferedReader in = new BufferedReader(
                new InputStreamReader(oracle.openStream()));

        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            System.out.println(inputLine);

            return inputLine;
        }
        in.close();
        return "";
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.editTextDate3:
                setupDateSetListener(startEdit);
                setupCalendar();
                break;
            case R.id.editTextDate4:
                setupDateSetListener(endEdit);
                setupCalendar();
                break;
            case R.id.button2:
                internet();
                break;
        }
    }

    void setupCalendar(){
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(getContext(),
                android.R.style.Theme_Holo_Dialog_MinWidth, mDateSetListener, year, month, day);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    void setupDateSetListener(EditText editText){
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = day + "." + month + "." + year;
                editText.setText(date);
            }
        };
    }

    private void save() throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream("tempFile");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(arrayList);
        objectOutputStream.close();
    }
}
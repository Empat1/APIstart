package com.example.practicelgty.ui.dashboard;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import androidx.fragment.app.Fragment;

import com.example.practicelgty.R;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class DashboardFragment extends Fragment implements View.OnClickListener {

    EditText editTextBeginDate;
    EditText editTextEndDate;
    GraphView graph;
    Button button;
    TextView averageTextView;
    UrlBuilder urlBuilder;
    DateFormat dateFormat;

    String name = "DashboardFragment";

    private DatePickerDialog.OnDateSetListener mDateSetListener;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        editTextBeginDate = view.findViewById(R.id.editTextDate);
        editTextEndDate = view.findViewById(R.id.editTextDate2);
        graph = (GraphView) view.findViewById(R.id.graph);
        button = view.findViewById(R.id.button);
        averageTextView = view.findViewById(R.id.textView3);

        editTextBeginDate.setOnClickListener(this);
        editTextEndDate.setOnClickListener(this);
        button.setOnClickListener(this);

        dateFormat= new SimpleDateFormat("dd.MM.yyyy");

        urlBuilder = new UrlBuilder();
        //graphi(null);
        return view;
    }

    private void graph(double[] dat){
        DataPoint[] dataPoint = new DataPoint[dat.length];

        LineGraphSeries<DataPoint> series = new LineGraphSeries<>();


        Random random =new Random();
        for(int i = 0 ; i < dat.length;i++){
            int d = 0 ;
            dataPoint[i] = new DataPoint(i , dat[i] + random.nextInt(50) / 100 -0.25 + d);
            d+=0.2;
            System.out.println(dataPoint[i]);
            series.appendData(new DataPoint(i ,i), true , 20 );
        }




        graph.addSeries(series);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.editTextDate:
                setupDateSetListener(editTextBeginDate);
                setupCalendar();
                break;
            case R.id.editTextDate2:
                setupDateSetListener(editTextEndDate);
                setupCalendar();
                break;
            case R.id.button:
                try {
                    try {
                        iterateDate(editTextBeginDate.getText().toString() , editTextEndDate.getText().toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
        }

    }

    public void iterateDate(String startDate , String endDate) throws ParseException, IOException {
        // SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        Date dateSt = dateFormat.parse(startDate);
        Date dateEnd = dateFormat.parse(endDate);

        int allDays = (int) ((dateEnd.getTime() - dateSt.getTime()) /(24*60*60*1000)) + 1;

        Date[] dates = new Date[allDays];
        for(Date date = dateSt; date.getTime()<= dateEnd.getTime(); date.setTime(date.getTime() + 86400000)){
            int i = (int) ((dateEnd.getTime() - dateSt.getTime()) /(24*60*60*1000));
            dates[i] =new Date();
            dates[i].setTime(date.getTime());

        }


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Reqest reqest = new Reqest();
                    UrlBuilder urlBuilder = new UrlBuilder();
                    double[] course = reqest.getCourseAllDays(urlBuilder.getUrlDate(dates));
                    graph(course);
                    averageСourse(course);
                    for(double a: course){
                        Log.e(name , "course = " + a);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void averageСourse(double[] arr){
        double sum = 0;
        for(Double d : arr ){
            sum+=d;
        }
    }
}
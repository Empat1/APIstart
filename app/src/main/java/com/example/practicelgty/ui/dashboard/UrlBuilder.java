package com.example.practicelgty.ui.dashboard;

import android.os.AsyncTask;
import android.text.format.DateUtils;
import android.util.Log;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class UrlBuilder {

    public final static String name = "URLBulder";
    final static String URL_STR = "https://api.privatbank.ua/p24api/exchange_rates?json&date=";
    DateFormat dateFormat;

    public UrlBuilder(){
        dateFormat= new SimpleDateFormat("dd.MM.yyyy");
    }

    public  URL getUrlDate(String date) throws MalformedURLException {
        URL url = new URL(URL_STR + date);
        return url;
    }

    public URL getUrlDate(Date date) throws MalformedURLException {
        String s = URL_STR + dateFormat.format(date);
//        Log.i(name , "URL = " + s);
        return new URL(s);
    }

    public URL[] getUrlDate(Date[] date) throws MalformedURLException {
        URL[] urls = new URL[date.length];
        for(int i = 0; i < date.length;i++){
            urls[i] = getUrlDate(date[i]);
            Log.i(name, "getUrlDate[" + i + "] = " + urls[i]);
        }
        return urls;
    }

}

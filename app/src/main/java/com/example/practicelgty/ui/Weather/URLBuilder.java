package com.example.practicelgty.ui.Weather;

import android.util.Log;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

public class URLBuilder {

    String name = "URLBuilder";

    public URLBuilder(){

    }

    public URL getURL(Date date) throws MalformedURLException {
        Log.i( name, "date : " + date.getTime());

        Date newData = new Date();
        URL oracle;
        if(newData.getTime() > date.getTime()) {
            oracle = new URL("https://api.openweathermap.org/data/2.5/onecall/timemachine?lat=52.541702&lon=39.5827&units=metric&\" +\n" +
                    "                \"exclude=current,minutely,hourly,alerts&lang=ru" +
                    "&appid=0cce5d857ec9616744a0d9e8ec71b1eb" + "&dt=" + date.getTime() / 1000);
        }else{
            oracle =oracle = new URL("https://api.openweathermap.org/data/2.5/onecall?lat=52.539470&units=metric&" +
                "exclude=current,minutely,hourly,alerts&" +
                "lon=39.582780&lang=ru&appid=0cce5d857ec9616744a0d9e8ec71b1eb" +
                "&dt = " + date.getTime());
        }

        return oracle;
    }
}
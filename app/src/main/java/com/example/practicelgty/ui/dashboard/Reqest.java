package com.example.practicelgty.ui.dashboard;

import android.os.AsyncTask;
import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class Reqest {
    public static String name = "Reqest";


    public double getСourseDay(URL oracle) throws IOException {

        BufferedReader in = new BufferedReader(
                new InputStreamReader(oracle.openStream()));

        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            System.out.println(inputLine);

            Model ru = newObjectByJson(getObj("RUB" , inputLine));
            Model usd = newObjectByJson(getObj("USD" , inputLine));
            return (usd.getPurchaseRate()*(1 - 0.026)/ru.getPurchaseRate());
        }
        in.close();
        return -1;
    }

    public double[] getCourseAllDays(URL[] uRls) throws IOException {
        double[] days = new double[uRls.length];
        for (int i =0 ; i < uRls.length;i++){
            days[i] =getСourseDay(uRls[i]);
        }
        return days;
    }

    public String[] jsonString(String s) throws IOException {
        s= s.substring(1 , s.length() -2);
        String[] out = s.split("");
//        for(String s1: out){
//            s1=s1+"}";
//            System.out.println(s1);
//            newObjectByJson(s1);
//        }
        return out;
    }

    private String getObj(String word, String json){
        int index = json.indexOf(word);

        int start = 0 , end = 0;
        for(int i = index; i < json.length(); i++){
            if(json.charAt(i) == '}'){
                System.out.println("st = " + i);
                end = i;
                break;
            }
        }
        for(int i = index; i > 0; i--){
            if(json.charAt(i) == '{'){
                System.out.println("en = " + i);
                start = i;
                break;
            }
        }
        System.out.println(json.length());

        Log.i(name , " st = " + start + " end =" + end + ": "  + json.substring(start , end));
        return json.substring(start , end) + "}";
    }

    public Model newObjectByJson(String jsonObj) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Model model = mapper.readValue(jsonObj , Model.class);
        System.out.println(model);

        String prettyStaff1 = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(model);
        System.out.println(prettyStaff1);
        return model;
    }

}


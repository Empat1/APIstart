package com.example.practicelgty.ui.notifications;

import android.util.Log;

import java.util.Date;

public class Card {
    String tempDay;
    String tempNight;
    String tempEve;
    String tempMorn;
    String wendSpeed;
    String humidity;
    String weather;
    String minTemp;
    String maxTemp;
    Date date;

    Card(String json , Date date){
//        System.out.println(getObj("temp" , json));
//        System.out.println(getEdit("wind_speed" , json));
//        System.out.println(getEdit("humidity" , json));
//        System.out.println(getObj("weather" , json));
        this.date = date;

        wendSpeed= getEdit("wind_speed" , json);
        humidity = getEdit("humidity" , json);

        tempDay=  getEdit("temp" , json);
        descriptionParse(getObj("weather" , json));
    }

    private void tempParse(String tempString){
        tempString = tempString.substring(7 , tempString.length()-1);
        System.out.println(tempString);

        String[] arr = tempString.split(",");
        for(String period: arr){
            Log.e("Card", period);
            period = period.substring(period.length() - 4, period.length());
        }

        tempDay= delWord(arr[0]);
        minTemp =delWord(arr[1]);
        maxTemp = delWord(arr[2]);
        tempNight = delWord(arr[3]);
        tempEve=delWord(arr[4]);
        tempMorn=delWord(arr[5]);
    }

    private void descriptionParse(String tempString){
        String[] arr = tempString.split(",");
        arr[2] = arr[2].substring(15 , arr[2].length()-1);
        weather =  arr[2];
    }

    private String getObj(String word, String json){
        int index = json.indexOf(word);

        int end = 0;
        for(int i = index; i < json.length(); i++){
            if(json.charAt(i) == '}'){
                System.out.println("st = " + i);
                end = i;
                break;
            }
        }


        return json.substring(index , end) + "}";
    }

    private String getEdit(String word, String json){
        int index = json.indexOf(word);

        int end = 0;
        for(int i = index; i < json.length(); i++){
            if(json.charAt(i) == ','){
                System.out.println("st = " + i);
                end = i;
                break;
            }
        }


        return delWord(json.substring(index , end));
    }

    private String delWord(String s){
        String[] arr = s.split(":");
        return arr[1];
    }
}

package com.example.practicelgty.ui.home;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class Internet {
    private static URL urlBuilder(String id) throws MalformedURLException {
        String token = "eeeff5489cd3374a85ac7352e46dafecd247699cb10df527aa06409fa91e84c82fac2431d10a5df7a47e3";

        URL oracle = new URL("https://api.vk.com/method/photos.getAll?v=5.52&scope=photos&" +
                "owner_id=" + id+
                "&count=200"+
                "&access_token=" + token);
        return oracle;
    }

    public static ArrayList<String> request() throws IOException {
        ArrayList<String> ids = new ArrayList<>();
        ArrayList<String> arr = new ArrayList<>();

        ids.add("216609390");
        ids.add("290347606");
        ids.add("222242322");
        ids.add("269976187");
        ids.add("354874629");
        ids.add("187620851");
        ids.add("124686351");
        ids.add("523262487");
        ids.add("473319242");
        ids.add("499979533");
        ids.add("17599303");
        ids.add("606645004");

        for(String id : ids) {
            URL oracle = urlBuilder(id);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(oracle.openStream()));


            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println(inputLine);

                arr = getUrlPhotos(inputLine , arr);
                for (String s : arr) {
                    System.out.println(s);
                }
            }
            in.close();
        }
        System.out.println(arr.size());

        return arr;
    }

    public static ArrayList<String> getUrlPhotos(String json ,ArrayList<String> arr){
        boolean flag= true;
        int n=0;
        int start = 0, end = 0;

        for(int i =0 ; i < json.length();i++){
            if(flag) {
                i = json.indexOf("photo_1280");
                if(i == -1) break;
                n = 0;
                flag = false;
            }
            if(json.charAt(i) == '"') n++;
            if(json.charAt(i) == '"' && n == 2) start = i;
            if(json.charAt(i) == '"' && n == 3){
                end = i;
                arr.add(json.substring(start+1 , end));
                json = json.substring(end , json.length());
                flag = true;
            }
        }
        return arr;
    }
}


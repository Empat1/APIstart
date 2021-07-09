package com.example.practicelgty.ui.home;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.practicelgty.R;
import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


public class HomeFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "HomeFragment";
    ImageView imageView ;
    Button button , buttonNext , buttonBack;
    ArrayList<String> imgUrl;

    int nPhoto=0;

    public int getnPhoto() {
        return nPhoto;
    }

    public void setnPhoto(int nPhoto) {
        if (nPhoto < 0) {
            this.nPhoto = imgUrl.size()-1;
        } else if (nPhoto >= imgUrl.size()){
            this.nPhoto = 0;
        }else{
            this.nPhoto = nPhoto;
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home , container , false);
        imageView = root.findViewById(R.id.imageView);
        button = root.findViewById(R.id.button);
        buttonNext = root.findViewById(R.id.buttonNext);
        buttonBack = root.findViewById(R.id.buttonBack);

        buttonNext.setOnClickListener(this);
        buttonBack.setOnClickListener(this);

        String pathImg = ("https:\\/\\/sun9-50.userapi.com\\/impg\\/ZDTPEDw0MSMdsZkgm5uvyC3CSDdX9ZoB4rG8rw\\/GHmLfErAkPc.jpg?size=808x1080&quality=96&sign=993aab53b218f31dbaab0117d9d26f1b&c_uniq_tag=kkw18INalVRq0dfR_xCfeRqghZmbYgNd0nOYEDaFDJM&type=album");

        imgUrl =new ArrayList();
        imgUrl.add(pathImg);
        imgUrl.add("https://sun9-42.userapi.com/impf/c854224/v854224033/95a3f/xMLBLySAnNk.jpg?size=810x1080&quality=96&sign=ae66a7aa55d94ef33915d6b7a0cbeed9&c_uniq_tag=WTASmbyu4EeyiAipn5zNKP6rRMx1z7mg6VarLdiHqEo&type=album");
        setPhoto();
        requestVK();
        return root;
    }

    void requestVK(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Internet internet = new Internet();
                try {
                    imgUrl = internet.request();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    void setPhoto(){
        Picasso.get().load(imgUrl.get(nPhoto)).into(imageView);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonNext:
                setnPhoto(nPhoto+1);
                setPhoto();
                break;
            case R.id.buttonBack:
                setnPhoto(nPhoto-1);
                setPhoto();
                break;
        }
    }
}
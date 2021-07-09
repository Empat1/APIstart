package com.example.practicelgty.ui.notifications;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.example.practicelgty.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.PersonViewHolder>{


    public static class PersonViewHolder extends RecyclerView.ViewHolder{

        CardView cv , cardView;
        EditText title, descripton, startTime , endTime;

        TextView textView, textView2, textView3, textView4 ,textView5 , textView6 , textView7 , textView8 , textView9;


        public PersonViewHolder(@NonNull View itemView) {
            super(itemView);
            cv=itemView.findViewById(R.id.resylerView);
            textView = itemView.findViewById(R.id.textView4);
//            textView2 = itemView.findViewById(R.id.textView5);
//            textView3 = itemView.findViewById(R.id.textView6);
//            textView4 = itemView.findViewById(R.id.textView7);
//            textView5 = itemView.findViewById(R.id.textView8);
//            textView6 = itemView.findViewById(R.id.textView9);
            textView7 = itemView.findViewById(R.id.textView10);
            textView8 = itemView.findViewById(R.id.textView11);
            textView9 = itemView.findViewById(R.id.textView12);
        }
    }

    List<Card> cardList;

    public Adapter(ArrayList<Card> cards){
        this.cardList = cards;
    }

    @NonNull
    @NotNull
    @Override
    public PersonViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_weather, parent , false);
        PersonViewHolder pvh = new PersonViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull PersonViewHolder holder, int i) {

        holder.textView9.setText(cardList.get(i).date + "");
        holder.textView.setText("Температура : " + cardList.get(i).tempDay);
//        holder.textView2.setText("максимальная температура : " + cardList.get(i).maxTemp);
//        holder.textView3.setText("минимальная температура : " + cardList.get(i).minTemp);
//        holder.textView4.setText("Температура ночью : " + cardList.get(i).tempNight);
//        holder.textView5.setText("Температура вечером : " + cardList.get(i).tempEve);
//        holder.textView6.setText("Температура утром : " + cardList.get(i).tempMorn);
        holder.textView7.setText("Ветер : " + cardList.get(i).wendSpeed);
        holder.textView8.setText("Погода : " + cardList.get(i).weather);
    }

    @Override
    public int getItemCount() {
        if(cardList != null) {
            return cardList.size();
        }else {
            return 0;
        }
    }
}


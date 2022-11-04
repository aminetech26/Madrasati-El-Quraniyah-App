package com.example.madrasatielquraniyah;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    private Activity activity;
    private ArrayList student_id, student_firstname, student_lastname, student_email;

    CustomAdapter(Activity activity, Context context, ArrayList student_id, ArrayList student_firstname, ArrayList student_lastname,
                  ArrayList student_email){
        this.activity = activity;
        this.context = context;
        this.student_id = student_id;
        this.student_firstname = student_firstname;
        this.student_lastname = student_lastname;
        this.student_email = student_email;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        holder.student_id_txt.setText(String.valueOf(student_id.get(position)));
        holder.student_firstname_txt.setText(String.valueOf(student_firstname.get(position)));
        holder.student_lastname_txt.setText(String.valueOf(student_lastname.get(position)));
        holder.student_email_txt.setText(String.valueOf(student_email.get(position)));
        //Recyclerview onClickListener
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateActivity.class);
                intent.putExtra("id", String.valueOf(student_id.get(position)));
                intent.putExtra("firstname", String.valueOf(student_firstname.get(position)));
                intent.putExtra("lastname", String.valueOf(student_lastname.get(position)));
                intent.putExtra("email", String.valueOf(student_email.get(position)));
                activity.startActivityForResult(intent, 1);
            }
        });


    }

    @Override
    public int getItemCount() {
        return student_id.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView student_id_txt, student_firstname_txt, student_lastname_txt, student_email_txt;
        LinearLayout mainLayout;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            student_id_txt = itemView.findViewById(R.id.student_id_txt);
            student_firstname_txt = itemView.findViewById(R.id.student_firstname_txt);
            student_lastname_txt = itemView.findViewById(R.id.student_lastname_txt);
            student_email_txt = itemView.findViewById(R.id.student_email_txt);
            mainLayout = itemView.findViewById(R.id.mainLayout);
            //Animate Recyclerview
            //Animation translate_anim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            //mainLayout.setAnimation(translate_anim);
        }

    }

}
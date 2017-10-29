package com.hezare.mmv.Adapter;

/**
 * Created by amirhododi on 8/2/2017.
 */

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hezare.mmv.App;
import com.hezare.mmv.Models.ClassGradeListModel;
import com.hezare.mmv.Models.ElanatListModel;
import com.hezare.mmv.R;

import java.util.List;
import java.util.Random;

public class ClassGradeListAdapter extends RecyclerView.Adapter<ClassGradeListAdapter.MyViewHolder> {
    OnClickListner onCardClickListner;
    private  List<ClassGradeListModel> moviesList;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title,teacher;
        public ImageView icon;
        public RelativeLayout click;
        public CardView bg;
        String [] colorsid= {
                "#607D8B",
                "#F44336",
                "#9C27B0",
                "#673AB7",
                "#2196F3",
                "#009688",
                "#4CAF50",
                "#CDDC39",
                "#FFEB3B",
                "#FFC107",
                "#F57C00",
                "#795548",
                "#9E9E9E"

        };
        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.class_grade_item);
            teacher = (TextView) view.findViewById(R.id.class_grade_item_moalem);
            click = (RelativeLayout) view.findViewById(R.id.classgradeclick);
            bg = (CardView) view.findViewById(R.id.card_view);


        }
    }


    public ClassGradeListAdapter(List<ClassGradeListModel> moviesList) {
        this.moviesList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v =
                inflater.inflate(R.layout.classgradegrid_row, parent, false);
        // set the view's size, margins, paddings and layout parameters
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final ClassGradeListModel ClassGradeListModel = moviesList.get(position);
        holder.title.setText(ClassGradeListModel.getTitle());
        holder.teacher.setText(ClassGradeListModel.getTeacher());
        Random rnd = new Random();
       // int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));


        holder.bg.setCardBackgroundColor(Color.parseColor(holder.colorsid[rnd.nextInt(13)]));
        //holder.icon.setText(ElanatListModel.getMatn());
        holder.teacher.setTypeface(Typeface.createFromAsset(App.getContext().getAssets(), "font.ttf"));
        holder.title.setTypeface(Typeface.createFromAsset(App.getContext().getAssets(), "font.ttf"));




        holder.click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCardClickListner.OnClicked(v, position,moviesList);
            }
        });







    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public interface OnClickListner {
        void OnClicked(View view, int position, List<ClassGradeListModel> moviesList);
    }

    public void setOnClickListner(OnClickListner onCardClickListner) {
        this.onCardClickListner = onCardClickListner;
    }

}


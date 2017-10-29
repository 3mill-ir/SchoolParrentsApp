package com.hezare.mmv.Adapter;

/**
 * Created by amirhododi on 8/2/2017.
 */

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.widget.CompoundButtonCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.hezare.mmv.App;
import com.hezare.mmv.Models.ElanatListModel;
import com.hezare.mmv.R;

import java.util.List;

public class ElanatListAdapter extends RecyclerView.Adapter<ElanatListAdapter.MyViewHolder> {
    OnClickListner onCardClickListner;
    private  List<ElanatListModel> moviesList;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title,matn,date;
        public ImageView icon;
        public RelativeLayout click;
        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.elanat_list_title);
            matn = (TextView) view.findViewById(R.id.elanat_list_matn);
            date = (TextView) view.findViewById(R.id.elanat_list_date);
            icon = (ImageView) view.findViewById(R.id.elanat_list_image);
            click = (RelativeLayout) view.findViewById(R.id.elanatclick);


        }
    }


    public ElanatListAdapter(List<ElanatListModel> moviesList) {
        this.moviesList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v =
                inflater.inflate(R.layout.elanattlist_row, parent, false);
        // set the view's size, margins, paddings and layout parameters
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final ElanatListModel ElanatListModel = moviesList.get(position);
        holder.title.setText(ElanatListModel.getTitle());
        String date=ElanatListModel.getDate().substring(0,10);
        holder.date.setText(date);
        holder.matn.setTypeface(Typeface.createFromAsset(App.getContext().getAssets(), "font.ttf"));
        holder.title.setTypeface(Typeface.createFromAsset(App.getContext().getAssets(), "font.ttf"));
        holder.date.setTypeface(Typeface.createFromAsset(App.getContext().getAssets(), "font.ttf"));

        //holder.icon.setText(ElanatListModel.getMatn());
        if (ElanatListModel.getMatn().length()>100) {
            String text=ElanatListModel.getMatn().substring(0,100)+"......";
            holder.matn.setText(text);


        }else{
            holder.matn.setText(ElanatListModel.getMatn());

        }


        setScaleAnimation(holder.click);





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
        void OnClicked(View view, int position, List<ElanatListModel> moviesList);
    }

    public void setOnClickListner(OnClickListner onCardClickListner) {
        this.onCardClickListner = onCardClickListner;
    }
    private void setScaleAnimation(View view) {
        ScaleAnimation anim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setDuration(500);
        view.startAnimation(anim);
    }
}


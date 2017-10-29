package com.hezare.mmv.Adapter;

/**
 * Created by amirhododi on 8/2/2017.
 */

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hezare.mmv.App;
import com.hezare.mmv.MainActivity;
import com.hezare.mmv.Models.MainNomreListModel;
import com.hezare.mmv.Models.TakalifListModel;
import com.hezare.mmv.R;
import com.hezare.mmv.Utli;

import java.io.File;
import java.util.List;

public class MainNomrehListAdapter extends RecyclerView.Adapter<MainNomrehListAdapter.MyViewHolder> {
    OnClickListner onCardClickListner;
    private  List<MainNomreListModel> moviesList;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title,nomreh;
        public RelativeLayout click;
        public ImageView badge;
        public LinearLayout bgcolor;
        public boolean created=false;
        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            nomreh = (TextView) view.findViewById(R.id.nomreh);
            badge = (ImageView) view.findViewById(R.id.newbadge);
            click = (RelativeLayout) view.findViewById(R.id.nomreclick);
            bgcolor = (LinearLayout) view.findViewById(R.id.main_nomreh_lin);


        }
    }


    public MainNomrehListAdapter(List<MainNomreListModel> moviesList) {
        this.moviesList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v =
                inflater.inflate(R.layout.home_nomarat_row, parent, false);
        // set the view's size, margins, paddings and layout parameters
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final MainNomreListModel MainNomreListModel = moviesList.get(position);
        holder.title.setText(MainNomreListModel.getTitle());
        holder.nomreh.setText(MainNomreListModel.getNomre());
        if(MainNomreListModel.getIsNew()==1){
            holder.badge.setVisibility(View.VISIBLE);
        }else{
            holder.badge.setVisibility(View.INVISIBLE);

        }
        holder.title.setTypeface(Typeface.createFromAsset(App.getContext().getAssets(), "font.ttf"));
        holder.nomreh.setTypeface(Typeface.createFromAsset(App.getContext().getAssets(), "font.ttf"));

       if(!holder.created){
           boolean a=((MainActivity)MainNomreListModel.getContext()).YekiDarMian;
           if(a){
               holder.bgcolor.setBackgroundColor(Color.parseColor("#fce4ec"));
               ((MainActivity)MainNomreListModel.getContext()).ChangeState(false);

           }else{
               holder.bgcolor.setBackgroundColor(Color.parseColor("#ffffff"));
               ((MainActivity)MainNomreListModel.getContext()).ChangeState(true);

           }
           Log.e("Stat ",a+"");
           holder.created=true;
       }
        setScaleAnimation(holder.click);




                 holder.click.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         onCardClickListner.OnClicked( position,moviesList);

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

    @Override
    public int getItemViewType(int position) {
        return position;
    }



    public interface OnClickListner {
        void OnClicked(int position, List<MainNomreListModel> moviesList);
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


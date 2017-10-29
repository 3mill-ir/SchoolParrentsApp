package com.hezare.mmv.Adapter;

/**
 * Created by amirhododi on 8/2/2017.
 */

import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hezare.mmv.App;
import com.hezare.mmv.Models.GeybatListModel;
import com.hezare.mmv.Models.TakalifListModel;
import com.hezare.mmv.R;

import java.util.List;

public class GeybatListAdapter extends RecyclerView.Adapter<GeybatListAdapter.MyViewHolder> {
    OnClickListner onCardClickListner;
    private  List<GeybatListModel> moviesList;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView gaybatmode,dalil_title,date;
        public RelativeLayout click;
        public LinearLayout dalil,modat;
        public ImageView type;
        public MyViewHolder(View view) {
            super(view);
         /*   date = (TextView) view.findViewById(R.id.geybat_tarikh_top);
            dalil = (LinearLayout) view.findViewById(R.id.geybat_dalil);
            modat = (LinearLayout) view.findViewById(R.id.gaybat_modat);
            dalil_title = (TextView) view.findViewById(R.id.geybat_dalil_title);
            modattitle = (TextView) view.findViewById(R.id.geybat_modat_title);
            type = (ImageView) view.findViewById(R.id.imagegeybat);
            click = (RelativeLayout) view.findViewById(R.id.geyabclick);*/
            type = (ImageView) view.findViewById(R.id.imagegeybat);
            gaybatmode = (TextView) view.findViewById(R.id.geybat_mode);
            click = (RelativeLayout) view.findViewById(R.id.geyabclick);
            date = (TextView) view.findViewById(R.id.geybat_tarikh_top);
            dalil = (LinearLayout) view.findViewById(R.id.geybat_dalil);
            dalil_title = (TextView) view.findViewById(R.id.geybat_dalil_title);
        }
    }


    public GeybatListAdapter(List<GeybatListModel> moviesList) {
        this.moviesList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v =
                inflater.inflate(R.layout.hozorgyab_row, parent, false);
        // set the view's size, margins, paddings and layout parameters
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final GeybatListModel GeybatListModel = moviesList.get(position);
        holder.date.setText(GeybatListModel.getDate());
        holder.date.setTypeface(Typeface.createFromAsset(App.getContext().getAssets(), "font.ttf"));
        holder.dalil_title.setTypeface(Typeface.createFromAsset(App.getContext().getAssets(), "font.ttf"));
        holder.gaybatmode.setTypeface(Typeface.createFromAsset(App.getContext().getAssets(), "font.ttf"));

       /* int type=GeybatListModel.getType();
        if(type==1){
            holder.type.setImageResource(R.drawable.gheybat);

        }else if(type==2){
            holder.type.setImageResource(R.drawable.takhir);

        }*/
       if(!GeybatListModel.getTozih().matches("null")){
           holder.dalil.setVisibility(View.VISIBLE);
           holder.dalil_title.setText("توضیحات : "+GeybatListModel.getTozih());
       }else{
           holder.dalil.setVisibility(View.GONE);
           holder.dalil_title.setText("");

       }
        if(!GeybatListModel.getModat().matches("")){
            holder.type.setImageResource(R.drawable.ic_access_time_black_24dp);
            holder.gaybatmode.setText("تاخیر");

        }else{
            holder.type.setImageResource(R.drawable.ic_event_busy_black_24dp);
            holder.gaybatmode.setText("غیبت");

        }

        //holder.icon.setText(ElanatListModel.getMatn());





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

    @Override
    public int getItemViewType(int position) {
        return position;
    }



    public interface OnClickListner {
        void OnClicked(View view, int position, List<GeybatListModel> moviesList);
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


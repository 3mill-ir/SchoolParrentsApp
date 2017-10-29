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
import com.hezare.mmv.Models.TashvigListModel;
import com.hezare.mmv.R;

import java.util.List;

public class TashvigListAdapter extends RecyclerView.Adapter<TashvigListAdapter.MyViewHolder> {
    OnClickListner onCardClickListner;
    private  List<TashvigListModel> moviesList;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView date,title,dalil;
        public RelativeLayout click;
        public ImageView type;
        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.tashvig_title);
            dalil = (TextView) view.findViewById(R.id.tashvig_tozih);
            date = (TextView) view.findViewById(R.id.tashvig_date);
            type = (ImageView) view.findViewById(R.id.tashvig_image);
            click = (RelativeLayout) view.findViewById(R.id.tashvigclick);


        }
    }


    public TashvigListAdapter(List<TashvigListModel> moviesList) {
        this.moviesList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v =
                inflater.inflate(R.layout.tashvig_row, parent, false);
        // set the view's size, margins, paddings and layout parameters
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final TashvigListModel TashvigListModel = moviesList.get(position);
        holder.dalil.setText("توضیحات : "+TashvigListModel.getTozih());
        holder.date.setText(TashvigListModel.getDate());
        int type=TashvigListModel.getType();
        if(type==1){
            holder.type.setImageResource(R.drawable.star);

        }else if(type==2){
            holder.type.setImageResource(R.drawable.failed);

        }
        holder.dalil.setTypeface(Typeface.createFromAsset(App.getContext().getAssets(), "font.ttf"));
        holder.date.setTypeface(Typeface.createFromAsset(App.getContext().getAssets(), "font.ttf"));


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
        void OnClicked(View view, int position, List<TashvigListModel> moviesList);
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


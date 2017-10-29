package com.hezare.mmv.Adapter;

/**
 * Created by amirhododi on 8/2/2017.
 */

import android.content.Intent;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hezare.mmv.App;
import com.hezare.mmv.Models.ElanatListModel;
import com.hezare.mmv.Models.TakalifListModel;
import com.hezare.mmv.R;
import com.hezare.mmv.Utli;

import java.io.File;
import java.util.List;

public class TakalifListAdapter extends RecyclerView.Adapter<TakalifListAdapter.MyViewHolder> {
    OnClickListner onCardClickListner;
    private  List<TakalifListModel> moviesList;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title,matn,date;
        public RelativeLayout click;
        public Button download;
        public MyViewHolder(View view) {
            super(view);
            date = (TextView) view.findViewById(R.id.taklif_date);
            title = (TextView) view.findViewById(R.id.taklif_title);
            matn = (TextView) view.findViewById(R.id.taklif_about);
            download = (Button) view.findViewById(R.id.down_taklif);
            click = (RelativeLayout) view.findViewById(R.id.taklifclick);


        }
    }


    public TakalifListAdapter(List<TakalifListModel> moviesList) {
        this.moviesList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v =
                inflater.inflate(R.layout.takaliftlist_row, parent, false);
        // set the view's size, margins, paddings and layout parameters
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final TakalifListModel TakalifListModel = moviesList.get(position);
        holder.date.setText(TakalifListModel.getDate());
        holder.date.setTypeface(Typeface.createFromAsset(App.getContext().getAssets(), "font.ttf"));
        holder.title.setTypeface(Typeface.createFromAsset(App.getContext().getAssets(), "font.ttf"));
        holder.matn.setTypeface(Typeface.createFromAsset(App.getContext().getAssets(), "font.ttf"));
        holder.download.setTypeface(Typeface.createFromAsset(App.getContext().getAssets(), "font.ttf"));
        holder.title.setText(TakalifListModel.getTitle());
        holder.matn.setText(TakalifListModel.getMatn());
        final File server=new File(TakalifListModel.getFile());
        final File taklif=new File("/sdcard/solda/"+server.getName());
        if(taklif.exists()){
            holder.download.setText("باز کردن تکلیف");
        }else{
            holder.download.setText("دانلود تکلیف");

        }
        //holder.icon.setText(ElanatListModel.getMatn());


        setScaleAnimation(holder.click);





        holder.download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(taklif.exists()){
                  Intent intent = new Intent();
                    intent.setAction(android.content.Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.fromFile(taklif), Utli.getMimeType(server.getAbsolutePath()));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    App.getContext().startActivity(intent);
                }else{
                    onCardClickListner.OnClicked(v, position,moviesList);

                }

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
        void OnClicked(View view, int position, List<TakalifListModel> moviesList);
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


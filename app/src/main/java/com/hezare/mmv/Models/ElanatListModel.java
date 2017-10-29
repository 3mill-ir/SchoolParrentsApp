package com.hezare.mmv.Models;

import android.view.View;

/**
 * Created by amirhododi on 8/2/2017.
 */
public class ElanatListModel {
    private String title ;
    private String matn ;
    private String date ;
    private String Image ;


    public ElanatListModel() {
    }

    public ElanatListModel(String title ,String matn,String date,String Image) {
        this.title = title;
        this.matn = matn;
        this.date = date;
        this.Image = Image;




    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }


 public String getMatn() {
        return matn;
    }

    public void setMatn(String matn) {
        this.matn = matn;
    }



    public String getImage() {
        return Image;
    }

    public void setImage(String Image) {
        this.Image = Image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
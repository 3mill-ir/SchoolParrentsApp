package com.hezare.mmv.Models;

import android.content.Context;

/**
 * Created by amirhododi on 8/2/2017.
 */
public class MainNomreListModel {
    private String title ;
    private String nomre ;
    private int isnew;
    private Context c;

    public MainNomreListModel() {
    }

    public MainNomreListModel(String title , String nomre, int isnew,Context c) {
        this.title = title;
        this.nomre = nomre;
        this.isnew = isnew;
        this.c = c;




    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }


 public String getNomre() {
        return nomre;
    }

    public void setNomre(String nomre) {
        this.nomre = nomre;
    }



    public int getIsNew() {
        return isnew;
    }

    public void setIsNew(int isnew) {
        this.isnew = isnew;
    }

    public Context getContext() {
        return c;
    }

    public void setContext(Context c) {
        this.c = c;
    }

}
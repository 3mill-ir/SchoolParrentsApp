package com.hezare.mmv.Models;

/**
 * Created by amirhododi on 8/2/2017.
 */
public class GeybatListModel {
    private String date ;
   // private int type ;
    private String tozih ;
    private String modat ;


    public GeybatListModel() {
    }

    public GeybatListModel(String date/* , int  type*/, String tozih, String modat) {
        this.date = date;
    //    this.type = type;
        this.tozih = tozih;
        this.modat = modat;




    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


/*
 public int getType() {
        return type;
    }

    public void setType(int  type) {
        this.type = type;
    }
*/



    public String getTozih() {
        return tozih;
    }

    public void setTozih(String tozih) {
        this.tozih = tozih;
    }

    public String getModat() {
        return modat;
    }

    public void setModat(String modat) {
        this.modat = modat;
    }

}
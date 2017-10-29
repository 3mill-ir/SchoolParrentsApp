package com.hezare.mmv.Models;

/**
 * Created by amirhododi on 8/2/2017.
 */
public class TashvigListModel {
    private String title ;
    private int type ;
    private String tozih ;
    private String date ;


    public TashvigListModel() {
    }

    public TashvigListModel(/*String title ,*/ int  type, String tozih, String date) {
//        this.title = title;
        this.type = type;
        this.tozih = tozih;
        this.date = date;




    }
/*

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }
*/


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


 public int getType() {
        return type;
    }

    public void setType(int  type) {
        this.type = type;
    }



    public String getTozih() {
        return tozih;
    }

    public void setTozih(String tozih) {
        this.tozih = tozih;
    }


}
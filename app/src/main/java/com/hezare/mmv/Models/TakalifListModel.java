package com.hezare.mmv.Models;

/**
 * Created by amirhododi on 8/2/2017.
 */
public class TakalifListModel {
    private String title ;
    private String matn ;
    private String date ;
    private String file ;


    public TakalifListModel() {
    }

    public TakalifListModel(String title , String matn, String date,String file) {
        this.title = title;
        this.matn = matn;
        this.date = date;
        this.file = file;




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



    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

}
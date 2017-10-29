package com.hezare.mmv.Models;

/**
 * Created by amirhododi on 8/2/2017.
 */
public class ChatListModel {
    private String title ;
    private String id ;



    public ChatListModel() {
    }

    public ChatListModel(String title , String id) {
        this.title = title;
        this.id = id;





    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }


 public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }





}
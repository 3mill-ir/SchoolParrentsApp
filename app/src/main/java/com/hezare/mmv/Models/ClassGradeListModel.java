package com.hezare.mmv.Models;

/**
 * Created by amirhododi on 8/2/2017.
 */
public class ClassGradeListModel {
    private String title ;
    private String teacher ;
    private String BarnameHaftegiId ;


    public ClassGradeListModel() {
    }

    public ClassGradeListModel(String BarnameHaftegiId,String title,String teacher ) {
        this.title = title;
        this.teacher = teacher;
        this.BarnameHaftegiId = BarnameHaftegiId;




    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }
    public String getBarnameHaftegiId() {
        return BarnameHaftegiId;
    }

    public void setBarnameHaftegiId(String BarnameHaftegiId) {
        this.BarnameHaftegiId = BarnameHaftegiId;
    }
}
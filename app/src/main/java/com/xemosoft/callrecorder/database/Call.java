package com.xemosoft.callrecorder.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "calls")
public class Call {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "phonenumber")
    private String number;
    @ColumnInfo(name = "dateofcall")
    private String date;
    @ColumnInfo(name = "talktime")
    private String talkTime;
    @ColumnInfo(name = "filename")
    private String fileName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTalkTime() {
        return talkTime;
    }

    public void setTalkTime(String talkTime) {
        this.talkTime = talkTime;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}

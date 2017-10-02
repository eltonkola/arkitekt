package com.eltonkola.todoapp.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by elton on 9/29/17.
 */

@Entity(tableName = "todo")
public class Todo {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "note")
    private String note;

    @ColumnInfo(name = "done")
    private Boolean done;

    public Todo(final String title, final String note) {
        this.title = title;
        this.note = note;
        this.done = false;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Boolean getDone() {
        return done;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }

    @Override
    public String toString() {
        return ">>object id: " + id +" -checked: " + done +  " - title:" + title + " - note: " + note;
    }
}

package com.eltonkola.todoapp.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.eltonkola.todoapp.model.Todo;

/**
 * Created by elton on 9/29/17.
 */
@Database(entities = {Todo.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract TodoDao todoDao();
}

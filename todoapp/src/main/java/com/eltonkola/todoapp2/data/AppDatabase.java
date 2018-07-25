package com.eltonkola.todoapp2.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.eltonkola.todoapp2.model.Todo;

/**
 * Created by elton on 9/29/17.
 */
@Database(entities = {Todo.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract TodoDao todoDao();
}

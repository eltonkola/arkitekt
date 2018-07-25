package com.eltonkola.todoapp2.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.eltonkola.todoapp2.model.Todo;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by elton on 9/29/17.
 */

@Dao
public interface TodoDao {

    @Query("SELECT * from todo where id = :id LIMIT 1")
    Flowable<Todo> loadById(int id);

    @Query("SELECT * from todo")
    Flowable<List<Todo>> getAllRx();

    @Query("SELECT * FROM todo")
    List<Todo> getAll();

    @Query("SELECT * FROM todo WHERE id IN (:noteIds)")
    List<Todo> loadAllByIds(int[] noteIds);

    @Query("SELECT * FROM todo WHERE title LIKE :title AND note LIKE :note LIMIT 1")
    Todo findByName(String title, String note);

    @Insert
    void insertAll(Todo... todos);

    @Insert
    void insert(Todo todo);

    @Delete
    void delete(Todo todo);

    @Update
    void update(Todo... todo);
}
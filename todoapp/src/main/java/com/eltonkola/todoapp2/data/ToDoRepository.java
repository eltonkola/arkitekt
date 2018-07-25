package com.eltonkola.todoapp2.data;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.util.Log;

import com.eltonkola.todoapp2.model.Todo;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableOnSubscribe;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by elton on 10/2/17.
 */

public class ToDoRepository {

    private AppDatabase mAppDatabase;
    private TodoDao mTodoDao;
    public ToDoRepository(final Context context){
        mAppDatabase = Room.databaseBuilder(context, AppDatabase.class, "database-todo").build();
        mTodoDao = mAppDatabase.todoDao();
    }

    public Flowable<List<Todo>> getTodos(){
        return mTodoDao.getAllRx()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Completable createTask(final Todo todo){
        Log.v("eltonkolaxx", "createTask:" + todo.toString());

        return Completable.create(new CompletableOnSubscribe() {
            @Override
            public void subscribe(CompletableEmitter e) throws Exception {
                try {
                    mTodoDao.insert(todo);
                    e.onComplete();
                }catch (Exception error){
                    e.onError(error.getCause());
                    error.printStackTrace();
                }
            }
        }).subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread());
    }


    public Completable delete(final Todo element) {
        Log.v("eltonkolaxx", "delete:" + element.toString());

        return Completable.create(new CompletableOnSubscribe() {
            @Override
            public void subscribe(CompletableEmitter e) throws Exception {
                try {
                    mTodoDao.delete(element);
                    e.onComplete();
                }catch (Exception error){
                    e.onError(error.getCause());
                    error.printStackTrace();
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());


    }

    public Completable update(final Todo element) {
        Log.v("eltonkolaxx", "update:" + element.toString());
        return Completable.create(new CompletableOnSubscribe() {
            @Override
            public void subscribe(CompletableEmitter e) throws Exception {
                try {
                    mTodoDao.update(element);
                    e.onComplete();
                }catch (Exception error){
                    e.onError(error.getCause());
                    error.printStackTrace();
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }
}

package com.eltonkola.todoapp.ui.main;

import android.support.v7.util.DiffUtil;

import com.eltonkola.todoapp.model.Todo;

import java.util.List;

/**
 * Created by elton on 10/3/17.
 */

public class TodoDiffCallback extends DiffUtil.Callback {

    private final List<Todo> mOldList;
    private final List<Todo> mNewList;

    public TodoDiffCallback(List<Todo> oldList, List<Todo> newList) {
        this.mOldList = oldList;
        this.mNewList = newList;
    }



    @Override
    public int getOldListSize() {
        return mOldList.size();
    }

    @Override
    public int getNewListSize() {
        return mNewList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return mOldList.get(oldItemPosition).getId() == mNewList.get(newItemPosition).getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        Todo ob1 = mOldList.get(oldItemPosition);
        Todo ob2 = mNewList.get(newItemPosition);
        return ob1.getId() == ob2.getId()
                && ob1.getDone() == ob2.getDone()
                && ob1.getNote().equalsIgnoreCase(ob2.getNote())
                && ob1.getTitle().equalsIgnoreCase(ob2.getTitle());
    }

    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        // Implement method if you're going to use ItemAnimator
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}

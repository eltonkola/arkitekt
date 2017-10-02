package com.eltonkola.todoapp.ui.main;

/**
 * Created by elton on 9/29/17.
 */

import java.util.List;

import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.eltonkola.todoapp.R;
import com.eltonkola.todoapp.model.Todo;

public class ToDoAdapter extends RecyclerSwipeAdapter<TodoViewholder> {

    private List<Todo> values;
    private TodoListEvents mTodoListEvents;

    public ToDoAdapter(List<Todo> myDataset, TodoListEvents todoListEvents) {
        values = myDataset;
        mTodoListEvents = todoListEvents;
    }

    @Override
    public TodoViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.row_note, parent, false);
        TodoViewholder vh = new TodoViewholder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(TodoViewholder holder, final int position) {
        final Todo element = values.get(position);
        holder.bind(element, mTodoListEvents);
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    public void updateItems(List<Todo> newData) {
        final TodoDiffCallback diffCallback = new TodoDiffCallback(this.values, newData);
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);

        this.values.clear();
        this.values.addAll(newData);
        diffResult.dispatchUpdatesTo(this);
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.root_row;
    }
}

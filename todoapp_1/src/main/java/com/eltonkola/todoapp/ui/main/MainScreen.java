package com.eltonkola.todoapp.ui.main;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.daimajia.swipe.util.Attributes;
import com.eltonkola.arkitekt.AppScreen;
import com.eltonkola.todoapp.R;
import com.eltonkola.todoapp.TodoApp;
import com.eltonkola.todoapp.model.Todo;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
/**
 * Created by elton on 9/29/17.
 */

public class MainScreen extends AppScreen<Void> {

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;

    @Override
    public int getView() {
        return R.layout.main_screen;
    }

    private ToDoAdapter mToDoAdapter;

    @Override
    public void onEntered() {
        super.onEntered();

        final Toolbar toolbar =  getMRootView().findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(getMContext().getResources().getDrawable(R.drawable.ic_dvr_white_24dp));
        toolbar.setTitle("SSN - Simple Stupid Note");

        mRecyclerView = getMRootView().findViewById(R.id.recyclerView);
        mLinearLayoutManager = new LinearLayoutManager(getMContext());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setHasFixedSize(true);

        mToDoAdapter = new ToDoAdapter(new ArrayList<Todo>(), mTodoListEvents);
        mRecyclerView.setAdapter(mToDoAdapter);
        mToDoAdapter.setMode(Attributes.Mode.Multiple);

        TodoApp.app().getToDoRepository().getTodos().subscribe(new Consumer<List<Todo>>() {
            @Override
            public void accept(List<Todo> todos) throws Exception {
                Log.v("eltonkolaxx", "create adapter with nr of elements:" + todos.size());
                mToDoAdapter.updateItems(todos);
            }
        });

        getMRootView().findViewById(R.id.butCreate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goTo(TodoApp.PATH_CREATE);
            }
        });

    }

    private final TodoListEvents mTodoListEvents = new TodoListEvents() {
        @Override
        public void onClick(Todo element) {
            goTo(TodoApp.PATH_DETAILS, element);
        }

        @Override
        public void onDelete(Todo element) {
            TodoApp.app().getToDoRepository().delete(element).subscribe(new Action() {
                @Override
                public void run() throws Exception {
                    Log.v("eltonkola", "todo list item deleted");
                }
            }, new Consumer<Throwable>() {
                @Override
                public void accept(Throwable throwable) throws Exception {
                    Log.v("eltonkola", "error deleteding todo");
                }
            });
        }

        @Override
        public void onCheckChnaged(Todo element, Boolean checked) {
            element.setDone(checked);
            TodoApp.app().getToDoRepository().update(element).subscribe(new Action() {
                @Override
                public void run() throws Exception {
                    Log.v("eltonkola", "todo list item deleted");
                }
            }, new Consumer<Throwable>() {
                @Override
                public void accept(Throwable throwable) throws Exception {
                    Log.v("eltonkola", "error deleteding todo");
                }
            });
        }
    };


    @Override
    public void onExit() {
        super.onExit();
    }
}

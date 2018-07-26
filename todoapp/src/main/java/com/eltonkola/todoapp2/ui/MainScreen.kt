package com.eltonkola.todoapp2.ui

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.View

import com.daimajia.swipe.util.Attributes
import com.eltonkola.annotations.ScreenView
import com.eltonkola.arkitekt.AppScreen
import com.eltonkola.config.AppScreens
import com.eltonkola.todoapp2.R
import com.eltonkola.todoapp2.TodoApp
import com.eltonkola.todoapp2.model.Todo
import com.eltonkola.todoapp2.ui.adapter.ToDoAdapter
import com.eltonkola.todoapp2.ui.adapter.TodoListEvents

import java.util.ArrayList

import io.reactivex.functions.Action
import io.reactivex.functions.Consumer

@ScreenView
class MainScreen : AppScreen<Void>() {


    private var mRecyclerView: RecyclerView? = null
    private var mLinearLayoutManager: LinearLayoutManager? = null

    override val view: Int
        get() = R.layout.main_screen

    private var mToDoAdapter: ToDoAdapter? = null

    private val mTodoListEvents = object : TodoListEvents {
        override fun onClick(element: Todo) {
            goTo(AppScreens.DetailsScreen, element)
        }

        override fun onDelete(element: Todo) {
            TodoApp.getApp().toDoRepository.delete(element).subscribe({ Log.v("eltonkola", "todo list item deleted") },
                                                                      { Log.v("eltonkola", "error deleteding todo") })
        }

        override fun onCheckChnaged(element: Todo, checked: Boolean?) {
            element.done = checked
            TodoApp.getApp().toDoRepository.update(element).subscribe({ Log.v("eltonkola", "todo list item deleted") },
                                                                      { Log.v("eltonkola", "error deleteding todo") })
        }
    }

    override fun onEntered() {
        super.onEntered()

        val toolbar = mRootView!!.findViewById<Toolbar>(R.id.toolbar)
        toolbar.navigationIcon = mContext!!.resources.getDrawable(R.drawable.ic_dvr_white_24dp)
        toolbar.title = "SSN - Simple Stupid Note"

        mRecyclerView = mRootView!!.findViewById(R.id.recyclerView)
        mLinearLayoutManager = LinearLayoutManager(mContext)
        mRecyclerView!!.layoutManager = mLinearLayoutManager
        mRecyclerView!!.setHasFixedSize(true)

        mToDoAdapter = ToDoAdapter(ArrayList(), mTodoListEvents)
        mRecyclerView!!.adapter = mToDoAdapter
        mToDoAdapter!!.mode = Attributes.Mode.Multiple

        TodoApp.getApp().toDoRepository.todos.subscribe { todos ->
            Log.v("eltonkolaxx", "create adapter with nr of elements:" + todos.size)
            mToDoAdapter!!.updateItems(todos)
        }

        mRootView!!.findViewById<View>(R.id.butCreate).setOnClickListener {
            goTo(AppScreens.CreateScreen)
        }

    }


}

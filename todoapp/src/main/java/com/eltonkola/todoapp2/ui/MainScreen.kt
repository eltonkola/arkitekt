package com.eltonkola.todoapp2.ui

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.widget.Button
import com.daimajia.swipe.util.Attributes
import com.eltonkola.arkitekt.AppScreen
import com.eltonkola.arkitekt.annotations.ScreenView
import com.eltonkola.config.AppScreens
import com.eltonkola.todoapp2.R
import com.eltonkola.todoapp2.TodoApp
import com.eltonkola.todoapp2.model.Todo
import com.eltonkola.todoapp2.ui.adapter.ToDoAdapter
import com.eltonkola.todoapp2.ui.adapter.TodoListEvents
import kotterknife.bindView
import java.util.*

@ScreenView
class MainScreen : AppScreen<Void>() {

    val toolbar: Toolbar by bindView(R.id.toolbar)
    val recyclerView: RecyclerView by bindView(R.id.recyclerView)
    val butCreate: Button by bindView(R.id.butCreate)

    override val view: Int
        get() = R.layout.main_screen

    private val todoListEvents = object : TodoListEvents {
        override fun onClick(element: Todo) {
            goTo(AppScreens.DetailsScreen, element)
        }

        override fun onDelete(element: Todo) {
            TodoApp.getApp().toDoRepository.delete(element).subscribe({ Log.v("eltonkolaxx", "todo list item deleted") },
                                                                      { Log.v("eltonkolaxx", "error deleteding todo") })
        }

        override fun onCheckChnaged(element: Todo, checked: Boolean?) {
            element.done = checked
            TodoApp.getApp().toDoRepository.update(element).subscribe({ Log.v("eltonkolaxx", "todo list item deleted") },
                                                                      { Log.v("eltonkolaxx", "error deleteding todo") })
        }
    }

    val toDoAdapter = ToDoAdapter(ArrayList(), todoListEvents)

    override fun onEntered() {
        super.onEntered()


        Log.v("eltonkolaxx", "onEntered")

        toolbar.navigationIcon = mContext!!.resources.getDrawable(R.drawable.ic_dvr_white_24dp)
        toolbar.title = "SSN - Simple Stupid Note"

        recyclerView.setHasFixedSize(true)


        recyclerView.adapter = toDoAdapter
        toDoAdapter.mode = Attributes.Mode.Multiple

        TodoApp.getApp().toDoRepository.todos.subscribe { todos ->
            Log.v("eltonkolaxx", "todo loaded ${todos.size}")
            toDoAdapter.updateItems(todos)
        }

        butCreate.setOnClickListener { goTo(AppScreens.CreateScreen) }

    }

    override fun onExit() {
        super.onExit()
        Log.v("eltonkolaxx", "onExit")
    }
}

package com.eltonkola.todoapp2

import com.eltonkola.arkitekt.ArkitektApp
import com.eltonkola.config.AppRoute
import com.eltonkola.todoapp2.data.ToDoRepository
import java.util.*

class TodoApp : ArkitektApp() {

    lateinit var toDoRepository: ToDoRepository
        private set


    override fun onCreate() {
        super.onCreate()

        toDoRepository = ToDoRepository(this)
        routeConfig(AppRoute.routes as HashMap<String, Class<*>>)

    }

    companion object {

        fun getApp(): TodoApp {
            return app() as TodoApp
        }
    }


}

package com.eltonkola.todoapp2;

import com.eltonkola.arkitekt.ArkitektApp;
import com.eltonkola.config.AppRoute;
import com.eltonkola.todoapp2.data.ToDoRepository;

public class TodoApp extends ArkitektApp {

    public static TodoApp app(){
        return (TodoApp) Companion.app();
    }

    private ToDoRepository mToDoRepository;

    public ToDoRepository getToDoRepository(){
        return  mToDoRepository;
    }


    @Override
    public void onCreate() {
        super.onCreate();

        mToDoRepository =  new ToDoRepository(this);
        routeConfig(AppRoute.routes);

    }
}

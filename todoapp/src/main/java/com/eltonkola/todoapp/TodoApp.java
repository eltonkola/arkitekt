package com.eltonkola.todoapp;

import com.eltonkola.arkitekt.ArkitektApp;
import com.eltonkola.todoapp.data.ToDoRepository;
import com.eltonkola.todoapp.ui.CreateScreen;
import com.eltonkola.todoapp.ui.DetailsScreen;
import com.eltonkola.todoapp.ui.main.MainScreen;

/**
 * Created by elton on 9/29/17.
 */

public class TodoApp extends ArkitektApp {

    public static final String PATH_ROOT = "/";
    public static final String PATH_DETAILS = "/details";
    public static final String PATH_CREATE = "/create";

    public static TodoApp app(){
        return (TodoApp)mApp;
    }

    private ToDoRepository mToDoRepository;

    public ToDoRepository getToDoRepository(){
        return  mToDoRepository;
    }

    @Override
    protected void routeConfig() {

        addScreen(PATH_ROOT, MainScreen.class);
        addScreen(PATH_DETAILS, DetailsScreen.class);
        addScreen(PATH_CREATE, CreateScreen.class);

        mToDoRepository =  new ToDoRepository(this);

    }

}

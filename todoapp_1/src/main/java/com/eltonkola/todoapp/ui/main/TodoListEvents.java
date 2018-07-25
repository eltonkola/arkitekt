package com.eltonkola.todoapp.ui.main;

import com.eltonkola.todoapp.model.Todo;

/**
 * Created by elton on 10/2/17.
 */

public interface TodoListEvents {

    void onClick(Todo element);
    void onDelete(Todo element);
    void onCheckChnaged(Todo element, Boolean checked);

}

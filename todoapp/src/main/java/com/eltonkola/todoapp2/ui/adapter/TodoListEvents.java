package com.eltonkola.todoapp2.ui.adapter;

import com.eltonkola.todoapp2.model.Todo;

/**
 * Created by elton on 10/2/17.
 */

public interface TodoListEvents {

    void onClick(Todo element);
    void onDelete(Todo element);
    void onCheckChnaged(Todo element, Boolean checked);

}

package com.eltonkola.todoapp.ui.main;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.eltonkola.todoapp.R;
import com.eltonkola.todoapp.model.Todo;

/**
 * Created by elton on 10/2/17.
 */

public class TodoViewholder  extends RecyclerView.ViewHolder {
    // each data item is just a string in this case
    private TextView txtHeader;
    private TextView txtFooter;
    private View row_container;
    private CheckBox checkBox;
    private ImageView butDelete;

    public TodoViewholder(View v) {
        super(v);
        row_container = v.findViewById(R.id.row_container);
        txtHeader = v.findViewById(R.id.firstLine);
        txtFooter = v.findViewById(R.id.secondLine);
        butDelete = v.findViewById(R.id.butDelete);
        checkBox = v.findViewById(R.id.checkBox);
    }


    public void bind(final Todo element, final TodoListEvents mTodoListEvents) {

        txtHeader.setText(element.getTitle());
        txtFooter.setText(Html.fromHtml(element.getNote()));

        row_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTodoListEvents.onClick(element);
            }
        });

        butDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTodoListEvents.onDelete(element);
            }
        });

        checkBox.setChecked(element.getDone());
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mTodoListEvents.onCheckChnaged(element, b);
            }


        });

    }
}
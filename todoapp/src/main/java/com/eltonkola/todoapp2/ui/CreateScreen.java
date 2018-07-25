package com.eltonkola.todoapp2.ui;

import com.eltonkola.annotations.ScreenView;
import android.graphics.Color;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.eltonkola.arkitekt.AppScreen;
import com.eltonkola.todoapp2.R;
import com.eltonkola.todoapp2.TodoApp;
import com.eltonkola.todoapp2.model.Todo;

import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import jp.wasabeef.richeditor.RichEditor;

@ScreenView(path = "/create")
public class CreateScreen extends AppScreen<Void> {

    private Toolbar mToolbar;
    private EditText mTitle;
    private RichEditor mEditor;

    @Override
    public int getView() {
        return R.layout.dialog_create;
    }

    @Override
    public void onEntered() {
        super.onEntered();


        mToolbar = getMRootView().findViewById(R.id.toolbar);
        mToolbar.setNavigationIcon(getMContext().getResources().getDrawable(R.drawable.ic_arrow_back_white_24dp));
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                close();
            }
        });

        mToolbar.setTitle("New Note");


        ActionMenuView bottomBar = getMRootView().findViewById(R.id.bottom_toolbar);
        Menu bottomMenu = bottomBar.getMenu();
        getMenuInflater().inflate(R.menu.menu_create, bottomMenu);
        for (int i = 0; i < bottomMenu.size(); i++) {
            bottomMenu.getItem(i).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {

                    switch (item.getItemId()) {
                        case R.id.menu_create_save:
                            save();
                            break;
                        case R.id.menu_create_undo:
                            mEditor.undo();
                            break;
                        case R.id.menu_create_redo:
                            mEditor.redo();
                            break;

                    }


                    return true;
                }
            });
        }


        mTitle = getMRootView().findViewById(R.id.title);
        mEditor = getMRootView().findViewById(R.id.editor);


        mEditor.setEditorHeight(200);
        mEditor.setEditorFontSize(18);
        mEditor.setEditorFontColor(Color.BLACK);
        //mEditor.setEditorBackgroundColor(Color.BLUE);
        //mEditor.setBackgroundColor(Color.BLUE);
        //mEditor.setBackgroundResource(R.drawable.bg);
        mEditor.setPadding(10, 10, 10, 10);

        getMRootView().findViewById(R.id.action_bold).setOnClickListener(mOnClick);
        getMRootView().findViewById(R.id.action_italic).setOnClickListener(mOnClick);
        getMRootView().findViewById(R.id.action_subscript).setOnClickListener(mOnClick);
        getMRootView().findViewById(R.id.action_superscript).setOnClickListener(mOnClick);
        getMRootView().findViewById(R.id.action_strikethrough).setOnClickListener(mOnClick);
        getMRootView().findViewById(R.id.action_underline).setOnClickListener(mOnClick);
        getMRootView().findViewById(R.id.action_heading1).setOnClickListener(mOnClick);
        getMRootView().findViewById(R.id.action_heading2).setOnClickListener(mOnClick);
        getMRootView().findViewById(R.id.action_heading3).setOnClickListener(mOnClick);
        getMRootView().findViewById(R.id.action_heading4).setOnClickListener(mOnClick);
        getMRootView().findViewById(R.id.action_heading5).setOnClickListener(mOnClick);
        getMRootView().findViewById(R.id.action_heading6).setOnClickListener(mOnClick);
        getMRootView().findViewById(R.id.action_indent).setOnClickListener(mOnClick);
        getMRootView().findViewById(R.id.action_outdent).setOnClickListener(mOnClick);
        getMRootView().findViewById(R.id.action_align_left).setOnClickListener(mOnClick);
        getMRootView().findViewById(R.id.action_align_center).setOnClickListener(mOnClick);
        getMRootView().findViewById(R.id.action_align_right).setOnClickListener(mOnClick);
        getMRootView().findViewById(R.id.action_blockquote).setOnClickListener(mOnClick);
        getMRootView().findViewById(R.id.action_insert_bullets).setOnClickListener(mOnClick);
        getMRootView().findViewById(R.id.action_insert_numbers).setOnClickListener(mOnClick);


    }

    private View.OnClickListener mOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.action_insert_numbers:
                    mEditor.setNumbers();
                    break;
                case R.id.action_insert_bullets:
                    mEditor.setBullets();
                    break;
                case R.id.action_blockquote:
                    mEditor.setBlockquote();
                    break;
                case R.id.action_align_right:
                    mEditor.setAlignRight();
                    break;
                case R.id.action_align_center:
                    mEditor.setAlignCenter();
                    break;
                case R.id.action_align_left:
                    mEditor.setAlignLeft();
                    break;
                case R.id.action_outdent:
                    mEditor.setOutdent();
                    break;
                case R.id.action_indent:
                    mEditor.setIndent();
                    break;
                case R.id.action_heading6:
                    mEditor.setHeading(6);
                    break;
                case R.id.action_heading5:
                    mEditor.setHeading(5);
                    break;
                case R.id.action_heading4:
                    mEditor.setHeading(4);
                    break;
                case R.id.action_heading3:
                    mEditor.setHeading(3);
                    break;
                case R.id.action_heading2:
                    mEditor.setHeading(2);
                    break;
                case R.id.action_heading1:
                    mEditor.setHeading(1);
                    break;
                case R.id.action_underline:
                    mEditor.setUnderline();
                    break;
                case R.id.action_bold:
                    mEditor.setBold();
                    break;
                case R.id.action_italic:
                    mEditor.setItalic();
                    break;
                case R.id.action_subscript:
                    mEditor.setSubscript();
                    break;
                case R.id.action_superscript:
                    mEditor.setSuperscript();
                    break;
                case R.id.action_strikethrough:
                    mEditor.setStrikeThrough();
                    break;

            }
        }
    };

    private void save() {
        if (mTitle.getText().toString() == null || mTitle.getText().toString().length() == 0 || mEditor.getHtml() == null || mEditor.getHtml().length() == 0) {
            return;
        }

        //        butCreate.setEnabled(false);

        Todo newItem = new Todo(mTitle.getText().toString(), mEditor.getHtml());
        TodoApp.app().getToDoRepository().createTask(newItem).subscribe(new Action() {
            @Override
            public void run() throws Exception {
                Log.v("eltonkola", "todo list item added");
                Toast.makeText(getMContext(), "Note saved", Toast.LENGTH_SHORT).show();
                close();
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //                butCreate.setEnabled(true);
                Log.v("eltonkola", "error creating todo");
                Toast.makeText(getMContext(), "Error saving note", Toast.LENGTH_SHORT).show();
            }
        });

    }


}
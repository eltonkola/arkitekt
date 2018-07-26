package com.eltonkola.todoapp2.ui

import com.eltonkola.annotations.ScreenView
import android.graphics.Color
import android.support.v7.widget.ActionMenuView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Toast

import com.eltonkola.arkitekt.AppScreen
import com.eltonkola.todoapp2.R
import com.eltonkola.todoapp2.TodoApp
import com.eltonkola.todoapp2.model.Todo

import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import jp.wasabeef.richeditor.RichEditor

@ScreenView(path = "/create")
class CreateScreen : AppScreen<Void>() {

    private var mToolbar: Toolbar? = null
    private var mTitle: EditText? = null
    private var mEditor: RichEditor? = null

    override val view: Int
        get() = R.layout.dialog_create

    private val mOnClick = View.OnClickListener { view ->
        when (view.id) {
            R.id.action_insert_numbers -> mEditor!!.setNumbers()
            R.id.action_insert_bullets -> mEditor!!.setBullets()
            R.id.action_blockquote     -> mEditor!!.setBlockquote()
            R.id.action_align_right    -> mEditor!!.setAlignRight()
            R.id.action_align_center   -> mEditor!!.setAlignCenter()
            R.id.action_align_left     -> mEditor!!.setAlignLeft()
            R.id.action_outdent        -> mEditor!!.setOutdent()
            R.id.action_indent         -> mEditor!!.setIndent()
            R.id.action_heading6       -> mEditor!!.setHeading(6)
            R.id.action_heading5       -> mEditor!!.setHeading(5)
            R.id.action_heading4       -> mEditor!!.setHeading(4)
            R.id.action_heading3       -> mEditor!!.setHeading(3)
            R.id.action_heading2       -> mEditor!!.setHeading(2)
            R.id.action_heading1       -> mEditor!!.setHeading(1)
            R.id.action_underline      -> mEditor!!.setUnderline()
            R.id.action_bold           -> mEditor!!.setBold()
            R.id.action_italic         -> mEditor!!.setItalic()
            R.id.action_subscript      -> mEditor!!.setSubscript()
            R.id.action_superscript    -> mEditor!!.setSuperscript()
            R.id.action_strikethrough  -> mEditor!!.setStrikeThrough()
        }
    }

    override fun onEntered() {
        super.onEntered()


        mToolbar = mRootView!!.findViewById(R.id.toolbar)
        mToolbar!!.navigationIcon = mContext!!.resources.getDrawable(R.drawable.ic_arrow_back_white_24dp)
        mToolbar!!.setNavigationOnClickListener { close() }

        mToolbar!!.title = "New Note"


        val bottomBar = mRootView!!.findViewById<ActionMenuView>(R.id.bottom_toolbar)
        val bottomMenu = bottomBar.menu
        menuInflater.inflate(R.menu.menu_create, bottomMenu)
        for (i in 0 until bottomMenu.size()) {
            bottomMenu.getItem(i).setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.menu_create_save -> save()
                    R.id.menu_create_undo -> mEditor!!.undo()
                    R.id.menu_create_redo -> mEditor!!.redo()
                }


                true
            }
        }


        mTitle = mRootView!!.findViewById(R.id.title)
        mEditor = mRootView!!.findViewById(R.id.editor)


        mEditor!!.setEditorHeight(200)
        mEditor!!.setEditorFontSize(18)
        mEditor!!.setEditorFontColor(Color.BLACK)
        //mEditor.setEditorBackgroundColor(Color.BLUE);
        //mEditor.setBackgroundColor(Color.BLUE);
        //mEditor.setBackgroundResource(R.drawable.bg);
        mEditor!!.setPadding(10, 10, 10, 10)

        mRootView!!.findViewById<View>(R.id.action_bold).setOnClickListener(mOnClick)
        mRootView!!.findViewById<View>(R.id.action_italic).setOnClickListener(mOnClick)
        mRootView!!.findViewById<View>(R.id.action_subscript).setOnClickListener(mOnClick)
        mRootView!!.findViewById<View>(R.id.action_superscript).setOnClickListener(mOnClick)
        mRootView!!.findViewById<View>(R.id.action_strikethrough).setOnClickListener(mOnClick)
        mRootView!!.findViewById<View>(R.id.action_underline).setOnClickListener(mOnClick)
        mRootView!!.findViewById<View>(R.id.action_heading1).setOnClickListener(mOnClick)
        mRootView!!.findViewById<View>(R.id.action_heading2).setOnClickListener(mOnClick)
        mRootView!!.findViewById<View>(R.id.action_heading3).setOnClickListener(mOnClick)
        mRootView!!.findViewById<View>(R.id.action_heading4).setOnClickListener(mOnClick)
        mRootView!!.findViewById<View>(R.id.action_heading5).setOnClickListener(mOnClick)
        mRootView!!.findViewById<View>(R.id.action_heading6).setOnClickListener(mOnClick)
        mRootView!!.findViewById<View>(R.id.action_indent).setOnClickListener(mOnClick)
        mRootView!!.findViewById<View>(R.id.action_outdent).setOnClickListener(mOnClick)
        mRootView!!.findViewById<View>(R.id.action_align_left).setOnClickListener(mOnClick)
        mRootView!!.findViewById<View>(R.id.action_align_center).setOnClickListener(mOnClick)
        mRootView!!.findViewById<View>(R.id.action_align_right).setOnClickListener(mOnClick)
        mRootView!!.findViewById<View>(R.id.action_blockquote).setOnClickListener(mOnClick)
        mRootView!!.findViewById<View>(R.id.action_insert_bullets).setOnClickListener(mOnClick)
        mRootView!!.findViewById<View>(R.id.action_insert_numbers).setOnClickListener(mOnClick)


    }

    private fun save() {
        if (mTitle!!.text.toString() == null || mTitle!!.text.toString().length == 0 || mEditor!!.html == null || mEditor!!.html.length == 0) {
            return
        }

        //        butCreate.setEnabled(false);

        val newItem = Todo(mTitle!!.text.toString(), mEditor!!.html)
        TodoApp.getApp().toDoRepository.createTask(newItem).subscribe(Action {
            Log.v("eltonkola", "todo list item added")
            Toast.makeText(mContext, "Note saved", Toast.LENGTH_SHORT).show()
            close()
        }, Consumer<Throwable> {
            //                butCreate.setEnabled(true);
            Log.v("eltonkola", "error creating todo")
            Toast.makeText(mContext, "Error saving note", Toast.LENGTH_SHORT).show()
        })

    }


}
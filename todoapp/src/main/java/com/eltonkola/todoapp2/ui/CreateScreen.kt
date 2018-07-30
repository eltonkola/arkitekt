package com.eltonkola.todoapp2.ui


import android.graphics.Color
import android.support.transition.Slide
import android.support.transition.Transition
import android.support.v7.widget.ActionMenuView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.eltonkola.annotations.ScreenView
import com.eltonkola.arkitekt.AppScreen
import com.eltonkola.todoapp2.R
import com.eltonkola.todoapp2.TodoApp
import com.eltonkola.todoapp2.model.Todo
import jp.wasabeef.richeditor.RichEditor
import kotterknife.bindView

@ScreenView(path = "/create")
class CreateScreen : AppScreen<Void>() {

    val toolbar: Toolbar by bindView(R.id.toolbar)
    val title: EditText by bindView(R.id.title)
    val editor: RichEditor by bindView(R.id.editor)


    override fun animationIn() : Transition {
            var t: Transition = Slide(Gravity.BOTTOM)
            t.duration = 100
            return t
    }

    override fun  animationOut() : Transition {
            var t: Transition = Slide(Gravity.BOTTOM)
            t.duration = 100
            return t
    }

    override val view: Int
        get() = R.layout.dialog_create

    private val mOnClick = View.OnClickListener { view ->
        when (view.id) {
            R.id.action_insert_numbers -> editor.setNumbers()
            R.id.action_insert_bullets -> editor.setBullets()
            R.id.action_blockquote     -> editor.setBlockquote()
            R.id.action_align_right    -> editor.setAlignRight()
            R.id.action_align_center   -> editor.setAlignCenter()
            R.id.action_align_left     -> editor.setAlignLeft()
            R.id.action_outdent        -> editor.setOutdent()
            R.id.action_indent         -> editor.setIndent()
            R.id.action_heading6       -> editor.setHeading(6)
            R.id.action_heading5       -> editor.setHeading(5)
            R.id.action_heading4       -> editor.setHeading(4)
            R.id.action_heading3       -> editor.setHeading(3)
            R.id.action_heading2       -> editor.setHeading(2)
            R.id.action_heading1       -> editor.setHeading(1)
            R.id.action_underline      -> editor.setUnderline()
            R.id.action_bold           -> editor.setBold()
            R.id.action_italic         -> editor.setItalic()
            R.id.action_subscript      -> editor.setSubscript()
            R.id.action_superscript    -> editor.setSuperscript()
            R.id.action_strikethrough  -> editor.setStrikeThrough()
        }
    }

    override fun onEntered() {
        super.onEntered()

        toolbar.navigationIcon = mContext!!.resources.getDrawable(R.drawable.ic_arrow_back_white_24dp)
        toolbar.setNavigationOnClickListener { close() }

        toolbar.title = "New Note"

        val bottomBar = mRootView!!.findViewById<ActionMenuView>(R.id.bottom_toolbar)
        val bottomMenu = bottomBar.menu
        menuInflater.inflate(R.menu.menu_create, bottomMenu)
        for (i in 0 until bottomMenu.size()) {
            bottomMenu.getItem(i).setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.menu_create_save -> save()
                    R.id.menu_create_undo -> editor.undo()
                    R.id.menu_create_redo -> editor.redo()
                }

                true
            }
        }


        editor.setEditorHeight(200)
        editor.setEditorFontSize(18)
        editor.setEditorFontColor(Color.BLACK)
        //mEditor.setEditorBackgroundColor(Color.BLUE);
        //mEditor.setBackgroundColor(Color.BLUE);
        //mEditor.setBackgroundResource(R.drawable.bg);
        editor.setPadding(10, 10, 10, 10)

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
        if (title.text.toString().isEmpty() || editor.html == null || editor.html.isEmpty()) {
            return
        }

        //        butCreate.setEnabled(false);

        val newItem = Todo(title.text.toString(), editor.html)
        TodoApp.getApp().toDoRepository.createTask(newItem).subscribe({
            Log.v("eltonkola", "todo list item added")
            Toast.makeText(mContext, "Note saved", Toast.LENGTH_SHORT).show()
            close()
        }, {
            //                butCreate.setEnabled(true);
            Log.v("eltonkola", "error creating todo")
            Toast.makeText(mContext, "Error saving note", Toast.LENGTH_SHORT).show()
        })

    }


}
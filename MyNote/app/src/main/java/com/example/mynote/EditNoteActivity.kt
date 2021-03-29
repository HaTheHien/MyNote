package com.example.mynote

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mynote.MyApplication.Companion.noteId
import com.example.mynote.MyApplication.Companion.notes
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*


class EditNoteActivity : AppCompatActivity() {
    private var titleView:EditText? = null
    private var tagView:RecyclerView? = null
    private var dataView:EditText? = null
    private var dateView:TextView? = null
    private var pressedTime: Long = 0
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.edit_toolbar_menu, menu)
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_note)
        titleView = findViewById(R.id.title)
        dataView = findViewById(R.id.data)
        dateView = findViewById(R.id.date)
        tagView = findViewById(R.id.recycle_view_tag_edit)
        val sharedPreferences = applicationContext.getSharedPreferences("notes", MODE_PRIVATE)
        val gson = Gson()
        var json: String? = sharedPreferences.getString("notes", null)
        json?.let {
            val type = object : TypeToken<ArrayList<Note?>?>() {}.type
            notes = gson.fromJson(json, type)
        }
        val tempNote = intent.getIntExtra("noteId", -2)
        if (tempNote != -2)
            noteId = tempNote
        if (noteId != -1){
            titleView!!.setText(notes[noteId].title)
            dateView!!.setText(notes[noteId].getDate())
            dataView!!.setText(notes[noteId].text)
        }
        else{
            notes.add(Note("", Date()))
            noteId = notes.size - 1
            val a = ArrayList<String>()
            notes[noteId].tag = a
            dateView!!.setText(notes[noteId].getDate())
            json = gson.toJson(notes)
            sharedPreferences.edit().putString("notes", json).apply()
        }
        tagView!!.adapter = TagAdapter(notes[noteId].tag)
        tagView!!.layoutManager = LinearLayoutManager(baseContext, LinearLayoutManager.HORIZONTAL, false)
        titleView!!.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                notes[noteId].title = s.toString()
                notes[noteId].date = Date()
                json = gson.toJson(notes)
                sharedPreferences.edit().putString("notes", json).apply()
            }
        })
        dataView!!.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                notes[noteId].text = s.toString()
                notes[noteId].date = Date()
                json = gson.toJson(notes)
                sharedPreferences.edit().putString("notes", json).apply()
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.edit_tag -> {
                val intent = Intent(applicationContext, EditTagActivity::class.java)
                intent.putExtra("noteId", noteId)
                startActivityForResult(intent, 2)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        val returnIntent = intent
        setResult(RESULT_OK, returnIntent)
        super.onBackPressed();
        finish();
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                recreate()
            }
        }
        tagView!!.adapter!!.notifyDataSetChanged()
    }
}
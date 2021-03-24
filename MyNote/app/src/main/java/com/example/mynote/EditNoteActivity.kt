package com.example.mynote

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.mynote.MyApplication.Companion.notes
import com.google.gson.Gson
import java.util.*


class EditNoteActivity : AppCompatActivity() {
    private var titleView:EditText? = null
    private var tagView:EditText? = null
    private var dataView:EditText? = null
    private var dateView:TextView? = null
    var noteId:Int = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eidt_note)
        titleView = findViewById(R.id.title)
        dataView = findViewById(R.id.data)
        dateView = findViewById(R.id.date)
        tagView = findViewById(R.id.tagData)
        noteId = intent.getIntExtra("noteId", -1)
        if (noteId != -1){
            titleView!!.setText(notes[noteId].title)
            dateView!!.setText(notes[noteId].getDate())
            dataView!!.setText(notes[noteId].text)
            tagView!!.setText(notes[noteId].tag)
        }
        else{
            notes.add(Note("", Date()))
            noteId = notes.size - 1
            dateView!!.setText(notes[noteId].getDate())
            val sharedPreferences = applicationContext.getSharedPreferences("notes", MODE_PRIVATE)
            val gson = Gson()
            val json = gson.toJson(notes)
            sharedPreferences.edit().putString("notes", json).apply()
        }
        titleView!!.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                notes[noteId].title = s.toString()
                notes[noteId].date = Date()
                val sharedPreferences = applicationContext.getSharedPreferences("notes", MODE_PRIVATE)
                val gson = Gson()
                val json = gson.toJson(notes)
                sharedPreferences.edit().putString("notes", json).apply()
            }
        })
        dataView!!.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                notes[noteId].text = s.toString()
                notes[noteId].date = Date()
                val sharedPreferences = applicationContext.getSharedPreferences("notes", MODE_PRIVATE)
                val gson = Gson()
                val json = gson.toJson(notes)
                sharedPreferences.edit().putString("notes", json).apply()
            }
        })
        tagView!!.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                notes[noteId].tag = s.toString()
                notes[noteId].date = Date()
                val sharedPreferences = applicationContext.getSharedPreferences("notes", MODE_PRIVATE)
                val gson = Gson()
                val json = gson.toJson(notes)
                sharedPreferences.edit().putString("notes", json).apply()
            }
        })
    }
}
package com.example.mynote

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.AdapterView
import android.widget.GridView
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.mynote.MyApplication.Companion.notes
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import timber.log.Timber
import java.util.*


class MainActivity : AppCompatActivity() {
    var adapter:NoteAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val listView = findViewById<ListView>(R.id.listView)
        val gridView = findViewById<GridView>(R.id.grid_view)
        val sharedPreferences = applicationContext.getSharedPreferences("notes", MODE_PRIVATE)
        val gson = Gson()
        val json: String? = sharedPreferences.getString("notes", null)
        json?.let {
            val type = object : TypeToken<ArrayList<Note?>?>() {}.type
            notes = gson.fromJson(json, type)
        }
        adapter = NoteAdapter(this, notes)
        listView.adapter = adapter
        gridView.adapter = adapter
        listView.setOnItemClickListener(AdapterView.OnItemClickListener() { adapterView, view, i, l ->
            val intent = Intent(applicationContext, EditNoteActivity::class.java)
            intent.putExtra("noteId", i)
            startActivity(intent)
        }
        )
        gridView.setOnItemClickListener(AdapterView.OnItemClickListener() { adapterView, view, i, l ->
            val intent = Intent(applicationContext, EditNoteActivity::class.java)
            intent.putExtra("noteId", i)
            startActivity(intent)
        }
        )
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        val sharedPreferences = applicationContext.getSharedPreferences("notes", MODE_PRIVATE)
        val gson = Gson()
        val json = gson.toJson(notes)
        sharedPreferences.edit().putString("notes", json).apply()
        super.onSaveInstanceState(outState, outPersistentState)
    }

    fun onAddNewNote(view: View){
        val intent = Intent(this, EditNoteActivity::class.java)
        startActivity(intent)
    }
}
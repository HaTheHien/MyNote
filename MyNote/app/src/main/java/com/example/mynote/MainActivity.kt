package com.example.mynote

import android.R.attr
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.GridView
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mynote.MyApplication.Companion.notes
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*


class MainActivity : AppCompatActivity() {
    private var listView:ListView? = null
    private var gridView:GridView? = null
    private var adapter:NoteAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        listView = findViewById(R.id.listView)
        gridView = findViewById(R.id.grid_view)
        val sharedPreferences = applicationContext.getSharedPreferences("notes", MODE_PRIVATE)
        val gson = Gson()
        val json: String? = sharedPreferences.getString("notes", null)
        json?.let {
            val type = object : TypeToken<ArrayList<Note?>?>() {}.type
            notes = gson.fromJson(json, type)
        }
        adapter = NoteAdapter(this, notes)
        listView!!.adapter = adapter
        gridView!!.adapter = adapter
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                recreate()
            }
            else {
                val sharedPreferences = applicationContext.getSharedPreferences("notes", MODE_PRIVATE)
                val gson = Gson()
                val json: String? = sharedPreferences.getString("notes", null)
                json?.let {
                    val type = object : TypeToken<ArrayList<Note?>?>() {}.type
                    notes = gson.fromJson(json, type)
                }
            }
        }
        adapter!!.notifyDataSetChanged()
    }
    fun onAddNewNote(view: View) {
        val intent = Intent(this, EditNoteActivity::class.java)
        intent.putExtra("noteId", -1);
        startActivityForResult(intent, 1)
    }
}
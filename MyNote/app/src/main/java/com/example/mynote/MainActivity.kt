package com.example.mynote

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.example.mynote.R
import com.example.mynote.NoteAdapter
import android.widget.GridView
import android.widget.ListView
import java.util.*

class MainActivity : AppCompatActivity() {
    var notes = ArrayList<Note>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val listView = findViewById<ListView>(R.id.listView)
        val adapter = NoteAdapter(this, notes)
        adapter.add(Note("abc", Date()))
        adapter.add(Note("abcd", Date()))
        adapter.add(Note("abce", Date()))
        listView.adapter = adapter
        val gridView = findViewById<GridView>(R.id.grid_view)
        gridView.adapter = adapter
    }
}
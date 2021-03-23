package com.example.mynote

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.GridView
import android.widget.ListView
import java.util.*
import com.example.mynote.MyApplication.Companion.notes

class MainActivity : AppCompatActivity() {
    var adapter:NoteAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val listView = findViewById<ListView>(R.id.listView)
        val gridView = findViewById<GridView>(R.id.grid_view)
        adapter = NoteAdapter(this, notes!!)
        adapter!!.add(Note("abc", Date()))
        adapter!!.add(Note("abcd", Date()))
        adapter!!.add(Note("abce", Date()))
        listView.adapter = adapter
        gridView.adapter = adapter
        listView.setOnItemClickListener(AdapterView.OnItemClickListener() { adapterView, view, i, l->
            val intent = Intent(applicationContext,EditNoteActivity::class.java)
            intent.putExtra("noteId",i)
            startActivity(intent)
        }
        )
        gridView.setOnItemClickListener(AdapterView.OnItemClickListener() { adapterView, view, i, l->
            val intent = Intent(applicationContext,EditNoteActivity::class.java)
            intent.putExtra("noteId",i)
            startActivity(intent)
        }
        )
    }
}
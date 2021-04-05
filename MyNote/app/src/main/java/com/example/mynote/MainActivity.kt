package com.example.mynote

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.GridView
import android.widget.ListView
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.example.mynote.MyApplication.Companion.notes
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {
    private var listView:ListView? = null
    private var gridView:GridView? = null
    private var searchView:SearchView? = null
    private var configSearch:MenuItem? = null
    private var adapter:NoteAdapter? = null
    private var curStyle:String? = null
    private var menu:Menu? = null
    private var mode:Int = 1
    private var noteSearch:ArrayList<Note>? = null
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main_menu, menu)
        this.menu = menu
        if (curStyle.isNullOrEmpty() || curStyle == "listView"){
            menu!!.findItem(R.id.style_view).icon = ContextCompat.getDrawable(this, R.drawable.ic_baseline_grid_view_24)
            curStyle = "listView"
            listView!!.visibility = View.VISIBLE
            gridView!!.visibility = View.GONE
            adapter!!.modeView = 1
        }
        else{
            menu!!.findItem(R.id.style_view).icon = ContextCompat.getDrawable(this, R.drawable.ic_baseline_format_list_bulleted_24)
            curStyle = "gridView"
            listView!!.visibility = View.GONE
            gridView!!.visibility = View.VISIBLE
            adapter!!.modeView = 0
        }

        val search = menu!!.findItem(R.id.app_bar_search)
        searchView = search.actionView as SearchView
        configSearch = menu!!.findItem(R.id.spinner_search)
        configSearch!!.setOnMenuItemClickListener {
            if (mode == 1) {
                configSearch!!.title = getString(R.string.by_data)
                mode = 0
            }
            else {
                configSearch!!.title = getString(R.string.by_tag)
                mode = 1
            }
            true
        }
        searchView!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                val sharedPreferences = applicationContext.getSharedPreferences("notes", MODE_PRIVATE)
                val gson = Gson()
                val json: String? = sharedPreferences.getString("notes", null)
                json?.let {
                    val type = object : TypeToken<ArrayList<Note?>?>() {}.type
                    notes = gson.fromJson(json, type)
                }
                noteSearch = ArrayList<Note>()
                if (query!!.isEmpty())
                {
                    for (note: Note in notes) {
                        noteSearch!!.add(note)
                    }
                    configSearch!!.isEnabled = true
                    adapter!!.clear()
                    adapter!!.addAll(noteSearch!!)
                    return false
                }
                configSearch!!.isEnabled = false
                if (mode == 1) {
                    for (note: Note in notes) {
                        if (note.title.contains(query as CharSequence) || note.text.contains(query as CharSequence)) {
                            noteSearch!!.add(note)
                        }
                    }
                }
                else
                {
                    for (note: Note in notes) {
                        for (tag:String in note.tag)
                            if (tag.contains(query as CharSequence) || tag.contains(query as CharSequence)) {
                                noteSearch!!.add(note)
                                break
                            }
                    }
                }
                adapter!!.clear()
                adapter!!.addAll(noteSearch!!)
                return false
            }
        })

        return true
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        listView = findViewById(R.id.listView)
        gridView = findViewById(R.id.grid_view)
        val sharedPreferences1 = applicationContext.getSharedPreferences("style", MODE_PRIVATE)
        val gson = Gson()
        curStyle = sharedPreferences1.getString("style", null)
        val sharedPreferences = applicationContext.getSharedPreferences("notes", MODE_PRIVATE)
        val json: String? = sharedPreferences.getString("notes", null)
        json?.let {
            val type = object : TypeToken<ArrayList<Note?>?>() {}.type
            notes = gson.fromJson(json, type)
        }
        adapter = NoteAdapter(this, notes)
        listView!!.adapter = adapter
        gridView!!.adapter = adapter

    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.app_bar_search -> {
                true
            }
            R.id.style_view -> {
                if (curStyle == "listView" || curStyle == null) {
                    menu!!.findItem(R.id.style_view).icon = ContextCompat.getDrawable(this, R.drawable.ic_baseline_format_list_bulleted_24)
                    curStyle = "gridView"
                    listView!!.visibility = View.GONE
                    gridView!!.visibility = View.VISIBLE
                    adapter!!.modeView = 0
                    val sharedPreferences = applicationContext.getSharedPreferences("style", MODE_PRIVATE)
                    sharedPreferences.edit().putString("style", curStyle).apply()
                } else {
                    menu!!.findItem(R.id.style_view).icon = ContextCompat.getDrawable(this, R.drawable.ic_baseline_grid_view_24)
                    curStyle = "listView"
                    listView!!.visibility = View.VISIBLE
                    gridView!!.visibility = View.GONE
                    adapter!!.modeView = 1
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
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
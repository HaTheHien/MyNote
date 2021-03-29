package com.example.mynote

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.os.Bundle
import android.text.InputType
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mynote.MyApplication.Companion.notes
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class EditTagActivity : AppCompatActivity() {
    private var noteId:Int = -1
    private var allTag:ArrayList<String>? = null;
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.edit_tag_toolbar_menu, menu)
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_tag)
        val sharedPreferences1 = applicationContext.getSharedPreferences("notes", MODE_PRIVATE)
        val gson = Gson()
        val json1: String? = sharedPreferences1.getString("notes", null)
        json1?.let {
            val type = object : TypeToken<java.util.ArrayList<Note?>?>() {}.type
            notes = gson.fromJson(json1, type)
        }
        val sharedPreferences2 = applicationContext.getSharedPreferences("allTag", MODE_PRIVATE)
        val json2: String? = sharedPreferences2.getString("allTag", null)
        json2?.let {
            val type = object : TypeToken<java.util.ArrayList<String?>?>() {}.type
            allTag = gson.fromJson(json2, type)
        }
        if (allTag == null){
            allTag = ArrayList()
        }

        noteId = intent.getIntExtra("noteId", -1)
        if (noteId != -1){
            onDetachedFromWindow()
        }

        val listTag = findViewById<ListView>(R.id.List_tag_edit)
        listTag.adapter = TagEditAdapter(this, noteId, allTag, notes)
    }
    @SuppressLint("ShowToast")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.getItemId()) {
            R.id.add_tag -> {
                val builder: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(this)
                builder.setTitle("Tag name")

                val input = EditText(this)
                builder.setView(input)

                builder.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
                    if (allTag!!.contains(input.text.toString()) || input.text.toString().isEmpty()){
                        Toast.makeText(this,"This tag is exist or empty",Toast.LENGTH_LONG).show()
                    }
                    else {
                        allTag!!.add(input.text.toString())
                        val sharedPreferences = applicationContext.getSharedPreferences("allTag", MODE_PRIVATE)
                        val gson = Gson()
                        val json = gson.toJson(allTag)
                        sharedPreferences.edit().putString("allTag", json).apply()
                    }
                })
                builder.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })

                builder.show()
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
}
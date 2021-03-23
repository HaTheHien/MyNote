package com.example.mynote

import android.app.Application
import java.util.ArrayList

class MyApplication : Application(){
    companion object {
        var notes = ArrayList<Note>()
    }
    override fun onCreate() {
        super.onCreate()
    }
}
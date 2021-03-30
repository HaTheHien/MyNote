package com.example.mynote;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.Gson;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class TagEditAdapter extends ArrayAdapter<String> {
    ArrayList<String> allTag;
    ArrayList<Note> notes;
    Integer noteId;
    Context context;
    public TagEditAdapter(Context context, int noteId, ArrayList<String> allTag,ArrayList<Note> notes){
        super(context,0,allTag);
        this.allTag = allTag;
        this.notes = notes;
        this.noteId = noteId;
        this.context = context;
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String tag = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_tag_edit, parent, false);
        }
        // Lookup view for data population
        TextView tvText = convertView.findViewById(R.id.textViewTag);
        CheckBox tvCheckBox = convertView.findViewById(R.id.checkBox);

        tvText.setText(tag);
        tvCheckBox.setChecked(notes.get(noteId).getTag().contains(tag));
        tvCheckBox.setOnClickListener(v -> {
            if (tvCheckBox.isChecked()){
                notes.get(noteId).getTag().add(tag);
            }
            else{
                ArrayList<String> tempTag = notes.get(noteId).tag;
                for (int i=0;i<tempTag.size();i++){
                    if (tempTag.get(i).compareTo(tag) == 0) {
                        notes.get(noteId).tag.remove(i);
                        break;
                    }
                }
            }
            SharedPreferences mPrefs = context.getSharedPreferences("notes",MODE_PRIVATE);
            Gson gson = new Gson();
            String json = gson.toJson(notes);
            mPrefs.edit().putString("notes", json).apply();
        });
        return convertView;
    }
}

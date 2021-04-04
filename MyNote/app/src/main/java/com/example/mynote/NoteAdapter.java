package com.example.mynote;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends ArrayAdapter<Note> {
    ArrayList<Note> notes = null;
    public NoteAdapter(Context context, ArrayList<Note> notes){
        super(context,0,notes);
        this.notes = notes;
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Note note = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_note, parent, false);
        }
        // Lookup view for data population
        TextView tvTitle = convertView.findViewById(R.id.tvTitle);
        TextView tvDate = convertView.findViewById(R.id.tvDate);
        RecyclerView tagView = convertView.findViewById(R.id.recycle_view_tag);

        tvTitle.setText(note.getTitle());
        tvDate.setText(note.getDate());

        TagAdapter tagAdapter = new TagAdapter(note.getTag());
        tagView.setAdapter(tagAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(parent.getContext(), LinearLayoutManager.HORIZONTAL, false);
        layoutManager.setItemPrefetchEnabled(false);
        tagView.setLayoutManager(layoutManager);
        //ConstraintLayout constraintLayout = convertView.findViewById(R.id.constraint);

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(parent.getContext(), EditNoteActivity.class);
                int nodePosition = -1;
                for (int i = 0; i < notes.size(); i++) {
                    if (notes.get(i).date.compareTo(note.date) == 0)
                    {
                        nodePosition = i;
                        break;
                    }
                }
                intent.putExtra("noteId", nodePosition);
                ((Activity)parent.getContext()).startActivityForResult(intent,1);
            }
        };

        convertView.setOnClickListener(clickListener);
        //tagView.setOnClickListener(clickListener);


        return convertView;
    }
}

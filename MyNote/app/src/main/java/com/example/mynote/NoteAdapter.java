package com.example.mynote;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NoteAdapter extends ArrayAdapter<Note> {
    public NoteAdapter(Context context, ArrayList<Note> notes){
        super(context,0,notes);
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
        TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
        TextView tvDate = (TextView) convertView.findViewById(R.id.tvDate);
        RecyclerView tagView = convertView.findViewById(R.id.recycle_view_tag);

        tvTitle.setText(note.getTitle());
        tvDate.setText(note.getDate());

        TagAdapter tagAdapter = new TagAdapter(note.getTag());
        tagView.setAdapter(tagAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(parent.getContext(), LinearLayoutManager.HORIZONTAL, false);
        layoutManager.setItemPrefetchEnabled(false);
        tagView.setLayoutManager(layoutManager);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(parent.getContext(), EditNoteActivity.class);
                intent.putExtra("noteId", position);
                parent.getContext().startActivity(intent);
            }
        });
        return convertView;
    }
}

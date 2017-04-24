package com.along.android.healthmanagement.fragments;

import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.along.android.healthmanagement.R;
import com.along.android.healthmanagement.activities.NoteDetailActivity;
import com.along.android.healthmanagement.adapters.NotesAdapter;
import com.along.android.healthmanagement.entities.Note;
import com.along.android.healthmanagement.helpers.EntityManager;

import java.util.ArrayList;
import java.util.List;

public class NotesFragment extends BasicFragment {
    NotesAdapter notesAdapter;

    public NotesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notes, container, false);


        List<Note> noteList;
        try {
            noteList = EntityManager.listAll(Note.class);
        } catch (SQLiteException e) {
            noteList = new ArrayList<>();
        }


        if (notesAdapter == null) {
            notesAdapter = new NotesAdapter(getActivity(), noteList);
        } else {
            notesAdapter.clear();
            notesAdapter.addAll(noteList);
            notesAdapter.notifyDataSetChanged();
        }

        ListView listView = (ListView) view.findViewById(R.id.note_list);
        TextView tvEmptyMsg = (TextView) view.findViewById(R.id.tvVSEmptyMsg);
        listView.setEmptyView(tvEmptyMsg);
        listView.setAdapter(notesAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView noteId = (TextView) view.findViewById(R.id.tvXLNoteId);

                Intent intent = new Intent(getActivity(),NoteDetailActivity.class);
                intent.putExtra("selectedNoteItemId", noteId.getText().toString());
                startActivity(intent);
            }
        });

        return view;
    }

}
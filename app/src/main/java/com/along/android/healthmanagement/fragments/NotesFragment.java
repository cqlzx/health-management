package com.along.android.healthmanagement.fragments;

import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.along.android.healthmanagement.R;
import com.along.android.healthmanagement.activities.NoteDetailActivity;
import com.along.android.healthmanagement.adapters.NotesAdapter;
import com.along.android.healthmanagement.entities.Note;
import com.along.android.healthmanagement.fragments.vitalsigns.AddVitalSignFormFragment;
import com.along.android.healthmanagement.helpers.EntityManager;

import java.util.ArrayList;
import java.util.List;

public class NotesFragment extends BasicFragment {
    NotesAdapter notesAdapter;
    public View rootView;

    public NotesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_notes, container, false);
        initializeNoteListData(rootView);

        return rootView;
    }

    private void initializeNoteListData(View view){


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


        ImageView fab = (ImageView) view.findViewById(R.id.add_note_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),NoteDetailActivity.class);
                startActivity(intent);
            }
        });

    }
    @Override
    public void onResume(){
        super.onResume();
        initializeNoteListData(rootView);
    }

}
package com.along.android.healthmanagement.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.along.android.healthmanagement.R;
import com.along.android.healthmanagement.entities.Note;
import com.along.android.healthmanagement.helpers.Utility;

import java.util.Calendar;
import java.util.List;


/**
 * Created by renxiaolei on 4/23/16.
 */

public class NotesAdapter extends ArrayAdapter<Note> {
    public NotesAdapter(Context context, List<Note> notes) {
        super(context, 0, notes);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        final ListView listView = (ListView) parent;
        final Note note = getItem(position);

        if(null == convertView) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_note, parent, false);
        }

        // Id
        TextView tvNoteId = (TextView) listItemView.findViewById(R.id.tvXLNoteId);
        tvNoteId.setText(note.getId().toString());

        // Title
        TextView tvNoteTitle = (TextView) listItemView.findViewById(R.id.tvNoteTitle);
        tvNoteTitle.setText(note.getTitle().toString());

        // Added time
        TextView tvAddedTime = (TextView) listItemView.findViewById(R.id.tvNoteAddedTime);

        System.out.println("-note t-->>>"+note.getTitle().toString());
        System.out.println("-date-->>>"+note.getDate().toString());

        // Prescription Date
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(Long.parseLong(note.getDate().toString()));
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        String[] months = new String[]{"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
        String prescriptionDateText =  months[mMonth] + " " + mDay + ", " + mYear;

        tvAddedTime.setText(prescriptionDateText);

        // Delete
        TextView tvDelete = (TextView) listItemView.findViewById(R.id.tvNoteDelete);
        tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteAlertDialog(note,listView);
            }
        });

        return listItemView;
    }
    private void showDeleteAlertDialog(final Note note, final ListView listView) {
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setTitle("Confirm Delete");
        alert.setMessage("Are you sure to delete this medicine entry?");
        alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                /* delete the note entry from the database */
                Note notesRecord = Note.findById(Note.class,note.getId());
                notesRecord.delete();
                NotesAdapter.this.remove(note);
                Utility.setListViewHeightBasedOnChildren(listView);
                dialog.dismiss();
            }
        });
        alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alert.show();
    }
}

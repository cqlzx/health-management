package com.along.android.healthmanagement.fragments;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import com.along.android.healthmanagement.R;

import java.util.Calendar;

/**
 * Created by RitenVithlani on 2/21/17.
 */

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener{

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String displayTime = hourOfDay + ":" + minute;
        if(null!= this.getArguments() && "addMedicineTime".equals(this.getArguments().getString("whichTimer"))) {
            TextView txtView = (TextView) getActivity().findViewById(R.id.tvTimings);
            if(null != txtView.getText() && !"".equals(txtView.getText())) {
                txtView.setText(txtView.getText() + ", " + displayTime);
            } else {
                txtView.setText(displayTime);
            }
            LinearLayout llMedicineTimings = (LinearLayout) getActivity().findViewById(R.id.llMedicineTimings);
            llMedicineTimings.setVisibility(View.VISIBLE);
        }
    }
}

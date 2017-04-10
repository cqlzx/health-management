package com.along.android.healthmanagement.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.along.android.healthmanagement.R;
import com.along.android.healthmanagement.entities.Diet;

import java.util.List;

/**
 * Created by renxiaolei on 4/6/16.
 */

public class DietAdapter extends ArrayAdapter<Diet> {
    public DietAdapter(Context context, List<Diet> diets) {
        super(context, 0, diets);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        final NestedScrollView listView = (NestedScrollView) parent;
        final Diet diet = getItem(position);

        if(null == convertView) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.diet_content_scrolling, parent, false);
        }
        // Id
        TextView vshId = (TextView) listItemView.findViewById(R.id.tvDietId);
        vshId.setText(diet.getId().toString());

//        Calendar c = Calendar.getInstance();
////        c.setTimeInMillis(Long.parseLong(diet.getDate().toString()));
//        int mYear = c.get(Calendar.YEAR);
//        int mMonth = c.get(Calendar.MONTH);
//        int mDay = c.get(Calendar.DAY_OF_MONTH);
//        String[] months = new String[]{"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
//        String prescriptionDateText =  months[mMonth] + " " + mDay + ", " + mYear;
//        // date
//        TextView vshDateTV = (TextView) listItemView.findViewById(R.id.tvStartDateInDiet);
//        vshDateTV.setText(prescriptionDateText);


//        TextView tvDelete = (TextView) listItemView.findViewById(R.id.tvDelete);
//        tvDelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showDeleteAlertDialog(vitalsign,listView);
//            }
//        });

        return listItemView;
    }

}

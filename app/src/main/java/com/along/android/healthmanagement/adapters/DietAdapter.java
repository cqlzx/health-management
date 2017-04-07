package com.along.android.healthmanagement.adapters;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.along.android.healthmanagement.entities.Diet;

import java.util.List;

/**
 * Created by renxiaolei on 4/6/16.
 */

public class DietAdapter extends ArrayAdapter<Diet> {
    public DietAdapter(Context context, List<Diet> diets) {
        super(context, 0, diets);
    }

//    @NonNull
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        View view = convertView;
//        final ListView listView = (ListView) parent;
//        final Diet diet = getItem(position);
//
//        if(null == convertView) {
//            view = LayoutInflater.from(getContext()).inflate(R.layout.diet_content_scrolling, parent, false);
//        }
//
//
//
//        // Id
//        TextView vshId = (TextView) listItemView.findViewById(R.id.tvXLVSHId);
//        vshId.setText(vitalsign.getId().toString());
//
//        // date
//        TextView vshDateTV = (TextView) listItemView.findViewById(R.id.tvXLVSHDate);
//
//        // Prescription Date
//        Calendar c = Calendar.getInstance();
////        String startDate = null != prescription.getStartDate() ? prescription.getStartDate() : "";
//        c.setTimeInMillis(Long.parseLong(vitalsign.getDate().toString()));
//        int mYear = c.get(Calendar.YEAR);
//        int mMonth = c.get(Calendar.MONTH);
//        int mDay = c.get(Calendar.DAY_OF_MONTH);
//
//        String[] months = new String[]{"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
//        String prescriptionDateText =  months[mMonth] + " " + mDay + ", " + mYear;
//
//        vshDateTV.setText(prescriptionDateText);
//
//
////        LinearLayout llDelete = (LinearLayout) listItemView.findViewById(R.id.llDelete);
////        ImageView ivDelete = (ImageView) listItemView.findViewById(R.id.ivDelete);
//        TextView tvDelete = (TextView) listItemView.findViewById(R.id.tvDelete);
//        tvDelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showDeleteAlertDialog(vitalsign,listView);
//            }
//        });
//
//        return view;
//    }
}

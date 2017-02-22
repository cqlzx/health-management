package com.along.android.healthmanagement.fragments;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.along.android.healthmanagement.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BasicFragment extends Fragment {


    public BasicFragment() {
        // Required empty public constructor
    }

    protected void createFragment(Fragment fragmentObject, String fragmentTag) {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_navigation_drawer, fragmentObject, fragmentTag).
                addToBackStack(fragmentTag).
                commit();
    }
}

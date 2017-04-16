package com.along.android.healthmanagement.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

    public static class NotificationFragment extends BasicFragment {
        public NotificationFragment() {
            // Required empty public constructor
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            return inflater.inflate(R.layout.fragment_notification, container, false);
        }
    }
}

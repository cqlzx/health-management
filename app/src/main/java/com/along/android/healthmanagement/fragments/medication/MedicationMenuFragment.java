package com.along.android.healthmanagement.fragments.medication;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.along.android.healthmanagement.R;
import com.along.android.healthmanagement.fragments.BasicFragment;


public class MedicationMenuFragment extends BasicFragment {
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private TabLayout tabLayout;

    public MedicationMenuFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_medication_listing, container, false);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) view.findViewById(R.id.medicationViewPager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        tabLayout = (TabLayout) view.findViewById(R.id.medicationTabs);
        tabLayout.setupWithViewPager(mViewPager);


        SearchView svMedicationSearchEntry = (SearchView) view.findViewById(R.id.sv_medication_search_entry);

        AutoCompleteTextView searchTextContent = (AutoCompleteTextView) svMedicationSearchEntry.findViewById(svMedicationSearchEntry.getContext().getResources().getIdentifier("android:id/search_src_text", null, null));
        searchTextContent.setTextSize(16); //Set the text size
        searchTextContent.setGravity(Gravity.BOTTOM);

        svMedicationSearchEntry.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    createFragment(new MedicationSearchFragment(), "medicationSearchFragment");
                }
            }
        });

        ImageView fab = (ImageView) view.findViewById(R.id.add_medication_fab);
        ScaleAnimation animation = new ScaleAnimation(0f, 1.0f, 0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setFillAfter(true);
        animation.setDuration(800);
        fab.startAnimation(animation);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Invoke the fragment to add new Medication form
                createFragment(new AddPrescriptionFormFragment(), "addPrescriptionFormFragment");
            }
        });
        return view;
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.

            switch (position) {
                case 0:
                    return new MedicationCurrentTabFragment();
                case 1:
                    return new MedicationHistoryTabFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Current Medication";
                case 1:
                    return "Medication History";
            }
            return null;
        }
    }

}

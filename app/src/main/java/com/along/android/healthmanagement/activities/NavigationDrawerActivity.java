package com.along.android.healthmanagement.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.along.android.healthmanagement.R;
import com.along.android.healthmanagement.fragments.DietFragment;
import com.along.android.healthmanagement.fragments.HomeFragment;
import com.along.android.healthmanagement.fragments.MedicationListingFragment;
import com.along.android.healthmanagement.helpers.SessionData;

public class NavigationDrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header=navigationView.getHeaderView(0);

        SessionData sessionData = new SessionData(NavigationDrawerActivity.this);
        TextView tvNavUsername = (TextView) header.findViewById(R.id.tvNavUsername);
        tvNavUsername.setText(null != sessionData.getUsername() ? sessionData.getUsername() : "");
        TextView tvNavEmail = (TextView) header.findViewById(R.id.tvNavEmail);
        tvNavEmail.setText(null != sessionData.getEmail() ? sessionData.getEmail() : "");

        createFragment(new HomeFragment(), "homeFragment");
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_signout) {
            return signOut();
        }

        return super.onOptionsItemSelected(item);
    }

    protected boolean signOut() {
        Intent intent = new Intent();
        intent.setClass(NavigationDrawerActivity.this, LoginActivity.class);
        startActivity(intent);

        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            Intent intent = new Intent(NavigationDrawerActivity.this, ProfileActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_medication) {
            createFragment(new MedicationListingFragment(), "medicationListingFragment");
        } else if (id == R.id.nav_diet) {
            createFragment(new DietFragment(), "dietFragment");
        } else if (id == R.id.nav_vital_signs) {

        } else if (id == R.id.nav_notification) {

        } else if (id == R.id.nav_settings) {

        }
        else if (id == R.id.nav_signout) {
            signOut();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void createFragment(Fragment fragmentObject, String fragmentTag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_navigation_drawer, fragmentObject, fragmentTag).
                addToBackStack(fragmentTag).
                commit();
    }
}

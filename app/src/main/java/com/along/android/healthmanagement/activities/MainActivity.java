package com.along.android.healthmanagement.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.along.android.healthmanagement.R;

public class MainActivity extends NavigationDrawerActivity {

    Button btnMedicationTile, btnDietTile, btnVitalSignsTile, btnCommunicationTile;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        super.onCreateDrawer();

        btnMedicationTile = (Button) findViewById(R.id.btn_medication_tile);
        btnDietTile = (Button) findViewById(R.id.btn_diet_tile);
        btnVitalSignsTile = (Button) findViewById(R.id.btn_vital_signs_tile);
        btnCommunicationTile = (Button) findViewById(R.id.btn_communication_tile);

        btnMedicationTile.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, MedicationActivity.class);
                startActivity(intent);
            }
        });

        btnDietTile.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, DietActivity.class);
                startActivity(intent);
            }
        });

        btnVitalSignsTile.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, VitalSignsActivity.class);
                startActivity(intent);
            }
        });

        btnCommunicationTile.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, CommunicationActivity.class);
                startActivity(intent);
            }
        });

    }
}

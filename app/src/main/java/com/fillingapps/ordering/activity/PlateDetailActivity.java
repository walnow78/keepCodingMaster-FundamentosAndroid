package com.fillingapps.ordering.activity;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.fillingapps.ordering.R;
import com.fillingapps.ordering.fragment.PlateDetailFragment;
import com.fillingapps.ordering.model.Plate;

public class PlateDetailActivity extends AppCompatActivity {

    private FloatingActionButton mAddPlateButton;
    private Plate mCurrentPlate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plate_detail);

        // Asignamos la Toolbar como "ActionBar"
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // TODO get plate from bundle arguments

        // Obtenemos la referencia al FAB para decirle qué pasa si lo pulsan
        mAddPlateButton = (FloatingActionButton) findViewById(R.id.add_plate_button);
        if (mAddPlateButton != null) {
            mAddPlateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO: añadir plato a la mesa y volver a lista de platos del menu
                }
            });
            mAddPlateButton.setVisibility(View.GONE);
        }

        //Insertamos el fragment con el detalle del plato
        FragmentManager fm = getFragmentManager();
        if (findViewById(R.id.plate_detail) != null) {
            if (fm.findFragmentById(R.id.plate_detail) == null) {
                fm.beginTransaction()
                        .add(R.id.plate_detail, PlateDetailFragment.newInstance(mCurrentPlate))
                        .commit();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
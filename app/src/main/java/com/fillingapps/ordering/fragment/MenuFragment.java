package com.fillingapps.ordering.fragment;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.fillingapps.ordering.PlateView;
import com.fillingapps.ordering.R;
import com.fillingapps.ordering.model.Plate;
import com.fillingapps.ordering.model.Plates;
import com.fillingapps.ordering.network.PlatesDownloader;

import android.support.v7.widget.RecyclerView;

public class MenuFragment extends Fragment implements PlatesDownloader.OnPlatesReceivedListener{

    private RecyclerView mPlateList;

    public static MenuFragment newInstance() {
        return new MenuFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View root = inflater.inflate(R.layout.fragment_menu, container, false);

        mPlateList = (RecyclerView) root.findViewById(R.id.menu_recycler);
        mPlateList.setLayoutManager(new LinearLayoutManager(getActivity()));
        mPlateList.setItemAnimator(new DefaultItemAnimator());

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Plates.getInstance().getPlates().size() == 0){
            downloadMenu();
        }else{
            setPlates();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_menu_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_refresh_plates) {
            downloadMenu();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void downloadMenu() {
        showLoading();
        AsyncTask<String, Integer, Plates> platesTask = new PlatesDownloader(this);
        platesTask.execute();
    }

    private void setPlates(){
        Plates plates = Plates.getInstance();
        if (plates.getPlates().size() > 0){
            mPlateList.swapAdapter(new PlatesAdapter(plates), false);
        }
    }

    public void showLoading() {
        if (getView() != null) {
            getView().findViewById(R.id.menu_recycler).setVisibility(View.GONE);
            getView().findViewById(R.id.loading).setVisibility(View.VISIBLE);
        }
    }

    public void showPlates() {
        if (getView() != null) {
            getView().findViewById(R.id.menu_recycler).setVisibility(View.VISIBLE);
            getView().findViewById(R.id.loading).setVisibility(View.GONE);
        }
    }

    @Override
    public void onPlatesReceivedListener() {
        // TODO:refresh table
        setPlates();
        showPlates();
    }

    // ViewHolder que maneja una vista
    protected class PlatesViewHolder extends RecyclerView.ViewHolder {
        private PlateView mPlateView;


        public PlatesViewHolder(View itemView) {
            super(itemView);

            mPlateView = (PlateView) itemView;
        }

        // Nos asocian el modelo con este ViewHolder
        public void bindForecast(final Plate plate) {

            mPlateView.setPlateName(plate.getName());
            mPlateView.setPlateIngredients(plate.getIngredientsString());
            mPlateView.setPlatePrice(plate.getPrice());
            int iconID = getActivity().getResources().getIdentifier(plate.getImage(), "drawable", getActivity().getPackageName());
            mPlateView.setPlateImage(iconID);
        }
    }

    // Adaptador de RecyclerView que maneja varios ViewHolder eficientemente
    protected class PlatesAdapter extends RecyclerView.Adapter<PlatesViewHolder> {
        private Plates mPlates;

        public PlatesAdapter(Plates plates) {
            super();
            mPlates = plates;
        }

        @Override
        public PlatesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new PlatesViewHolder(new PlateView(getActivity()));
        }

        @Override
        public void onBindViewHolder(PlatesViewHolder holder, int position) {
            Plate currentPlate = mPlates.getPlates().get(position);
            holder.bindForecast(currentPlate);
        }

        @Override
        public int getItemCount() {
            return mPlates.getPlates().size();
        }
    }

}

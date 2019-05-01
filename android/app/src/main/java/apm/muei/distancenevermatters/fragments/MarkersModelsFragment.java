package apm.muei.distancenevermatters.fragments;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import apm.muei.distancenevermatters.R;
import apm.muei.distancenevermatters.activities.CreateGameActivity;
import apm.muei.distancenevermatters.adapters.MapsRecyclerViewAdapter;
import apm.muei.distancenevermatters.adapters.MarkersRecyclerViewAdapter;
import apm.muei.distancenevermatters.adapters.ModelsRecyclerViewAdapter;
import apm.muei.distancenevermatters.entities.Marker;
import apm.muei.distancenevermatters.entities.Model;
import apm.muei.distancenevermatters.volley.VolleyCallback;
import apm.muei.distancenevermatters.volley.WebService;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;

public class MarkersModelsFragment extends Fragment implements MarkersRecyclerViewAdapter.OnItemDownloadSelected {
    private Gson gson;
    List<Marker> markers = new ArrayList<>();
    List<Model> models = new ArrayList<>();
    Marker marker;
    Model model;
    private static final int NUM_COLUMNS = 3;

    private MarkersRecyclerViewAdapter.OnItemDownloadSelected dSelected;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        View rootView = inflater.inflate(R.layout.fragment_marker_model, container, false);

        ButterKnife.bind(this, rootView);
        gson = new GsonBuilder().create();

        final ModelsRecyclerViewAdapter adapter = new ModelsRecyclerViewAdapter(getActivity().getApplicationContext(), this);

        final CreateGameActivity activity = (CreateGameActivity) getActivity();
        WebService.getModelsPreview(getActivity().getApplicationContext(), new VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                List<Model> models = Arrays.asList(gson.fromJson(result, Model[].class));
                List<Model> filteredModels = filterModels(models);
                adapter.setModels(filteredModels);
                RecyclerView recyclerView = getActivity().findViewById(R.id.markModModelsRecyclerView);
                LinearLayoutManager layoutManagerModels = new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                recyclerView.setLayoutManager(layoutManagerModels);
                recyclerView.setAdapter(adapter);

            }
        });

        final Fragment parentFragment = this;
        WebService.getMarkers(getActivity().getApplicationContext(), new VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                List<Marker> markers = Arrays.asList(gson.fromJson(result, Marker[].class));
                List<Marker> filteredMarkers = filterMarkers(markers);
                RecyclerView recyclerViewMarkers = getActivity().findViewById(R.id.markModMarkersRecyclerView);
                MarkersRecyclerViewAdapter adapterMarkers = new MarkersRecyclerViewAdapter(getActivity().getApplicationContext(), filteredMarkers, parentFragment);
                LinearLayoutManager layoutManagerMarkers = new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                recyclerViewMarkers.setLayoutManager(layoutManagerMarkers);
                recyclerViewMarkers.setAdapter(adapterMarkers);
            }
        });
        return rootView;
    }

    public void setMarker(Marker marker) {
        this.marker = marker;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    private List<Marker> filterMarkers(List<Marker> markers) {
        List<Marker> result = new ArrayList<>();
        CreateGameActivity activity = (CreateGameActivity) getActivity();
        Map<Marker, Model> selectedMarkerModels = activity.getMarkerModel();
        for(Marker marker : markers){
            if (!selectedMarkerModels.containsKey(marker)){
                result.add(marker);
            }
        }
        return result;
    }

    private List<Model> filterModels(List<Model> models){
        List<Model> result = new ArrayList<>();
        CreateGameActivity activity = (CreateGameActivity) getActivity();
        Map<Marker, Model> selectedMarkerModels = activity.getMarkerModel();

        for(Model model : models){
            if (!selectedMarkerModels.containsValue(model)){
                result.add(model);
            }
        }

        return result;
    }


    @Override
    public void onDownloadSelected() { dSelected.onDownloadSelected();}

    @OnClick (R.id.markerModelAddBtn)
    public void addMarkerModel(){
        Toast.makeText(getActivity().getApplicationContext(),
                "Añadiendo Marcador-Modelo" + model.getName(), Toast.LENGTH_LONG).show();
    }
}

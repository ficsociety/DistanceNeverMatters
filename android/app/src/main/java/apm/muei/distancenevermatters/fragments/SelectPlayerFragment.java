package apm.muei.distancenevermatters.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import apm.muei.distancenevermatters.R;
import apm.muei.distancenevermatters.activities.CreateGameActivity;
import apm.muei.distancenevermatters.activities.MainActivity;
import apm.muei.distancenevermatters.adapters.MarkersRecyclerViewAdapter;
import apm.muei.distancenevermatters.adapters.ModelsRecyclerViewAdapter;
import apm.muei.distancenevermatters.entities.Marker;
import apm.muei.distancenevermatters.entities.Model;
import apm.muei.distancenevermatters.entities.Player;
import apm.muei.distancenevermatters.entities.dto.GameDetailsDto;
import apm.muei.distancenevermatters.entities.dto.JoinGameDto;
import apm.muei.distancenevermatters.volley.VolleyCallback;
import apm.muei.distancenevermatters.volley.WebService;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SelectPlayerFragment extends Fragment implements MarkersRecyclerViewAdapter.OnItemDownloadSelected, MarkerModelInterface {
    private Gson gson;
    private Marker marker;
    private Model model;
    private long code;

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

        String result = getArguments().getString("gameDetails");
        final GameDetailsDto gameDetailsDto = new Gson().fromJson(result, GameDetailsDto.class);
        code = gameDetailsDto.getCode();

        ButterKnife.bind(this, rootView);
        gson = new GsonBuilder().create();

        final ModelsRecyclerViewAdapter adapter = new ModelsRecyclerViewAdapter(getActivity().getApplicationContext(), this);

        final CreateGameActivity activity = (CreateGameActivity) getActivity();
        WebService.getModelsPreview(getActivity().getApplicationContext(), new VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                List<Model> models = Arrays.asList(gson.fromJson(result, Model[].class));
                adapter.setModels(models);
                RecyclerView recyclerView = getActivity().findViewById(R.id.markModModelsRecyclerView);
                LinearLayoutManager layoutManagerModels = new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                recyclerView.setLayoutManager(layoutManagerModels);
                recyclerView.setAdapter(adapter);

            }
        });

        Button button = rootView.findViewById(R.id.markerModelAddBtn);
        button.setText(R.string.entrar);

        final MarkersRecyclerViewAdapter adapterMarkers = new MarkersRecyclerViewAdapter(getActivity().getApplicationContext(), this);

        final Fragment parentFragment = this;
        WebService.getMarkers(getActivity().getApplicationContext(), new VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                List<Marker> markers = Arrays.asList(gson.fromJson(result, Marker[].class));
                adapterMarkers.setMarkers(filterMarkers(gameDetailsDto.getPlayers(), markers));
                RecyclerView recyclerViewMarkers = getActivity().findViewById(R.id.markModMarkersRecyclerView);
                LinearLayoutManager layoutManagerMarkers = new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                recyclerViewMarkers.setLayoutManager(layoutManagerMarkers);
                recyclerViewMarkers.setAdapter(adapterMarkers);
            }
        });
        return rootView;
    }

    private List<Marker> filterMarkers(List<Player> players, List<Marker> markers){
        List<Marker> result = new ArrayList<>();

        for (Marker marker : markers) {
            boolean add = true;
            for (Player player : players) {
                if (marker.getName().equals("Map")) {
                    add = false;
                }
            }
            if (add){
                result.add(marker);
            }
        }
        return result;
    }



    public void setMarker(Marker marker) {
        this.marker = marker;
    }

    public void setModel(Model model) {
        this.model = model;
    }


    @Override
    public void onDownloadSelected() { dSelected.onDownloadSelected();}

    @OnClick (R.id.markerModelAddBtn)
    public void addMarkerModel(){
        if ((this.model != null) && (this.marker != null)){
            JoinGameDto joinGameDto = new JoinGameDto(this.marker.getId(), this.model.getId(), this.code);
            WebService.joinGame(getActivity().getApplicationContext(), joinGameDto, new VolleyCallback() {
                @Override
                public void onSuccess(String result) {
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                }
            });

        } else{
            Toast.makeText(getActivity().getApplicationContext(),
                    "Debe seleccionar un marcador y un modelo", Toast.LENGTH_LONG).show();
        }

    }
}

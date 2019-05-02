package apm.muei.distancenevermatters.fragments;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import apm.muei.distancenevermatters.activities.CreateGameActivity;
import apm.muei.distancenevermatters.adapters.MapsRecyclerViewAdapter;
import apm.muei.distancenevermatters.R;
import apm.muei.distancenevermatters.adapters.MarkerModelViewAdapter;
import apm.muei.distancenevermatters.entities.Marker;
import apm.muei.distancenevermatters.entities.Model;
import apm.muei.distancenevermatters.entities.dto.CreateGameDto;
import apm.muei.distancenevermatters.volley.VolleyCallback;
import apm.muei.distancenevermatters.volley.WebService;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreateGameFragment extends Fragment {

    private List<apm.muei.distancenevermatters.entities.Map> maps = new ArrayList<>();

    private int selectedMap = -1;
    TextInputLayout name;
    TextInputLayout description;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_create_game, container, false);

        ButterKnife.bind(this, rootView);
        name = rootView.findViewById(R.id.cGameName);
        description = rootView.findViewById(R.id.cGameDescription);

        drawMarkerModels(rootView);

        final MapsRecyclerViewAdapter adapter = new MapsRecyclerViewAdapter(getActivity().getApplicationContext(), this);

        WebService.getMaps(getActivity().getApplicationContext(), new VolleyCallback() {
            Gson gson = new GsonBuilder().create();

            @Override
            public void onSuccess(String result) {
                maps = Arrays.asList(gson.fromJson(result, apm.muei.distancenevermatters.entities.Map[].class));
                LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                RecyclerView recyclerView = getActivity().findViewById(R.id.cGameMapsRecyclerView);
                recyclerView.setLayoutManager(layoutManager);
                adapter.setMaps(maps);
                recyclerView.setAdapter(adapter);
            }
        });


        return rootView;
    }

    public void setSelectedMap(int selectedMap){
        this.selectedMap = selectedMap;
    }

    public int getSelectedMap(){
        return this.selectedMap;
    }

    public void removeMarkerModel(Marker marker){
        CreateGameActivity activity = (CreateGameActivity) getActivity();
        activity.removeMarkerModel(marker);
    }

    private void drawMarkerModels(View view){
        CreateGameActivity activity = (CreateGameActivity) getActivity();
        Map<Marker, Model> markerModel = activity.getMarkerModel();

        if (!markerModel.isEmpty()){
            LinearLayoutManager layoutManager = new LinearLayoutManager(activity.getApplicationContext(), LinearLayoutManager.VERTICAL, false);
            RecyclerView recyclerView = view.findViewById(R.id.cGameMarkersModelsView);
            recyclerView.setLayoutManager(layoutManager);
            MarkerModelViewAdapter adapter = new MarkerModelViewAdapter(getActivity().getApplicationContext(), this, markerModel);
            recyclerView.setAdapter(adapter);
        }

    }

    @Override
    public void onResume() {
        super.onResume();

        Toolbar toolbar = getActivity().findViewById(R.id.createGameToolbar);
        toolbar.setTitle(R.string.create_game_title);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                Toast.makeText(getActivity().getApplicationContext(),
                        "Mostrando lista de partidas", Toast.LENGTH_SHORT).show();
                getActivity().onBackPressed();
            }
        });
    }

    @OnClick(R.id.cGameBtnAddMarkerModel)
    public void addMarkerModel(View view){

        MarkersModelsFragment markersModelsFragment = new MarkersModelsFragment();
        this.getFragmentManager().beginTransaction()
                .replace(R.id.create_game_fragments, markersModelsFragment)
                .addToBackStack(null)
                .commit();
    }

    @OnClick(R.id.cGameBtnCreateGame)
    public void createGame(View view){

        if (name.getEditText().getText().toString().equals("")){
            name.setError("Campo obligatorio");
        } else if (selectedMap == -1){
            Toast.makeText(getActivity().getApplicationContext(),
                    "Debe seleccionar un mapa", Toast.LENGTH_LONG).show();

        } else {

            final CreateGameDto createGameDto = getCreateGameDto();
            final CreateGameFragment createGameFragment = this;


            WebService.createGame(getActivity().getApplicationContext(), createGameDto, new VolleyCallback() {
                Gson gson = new GsonBuilder().create();
                @Override
                public void onSuccess(String result) {
                    long code = gson.fromJson(result, Long.class);

                    GameCreatedFragment gameCreatedFragment = new GameCreatedFragment();
                    Bundle bundle = new Bundle();
                    bundle.putLong("code", code);
                    gameCreatedFragment.setArguments(bundle);

                    createGameFragment.getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.create_game_fragments, gameCreatedFragment)
                            .addToBackStack(null)
                            .commit();
                }

            });

        }
    }

    private CreateGameDto getCreateGameDto (){
        long mapId = maps.get(selectedMap).getId();
        Map<String, Long> markerModel = new HashMap<>();
        String name = this.name.getEditText().getText().toString();
        String description = this.description.getEditText().getText().toString();

        CreateGameActivity activity = (CreateGameActivity) getActivity();

        for(Map.Entry<Marker, Model> entry : activity.getMarkerModel().entrySet()){
            markerModel.put(String.valueOf(entry.getKey().getId()), entry.getValue().getId());
        }

        return new CreateGameDto(name, description, mapId, markerModel);
    }

}

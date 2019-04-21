package apm.muei.distancenevermatters.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.os.StrictMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.android.volley.toolbox.NetworkImageView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.Arrays;
import java.util.List;

import apm.muei.distancenevermatters.adapters.MapsRecyclerViewAdapter;
import apm.muei.distancenevermatters.entities.Map;
import apm.muei.distancenevermatters.R;
import apm.muei.distancenevermatters.volley.VolleyCallback;
import apm.muei.distancenevermatters.volley.VolleySingleton;
import apm.muei.distancenevermatters.volley.WebService;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreateGameFragment extends Fragment {

    private Gson gson;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        View rootView = inflater.inflate(R.layout.fragment_create_game, container, false);

        ButterKnife.bind(this, rootView);
        gson = new GsonBuilder().create();

        WebService.getMaps(getActivity().getApplicationContext(), new VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                List<Map> maps = Arrays.asList(gson.fromJson(result, Map[].class));

                LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                RecyclerView recyclerView = getActivity().findViewById(R.id.cGameMapsRecyclerView);
                recyclerView.setLayoutManager(layoutManager);
                MapsRecyclerViewAdapter adapter = new MapsRecyclerViewAdapter(getActivity().getApplicationContext(), maps);
                recyclerView.setAdapter(adapter);
            }
        });
        return rootView;
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
        Toast.makeText(getActivity().getApplicationContext(),
                "Crear Partida", Toast.LENGTH_LONG).show();

        // Intent intent = new Intent(getActivity(), GameCreatedActivity.class);
        // startActivity(intent);;
    }

    @OnClick({R.id.delete1, R.id.delete2})
    public void removeMarkerModel(View view) {
        Toast.makeText(getActivity().getApplicationContext(),
                "Eliminar marcador-modelo.", Toast.LENGTH_LONG).show();
    }

}

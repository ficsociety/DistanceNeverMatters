package apm.muei.distancenevermatters.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.os.StrictMode;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.Arrays;
import java.util.List;
import apm.muei.distancenevermatters.entities.Map;
import apm.muei.distancenevermatters.R;
import apm.muei.distancenevermatters.volley.VolleySingleton;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreateGameFragment extends Fragment {

    OnGameCreatedListener mCallback;
    public interface OnGameCreatedListener {
        /** Called by CreateGameFragment when a game is created */
        public void onGameCreated();
    }

    private String mapsUrl = "https://distance-never-matters-backend.appspot.com/maps";
    private RequestQueue queue;
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


        queue = VolleySingleton.getInstance(getActivity().getApplicationContext()).getRequestQueue();

        gson = new GsonBuilder().create();
        fetchMaps();
        return rootView;
    }

    private void fetchMaps(){
        Log.i("Peticion", "haciendo peticion");

        StringRequest request = new StringRequest(Request.Method.GET, mapsUrl, onMapsLoaded, onMapsError);
        queue.add(request);
    }

    private Response.Listener<String> onMapsLoaded = new Response.Listener<String>(){
        @Override
        public void onResponse(String response) {

            List<Map> maps = Arrays.asList(gson.fromJson(response, Map[].class));
            LinearLayout linearLayout = getActivity().findViewById(R.id.maps);
            for (Map map : maps){
                NetworkImageView image = new NetworkImageView(getActivity());
                image.setImageUrl(map.getUrl().toString(), VolleySingleton.getInstance(getActivity().getApplicationContext()).getImageLoader());
                linearLayout.addView(image);

            }
        }
    };

    private final Response.ErrorListener onMapsError = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.i("Error fetch maps",  error.toString());

        }
    };


    @Override
    public void onResume() {
        super.onResume();

        Toolbar toolbar = getActivity().findViewById(R.id.createGameToolbar);
        toolbar.setTitle("Crear partida"); // TODO change for string resource

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                Toast.makeText(getActivity().getApplicationContext(),
                        "Mostrando lista de partidas", Toast.LENGTH_SHORT).show();
                getActivity().onBackPressed();
            }
        });
    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception.
        try {
            mCallback = (OnGameCreatedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnGameCreatedListener");
        }
    }

    @OnClick(R.id.btn_add_marker_model)
    public void addMarkerModel(View view){
        Toast.makeText(getActivity().getApplicationContext(),
                "Añadir marcador-modelo.", Toast.LENGTH_LONG).show();
    }

    @OnClick(R.id.btn_create_game)
    public void createGame(View view){
        Toast.makeText(getActivity().getApplicationContext(),
                "Crear Partida", Toast.LENGTH_LONG).show();

        // Intent intent = new Intent(getActivity(), GameCreatedActivity.class);
        // startActivity(intent);
        fetchMaps();
    }

    @OnClick({R.id.delete1, R.id.delete2})
    public void removeMarkerModel(View view) {
        Toast.makeText(getActivity().getApplicationContext(),
                "Eliminar marcador-modelo.", Toast.LENGTH_LONG).show();
    }

}

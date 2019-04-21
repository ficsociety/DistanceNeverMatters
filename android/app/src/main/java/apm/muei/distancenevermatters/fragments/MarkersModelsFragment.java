package apm.muei.distancenevermatters.fragments;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Arrays;
import java.util.List;

import apm.muei.distancenevermatters.R;
import apm.muei.distancenevermatters.adapters.MapsRecyclerViewAdapter;
import apm.muei.distancenevermatters.adapters.ModelsRecyclerViewAdapter;
import apm.muei.distancenevermatters.entities.Map;
import apm.muei.distancenevermatters.entities.Model;
import apm.muei.distancenevermatters.volley.VolleyCallback;
import apm.muei.distancenevermatters.volley.WebService;
import butterknife.ButterKnife;

public class MarkersModelsFragment extends Fragment {
    private Gson gson;
    private static final int NUM_COLUMNS = 3;

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

        WebService.getModelsPreview(getActivity().getApplicationContext(), new VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                List<Model> models = Arrays.asList(gson.fromJson(result, Model[].class));

                RecyclerView recyclerView = getActivity().findViewById(R.id.markModModelsRecyclerView);
                ModelsRecyclerViewAdapter adapter = new ModelsRecyclerViewAdapter(getActivity().getApplicationContext(), models);
                StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(NUM_COLUMNS, LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(staggeredGridLayoutManager);
                recyclerView.setAdapter(adapter);
            }
        });
        return rootView;
    }
}

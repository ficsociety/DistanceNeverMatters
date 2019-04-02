package apm.muei.distancenevermatters.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import apm.muei.distancenevermatters.R;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreateGameFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_create_game, container, false);

        ButterKnife.bind(this, rootView);

        Toolbar toolbar = getActivity().findViewById(R.id.createGameToolbar);
        ((AppCompatActivity) getActivity()).setTitle("Crear partida");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                Toast.makeText(getActivity().getApplicationContext(),
                        "Mostrando lista de partidas", Toast.LENGTH_SHORT).show();
                getActivity().onBackPressed();
            }
        });

        return rootView;
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
    }

    @OnClick({R.id.delete1, R.id.delete2})
    public void removeMarkerModel(View view) {
        Toast.makeText(getActivity().getApplicationContext(),
                "Eliminar marcador-modelo.", Toast.LENGTH_LONG).show();
    }

}

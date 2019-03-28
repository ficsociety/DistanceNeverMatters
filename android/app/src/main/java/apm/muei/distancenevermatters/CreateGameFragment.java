package apm.muei.distancenevermatters;

import android.media.Image;
import android.os.Bundle;
import android.support.annotation.BinderThread;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import apm.muei.distancenevermatters.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreateGameFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.create_game_view, container, false);

        ButterKnife.bind(this, rootView);

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
    }

    @OnClick({R.id.delete1, R.id.delete2})
    public void removeMarkerModel(View view) {
        Toast.makeText(getActivity().getApplicationContext(),
                "Eliminar marcador-modelo.", Toast.LENGTH_LONG).show();
    }

}

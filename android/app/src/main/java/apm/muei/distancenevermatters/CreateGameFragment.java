package apm.muei.distancenevermatters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import apm.muei.distancenevermatters.R;

public class CreateGameFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.create_game_view, container, false);

        Button btnCreateGame = (Button) rootView.findViewById(R.id.btn_create_game);

        btnCreateGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                Toast.makeText(getActivity().getApplicationContext(),
                        "Crear Partida.", Toast.LENGTH_LONG).show();

            }
        });

        Button btnAddMarker = (Button) rootView.findViewById(R.id.btn_add_marker_model);
        btnAddMarker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                Toast.makeText(getActivity().getApplicationContext(),
                        "Añadir marcador-modelo.", Toast.LENGTH_LONG).show();

            }
        });

        ImageButton remove = rootView.findViewById(R.id.imageButton4);
        remove.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                removeMarkerModel(view);
            }
        });

        remove = rootView.findViewById(R.id.imageButton5);
        remove.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                removeMarkerModel(view);
            }
        });


        return rootView;
    }

    private void removeMarkerModel(View view) {
        Toast.makeText(getActivity().getApplicationContext(),
                "Eliminar marcador-modelo.", Toast.LENGTH_LONG).show();
    }

}

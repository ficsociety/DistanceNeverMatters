package apm.muei.distancenevermatters.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import apm.muei.distancenevermatters.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreateGameFragment extends Fragment {

    OnGameCreatedListener mCallback;
    public interface OnGameCreatedListener {
        /** Called by CreateGameFragment when a game is created */
        public void onGameCreated();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_create_game, container, false);

        ButterKnife.bind(this, rootView);

        return rootView;
    }

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
    public void onAttach(Activity activity) {
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

        mCallback.onGameCreated();

    }

    @OnClick({R.id.delete1, R.id.delete2})
    public void removeMarkerModel(View view) {
        Toast.makeText(getActivity().getApplicationContext(),
                "Eliminar marcador-modelo.", Toast.LENGTH_LONG).show();
    }

}

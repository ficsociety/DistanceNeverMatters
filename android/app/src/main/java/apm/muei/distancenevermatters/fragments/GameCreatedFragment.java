package apm.muei.distancenevermatters.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import apm.muei.distancenevermatters.R;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class GameCreatedFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_game_created, container, false);
        ButterKnife.bind(this, rootView);
        getActivity().setTitle("Partida creada");
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        return rootView;

    }

    @OnClick(R.id.gcreaFABshare)
    public void shareGame(View view) {
        Toast.makeText(getActivity().getApplicationContext(),
                "Compartir código.", Toast.LENGTH_LONG).show();
    }
}

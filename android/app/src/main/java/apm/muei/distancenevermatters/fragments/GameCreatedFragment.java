package apm.muei.distancenevermatters.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
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

        return rootView;

    }

    @OnClick(R.id.gcreaFABshare)
    public void shareGame(View view) {
        Toast.makeText(getActivity().getApplicationContext(),
                "Compartir código.", Toast.LENGTH_LONG).show();
    }
}

package apm.muei.distancenevermatters.fragments;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import apm.muei.distancenevermatters.R;
import apm.muei.distancenevermatters.adapters.DiceRecyclerViewAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DiceFragment extends Fragment {

    @BindView(R.id.diceListView)
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_dice, container, false);
        ButterKnife.bind(this, rootView);


        FloatingActionButton quitFab = getActivity().findViewById(R.id.quitFab);
        FloatingActionButton diceFab = getActivity().findViewById(R.id.gameFabBtnDice);
        diceFab.hide();
        quitFab.hide();

        final DiceRecyclerViewAdapter adapter = new DiceRecyclerViewAdapter(getActivity().getApplicationContext(), this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        return rootView;
    }
}

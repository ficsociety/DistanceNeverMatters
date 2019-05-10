package apm.muei.distancenevermatters.fragments;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.List;

import apm.muei.distancenevermatters.R;
import apm.muei.distancenevermatters.adapters.DiceGridViewAdapter;
import apm.muei.distancenevermatters.entities.Dice;
import apm.muei.distancenevermatters.entities.DiceContainer;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DiceFragment extends Fragment {


    @BindView(R.id.diceGridView)
    GridView gridView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_dice, container, false);
        ButterKnife.bind(this, rootView);

        List<Dice> diceList = DiceContainer.getDiceList();
        System.out.println(diceList.size());

        FloatingActionButton quitFab = getActivity().findViewById(R.id.quitFab);
        FloatingActionButton diceFab = getActivity().findViewById(R.id.gameFabBtnDice);
        diceFab.hide();
        quitFab.hide();
        final DiceGridViewAdapter adapter = new DiceGridViewAdapter(getActivity().getApplicationContext(), diceList);
        gridView.setAdapter(adapter);


        return rootView;
    }
}

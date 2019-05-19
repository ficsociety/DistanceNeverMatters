package apm.muei.distancenevermatters.fragments;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Toast;


import apm.muei.distancenevermatters.R;

import apm.muei.distancenevermatters.activities.GameActivity;

import apm.muei.distancenevermatters.adapters.DiceHistResultRecyclerAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;


public class DiceHistoricFragment extends Fragment {


    @BindView(R.id.hist_recyclerview)
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_hist_dice, container, false);
        ButterKnife.bind(this, rootView);

        Toolbar toolbar = getActivity().findViewById(R.id.gameToolbar);
        AppBarLayout appBar = getActivity().findViewById(R.id.gameAppBar);
        appBar.setVisibility(View.VISIBLE);

        toolbar.setTitle(R.string.historicotiradas);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                Toast.makeText(getActivity().getApplicationContext(),
                        "Volviendo a Tirada", Toast.LENGTH_SHORT).show();
                ((GameActivity) getActivity()).onBack();
            }
        });

        final DiceHistResultRecyclerAdapter adapter = new DiceHistResultRecyclerAdapter(this);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        return rootView;
    }


}

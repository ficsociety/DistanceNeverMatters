package apm.muei.distancenevermatters.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import apm.muei.distancenevermatters.R;
import apm.muei.distancenevermatters.adapters.GameRecyclerAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;

public class GameListFragment extends Fragment implements GameRecyclerAdapter.OnGameDetailsListener {

    private GameRecyclerAdapter.OnGameDetailsListener mListener;

    @BindView(R.id.gameRecyclerView)
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_game_list, container, false);
        ButterKnife.bind(this, rootView);

        // TODO Populate RecyclerView with real data
        GameRecyclerAdapter adapter = new GameRecyclerAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof GameRecyclerAdapter.OnGameDetailsListener) {
            mListener = (GameRecyclerAdapter.OnGameDetailsListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnGameDetailsListener");
        }
    }

    @Override
    public void onGameSelected() {
        mListener.onGameSelected();
    }

}

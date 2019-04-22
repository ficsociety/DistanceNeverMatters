package apm.muei.distancenevermatters.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import apm.muei.distancenevermatters.R;
import apm.muei.distancenevermatters.activities.MainActivity;
import apm.muei.distancenevermatters.adapters.GameRecyclerAdapter;
import apm.muei.distancenevermatters.entities.dto.GameDetailsDto;
import butterknife.BindView;
import butterknife.ButterKnife;

public class GameListFragment extends Fragment implements GameRecyclerAdapter.OnGameDetailsListener {

    private GameRecyclerAdapter.OnGameDetailsListener mListener;

    private final String user = "player1";

    private List<GameDetailsDto> filterGameList(String filter) {

        List<GameDetailsDto> gameDtoList = ((MainActivity) getActivity()).getGameDtoList();
        List<GameDetailsDto> filtered = new ArrayList<>();
        if (filter == "all") {
            return gameDtoList;
        } else {
            for(GameDetailsDto game : gameDtoList) {
                // TODO Obtener el usuario actual y quitar el hardcodeado
                if ((filter.equals("master")) && (game.getMaster().getUid().equals("roi"))) {
                    filtered.add(game);
                }
                if ((filter.equals("player")) && !(game.getMaster().getUid().equals("roi"))) {
                    filtered.add(game);
                }
            }
            return filtered;
        }
    }

    @BindView(R.id.gameRecyclerView)
    RecyclerView recyclerView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_game_list, container, false);
        ButterKnife.bind(this, rootView);

        List<GameDetailsDto> gameList = filterGameList(getArguments().getString("filter"));

        GameRecyclerAdapter adapter = new GameRecyclerAdapter(this, gameList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return rootView;
    }

    public static GameListFragment newInstance(String filter) {
        GameListFragment fragment = new GameListFragment();
        Bundle args = new Bundle();
        args.putString("filter", filter);
        fragment.setArguments(args);
        return fragment;
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

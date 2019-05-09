package apm.muei.distancenevermatters.fragments;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

import apm.muei.distancenevermatters.GlobalVars.GlobalVars;
import apm.muei.distancenevermatters.R;
import apm.muei.distancenevermatters.SharedPreference.PreferenceManager;
import apm.muei.distancenevermatters.activities.MainActivity;
import apm.muei.distancenevermatters.adapters.GameRecyclerAdapter;
import apm.muei.distancenevermatters.entities.dto.GameDetailsDto;
import butterknife.BindView;
import butterknife.ButterKnife;

public class GameListFragment extends Fragment implements GameRecyclerAdapter.OnGameDetailsListener {

    private GameRecyclerAdapter.OnGameDetailsListener mListener;

    private Context mContext;

    private List<GameDetailsDto> gameDtoList = new ArrayList<>();
    private GameDetailsDto gameDetails;
    private Gson gson;
    private IntentFilter intentFilter;
    private String filter;
    private GlobalVars gVars;


    public static final String BROADCAST_ACTION = "apm.muei.distancenevermatters.broadcastreceiverdemo";

    private List<GameDetailsDto> filterGameList(String filter) {
        setFilter(filter);
        List<GameDetailsDto> gameDtoList = ((MainActivity) getActivity()).getGameDtoList();
        List<GameDetailsDto> filtered = new ArrayList<>();
        int masterGames = 0;
        int playerGames = 0;
        gVars = new GlobalVars().getInstance();
        String userName = PreferenceManager.getInstance().getUserName();
        if (filter == mContext.getString(R.string.all)) {
            gVars.setTotal_games(gameDtoList.size());
            return gameDtoList;
        } else {
            for(GameDetailsDto game : gameDtoList) {
                // TODO Obtener el usuario actual y quitar el hardcodeado
                if ((filter.equals(mContext.getString(R.string.master))) && (game.getMaster().getUid().equals(userName))) {
                    filtered.add(game);
                    masterGames += 1;
                }
                if ((filter.equals(mContext.getString(R.string.player))) && !(game.getMaster().getUid().equals(userName))) {
                    filtered.add(game);
                    playerGames += 1;
                }
            }
            if ((filter.equals(mContext.getString(R.string.master)))) {
                gVars.setMaster_games(masterGames);
            }
            if ((filter.equals(mContext.getString(R.string.player)))) {
                gVars.setPlayer_games(playerGames);
            }
            return filtered;
        }
    }

    @BindView(R.id.gameRecyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefresh;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_game_list, container, false);
        ButterKnife.bind(this, rootView);
        new PreferenceManager().initPreference(getActivity().getApplicationContext());

        gson = new GsonBuilder().create();
        intentFilter = new IntentFilter();
        intentFilter.addAction(BROADCAST_ACTION);
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT);

        List<GameDetailsDto> gameList = filterGameList(getArguments().getString("filter"));

        GameRecyclerAdapter adapter = new GameRecyclerAdapter(this, gameList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Esto se ejecuta cada vez que se realiza el gesto
                swipeRefresh.setRefreshing(true);
                fetchGames(swipeRefresh);
            }
        });

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
        mContext = context;
        if (context instanceof GameRecyclerAdapter.OnGameDetailsListener) {
            mListener = (GameRecyclerAdapter.OnGameDetailsListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnGameDetailsListener");
        }
    }

    @Override
    public void onGameSelected(GameDetailsDto gameDetails) {
        mListener.onGameSelected(gameDetails);
    }

    @Override
    public void fetchGames(SwipeRefreshLayout swipeRefresh) {
        mListener.fetchGames(swipeRefresh);
    }

    @Override
    public void onResume() {
        super.onResume();
        getContext().registerReceiver(mReceiver, intentFilter);
    }

    @Override
    public void onPause() {
        super.onPause();
        getContext().unregisterReceiver(mReceiver);
    }

    public void updateList(List<GameDetailsDto> gameList){
        GameRecyclerAdapter adapter = new GameRecyclerAdapter(this, gameList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(BROADCAST_ACTION)) {
                String extra = intent.getStringExtra("extra");
                gameDtoList = Arrays.asList(gson.fromJson(extra, GameDetailsDto[].class));
                ((MainActivity) getActivity()).setGameDtoList(gameDtoList);
                updateList(filterGameList(getFilter()));
            }
        }
    };

    void setFilter(String filter){
        this.filter = filter;
    }

    String getFilter(){
        return this.filter;
    }

}

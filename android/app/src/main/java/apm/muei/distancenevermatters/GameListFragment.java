package apm.muei.distancenevermatters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.OnClick;

public class GameListFragment extends Fragment {

    OnGameDetailsListener mListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_game_list, container, false);

        Toolbar toolbar = rootView.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        // Fab callback
        FloatingActionButton fab = rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), CreateGameActivity.class));
            }
        });

        // OnTabSelectedListener
        TabLayout tabLayout = rootView.findViewById(R.id.tabLayout);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            // TODO get games matching filter
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                CharSequence text = "";
                switch(tab.getPosition()) {
                    // 'All' tab
                    case 0:
                        text = "Mostrando todas las partidas";
                        break;
                    // 'Master' tab
                    case 1:
                        text = "Mostrando partidas con rol de master";
                        break;
                    // 'Player' tab
                    case 2:
                        text = "Mostrando partidas con rol de jugador";
                        break;
                }
                Toast toast = Toast.makeText(getActivity().getApplicationContext(), text, Toast.LENGTH_SHORT);
                toast.show();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // Nothing to do here for now
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // Nothing to do here for now
            }
        });

        // TODO Populate RecyclerView with real data
        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerView);
        GameListFragment.GameAdapter adapter = new GameListFragment.GameAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnGameDetailsListener) {
            mListener = (OnGameDetailsListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnStartGameListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface OnGameDetailsListener {
        // TODO: Update arguments
        void onGameSelected();
    }

    // RecyclerAdapter for game list
    public class GameAdapter extends RecyclerView.Adapter<GameListFragment.GameAdapter.GameViewHolder> {
        // TODO Declare dataset attribute

        // ViewHolder for game list items
        public class GameViewHolder extends RecyclerView.ViewHolder {
            public TextView nameView;
            public TextView descriptionView;
            public Button start;
            public ImageView status;

            public GameViewHolder(View itemView) {
                super(itemView);

                nameView = (TextView) itemView.findViewById(R.id.textView);
                descriptionView = (TextView) itemView.findViewById(R.id.textView2);
                start = (Button) itemView.findViewById(R.id.startGameBtn);
                status = (ImageView) itemView.findViewById(R.id.imageView);

                // onClickListener for the game item
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CharSequence text = "Detalles partida";
                        Toast toast = Toast.makeText(getActivity().getApplicationContext(), text, Toast.LENGTH_SHORT);
                        toast.show();
                        mListener.onGameSelected();
                    }
                });

                // onClickListener for the game item
                start.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CharSequence text = "Empezando partida";
                        Toast toast = Toast.makeText(getActivity().getApplicationContext(), text, Toast.LENGTH_SHORT);
                        toast.show();
                    }
                });
            }
        }

        // TODO use constructor for receiving and setting actual dataset
        public GameAdapter() {
            // Initialise dataset
        }

        @Override
        public GameListFragment.GameAdapter.GameViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View gameView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.game_list_item, parent, false);

            GameListFragment.GameAdapter.GameViewHolder viewHolder = new GameListFragment.GameAdapter.GameViewHolder(gameView);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(GameListFragment.GameAdapter.GameViewHolder holder, int position) {
            /* TODO get element from dataset at this position
             * and replace the contents of the view holder with that element */
        }

        // TODO return actual dataset size
        @Override
        public int getItemCount() {
            return 10;
        }
    }

}

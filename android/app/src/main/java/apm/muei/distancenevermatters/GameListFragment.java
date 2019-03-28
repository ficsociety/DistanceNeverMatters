package apm.muei.distancenevermatters;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GameListFragment extends Fragment {

    private OnGameDetailsListener mListener;

    @BindView(R.id.gameRecyclerView)
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_game_list, container, false);
        ButterKnife.bind(this, rootView);

        // TODO Populate RecyclerView with real data
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
                    + " must implement OnGameDetailsListener");
        }
    }

    public interface OnGameDetailsListener {
        // TODO: Update arguments
        void onGameSelected();
    }

    // RecyclerAdapter for game list
    public class GameAdapter extends RecyclerView.Adapter<GameListFragment.GameAdapter.GameViewHolder> {
        // TODO Declare dataset attribute

        // ViewHolder for game list items
        public class GameViewHolder extends RecyclerView.ViewHolder {

            @BindView(R.id.gListTextViewName)
            TextView nameView;

            @BindView(R.id.gListTextViewDescription)
            TextView descriptionView;

            @BindView(R.id.gListBtnStart)
            Button start;

            @OnClick(R.id.gListBtnStart)
            public void startGame() {
                CharSequence text = "Empezando partida";
                Toast toast = Toast.makeText(getActivity().getApplicationContext(), text, Toast.LENGTH_SHORT);
                toast.show();
            }

            @BindView(R.id.gListImgStatus)
            ImageView status;

            @OnClick(R.id.gListItem)
            public void showDetails() {
                CharSequence text = "Detalles partida";
                Toast toast = Toast.makeText(getActivity().getApplicationContext(), text, Toast.LENGTH_SHORT);
                toast.show();
                mListener.onGameSelected();
            }

            public GameViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
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

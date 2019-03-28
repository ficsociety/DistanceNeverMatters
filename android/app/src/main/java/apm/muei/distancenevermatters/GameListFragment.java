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

public class GameListFragment extends Fragment {

    private OnGameDetailsListener mListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_game_list, container, false);

        // TODO Populate RecyclerView with real data
        RecyclerView recyclerView = rootView.findViewById(R.id.gameRecyclerView);
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
            public TextView nameView;
            public TextView descriptionView;
            public Button start;
            public ImageView status;

            public GameViewHolder(View itemView) {
                super(itemView);

                nameView = (TextView) itemView.findViewById(R.id.gListTextViewName);
                descriptionView = (TextView) itemView.findViewById(R.id.ginfTVdescription2);
                start = (Button) itemView.findViewById(R.id.gListBtnStart);
                status = (ImageView) itemView.findViewById(R.id.gListImgStatus);

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

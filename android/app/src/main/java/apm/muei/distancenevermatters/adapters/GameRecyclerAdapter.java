package apm.muei.distancenevermatters.adapters;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import apm.muei.distancenevermatters.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GameRecyclerAdapter extends RecyclerView.Adapter<GameRecyclerAdapter.GameViewHolder> {

    private Fragment fragment;

    // TODO Declare dataset attribute

    public interface OnGameDetailsListener {
        // TODO: Update arguments
        void onGameSelected();
    }

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
            Toast toast = Toast.makeText(fragment.getActivity().getApplicationContext(), text, Toast.LENGTH_SHORT);
            toast.show();
        }

        @BindView(R.id.gListImgStatus)
        ImageView status;

        @OnClick(R.id.gListItem)
        public void showDetails() {
            CharSequence text = "Detalles partida";
            Toast toast = Toast.makeText(fragment.getActivity().getApplicationContext(), text, Toast.LENGTH_SHORT);
            toast.show();
            ((OnGameDetailsListener) fragment).onGameSelected();
        }

        public GameViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    // TODO use constructor for receiving and setting actual dataset
    public GameRecyclerAdapter(Fragment parentFragment) {
        if (parentFragment instanceof OnGameDetailsListener) {
            fragment = parentFragment;
        } else {
            throw new RuntimeException(fragment.toString()
                    + " must implement OnGameDetailsListener");
        }

        // Initialise dataset
    }

    @Override
    public GameRecyclerAdapter.GameViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View gameView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.game_list_item, parent, false);

        GameRecyclerAdapter.GameViewHolder viewHolder = new GameRecyclerAdapter.GameViewHolder(gameView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(GameRecyclerAdapter.GameViewHolder holder, int position) {
        /* TODO get element from dataset at this position
         * and replace the contents of the view holder with that element */
    }

    // TODO return actual dataset size
    @Override
    public int getItemCount() {
        return 10;
    }
}

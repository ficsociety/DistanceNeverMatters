package apm.muei.distancenevermatters.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import apm.muei.distancenevermatters.R;
import butterknife.ButterKnife;

public class GameDetailsRecyclerAdapter extends RecyclerView.Adapter<GameDetailsRecyclerAdapter.GameDetailsViewHolder> {

    // ViewHolder for game list items
    public class GameDetailsViewHolder extends RecyclerView.ViewHolder {

        public GameDetailsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public GameDetailsRecyclerAdapter.GameDetailsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View gameView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.game_info_list_item, parent, false);

        GameDetailsRecyclerAdapter.GameDetailsViewHolder viewHolder = new GameDetailsRecyclerAdapter.GameDetailsViewHolder(gameView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(GameDetailsRecyclerAdapter.GameDetailsViewHolder holder, int position) {
        /* TODO get element from dataset at this position
         * and replace the contents of the view holder with that element */
    }

    // TODO return actual dataset size
    @Override
    public int getItemCount() {
        return 5;
    }

}

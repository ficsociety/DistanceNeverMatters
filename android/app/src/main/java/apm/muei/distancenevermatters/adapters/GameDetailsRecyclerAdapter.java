package apm.muei.distancenevermatters.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import apm.muei.distancenevermatters.R;
import apm.muei.distancenevermatters.entities.GameState;
import apm.muei.distancenevermatters.entities.Player;
import apm.muei.distancenevermatters.entities.User;
import apm.muei.distancenevermatters.entities.dto.GameDetailsDto;
import butterknife.BindView;
import butterknife.ButterKnife;

public class GameDetailsRecyclerAdapter extends RecyclerView.Adapter<GameDetailsRecyclerAdapter.GameDetailsViewHolder> {

    private Fragment fragment;
    private List<User> userList;

    // ViewHolder for game list items
    public class GameDetailsViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.detListItemName)
        TextView userName;

        @BindView(R.id.detListItemRole)
        TextView userRole;

        @BindView(R.id.detListItemBtnRemove)
        Button removeUserButton;

        public GameDetailsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public GameDetailsRecyclerAdapter(Fragment parentFragment, User master, List<Player> players) {
        List<User> newUsers = new ArrayList<>();
        for (Player player: players){
            newUsers.add(player.getUser());
        }

        newUsers.add(0, master);
        this.userList = newUsers;
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
        User user = userList.get(position);

        holder.userName.setText(user.getUid());
        holder.userRole.setText(position == 0 ? "Master" : "Jugador");
        if (position == 0) {
            holder.removeUserButton.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return this.userList.size();
    }

}

package apm.muei.distancenevermatters.adapters;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.util.List;

import apm.muei.distancenevermatters.R;
import apm.muei.distancenevermatters.SharedPreference.PreferenceManager;
import apm.muei.distancenevermatters.activities.GameActivity;
import apm.muei.distancenevermatters.entities.GameState;
import apm.muei.distancenevermatters.entities.dto.GameDetailsDto;
import apm.muei.distancenevermatters.entities.dto.UpdateStateDto;
import apm.muei.distancenevermatters.volley.VolleyCallback;
import apm.muei.distancenevermatters.volley.WebService;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GameRecyclerAdapter extends RecyclerView.Adapter<GameRecyclerAdapter.GameViewHolder> {

    private Fragment fragment;
    private List<GameDetailsDto> gameList;

    public interface OnGameDetailsListener {
        // TODO: Update arguments
        void onGameSelected(GameDetailsDto gameDetails);
        void fetchGames(SwipeRefreshLayout swipeRefresh);
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
            //Change State
            String userName = PreferenceManager.getInstance().getUserName();
            int t = getLayoutPosition();
            GameDetailsDto game = gameList.get(t);
            if ((game.getMaster().getUid().equals(userName))) {
                UpdateStateDto stateDto = new UpdateStateDto(GameState.PLAYING, game.getCode());
                WebService.changeGameState(fragment.getActivity().getApplicationContext(), stateDto, new VolleyCallback() {
                    @Override
                    public void onSuccess(String result) {
                    }
                });
            }

            if(game.getState().equals(GameState.PAUSED) && (game.getMaster().getUid().equals(userName))){
                UpdateStateDto stateDto = new UpdateStateDto(GameState.PLAYING, game.getCode());
                WebService.changeGameState(fragment.getActivity().getApplicationContext(), stateDto, new VolleyCallback() {
                    @Override
                    public void onSuccess(String result) {
                    }
                });
                // Launch GameActivity
                Intent intent = new Intent(fragment.getActivity(), GameActivity.class);
                String jsonGame = new Gson().toJson(gameList.get(t), GameDetailsDto.class);
                intent.putExtra("gameDetails", jsonGame);
                fragment.startActivity(intent);
                fragment.getActivity().finish();

            }

            else if(game.getState().equals(GameState.PLAYING)){
                Intent intent = new Intent(fragment.getActivity(), GameActivity.class);
                String jsonGame = new Gson().toJson(gameList.get(t), GameDetailsDto.class);
                intent.putExtra("gameDetails", jsonGame);
                fragment.startActivity(intent);
                fragment.getActivity().finish();

            }
        }

        @BindView(R.id.gListImgStatus)
        ImageView status;

        public GameViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Redraw the old selection and the new
                    int t = getLayoutPosition();
                    GameDetailsDto dto = gameList.get(t);
                    ((OnGameDetailsListener) fragment).onGameSelected(dto);
                }
            });
        }
    }

    public GameRecyclerAdapter(Fragment parentFragment, List<GameDetailsDto> gameList) {
        if (parentFragment instanceof OnGameDetailsListener) {
            fragment = parentFragment;
        } else {
            throw new RuntimeException(fragment.toString()
                    + " must implement OnGameDetailsListener");
        }
        this.gameList = gameList;
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
        GameDetailsDto game = gameList.get(position);
        String userName = PreferenceManager.getInstance().getUserName();

        // TODO Settear los demás campos
        if (game.getState() == GameState.PLAYING) {
            holder.status.setColorFilter(ContextCompat.getColor(fragment.getContext(), R.color.colorGamePlaying));
            holder.start.setText(R.string.joinGame);
        } else if (game.getState() == GameState.PAUSED) {
            holder.status.setColorFilter(ContextCompat.getColor(fragment.getContext(), R.color.colorGamePaused));
            holder.start.setText(R.string.startGame);
            if(!game.getMaster().getUid().equals(userName)){
                holder.start.setEnabled(false);
            }
        }

        holder.nameView.setText(game.getName());
        // TODO Cambiar este campo
        holder.descriptionView.setText(game.getMaster().getUid());
    }

    @Override
    public int getItemCount() {
        return gameList.size();
    }
}

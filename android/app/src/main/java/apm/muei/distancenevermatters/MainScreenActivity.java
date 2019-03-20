package apm.muei.distancenevermatters;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
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

public class MainScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Fab callback
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainScreenActivity.this, CreateGameActivity.class));
            }
        });

        // OnTabSelectedListener
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
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
                Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
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
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        GameAdapter adapter = new GameAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    // RecyclerAdapter for game list
    public class GameAdapter extends RecyclerView.Adapter<GameAdapter.GameViewHolder> {
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
                start = (Button) itemView.findViewById(R.id.button);
                status = (ImageView) itemView.findViewById(R.id.imageView);

                // onClickListener for the game item
                // TODO replace LoginActivity with EditGameActivity
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(MainScreenActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                });
            }
        }

        // TODO use constructor for receiving and setting actual dataset
        public GameAdapter() {
            // Initialise dataset
        }

        @Override
        public GameViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View gameView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.game_list_item, parent, false);

            GameViewHolder viewHolder = new GameViewHolder(gameView);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(GameViewHolder holder, int position) {
            /* TODO get element from dataset at this position
             * and replace the contents of the view holder with that element */
        }

        // TODO return actual dataset size
        @Override
        public int getItemCount() {
            return 10;
        }
    }

    // onClick callback for start button
    public void startGame(View view) {
        // TODO replace this toast with code to start the game
        CharSequence text = "Empezando partida";
        Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
        toast.show();
    }

    // onClick calback for 'all' tab
    public void showAllGames(View view) {
        CharSequence text = "Mostrando todas las partidas";
        Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
        toast.show();
    }

}

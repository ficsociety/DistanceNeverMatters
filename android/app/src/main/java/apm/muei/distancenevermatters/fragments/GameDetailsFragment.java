package apm.muei.distancenevermatters.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import apm.muei.distancenevermatters.R;
import apm.muei.distancenevermatters.SharedPreference.PreferenceManager;
import apm.muei.distancenevermatters.activities.MainActivity;
import apm.muei.distancenevermatters.adapters.GameDetailsRecyclerAdapter;
import apm.muei.distancenevermatters.dialogfragments.SaveGameDetailsFragment;
import apm.muei.distancenevermatters.entities.dto.GameDetailsDto;
import apm.muei.distancenevermatters.volley.VolleyCallback;
import apm.muei.distancenevermatters.volley.WebService;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GameDetailsFragment extends Fragment {

    private GameDetailsRecyclerAdapter adapter;

    @BindView(R.id.ginfETdescription)
    TextInputLayout description;

    @BindView(R.id.ginfETgameName)
    TextInputLayout gameName;

    @BindView(R.id.ginfTVdateValue)
    TextView gameDate;

    @BindView(R.id.ginfTVcodeValue)
    TextView gameCode;

    @BindView(R.id.ginfRecyclerView)
    RecyclerView recyclerView;

    GameDetailsDto gameDetails;
    Gson gson;

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.game_details_menu, menu);
        String userName = PreferenceManager.getInstance().getUserName();
        if (!gameDetails.getMaster().getUid().equals(userName)) {
            MenuItem item = menu.findItem(R.id.editGameDetails);
            item.setVisible(false);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void toggleItemEdit(MenuItem item) {
        if (item.isChecked()) {
            SaveGameDetailsFragment dialog = new SaveGameDetailsFragment();
            gameDetails.setName(gameName.getEditText().getText().toString());
            gameDetails.setDescription(description.getEditText().getText().toString());
            Bundle args = new Bundle();
            args.putString("game", gson.toJson(gameDetails));
            dialog.setArguments(args);
            dialog.show(getActivity().getSupportFragmentManager(), "saveGameDetails");
            description.getEditText().setEnabled(false);
            gameName.getEditText().setEnabled(false);
            item.setIcon(R.drawable.ic_edit_white_24dp);
        } else {
            description.setEnabled(true);
            gameName.setEnabled(true);
            item.setIcon(R.drawable.ic_save_white_24dp);
        }
        item.setChecked(!item.isChecked());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.editGameDetails:
                this.toggleItemEdit(item);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_game_details, container, false);
        ButterKnife.bind(this, rootView);
        setHasOptionsMenu(true);
        gson = new GsonBuilder().create();
        gameDetails = ((MainActivity) getActivity()).getGameDetails();

        description.setEnabled(false);
        gameName.setEnabled(false);

        gameName.getEditText().setText(gameDetails.getName());
        description.getEditText().setText(gameDetails.getDescription());
        gameCode.setText(String.valueOf(gameDetails.getCode()));
        //gameDate.setText(gameDetails.getDate().toString());

        Toolbar toolbar = getActivity().findViewById(R.id.mainToolbar);
        getActivity().setTitle(R.string.game_details);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final DrawerLayout navigationView = getActivity().findViewById(R.id.drawer_layout);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                navigationView.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                ((MainActivity) getActivity()).onBack();
            }
        });

        navigationView.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        // TODO Populate RecyclerView with real data
        adapter = new GameDetailsRecyclerAdapter(this, gameDetails);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return rootView;
    }

    @OnClick(R.id.gdetBtnInvite)
    public void onPressInvite(View view) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, R.string.app_name);
        shareIntent.putExtra(Intent.EXTRA_TEXT, String.valueOf(gameDetails.getCode()));
        startActivity(Intent.createChooser(shareIntent, view.getContext().getResources().getString(R.string.share_code)));
    }

    @OnClick(R.id.gdetBtnDelete)
    public void onPressDelete(View view) {
        WebService.deleteGame(getActivity().getApplicationContext(), String.valueOf(gameDetails.getCode()), new VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
    }
}

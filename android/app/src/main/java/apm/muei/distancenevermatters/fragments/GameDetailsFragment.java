package apm.muei.distancenevermatters.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import apm.muei.distancenevermatters.R;
import apm.muei.distancenevermatters.adapters.GameDetailsRecyclerAdapter;
import apm.muei.distancenevermatters.dialogfragments.SaveGameDetailsFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GameDetailsFragment extends Fragment {

    private GameDetailsRecyclerAdapter adapter;

    @BindView(R.id.ginfETdescription)
    EditText description;

    @BindView(R.id.ginfETgameName)
    EditText gameName;

    @BindView(R.id.ginfRecyclerView)
    RecyclerView recyclerView;

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.game_details_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void toggleItemEdit(MenuItem item) {
        if (item.isChecked()) {
            SaveGameDetailsFragment dialog = new SaveGameDetailsFragment();
            dialog.show(getActivity().getSupportFragmentManager(), "saveGameDetails");
            description.setEnabled(false);
            gameName.setEnabled(false);
            item.setIcon(R.drawable.ic_edit_white_24dp);
        } else {
            description.setEnabled(true);
            gameName.setEnabled(true);
            gameName.requestFocus();
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
        description.setEnabled(false);
        gameName.setEnabled(false);

        Toolbar toolbar = getActivity().findViewById(R.id.mainToolbar);
        // Debería ser el nombre de la partida ¿?
        getActivity().setTitle("Detalles partida");
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                Toast.makeText(getActivity().getApplicationContext(),
                        "Mostrando lista de partidas", Toast.LENGTH_SHORT).show();
                getActivity().onBackPressed();
                //REVISAR ESTO
            }
        });

        // TODO Populate RecyclerView with real data
        adapter = new GameDetailsRecyclerAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return rootView;
    }

    @OnClick(R.id.gdetBtnInvite)
    public void onPressInvite(View view) {
        Toast.makeText(getActivity().getApplicationContext(),
                "Invitar usuarios a partida.", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.gdetBtnDelete)
    public void onPressDelete(View view) {
        Toast.makeText(getActivity().getApplicationContext(),
                "Eliminar la partida.", Toast.LENGTH_SHORT).show();
    }
}

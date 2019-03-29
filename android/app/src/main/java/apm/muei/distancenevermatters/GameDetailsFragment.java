package apm.muei.distancenevermatters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class GameDetailsFragment extends Fragment {

    private boolean isChecked = false;

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.game_details_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.editGameDetails:
                isChecked = item.isChecked();
                item.setChecked(!isChecked);
                CharSequence text = "Habilitando edición...";
                Toast toast = Toast.makeText(getActivity().getApplicationContext(), text, Toast.LENGTH_SHORT);
                toast.show();
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

        Toolbar toolbar = getActivity().findViewById(R.id.mainToolbar);
        // Debería ser el nombre de la partida ¿?
        getActivity().setTitle("Detalles partida");
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                Toast.makeText(getActivity().getApplicationContext(),
                        "Mostrando lista de partidas", Toast.LENGTH_SHORT).show();
                getActivity().onBackPressed();
            }
        });

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

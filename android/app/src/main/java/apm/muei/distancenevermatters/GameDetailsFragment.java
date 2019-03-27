package apm.muei.distancenevermatters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class GameDetailsFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_game_details, container, false);

        Toolbar toolbar = rootView.findViewById(R.id.toolbar);
        // Debería ser el nombre de la partida ¿?
        ((AppCompatActivity) getActivity()).setTitle("Detalles partida");

        Button button1 = rootView.findViewById(R.id.buttonInvit);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                Toast.makeText(getActivity().getApplicationContext(),
                        "Invitar usuarios a partida.", Toast.LENGTH_SHORT).show();

            }
        });
        Button button2 = rootView.findViewById(R.id.buttonDelGame);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                Toast.makeText(getActivity().getApplicationContext(),
                        "Eliminar la partida.", Toast.LENGTH_SHORT).show();

            }
        });

        return rootView;
    }

}

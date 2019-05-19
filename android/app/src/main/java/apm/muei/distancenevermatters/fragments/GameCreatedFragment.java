package apm.muei.distancenevermatters.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import apm.muei.distancenevermatters.R;
import apm.muei.distancenevermatters.activities.MainActivity;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class GameCreatedFragment extends Fragment {

    TextView codeText;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_game_created, container, false);
        ButterKnife.bind(this, rootView);
        getActivity().setTitle("Partida creada");
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        codeText = rootView.findViewById(R.id.gcreaTVnumber);
        long code = getArguments().getLong("code");
        codeText.setText(String.valueOf(code));

        return rootView;

    }

    @OnClick(R.id.gcreaFABshare)
    public void shareGame(View view) {
//        Toast.makeText(getActivity().getApplicationContext(),
//                "Compartir código.", Toast.LENGTH_LONG).show();
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, R.string.app_name);
            shareIntent.putExtra(Intent.EXTRA_TEXT, codeText.getText());
            startActivity(Intent.createChooser(shareIntent, view.getContext().getResources().getString(R.string.share_code)));
        } catch(Exception e) {
            //e.toString();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        Toolbar toolbar = getActivity().findViewById(R.id.createGameToolbar);
        toolbar.setTitle(R.string.create_game_title);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
    }
}

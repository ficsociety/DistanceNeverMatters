package apm.muei.distancenevermatters.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.DateFormat;
import java.util.List;

import apm.muei.distancenevermatters.GlobalVars.GlobalVars;
import apm.muei.distancenevermatters.R;
import apm.muei.distancenevermatters.entities.User;
import apm.muei.distancenevermatters.entities.dto.GameDetailsDto;
import apm.muei.distancenevermatters.entities.dto.PlayerDto;
import apm.muei.distancenevermatters.volley.VolleyCallback;
import apm.muei.distancenevermatters.volley.WebService;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FindGameFragment extends Fragment {
    EditText code;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_find_game, container, false);
        ButterKnife.bind(this, rootView);

        code = rootView.findViewById(R.id.findGameCode);


        return rootView;
    }

    @OnClick(R.id.findGameBtn)
    public void onClickBtn(View view){

        if (code.getText().toString().equals("")){
            code.setError("Campo obligatorio");
        } else{

            final FindGameFragment findGameFragment = this;
            WebService.findGameByCode(getActivity().getApplicationContext(), code.getText().toString(), new VolleyCallback() {
                @Override
                public void onSuccess(String result) {
                    GameDetailsDto gameDetailsDto = new Gson().fromJson(result, GameDetailsDto.class);
                    if (userExistInGame(gameDetailsDto.getPlayers())){
                        Toast.makeText(getActivity().getApplicationContext(),
                                "Ya pertenece a esta partida", Toast.LENGTH_SHORT).show();
                    } else{

                        SelectPlayerFragment selectPlayerFragment = new SelectPlayerFragment();
                        Bundle bundle = new Bundle();
                        bundle.putLong("code", Long.valueOf(code.getText().toString()));
                        selectPlayerFragment.setArguments(bundle);

                        findGameFragment.getFragmentManager()
                                .beginTransaction()
                                .replace(R.id.find_game_fragments, selectPlayerFragment)
                                .addToBackStack(null)
                                .commit();

                    }
                }
            });
        }
    }

    private boolean userExistInGame(List<PlayerDto> players){

        String username = GlobalVars.getInstance().getUsername();
        for (PlayerDto player : players){
            if (player.getUser().getUid().equals(username)) {
                return true;
            }
        }
        return false;
    }

}

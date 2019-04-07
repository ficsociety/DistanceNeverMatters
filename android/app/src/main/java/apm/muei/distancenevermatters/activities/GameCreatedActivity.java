package apm.muei.distancenevermatters.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import apm.muei.distancenevermatters.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GameCreatedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_created);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.gcreaFABshare)
    public void shareGame() {
        Toast.makeText(getApplicationContext(),
                "Compartir código.", Toast.LENGTH_LONG).show();
    }
}
package apm.muei.distancenevermatters.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.squareup.picasso.Picasso;

import apm.muei.distancenevermatters.GlobalVars.GlobalVars;
import apm.muei.distancenevermatters.R;
import apm.muei.distancenevermatters.entities.dto.GameDetailsDto;
import apm.muei.distancenevermatters.fragments.GameListFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProfileActivity extends AppCompatActivity {

    @BindView(R.id.profileTextName)
    TextView profileName;

    @BindView(R.id.profileTextEmail)
    TextView profileEmail;

    @BindView(R.id.profileImage)
    ImageView profileImage;

    @BindView(R.id.profileTextAllGames)
    TextView profileAllGames;

    @BindView(R.id.profileTextMasterGames)
    TextView profileMasterGames;

    @BindView(R.id.profileTextPlayerGames)
    TextView profilePlayerGames;

    @BindView(R.id.profileToolbar)
    Toolbar toolbar;

    private GlobalVars gVars;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);
        gVars = new GlobalVars().getInstance();
        setSupportActionBar(toolbar);
        setTitle(R.string.perfil);

    }

    @Override
    protected void onStart() {
        super.onStart();
        profileName.setText(gVars.getmAuth().getCurrentUser().getDisplayName());
        profileEmail.setText(gVars.getmAuth().getCurrentUser().getEmail());
        profileAllGames.setText(gVars.getTotal_games());
        profileMasterGames.setText(gVars.getMaster_games());
        profilePlayerGames.setText(gVars.getPlayer_games());
        if(gVars.getmAuth().getCurrentUser().getPhotoUrl() != null){
            Picasso.get().load(gVars.getmAuth().getCurrentUser().getPhotoUrl()).into(profileImage);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                onBack();
            }
        });
    }

    @OnClick(R.id.profileButtonConf)
    public void onPressConf(View view) {
        Toast.makeText(this.getApplicationContext(), R.string.help, Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.profileButtonSingOut)
    public void onPressSignOut(View view) {
        signOut();
    }

    private void signOut() {
        // Firebase sign out
        gVars.getmAuth().signOut();

        // Google sign out
        gVars.getSignInClient().signOut().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
    }

    @Override
    public void onBackPressed() {

    }

    public void onBack(){
        super.onBackPressed();
    }



}

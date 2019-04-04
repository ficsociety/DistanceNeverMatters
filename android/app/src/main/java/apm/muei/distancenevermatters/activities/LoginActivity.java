package apm.muei.distancenevermatters.activities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import apm.muei.distancenevermatters.R;
import apm.muei.distancenevermatters.dialogfragments.NoCameraDialogFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    GoogleApiClient apiClient;

    private static final int ERROR_DIALOG_REQUEST = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Check if a camera is available before anything else
        checkCamera();

        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        GoogleSignInOptions gso =
                new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestEmail()
                        .build();

        apiClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


    }

    private void checkCamera() {
        // Check whether the device has a camera and an app to capture video
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)
            || takeVideoIntent.resolveActivity(getPackageManager()) == null) {
            NoCameraDialogFragment dialog = new NoCameraDialogFragment();
            dialog.setCancelable(false);
            dialog.show(getSupportFragmentManager(), "noCamera");
        }
    }

    @OnClick(R.id.logBtnGoogle)
    public void onPressSignInGoogle(View view) {
        signInGoogle();
    }

    @OnClick(R.id.logBtnIniciarSesion)
    public void onPressSignIn(View view) {
        signIn();
    }

    @OnClick(R.id.logBtnRegistro)
    public void onPressRegister(View view) {
        register();
    }

    private void signIn() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void register() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    private void signInGoogle() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(apiClient);
        startActivityForResult(signInIntent, ERROR_DIALOG_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ERROR_DIALOG_REQUEST) {
            GoogleSignInResult result =
                    Auth.GoogleSignInApi.getSignInResultFromIntent(data);

            handleSignInResult(result);
        }


    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else {

        }
    }

    @Override
    public void onBackPressed() {
    }
}

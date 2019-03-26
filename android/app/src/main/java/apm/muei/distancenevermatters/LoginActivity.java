package apm.muei.distancenevermatters;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.login_google)
    SignInButton btnSignInGoogle;

    @BindView(R.id.login)
    Button btnSignIn;

    @BindView(R.id.register)
    Button btnRegister;

    GoogleApiClient apiClient;

    private static final int ERROR_DIALOG_REQUEST = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

    @OnClick(R.id.login_google)
    public void onPressSignInGoogle(View view) {
        signInGoogle();
    }

    @OnClick(R.id.login)
    public void onPressSignIn(View view) {
        signIn();
    }

    @OnClick(R.id.register)
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

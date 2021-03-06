package apm.muei.distancenevermatters.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.StringTokenizer;

import apm.muei.distancenevermatters.GlobalVars.GlobalVars;
import apm.muei.distancenevermatters.LocaleManager.LocaleHelper;
import apm.muei.distancenevermatters.R;
import apm.muei.distancenevermatters.SharedPreference.PreferenceManager;
import apm.muei.distancenevermatters.dialogfragments.EulaDialogFragment;
import apm.muei.distancenevermatters.dialogfragments.NoCameraDialogFragment;
import apm.muei.distancenevermatters.entities.User;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    @BindView(R.id.logEditTextEmail)
    EditText regTextEmail;

    @BindView(R.id.logEditTextPassword)
    EditText regTextPass;

    @BindView(R.id.logProgressBarSign)
    ProgressBar progressBarSign;

    private GlobalVars gVars;

    GoogleApiClient apiClient;

    private static final int ERROR_DIALOG_REQUEST = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new PreferenceManager().initPreference(getApplicationContext());

        // Check if a camera is available before anything else
        checkCamera();
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        if (!sharedPreferences.getBoolean("EULA", false)) {
            EulaDialogFragment dialog = new EulaDialogFragment();
            dialog.setCancelable(false);
            dialog.show(getSupportFragmentManager(), "eula");
        }

        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        gVars =  GlobalVars.getInstance();

        GoogleSignInOptions gso =
                new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(getString(R.string.default_web_client_id))
                        .requestEmail()
                        .build();

        apiClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        gVars.setSignInClient(GoogleSignIn.getClient(this, gso));

        gVars.setmAuth(FirebaseAuth.getInstance());
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



    @OnClick(R.id.logBtnSignInGoogle)

    public void onPressSignInGoogle(View view) {
        signInGoogle();
    }

    @OnClick(R.id.logBtnSignIn)

    public void onPressSignIn(View view) {
        signIn();
    }

    @OnClick(R.id.logBtnRegister)
    public void onPressRegister(View view) {
        register();
    }

    private void signIn() {
        String email = regTextEmail.getText().toString();
        String password = regTextPass.getText().toString();
        if(validateLogIn(email,password)){
            progressBarSign.setVisibility(View.VISIBLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            firebaseAuth(email, password);
        }

    }

    private void register() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    private void signInGoogle() {
        progressBarSign.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(apiClient);
        startActivityForResult(signInIntent, ERROR_DIALOG_REQUEST);
    }

    private boolean validateLogIn(String email,String password){
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Introduce un email o contraseña validos", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    public void onStart() {
        super.onStart();
        // Comprobamos si el usuario ya esta registrado
        FirebaseUser currentUser = gVars.getmAuth().getCurrentUser();
        if (currentUser != null) {
            gVars.setUser(new User(currentUser.getEmail()));
        }
        updateUI(currentUser);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Resultado que nos devuelve GoogleSignInApi.getSignInIntent(...)
        if (requestCode == ERROR_DIALOG_REQUEST) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                //  Exito al iniciar sesion con Google, autenticacion con Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                //  Fallo al iniciar sesion con Google
                Log.w(TAG, "Google sign in failed", e);
                updateUI(null);
            }
        }
    }

    private void firebaseAuthWithGoogle(final GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        gVars.getmAuth().signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //  Exito al iniciar sesion con Firebase, guardamos las credenciales.
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = gVars.getmAuth().getCurrentUser();
                            gVars.setUser(new User(user.getEmail()));
                            saveCredentials(user);
                        } else {
                            //  Fallo al iniciar sesion con Firebase.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            updateUI(null);
                        } }
                });
    }

    private void firebaseAuth(String email, String password) {
        Log.d(TAG, "firebaseAuth:" + email);
        gVars.getmAuth().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = gVars.getmAuth().getCurrentUser();
                            gVars.setUser(new User(user.getEmail()));
                            saveCredentials(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, " El correo o la contraseña de su cuenta es incorrecto.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }

    private void saveCredentials(FirebaseUser user){
        String username;
        username = new StringTokenizer(user.getEmail(), "@").nextToken();

        if (user.getEmail().endsWith(".es")) {
            username = username.concat("$");
        }

        PreferenceManager.getInstance().setUserName(username);
        updateUI(user);
    }

    private void updateUI(FirebaseUser user) {
        progressBarSign.setVisibility(View.INVISIBLE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        if (user != null) {
            LoginActivity.this.finish();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            this.finish();
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void attachBaseContext(Context base) {
        new PreferenceManager().initPreference(base);
        super.attachBaseContext(LocaleHelper.onAttach(base, "es"));
    }
}

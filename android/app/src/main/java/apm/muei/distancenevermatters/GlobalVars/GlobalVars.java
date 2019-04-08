package apm.muei.distancenevermatters.GlobalVars;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;

public class GlobalVars {
    private static GlobalVars _instance;

    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;


    public GlobalVars() {
    }

    public GlobalVars getInstance(){
        if(_instance == null) {
            _instance = new GlobalVars();
        }
        return _instance;
    }

    public FirebaseAuth getmAuth() {
        return mAuth;
    }

    public void setmAuth(FirebaseAuth mAuth) {
        this.mAuth = mAuth;
    }

    public GoogleSignInClient getSignInClient() {
        return mGoogleSignInClient;
    }

    public void setSignInClient(GoogleSignInClient mGoogleSignInClient) {
        this.mGoogleSignInClient = mGoogleSignInClient;
    }
}

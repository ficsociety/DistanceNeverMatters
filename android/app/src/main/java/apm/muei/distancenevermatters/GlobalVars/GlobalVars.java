package apm.muei.distancenevermatters.GlobalVars;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;

import java.util.StringTokenizer;

import apm.muei.distancenevermatters.entities.User;

public class GlobalVars {
    private static GlobalVars _instance;

    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private User user;
    private String total_games;
    private String player_games;
    private String master_games;

    private GlobalVars() {
    }

    public static GlobalVars getInstance(){
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

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public GoogleSignInClient getSignInClient() {
        return mGoogleSignInClient;
    }

    public void setSignInClient(GoogleSignInClient mGoogleSignInClient) {
        this.mGoogleSignInClient = mGoogleSignInClient;
    }

    public String  getTotal_games() {
        return total_games;
    }

    public void setTotal_games(int total_games) {
        this.total_games = Integer.toString(total_games);
    }

    public String getPlayer_games() {
        return player_games;
    }

    public void setPlayer_games(int player_games) {
        this.player_games = Integer.toString(player_games);
    }

    public String getMaster_games() {
        return master_games;
    }

    public void setMaster_games(int master_games) {
        this.master_games = Integer.toString(master_games);
    }

    public String getUsername() {
        String email = getmAuth().getCurrentUser().getEmail();
        String username = new StringTokenizer(email, "@").nextToken();

        if (email.endsWith(".es")) {
            username.concat("$");
        }
        return username;
    }
}

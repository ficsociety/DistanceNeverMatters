package apm.muei.distancenevermatters.SharedPreference;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceManager {
    private final String LANGUAGE = "_language";
    private final String USERNAME = "_userName";



    private String _language;
    private String _userName;


    private static PreferenceManager _instance;
    private SharedPreferences sharedPreference;

    public PreferenceManager() {
    }

    public void initPreference(Context ctx){
        if(_instance == null){
            _instance =  new PreferenceManager();
            _instance.sharedPreference = ctx.getSharedPreferences("PreferenceManager", Context.MODE_PRIVATE);
        }
    }

    public static PreferenceManager getInstance(){
        return _instance;
    }

    public String getLanguage() {
        if(_language == null){
            _language = sharedPreference.getString(LANGUAGE, "EN");
        }
        return _language;
    }

    public void setLanguage(String language) {
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putString(LANGUAGE, language );
        editor.commit();
        _language = language;
    }

    public String getUserName() {
        if(_userName == null){
            _userName = sharedPreference.getString(USERNAME, "");
        }
        return _userName;
    }

    public void setUserName(String userName) {
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putString(USERNAME, userName );
        editor.commit();
        _userName = userName;
    }

}

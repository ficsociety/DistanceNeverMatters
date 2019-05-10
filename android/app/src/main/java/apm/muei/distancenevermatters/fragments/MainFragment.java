package apm.muei.distancenevermatters.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import apm.muei.distancenevermatters.GlobalVars.GlobalVars;
import apm.muei.distancenevermatters.LocaleManager.LocaleHelper;
import apm.muei.distancenevermatters.R;
import apm.muei.distancenevermatters.activities.CreateGameActivity;
import apm.muei.distancenevermatters.activities.FindGameActivity;
import apm.muei.distancenevermatters.activities.LoginActivity;
import apm.muei.distancenevermatters.activities.MainActivity;
import apm.muei.distancenevermatters.activities.ProfileActivity;
import apm.muei.distancenevermatters.adapters.GameTabsPagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainFragment extends Fragment {

    @BindView(R.id.gListAddFab)
    FloatingActionButton fab;

    @OnClick(R.id.gListAddFab)
    public void addGame() {
        startActivity(new Intent(getActivity(), CreateGameActivity.class));
        getActivity().finish();
    }

    @BindView(R.id.gListViewPager)
    ViewPager viewPager;

    @BindView(R.id.gListTabLayout)
    TabLayout tabLayout;

    private GlobalVars gVars;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, rootView);
        gVars = new GlobalVars().getInstance();

        setHasOptionsMenu(true);

        // Set the tabs
        GameTabsPagerAdapter adapter = new GameTabsPagerAdapter(getActivity(), getChildFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        getActivity().setTitle(R.string.game_list); // TODO change for string resource
        //((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.logout) {
            CharSequence text = "Regresando a login";
            Toast toast = Toast.makeText(getActivity().getApplicationContext(), text, Toast.LENGTH_SHORT);
            toast.show();
            singOut();
        }
        else if(id == R.id.buscarPartida){
            Intent intent = new Intent(getActivity(), FindGameActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.help) {
            Toast.makeText(getActivity().getApplicationContext(), R.string.help, Toast.LENGTH_SHORT).show();
        }
        else if (id == R.id.perfil) {
            showProfile();
        }
        else if (id == R.id.lang) {
            changeLanguage();
        }
        return true;
    }

    private void singOut() {
        // Firebase sign out
        gVars.getmAuth().signOut();

        // Google sign out
        gVars.getSignInClient().signOut().addOnCompleteListener(this.getActivity(),
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                    }
                });
    }
    
    private void showProfile() {
        Intent intent = new Intent(getActivity(), ProfileActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    private void changeLanguage(){
        final String languages[] = new String[] {"es", "en"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(R.string.Seleccionar_idioma);
        builder.setItems(languages, new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onClick(DialogInterface dialog, int which) {
                LocaleHelper.setLocale(getActivity(), languages[which]);
                getActivity().finish();
                startActivity(getActivity().getIntent());
            }
        });
        builder.show();
    }
}
package apm.muei.distancenevermatters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MainFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        Toolbar toolbar = rootView.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        // Fab callback
        FloatingActionButton fab = rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), CreateGameActivity.class));
            }
        });

        // Set the tabs
        ViewPager viewPager = rootView.findViewById(R.id.view_pager);
        GameTabsPagerAdapter adapter = new GameTabsPagerAdapter(getActivity(), getChildFragmentManager());
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = rootView.findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

        return rootView;
    }

    // PagerAdapter for tabs
    public class GameTabsPagerAdapter extends FragmentPagerAdapter {

        private Context mContext;

        public GameTabsPagerAdapter(Context context, FragmentManager fm) {
            super(fm);
            mContext = context;
        }

        @Override
        public Fragment getItem(int position) {
            return new GameListFragment();
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch(position) {
                case 0:
                    return mContext.getString(R.string.all);
                case 1:
                    return mContext.getString(R.string.master);
                case 2:
                    return mContext.getString(R.string.player);
                default:
                    return null;
            }
        }
    }

}

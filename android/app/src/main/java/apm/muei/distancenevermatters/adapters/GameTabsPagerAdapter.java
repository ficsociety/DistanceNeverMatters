package apm.muei.distancenevermatters.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import apm.muei.distancenevermatters.R;
import apm.muei.distancenevermatters.fragments.GameListFragment;

public class GameTabsPagerAdapter extends FragmentPagerAdapter {

    private Context mContext;

    public GameTabsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 1:
                return GameListFragment.newInstance(mContext.getString(R.string.master));
            case 2:
                return GameListFragment.newInstance(mContext.getString(R.string.player));
            default:
                return GameListFragment.newInstance(mContext.getString(R.string.all));
        }
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

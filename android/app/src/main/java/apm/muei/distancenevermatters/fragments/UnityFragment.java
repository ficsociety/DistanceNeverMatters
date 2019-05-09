package apm.muei.distancenevermatters.fragments;

import android.content.Context;
import android.content.res.Configuration;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.unity3d.player.UnityPlayer;

import apm.muei.distancenevermatters.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class UnityFragment extends Fragment {

    private OnUnityFragmentInteractionListener mListener;
    protected UnityPlayer mUnityPlayer;
    public static UnityFragment instance; // Para poder acceder desde Unity

    public static UnityFragment getInstance() {
        if (instance == null) {
            instance = new UnityFragment();
        }

        return  instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_unity, container, false);

        Log.d("weird", "create unity fragment");
        ViewGroup frameLayout = rootView.findViewById(R.id.unityFrameLayout);
        if (frameLayout.getChildAt(0) == null) {
            FrameLayout.LayoutParams lp =
                    new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                            FrameLayout.LayoutParams.MATCH_PARENT);
            frameLayout.addView(mUnityPlayer.getView(), 0, lp);
        }

        mUnityPlayer.requestFocus();

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof  OnUnityFragmentInteractionListener) {
            OnUnityFragmentInteractionListener listener =
                    (OnUnityFragmentInteractionListener) context;
            mListener = listener;
            mUnityPlayer = listener.getUnityPlayer();
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnUnityFragmentInteractionListener");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mUnityPlayer.resume();

        Log.d("Weird", "resuming");
        // If Unity view is not set, set it
        // This can happen when blocking and unblocking mobile while in this screen
        FrameLayout frameLayout = getView().findViewById(R.id.unityFrameLayout);
        if (frameLayout.getChildAt(0) == null) {
            FrameLayout.LayoutParams lp =
                    new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                            FrameLayout.LayoutParams.MATCH_PARENT);
            frameLayout.addView(mUnityPlayer.getView(), 0, lp);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroy() {
        Log.d("weird", "quitting UnityPlayer");
        mUnityPlayer.quit();
        super.onDestroy();
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("weird", "pausing");
        mUnityPlayer.pause();
    }

    @Override
    public void onStart() {
        super.onStart();
        mUnityPlayer.start();
        Log.d("weird", "start unity");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("weird", "stopping");
        mUnityPlayer.stop();
        // We need to remove the view from the FrameLayout, or else app will crash next time we open this screen
        // Not putting the Unity view in the FrameLayout after the first creation DOES NOT WORK,
        // the app doesn't crash, but Unity isn't displayed
        ViewGroup unityView = ((ViewGroup) mUnityPlayer.getView().getParent());
        unityView.removeView(mUnityPlayer.getView());
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mUnityPlayer.configurationChanged(newConfig);
    }

    public interface OnUnityFragmentInteractionListener {
        UnityPlayer getUnityPlayer();
    }
}

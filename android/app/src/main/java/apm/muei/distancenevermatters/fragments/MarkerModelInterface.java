package apm.muei.distancenevermatters.fragments;

import android.support.v4.app.FragmentActivity;

import apm.muei.distancenevermatters.entities.Marker;
import apm.muei.distancenevermatters.entities.Model;

public interface MarkerModelInterface {

     void setMarker(Marker marker);
     void setModel(Model model);
     FragmentActivity getActivity();

}

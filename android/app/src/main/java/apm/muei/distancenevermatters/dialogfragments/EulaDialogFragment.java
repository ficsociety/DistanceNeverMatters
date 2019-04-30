package apm.muei.distancenevermatters.dialogfragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import apm.muei.distancenevermatters.GlobalVars.GlobalVars;
import apm.muei.distancenevermatters.R;
import apm.muei.distancenevermatters.activities.MainActivity;

public class EulaDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final SharedPreferences sharedPreferences = getActivity().getPreferences(getActivity().getApplicationContext().MODE_PRIVATE);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(false)
                .setTitle(R.string.eulaTitle)
                .setMessage(R.string.eulaContent)
                .setPositiveButton(R.string.acceptEula, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sharedPreferences.edit().putBoolean("EULA", true).commit();
                    }
                });
        return builder.create();
    }
}
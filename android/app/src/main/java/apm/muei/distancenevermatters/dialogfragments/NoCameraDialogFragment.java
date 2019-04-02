package apm.muei.distancenevermatters.dialogfragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import apm.muei.distancenevermatters.R;

public class NoCameraDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.no_camera)
                .setMessage(R.string.camera_and_app_warning)
                .setPositiveButton(R.string.ok_sadface, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Ok then, nothing to do here
                getActivity().finish();
            }
        });

        return builder.create();
    }
}

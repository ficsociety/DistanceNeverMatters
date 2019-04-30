package apm.muei.distancenevermatters.dialogfragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.Toast;

import apm.muei.distancenevermatters.R;

public class SaveGameDetailsFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.save_game_details)
                .setMessage(R.string.save_game_details_exp)
                .setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Ok then, nothing to do here
                        Toast.makeText(getActivity().getApplicationContext(),
                                "Guardando cambios", Toast.LENGTH_SHORT).show();
                    }
                }).setNegativeButton(R.string.cancel, null);

        return builder.create();
    }
}


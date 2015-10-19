package com.nachiketnaik.stormy.UI;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.content.Context;

import com.nachiketnaik.stormy.R;

/**
 * Created by nachiketnaik on 9/26/15.
 */
public class AlertDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Context context = getActivity();
        AlertDialog.Builder builder =  new AlertDialog.Builder(context)
                .setTitle(R.string.error_title)
                .setMessage(R.string.error_msg)
                .setPositiveButton(R.string.error_Ok_Button, null);
        AlertDialog dialog = builder.create();
        return dialog;
    }
}

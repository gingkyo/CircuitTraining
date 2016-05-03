package com.eaton.chris.circuittraining;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by chris on 03/05/2016.
 */
public class DialogBoxController {}
//extends DialogFragment {
//        @Override
//        public Dialog onCreateDialog(Bundle savedInstanceState) {
//            // Use the Builder class for convenient dialog construction
//            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//            builder.setMessage(R.string.dialog_fire_missiles)
//                    .setPositiveButton(R.string.fire, new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int id) {
//                            // FIRE ZE MISSILES!
//                        }
//                    })
//                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int id) {
//                            // User cancelled the dialog
//                        }
//                    });
//            // Create the AlertDialog object and return it
//            return builder.create();
//        }
//}
//    public void addAlertBox(boolean isIntructions, String title, String message) {
//        AlertDialog.Builder ad = new AlertDialog.Builder(this);
//        ad.setTitle(title);
//        ad.setMessage(message);
//        ad.setCancelable(isIntructions);
//        if (!isIntructions) {
//            if (currentLevel == 4) {
//                ad.setPositiveButton(
//                        "Exit",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                finish();
//                            }
//                        });
//            } else {
//                ad.setPositiveButton(
//                        "Next",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                currentLevel++;
//                                question.setText(questionText[currentLevel]);
//                                resetGameBoard();
//                            }
//                        });
//            }
//        } else {
//            ad.setPositiveButton(
//                    "Yes",
//                    new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int id) {
//                            Intent instructionIntent = new Intent(getActivity(), InstructionsActivity.class);
//                            startActivity(instructionIntent);
//                        }
//                    });
//            ad.setNegativeButton(
//                    "No",
//                    new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int id) {
//                            dialog.cancel();
//                        }
//                    });
//        }
//
//        AlertDialog alert = ad.create();
//        alert.show();
//    }
//

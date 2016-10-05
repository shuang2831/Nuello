package com.example.stan.phonebook;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.widget.EditText;

/**
 * Created by Stan on 10/4/2016.
 */
public class DialogList extends DialogFragment {

    public static DialogList newInstance(int num) {
        DialogList f = new DialogList();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putInt("num", num);
        f.setArguments(args);

        return f;
    }

    CharSequence[] csMood = {"happy", "neutral", "sad"};
    CharSequence[] csStatus = {"free", "limited", "busy"};
    private String userID;
    private int mNum;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNum = getArguments().getInt("num");
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        userID = sharedPreferences.getString(Config.UID_SHARED_PREF, "0");
        //Getting editor
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        switch(mNum) {
            case 1:
                builder.setTitle("Pick Mood")
                        .setItems(csMood, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // The 'which' argument contains the index position
                                // of the selected item
                                NetworkHelper.updateField("mood", csMood[which].toString(), userID, getActivity());
                                NetworkHelper.refreshList(userID, getActivity());
                                //Puting the value false for loggedin
                                editor.putString(Config.MOOD_SHARED_PREF, csMood[which].toString());
                                editor.apply();
                                //mListener.onDialogPositiveClick(DialogList.this);
                                //updateHelpers.imgViewUpdater(cs[which], );
                            }
                        });
                break;
            case 2:
                builder.setTitle("Pick Status")
                        .setItems(csStatus, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // The 'which' argument contains the index position
                                // of the selected item
                                NetworkHelper.updateField("status", csStatus[which].toString(), userID, getActivity());
                                NetworkHelper.refreshList(userID, getActivity());
                                //Puting the value false for loggedin
                                editor.putString(Config.STATUS_SHARED_PREF, csStatus[which].toString());
                                editor.apply();
                                MainActivity.mainToolbar.setBackgroundColor(updateHelpers.statusColor(csStatus[which].toString()));
                                //mListener.onDialogPositiveClick(DialogList.this);
                                //updateHelpers.imgViewUpdater(cs[which], );
                            }
                        });
                break;
            case 3:
                builder.setTitle("Watcha Currently Into?");

// Set up the input
                final EditText input = new EditText(getActivity());
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                builder.setView(input);

// Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        NetworkHelper.updateField("ci", input.getText().toString(), userID, getActivity());
                        NetworkHelper.refreshList(userID, getActivity());
                        editor.putString(Config.CI_SHARED_PREF, input.getText().toString());
                        editor.apply();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                break;

        }
        return builder.create();
    }




}
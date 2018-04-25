package api.eden.manga.mangaedenapiandroid.fragments;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.widget.EditText;


import api.eden.manga.mangaedenapiandroid.R;
import api.eden.manga.mangaedenapiandroid.model.Profile;

public class DialogClassFragment extends DialogFragment {
    static DialogClassFragment newInstance() {
        return new DialogClassFragment();
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();



        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.profile, null))
                // Add action buttons
                .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        final EditText etSearch = (EditText)getDialog().findViewById(R.id.pseudo);
                        getDialog().getWindow().setSoftInputMode(
                                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                        Profile profile = new Profile(etSearch.getText().toString());
                        profile.save();

                    }
                });


        return builder.create();
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        Profile profile = new Profile("Default");
        profile.save();
    }
}

package py.com.sshc.app;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;

public class NumeroDocumentoDialogFragment extends DialogFragment {

        /**
         * Host activities have to implement this interface to receive button click
         * callbacks.
         *
         */
        View vista;

        public static interface Host {
            public void onOptionOne(String numeroDocumento);

            public void onOptionTwo();
        }

        public static py.com.sshc.app.NumeroDocumentoDialogFragment newInstance() {
            return new py.com.sshc.app.NumeroDocumentoDialogFragment();
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            DialogInterface.OnClickListener clickListener = new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Host host;
                    try {
                        host = (Host) getActivity();
                    } catch (ClassCastException e) {
                        String name = getActivity().getClass().getName();
                        throw new RuntimeException("Class " +  name + " doesn't implement MyAlertDialog.Host interface");
                    }

                    if (which == DialogInterface.BUTTON_POSITIVE) {
                        EditText editText = (EditText) vista.findViewById(R.id.numeroDocumento);
                        host.onOptionOne(editText.getText().toString());
                    }
                    if (which == DialogInterface.BUTTON_NEGATIVE)
                        host.onOptionTwo();

                }
            };

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            // Get the layout inflater
            LayoutInflater inflater = requireActivity().getLayoutInflater();

            // Inflate and set the layout for the dialog
            // Pass null as the parent view because its going in the dialog layout
            vista = inflater.inflate(R.layout.fragment_numero_documento, null);
            builder.setView(vista)
                    // Add action buttons
                    .setPositiveButton(R.string.action_registrar, clickListener)
                    .setNegativeButton(R.string.cancel, clickListener)
                    .setMessage("Ingrese el número de documento");
            return builder.create();

        }
    }
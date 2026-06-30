package com.example.pr19_rmp_gudochkina;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class CustomDialogFragment extends DialogFragment {

    private String receivedData;
    private DialogFragmentListener listener;

    public interface DialogFragmentListener {
        void onDialogResult(String result);
    }

    public void setReceivedData(String data) {
        this.receivedData = data;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof DialogFragmentListener) {
            listener = (DialogFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement DialogFragmentListener");
        }
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog, null);

        EditText editText = view.findViewById(R.id.edit_text_dialog);
        if (receivedData != null) {
            editText.setText(receivedData);
        }

        return builder
                .setTitle("Диалоговое окно")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setView(view)
                .setPositiveButton("OK", (dialog, which) -> {
                    String result = editText.getText().toString();
                    if (listener != null) {
                        listener.onDialogResult(result);
                    }
                })
                .setNegativeButton("Отмена", null)
                .create();
    }
}


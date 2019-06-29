package com.example.mark3;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PinSetDialog extends Dialog implements android.view.View.OnClickListener {
    private Activity activity;
    private EditText epin,cpin;
    private Button confirm;
    String TAG="PinSetDialog";
    public PinSetDialog(Activity a){
        //Empty constructor. Leave it.
        super(a);
        this.activity=a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.pin_dialog);
        this.setCanceledOnTouchOutside(false);
        epin=(EditText) findViewById(R.id.enterPinField);
        cpin=(EditText) findViewById(R.id.confirmPinField);
        //cancel=(Button) findViewById(R.id.bCancel);
        confirm=(Button) findViewById(R.id.bConfirm);
        //cancel.setOnClickListener(this);
        confirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            /*case R.id.bCancel:
                Log.i(TAG, "Cancel ");
                dismiss();
                break;*/
            case R.id.bConfirm:
                if(String.valueOf(epin.getText()).equals(String.valueOf(cpin.getText()))){
                    MainActivity.setParentalPin(String.valueOf(epin.getText()));
                    dismiss();
                    Log.i(TAG, "PIN SAVED");
                }
                else
                    Toast.makeText(activity, "Invalid Inputs, Try Again!", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    /* @Override
    public void onViewCreated(@NotNull View view, @Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View currview=inflater.inflate(R.layout.pin_dialog,null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),R.style.Theme_AppCompat_DayNight_Dialog_Alert);
        //builder.setView(inflater.inflate(R.layout.pin_dialog,null));
        final AlertDialog dialog=builder.create();
                // Add action buttons
                *//*.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // sign in the user ...
                        if(epin.getText().equals(cpin.getText())){
                            MainActivity.ParentalPin= (String) epin.getText();
                        }
                        else {
                            Toast.makeText(getContext(), "PIN doesn't match!", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        PinSetDialog.this.getDialog().cancel();
                    }
                });*//*
        final TextView epin= currview.findViewById(R.id.enterPinField);
        final TextView cpin= currview.findViewById(R.id.confirmPinField);
        Button bcancel=(Button) currview.findViewById(R.id.bCancel);
        Button bconfirm=(Button) currview.findViewById(R.id.bConfirm);
        bcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //cancel button code::::::::::
                Log.i("BUtton", "Cancel ");
                dialog.dismiss();
            }
        });
        bconfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //confirm button code::::::::::
                Log.i("Button", "Confirm ");
                dialog.dismiss();
            }
        });
        dialog.setView(currview);

    }*/

    /*@Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       return inflater.inflate(R.layout.pin_dialog,container,false);
    }*/

}

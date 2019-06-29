package com.example.mark3;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PinCheckDialog extends Dialog implements android.view.View.OnClickListener{
    private Activity activity;
    private EditText epin;
    private Button confirm;
    String TAG="PinCheckDialog";
    public PinCheckDialog(Activity a) {
        super(a);
        this.activity=a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.pin_check);
        this.setCanceledOnTouchOutside(false);
        epin=(EditText) findViewById(R.id.enterPinField);
//        cancel=(Button) findViewById(R.id.bCancel);
        confirm=(Button) findViewById(R.id.bConfirm);
//        cancel.setOnClickListener(this);
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
                if(String.valueOf(epin.getText()).equals(MainActivity.getParentalPin())){
                    MainActivity.setParentalPin(null);
                    //MainActivity.turnOffParental();
                    Toast.makeText(activity, "Parental Mode OFF", Toast.LENGTH_SHORT).show();
                    dismiss();
                    Log.i(TAG, "PIN RESET");
                }
                else
                    Toast.makeText(activity, "Invalid PIN, Try Again!", Toast.LENGTH_SHORT).show();
                    
                break;
            default:
                break;
        }
    }
}

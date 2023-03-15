package com.example.proyectoilulu2_0;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class pagar extends AppCompatActivity {
    public static final String GPAY_PACKAGE_NAME = "com.google.android.apps.nbu.paisa.user";
    EditText name, upiId, amount, note;
    TextView msg;
    Button pay;
    Uri uri;
    String approvalRefNo;

    public static String payerName, UpiId, msgNote, sendAmount, status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = findViewById(R.id.name);
        upiId = findViewById(R.id.upi_id);
        amount = findViewById(R.id.amount);
        note = findViewById(R.id.transaction_note);

        msg = findViewById(R.id.status);
        pay = findViewById(R.id.pay);


        //initialising default value
        name.setText("NEXTECH");
        upiId.setText("7409484009@kotak");
        note.setText("mensualidad");
        amount.setText("100");

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                payerName = name.getText().toString();
                UpiId = upiId.getText().toString();
                msgNote = note.getText().toString();
                sendAmount = amount.getText().toString();

                if(!payerName.equals("") && !upiId.equals("") && !msgNote.equals("") && !sendAmount.equals("")){

                    uri = getUpiPaymentUri(payerName, UpiId, msgNote, sendAmount);
                    payWithGpay(GPAY_PACKAGE_NAME);

                }
                else {
                    Toast.makeText(pagar.this,"Llene los campos de nuevo.", Toast.LENGTH_SHORT).show();


                }



            }
        });

    }

    private static Uri getUpiPaymentUri(String name, String upiId, String note, String amount){
        return  new Uri.Builder()
                .scheme("upi")
                .authority("pay")
                .appendQueryParameter("pa",upiId)
                .appendQueryParameter("pn",name)
                .appendQueryParameter("tn",note)
                .appendQueryParameter("am",amount)
                .appendQueryParameter("cu","INR")
                .build();
    }

    private void payWithGpay(String packageName){

        if(isAppInstalled(this,packageName)){

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(uri);
            intent.setPackage(packageName);
            startActivityForResult(intent,0);

        }
        else{
            Toast.makeText(pagar.this,"Google pay no está intslado.", Toast.LENGTH_SHORT).show();
        }

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {
            status = data.getStringExtra("Status").toLowerCase();
            approvalRefNo = data.getStringExtra("Gracias");
        }
        if ((RESULT_OK == resultCode) && status.equals("success")) {
            Toast.makeText(pagar.this, "Transaction successful. "+approvalRefNo, Toast.LENGTH_SHORT).show();
            msg.setText("Transacción completada de  $" + sendAmount);

        }

        else{
            Toast.makeText(pagar.this, "Transacción fallida. Intente de nuevo.", Toast.LENGTH_SHORT).show();
            msg.setText("Transacción fallida de $" + sendAmount);
        }

    }


    public static boolean isAppInstalled(Context context, String packageName){
        try{
            context.getPackageManager().getApplicationInfo(packageName,0);
            return true;
        }catch (PackageManager.NameNotFoundException e){
            return false;
        }
    }
}
package com.example.intents;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class IntentsActivity extends Activity {
    private static final int REQUEST_CODE = 0;   

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        final Button explicit1 =
                (Button) findViewById(R.id.explicit_intent_button1);
        explicit1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                ComponentName cn = new ComponentName(
                        IntentsActivity.this,
                        TipCalculatorActivity.class);
                intent.setComponent(cn);
                startActivity(intent);
            }
        });

        final Button explicit2 =
                (Button) findViewById(R.id.explicit_intent_button2);
        explicit2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                ComponentName cn = new ComponentName(
                        "com.example.tips",
                        "com.example.tips.TipCalculatorActivity");
                intent.setComponent(cn);
                startActivity(intent);
            }
        });

       
        final Button passData1 =
                (Button) findViewById(R.id.pass_extras_button);
        passData1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IntentsActivity.this,
                        TipCalculatorActivity.class);
                Bundle extras = new Bundle();
                extras.putDouble("BillAmount", 30.00);
                extras.putInt("TipPercent", 15);
                intent.putExtras(extras);
                startActivity(intent);
            }
        });
        //////////////////Pasamos valores extras y recojemos el resultado//////////////////////////////////////////
        final Button passData2 =
                (Button) findViewById(R.id.return_results_button);
        passData2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IntentsActivity.this, 
                		                   TipCalculatorActivity.class);
                Bundle extras = new Bundle();
                extras.putDouble("BillAmount", 250.00);
                extras.putInt("TipPercent", 10);
                intent.putExtras(extras);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
        ///////////////////////////////////////////////////////////////////////
        final Button intent1 =
                (Button) findViewById(R.id.intent_button1);
        intent1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:303-555-1234"));
                startActivity(intent);
            }
        });

        final Button intent2 =
                (Button) findViewById(R.id.intent_button2);
        intent2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(ContactsContract.Contacts.CONTENT_URI);
                startActivity(intent);
            }
        });

        final Button category1 =
                (Button) findViewById(R.id.category_intent_button1);
        category1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                startActivity(intent);
            }
        });

        final Button category2 =
                (Button) findViewById(R.id.category_intent_button2);
        category2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_TEST);
                startActivity(intent);
            }
        });

        final Button data1 =
                (Button) findViewById(R.id.data_intent_button1);
        data1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setType("text/plain");
                startActivity(intent);
            }
        });

        final Button data2 =
                (Button) findViewById(R.id.data_intent_button2);
        data2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://www.google.com"));
                startActivity(intent);
            }
        });

        final Button data3 =
                (Button) findViewById(R.id.data_intent_button3);
        data3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setDataAndType(
                        Uri.parse("https://www.google.com"),
                        "text/plain");
                startActivity(intent);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode,
            Intent data) {
        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                Toast.makeText(this, data.getStringExtra("TipAmount"),
                		             Toast.LENGTH_LONG).show();
            }
        }
    }
}
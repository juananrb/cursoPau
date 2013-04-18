package com.example.intents;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class TestActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testactivity);    

        Intent intent = getIntent();
        StringBuilder sb = new StringBuilder();
        sb.append("ACTION  : " + intent.getAction() + "\n");
        sb.append("CATEGORY: " + intent.getCategories() + "\n");
        sb.append("DATA URI: " + intent.getDataString() + "\n");
        sb.append("TYPE    : " + intent.getType());

        TextView textView = (TextView) findViewById(R.id.textview);
        textView.setText(sb);
    }
}
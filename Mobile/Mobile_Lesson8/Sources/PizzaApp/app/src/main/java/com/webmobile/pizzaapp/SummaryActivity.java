package com.webmobile.pizzaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SummaryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        Intent summaryIntent = getIntent();
        String summaryMessage = summaryIntent.getStringExtra("summary");
        TextView summaryText = findViewById(R.id.textSummary);
        summaryText.setText(summaryMessage);
    }

    public void gotoOrder(View view) {
        finish();
    }
}
package com.webmobile.pizzaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ConfirmationActivity extends AppCompatActivity {
    boolean hideEmailBox = true;
    String orderMessage = "";
    String Customer = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

        Intent summaryIntent = getIntent();
        orderMessage = summaryIntent.getStringExtra("summary");
        Customer = summaryIntent.getStringExtra("customer");
        TextView summaryText = findViewById(R.id.textSummary);
        summaryText.setText(orderMessage);
    }

    public void showEmailBox(View view) {
        hideEmailBox = !hideEmailBox;
        EditText emailBox = findViewById(R.id.editTextEmail);
        if(hideEmailBox)
            emailBox.setVisibility(View.GONE);
        else
            emailBox.setVisibility(View.VISIBLE);
    }

    public void submitOrder(View view) {
        CheckBox emailCheck = findViewById(R.id.checkboxEmail);
        if(emailCheck.isChecked()) {
            EditText emailBox = findViewById(R.id.editTextEmail);

            String emailMessage = "mailto:" + emailBox.getText().toString() +
                    "?cc=" +
                    "&subject=" + Uri.encode("Order Confirmation Do Not Reply") +
                    "&body=" + Uri.encode("Dear " + Customer + ",\n\n" +orderMessage);

            Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
            emailIntent.setData(Uri.parse(emailMessage));

            try {
                startActivity(emailIntent);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(this, "Unable To Send Email", Toast.LENGTH_SHORT).show();
            }
        } else
            startActivity(new Intent(this, MainActivity.class));

    }
}
package com.webmobile.pizzaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    int quantity = 1;
    double price = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner cheeseSpinner = findViewById(R.id.spinnerPizzaCheese);
        Spinner sauceSpinner = findViewById(R.id.spinnerPizzaSauce);
        cheeseSpinner.setSelection(2);
        sauceSpinner.setSelection(1);
    }

    public void makeSummary(View view) {
        Intent summaryIntent = new Intent(this, SummaryActivity.class);
        summaryIntent.putExtra("summary", getSummary());
        startActivity(summaryIntent);
    }

    public void makeOrder(View view) {
        Intent orderIntent = new Intent(this, ConfirmationActivity.class);
        orderIntent.putExtra("summary", getSummary());
        EditText customerCtl = findViewById(R.id.editTextCustomer);
        orderIntent.putExtra("customer", customerCtl.getText().toString());
        startActivity(orderIntent);
    }

    private String getSummary() {
        String summary = "";
        price = 0.0;

        EditText customerCtl = findViewById(R.id.editTextCustomer);
        summary += "Customer name: " + customerCtl.getText().toString() + "\n";

        RadioGroup sizeCtrl = findViewById(R.id.radioGroupPizzaSize);
        RadioButton checkSizeCtrl = findViewById(sizeCtrl.getCheckedRadioButtonId());
        summary += quantity + " pizza\n";
        summary += checkSizeCtrl.getText().toString() + " ";
        switch(sizeCtrl.indexOfChild(checkSizeCtrl)) {
            case 2:
                price += 7.99;
                break;
            case 3:
                price += 9.99;
                break;
            default:
                price += 5.99;
        }

        RadioGroup crustCtrl = findViewById(R.id.radioGroupPizzaCrust);
        RadioButton checkCrustCtrl = findViewById(crustCtrl.getCheckedRadioButtonId());
        summary += checkCrustCtrl.getText().toString() + " Crust\n";

        Spinner cheeseSpinner = findViewById(R.id.spinnerPizzaCheese);
        String cheeseSel = cheeseSpinner.getSelectedItem().toString();
        Spinner sauceSpinner = findViewById(R.id.spinnerPizzaSauce);
        String sauceSel = sauceSpinner.getSelectedItem().toString();
        summary += cheeseSel + " Cheese and " + sauceSel + " Sauce\n";
        price += cheeseSpinner.getSelectedItemPosition() * 0.5;

        String toppings = "";

        if(((CheckBox)findViewById(R.id.checkBoxHam)).isChecked()) {
            toppings += "Ham, ";
            price += 0.5;
        }

        if(((CheckBox)findViewById(R.id.checkBoxBeef)).isChecked()) {
            toppings += "Beef, ";
            price += 0.5;
        }

        if(((CheckBox)findViewById(R.id.checkBoxPepperoni)).isChecked()) {
            toppings += "Pepperoni, ";
            price += 0.5;
        }

        if(((CheckBox)findViewById(R.id.checkBoxSausage)).isChecked()) {
            toppings += "Sausage, ";
            price += 0.5;
        }

        if(((CheckBox)findViewById(R.id.checkBoxOnions)).isChecked()) {
            toppings += "Onions, ";
            price += 0.5;
        }

        if(((CheckBox)findViewById(R.id.checkBoxGreenPepper)).isChecked()) {
            toppings += "Green Pepper, ";
            price += 0.5;
        }

        if(((CheckBox)findViewById(R.id.checkBoxBlackOlive)).isChecked()) {
            toppings += "Black Olives, ";
            price += 0.5;
        }

        if(((CheckBox)findViewById(R.id.checkBoxPineapple)).isChecked()) {
            toppings += "Pineapple, ";
            price += 0.5;
        }

        if(((CheckBox)findViewById(R.id.checkBoxMushroom)).isChecked()) {
            toppings += "Mushrooms, ";
            price += 0.5;
        }

        if(!toppings.isEmpty())
            summary += "Toppings: " + toppings.substring(0, toppings.length() - 2) + "\n";
        else
            summary += "No Toppings\n";

        summary += "Total: $" + (Math.round(price * quantity * 100) / 100.00);

        return summary;
    }

    public void IncrementQuantity(View view) {
        ++quantity;
        updateQuantity();
    }

    public void DecrementQuantity(View view) {
        if(quantity <= 1) {
            Toast.makeText(this, "@string/pizza_too_few", Toast.LENGTH_SHORT).show();
            if(quantity < 1) {
                quantity = 1;
                updateQuantity();
            }
        } else {
            --quantity;
            updateQuantity();
        }
    }

    private void updateQuantity() {
        TextView quantityCtr = findViewById(R.id.textQuantity);
        quantityCtr.setText("" + quantity);
    }
}
package com.stetson_pierce.currencyconverter;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // Grab both spinners
    Spinner spinnerLeft;
    Spinner spinnerRight;

    // Constants
    public final String DEFAULT = "Select Currency";
    public final String USD = "$ - Dollars (USD)";
    public final String GBP = "£ - Pounds (GBP)";
    public final String EUR = "€ - Euros (EUR)";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Custom code
        // Grab the spinners
        spinnerLeft = (Spinner) findViewById(R.id.currencySelection1);
        spinnerRight = (Spinner) findViewById(R.id.currencySelection2);

        // Create an array adapter with arraylist created in Strings.xml
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.country_currencies, R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Add the adapter to the Spinners
        spinnerLeft.setAdapter(adapter);
        spinnerRight.setAdapter(adapter);

        // Code for ListView


    }

    // Method called when button is pressed
    public void convertCurrency(View view) {
        // Check if there is any reason math cannot be performed
        EditText numToConvert = (EditText) findViewById(R.id.numberToConvert);
        String spinner1 = spinnerLeft.getSelectedItem().toString();
        String spinner2 = spinnerRight.getSelectedItem().toString();
        if (spinner1.contains(DEFAULT) || spinner2.contains(DEFAULT)){
            Toast.makeText(getApplicationContext(), "Make sure you've selected currencies to convert",
                    Toast.LENGTH_SHORT).show();
        }
        else if (spinner1 == spinner2) {
            Toast.makeText(getApplicationContext(), "Hey jackass, " +
                    spinner1.substring(3) + " is already converted to " + spinner1.substring(3),
                    Toast.LENGTH_LONG).show();
        }
        else if (numToConvert.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Make sure you have a number to convert",
                    Toast.LENGTH_LONG).show();
        }
        else
        {
            // We should be able to perform some valid maths
            double myNum = Double.parseDouble(numToConvert.getText().toString());
            switch (spinner1) {
                case USD:
                    switch (spinner2)
                    {
                        case GBP:
                            myNum = convertCurrency(myNum, 0.65d);
                            displayConvertedAmt("£", myNum);
                            break;
                        case EUR:
                            myNum = convertCurrency(myNum, 0.88d);
                            displayConvertedAmt("€", myNum);
                            break;
                    }
                    break;
                case GBP:
                    switch (spinner2)
                    {
                        case USD:
                            myNum = convertCurrency(myNum, 1.54d);
                            displayConvertedAmt("$", myNum);
                            break;
                        case EUR:
                            myNum = convertCurrency(myNum, 1.36d);
                            displayConvertedAmt("€", myNum);
                            break;
                    }
                    break;
                case EUR:
                    switch (spinner2)
                    {
                        case USD:
                            myNum = convertCurrency(myNum, 1.14d);
                            displayConvertedAmt("$", myNum);
                            break;
                        case GBP:
                            myNum = convertCurrency(myNum, 0.74d);
                            displayConvertedAmt("£", myNum);
                            break;
                    }
                    break;
            }
        }

    }

    public double convertCurrency(double convertingNum, double exchangeRate) {
        double convertedAmount = 0d;
        convertedAmount = convertingNum * exchangeRate;
        return convertedAmount;
    }

    public void displayConvertedAmt(String symbol, double myNum) {
        Toast.makeText(getApplicationContext(),
                symbol + String.valueOf(myNum),
                Toast.LENGTH_LONG).show();
    }
}
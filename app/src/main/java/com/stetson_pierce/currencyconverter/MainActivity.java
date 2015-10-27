package com.stetson_pierce.currencyconverter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    // Delcare Views to be used in multiple methods
    Spinner spinnerLeft;
    Spinner spinnerRight;
    ListView recentConversionsView;
    ArrayList<String> arrRecentConversionsList;
    ArrayAdapter<String> recentConversionsAdapter;

    // Constants for easy comparison and code readability
    public final String DEFAULT = "Select Currency";
    public final String USD = "$ - Dollars (USD)";
    public final String GBP = "£ - Pounds (GBP)";
    public final String EUR = "€ - Euros (EUR)";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Custom code

        /*
         *  Recent selection ListView setup
         */

        // Grab the ListView
        recentConversionsView = (ListView) findViewById(R.id.recentConvertions);

        // Initialize ListView
        arrRecentConversionsList = new ArrayList<>();

        // Create an ArrayAdapter with recentConversions
        recentConversionsAdapter =
                new ArrayAdapter<>(this,
                        android.R.layout.simple_list_item_1,
                        arrRecentConversionsList);

        // Add the ArrayAdapter to the ListView
        recentConversionsView.setAdapter(recentConversionsAdapter);

        /*
         *  Selection Spinner(s) setup
         */

        // Grab the selection Spinner(s)
        spinnerLeft = (Spinner) findViewById(R.id.currencySelection1);
        spinnerRight = (Spinner) findViewById(R.id.currencySelection2);

        // Currency values used for selection Spinners
        ArrayList<String> currencies =
                new ArrayList<>(Arrays.asList(DEFAULT, USD, GBP, EUR));

        // Create an ArrayAdapter with currencies ArrayList
        ArrayAdapter<String> currencyAdapter =
                new ArrayAdapter<>(this,                // this refers to the app context
                android.R.layout.simple_list_item_1,
                currencies);

        // Add the ArrayAdapter to the selection Spinner(s)
        spinnerLeft.setAdapter(currencyAdapter);
        spinnerRight.setAdapter(currencyAdapter);
    }

    // Method called when button is pressed
    public void onClick(View view) {
        // Check if there is any reason math cannot be performed
        EditText numToConvert = (EditText) findViewById(R.id.numberToConvert);
        String spinner1 = spinnerLeft.getSelectedItem().toString();
        String spinner2 = spinnerRight.getSelectedItem().toString();
        if (spinner1.contains(DEFAULT) || spinner2.contains(DEFAULT)){
            Toast.makeText(getApplicationContext(), "Make sure you've selected currencies to convert",
                    Toast.LENGTH_SHORT).show();
        }
        else if (spinner1.equals(spinner2)) {
            Toast.makeText(getApplicationContext(), "Hey jackass, " +
                    spinner1.substring(3) + " is already converted to " + spinner1.substring(3),
                    Toast.LENGTH_LONG).show();
        }
        else if (numToConvert.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Make sure you have a number to convert",
                    Toast.LENGTH_LONG).show();
        }
        else {
            // We should be able to perform some valid maths
            double initialNum = Double.parseDouble(numToConvert.getText().toString());

            switch (spinner1) {
                case USD:
                    switch (spinner2) {
                        case GBP:
                            convertCurrency(initialNum, 0.65d, "$", "£");
                            break;
                        case EUR:
                            convertCurrency(initialNum, 0.88d, "$", "€");
                            break;
                    }
                    break;
                case GBP:
                    switch (spinner2) {
                        case USD:
                            convertCurrency(initialNum, 1.54d, "£", "$");
                            break;
                        case EUR:
                            convertCurrency(initialNum, 1.36d, "£", "€");
                            break;
                    }
                    break;
                case EUR:
                    switch (spinner2) {
                        case USD:
                            convertCurrency(initialNum, 1.14d, "€", "$");
                            break;
                        case GBP:
                            convertCurrency(initialNum, 0.74d, "€", "£");
                            break;
                    }
                    break;
            }
        }
    }

    public void convertCurrency(double convertingNum, double exchangeRate, String initialSymbol,
                                String exchangeSymbol) {
        double convertedAmount = convertingNum * exchangeRate;
        toastConvertedAmt(initialSymbol, convertedAmount);
        updateRecentConversions(initialSymbol + convertingNum, exchangeSymbol + convertedAmount);
    }

    public void toastConvertedAmt(String symbol, double myNum) {
        Toast.makeText(getApplicationContext(),
                symbol + String.valueOf(myNum),
                Toast.LENGTH_LONG).show();
    }

    public void updateRecentConversions(String initial, String converted){
        // Test updating the Recent Conversions
        arrRecentConversionsList.add(initial + " = " + converted);
        recentConversionsAdapter.notifyDataSetChanged();
    }
}
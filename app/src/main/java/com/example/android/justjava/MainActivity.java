package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    /**
     * This method is called when the PLUS button is clicked.
     */
    public void increment(View view) {

        if(quantity == 100) {
            /**
             *  Toast informing of inrementation limit
             */
            Toast.makeText(this, "You can't have more than 100 cups!", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);
    }
    /**
     *  Toast informing of decrementation  limit
     */
    public void decrement(View view) {

        if(quantity == 1){

            Toast.makeText(this, "You can't have less than 1 cup!", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);
    }
    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {

        /** get the value from EditText */
        EditText nameView = (EditText) findViewById(R.id.name_view);
        String name = nameView.getText().toString();

        /** checking if whippedcream checkbox is checked */
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_check);
        boolean hasWhippedCream =  whippedCreamCheckBox.isChecked();

        /** checking if chocolate checkbox is checked */
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_check);
        boolean hasChocolate = chocolateCheckBox.isChecked();


        int price = calculatePrice(hasWhippedCream, hasChocolate);
        String priceMessage = createOrderSummary(name, price, hasWhippedCream, hasChocolate);
       // displayMessage(priceMessage);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for " + name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * Calculates the price of the order.
     * @param addChocolate
     * @param addWhippedCream
     * @return total price
     *
     */
    private int calculatePrice(boolean addWhippedCream, boolean addChocolate) {
        int baseprice =  5;

        if(addWhippedCream) {
            baseprice = baseprice +1;
        }

        if(addChocolate) {
            baseprice = baseprice + 2;
        }
        return quantity * baseprice;
    }

    /**
     * @param name for value of input
     * @param price for the order
     * @param addChocolate for checkbox value
     * @param addWhippedCream for checkbox value
     * @return text summary
     */
    private String createOrderSummary(String name, int price, boolean addWhippedCream, boolean addChocolate){
        String priceMessage = "Name: " + name;
        priceMessage = priceMessage + "\nAdd whipped cream?: " + addWhippedCream;
        priceMessage = priceMessage + "\nAdd chocolate: " + addChocolate;
        priceMessage = priceMessage + "\nQuantity: " + quantity;
        priceMessage = priceMessage + "\nTotal: " + price;
        priceMessage = priceMessage + "\nThank you!";
        return priceMessage;
    }
    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }
}
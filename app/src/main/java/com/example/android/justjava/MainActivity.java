package com.example.android.justjava;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends ActionBarActivity {

    int quantity = 1;
    int priceOfCoffee = 3;
    boolean whippedCream;
    boolean chocolate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        CheckBox whippedCreamView = (CheckBox) findViewById(R.id.whipped_cream_check_box);
        whippedCream = whippedCreamView.isChecked();
        CheckBox chocolateView = (CheckBox) findViewById(R.id.chocolate_check_box);
        chocolate = chocolateView.isChecked();
        EditText nameEditTextView = (EditText) findViewById(R.id.name_edit_text);
        String name = nameEditTextView.getText().toString();
        String priceMessage = "";
            priceMessage += "Name: " + name;
            priceMessage += "\nAdd whipped cream? " + whippedCream;
            priceMessage += "\nAdd chocolate? " + chocolate;
            priceMessage += "\nQuantity: " + quantity;
            priceMessage += "\nTotal: $" + calculatePrice(whippedCream, chocolate, quantity, priceOfCoffee);
            priceMessage += "\nThank you!";

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, "");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java Order");
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public int calculatePrice(boolean whi, boolean cho, int quan, int pri) {
        int toppings = 0;

        if(whi == true) {
            toppings = toppings + 1;
        }
        if(cho == true) {
            toppings = toppings + 2;
        }
        int total = quan * ( pri + toppings);
        return total;
    }

    /**
     * This method is called when the '+' button is pushed and will change the quantity to 3
     */
    public void increment(View view) {
        if (quantity == 100)
        {
            Context context = getApplicationContext();
            CharSequence text = "You cannot order more that 100 cups at a time!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            return;
        }
        else {
            quantity = quantity + 1;
            displayQuantity(quantity);
        }
    }

    /**
     * This method is called when the '-' button is pushed and will change the quantity to 1
     */
    public void decrement(View view) {
        if (quantity == 1) {
            Context context = getApplicationContext();
            CharSequence text = "You cannot order less than 1 cup!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            return;
        }
        else {
            quantity = quantity -1;
            displayQuantity(quantity);
        }
    }
    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }
}
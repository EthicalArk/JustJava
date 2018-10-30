package in.cbes.justjava;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

/**
 * This app displays and order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity = 0;
    int prices = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the plus button is clicked.
     */

    public void increment(View view) {
        quantity = quantity + 1;
        displayQuantity(quantity);

    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {
        if (quantity != 0) {
            quantity = quantity - 1;
        }
        displayQuantity(quantity);

    }

    /**
     * This method called when the order button is clicked.
     */

    public void submitOrder(View view) {

        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        EditText edit = (EditText) findViewById(R.id.txt_NameTextField);
        String result = edit.getText().toString();

        displayQuantity(quantity);
        int price = calculatePrice(quantity, prices, hasChocolate, hasWhippedCream);
        String order = orderSummary(price, hasWhippedCream, hasChocolate, result);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just java order to" + result);
        intent.putExtra(Intent.EXTRA_TEXT, order);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
        displayMessage(order);

    }

    /**
     * This method returns the Order Summary .
     *
     * @param price           is the total price
     * @param addWhippedCream is the  Toppings add or not
     * @param addChocolate    is for the topping chocolate.
     * @return text Summary.
     */


    public String orderSummary(int price, boolean addWhippedCream, boolean addChocolate, String result) {
        String priceMessage = "Name:" + result;
        priceMessage = priceMessage + "\n Add Chocolate:" + addChocolate;
        priceMessage = priceMessage + "\n Add Whipped Cream?" + addWhippedCream;
        priceMessage = priceMessage + "\n Quantity :" + quantity;
        priceMessage = priceMessage + "\n Total :" + price;
        priceMessage = priceMessage + " \n ThankYou!";
        return priceMessage;


    }

    /**
     * This method is calculating the price of each cofee cup ordered.
     *
     * @param number here is the quantity of the cofee ordered
     * @param amount here is the price of one cofee ordered
     * @return calculated price.
     */

    private int calculatePrice(int number, int amount, boolean addWhippedCream, boolean addChocolate) {

        int total = 0;
        if (addWhippedCream == true) {
            total += 5;
        } else if (addChocolate == true) {

            total += 5;
        }
        return (total + amount) * number;

    }

    /**
     * This method displays the given quantity value on the screen.
     */
    @SuppressLint("SetTextI18n")
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }


    /**
     * This method displays the string with the price message.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);

    }


}

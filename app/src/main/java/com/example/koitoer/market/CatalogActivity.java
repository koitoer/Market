package com.example.koitoer.market;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.koitoer.market.model.MarketItem;
import com.example.koitoer.market.service.MarketService;
import com.example.koitoer.market.service.MarketServiceLocal;

import java.text.NumberFormat;

/**
 * Add Item Activity
 * @author koitoer
 */
public class CatalogActivity extends AppCompatActivity {

    MarketService marketService = new MarketServiceLocal();
    String current = "";
    EditText priceEditText, itemEditText;
    Spinner categorySpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_item_activity);

        this.createCategorySpinner();
        this.createPriceEditText();
        itemEditText = ((EditText) findViewById(R.id.itemEditText));

    }


    /**
     * Method executed in the Cancel Button click
     * Back to the landing page
     * @param view
     */
    public void returnMain(View view) {
        finish();
    }

    /**
     * Method execute in the Add Button click
     * Add the item to the list and clear the edit boxes.
     * @param view
     */
    public void addItem(View view) {
        String item = ((EditText)findViewById(R.id.itemEditText)).getText().toString();
        String price = priceEditText.getText().toString().replaceAll("[$]", "");
        double priceValue = Double.parseDouble(price);
        marketService.setContext(this);
        marketService.addItem(new MarketItem((long) marketService.getMarketCatalog().size(), item, priceValue));
        itemEditText.setText("");
        priceEditText.setText("0.00");
        categorySpinner.setId(0);
    }

    /**
     * Method used to create the category spinner
     */
    private void createCategorySpinner() {
        categorySpinner = (Spinner)findViewById(R.id.categoryItemSpinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.categories_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        categorySpinner.setAdapter(adapter);
    }

    /**
     * Method used to create the price edit text and its effect
     */
    private void createPriceEditText() {
        priceEditText = (EditText) findViewById(R.id.priceEditText);
        priceEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!s.toString().equals(current)) {
                    priceEditText.removeTextChangedListener(this);

                    String cleanString = s.toString().replaceAll("[$,.]", "");

                    double parsed = Double.parseDouble(cleanString);
                    String formatted = NumberFormat.getCurrencyInstance().format((parsed / 100));

                    current = formatted;
                    priceEditText.setText(formatted);
                    priceEditText.setSelection(formatted.length());
                    priceEditText.addTextChangedListener(this);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}

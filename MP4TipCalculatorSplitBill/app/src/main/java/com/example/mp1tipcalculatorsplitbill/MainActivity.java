package com.example.mp1tipcalculatorsplitbill;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.lang.Math;

import java.text.NumberFormat;


public class MainActivity extends AppCompatActivity implements TextWatcher, AdapterView.OnItemSelectedListener {

    public static final String TAG = "MainActivity";

    private EditText editTxtBill;
    private TextView txtBill, txtPercentage, txtTipAmount, txtTotalAmount, txtNumOfPeople, txtTotalAmountPerPerson;
    private SeekBar seekBar;

    private int numOfPeople;
    private Spinner spinner;
    ArrayAdapter<CharSequence> adapter;

    private double billAmount = 0;
    private double percent = 0;
    private double tip = 0;
    private double billTotal = 0;
    private double perPersonBill = 0;

    private String roundOption = "";
    boolean roundOptionChosen = false;

    private static final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
    private static final NumberFormat percentFormat = NumberFormat.getPercentInstance();

    private static final String BILL = "Bill";
    private static final String TIP = "Tip";
    private static final String TOTAL = "Total";
    private static final String PER_PERSON = "Per_Person";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "Inside oncreate method");

        getReferencesToWidgets();

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.d(TAG, "Inside onprogresschanged method");

                Log.d(TAG, "progress = " + progress);
                percent = progress/100.0;


                Log.d(TAG, "Percent = " + percent);

                calculate();
                Log.d(TAG, "End of onprogresschanged method");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });

        Log.d(TAG, "End of oncreate method");
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) { }

    @Override
    public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
        Log.d("MainActivity", "inside onTextChanged method: charSequence= "+ charSequence);

        //prevents NumberFormatException
        if(!charSequence.toString().isEmpty()) {
            billAmount = Double.parseDouble(charSequence.toString()) / 100;
        }
        else {
            billAmount = 0;
        }

        Log.d("MainActivity", "Bill Amount = "+billAmount);


        txtBill.setText(currencyFormat.format(billAmount));

        calculate();
    }

    @Override
    public void afterTextChanged(Editable s) { }

    private void getReferencesToWidgets(){
        Log.d(TAG, "inside of getReferencesToWidgets method");

        editTxtBill = findViewById(R.id.editTxt_Bill);
        editTxtBill.addTextChangedListener((TextWatcher)this);
        txtBill = findViewById(R.id.txt_Bill);

        txtPercentage = findViewById(R.id.txt_Percentage);
        seekBar = findViewById(R.id.seek_bar);

        txtTipAmount = findViewById(R.id.txt_TipAmount);
        txtTotalAmount = findViewById(R.id.txt_TotalAmount);

        txtNumOfPeople = findViewById(R.id.txt_num_of_people);
        spinner = findViewById(R.id.spinner);
        adapter = ArrayAdapter.createFromResource(this, R.array.num_of_people_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        if(spinner != null){
            spinner.setOnItemSelectedListener(this);
            spinner.setAdapter(adapter);
            spinner.setSelection(0);
        }

        txtTotalAmountPerPerson = findViewById(R.id.txt_TotalAmountPerPerson);

        Log.d(TAG, "end of getReferencesToWidgets method");
    }

    private void calculate() {
        Log.d(TAG, "Inside the calculate method");
        txtPercentage.setText(percentFormat.format(percent));

        tip = billAmount * percent;
        billTotal = billAmount + tip;

        if(roundOptionChosen){
            if(roundOption.equals("No")){
                Log.d(TAG, "no radio button was chosen");

                txtTipAmount.setText(currencyFormat.format(tip));
                txtTotalAmount.setText(currencyFormat.format(billTotal));
            }
            else if(roundOption.equals("Tip")){
                Log.d(TAG, "tip radio button was chosen");
                Log.d(TAG, "before "+tip);

                tip = Math.round(tip);
                txtTipAmount.setText(currencyFormat.format(tip));
                txtTotalAmount.setText(currencyFormat.format(billTotal));

                Log.d(TAG, "after "+tip);
            }
            else if(roundOption.equals("Total")){
                Log.d(TAG, "total radio button was chosen");
                Log.d(TAG, "before"+billTotal);

                billTotal = Math.round(billTotal);
                txtTipAmount.setText(currencyFormat.format(tip));
                txtTotalAmount.setText(currencyFormat.format(billTotal));

                Log.d(TAG, "after"+billTotal);
            }
            else{
                Toast.makeText(this, "What did you click on????", Toast.LENGTH_LONG).show();
            }

            perPersonBill = billTotal / numOfPeople;
            txtTotalAmountPerPerson.setText(currencyFormat.format(perPersonBill));

        }
        else{
            Toast.makeText(this, "Pick a rounding option!", Toast.LENGTH_LONG).show();
        }

        Log.d(TAG, "End of calculate method");
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "inside of onSaveInstanceState");

        outState.putDouble(BILL, billAmount);
        outState.putDouble(TIP, tip);
        outState.putDouble(TOTAL, billTotal);
        outState.putDouble(PER_PERSON, perPersonBill);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d(TAG, "inside of onRestoreInstanceState");

        billAmount = savedInstanceState.getDouble(BILL,0);
        tip = savedInstanceState.getDouble(TIP,0);
        billTotal = savedInstanceState.getDouble(TOTAL,0);
        perPersonBill = savedInstanceState.getDouble(PER_PERSON,0);

        if(txtBill != null || txtTipAmount != null || txtTotalAmount != null || txtTotalAmountPerPerson != null) {
            txtBill.setText(currencyFormat.format(billAmount));
            txtTipAmount.setText(currencyFormat.format(tip));
            txtTotalAmount.setText(currencyFormat.format(billTotal));
            txtTotalAmountPerPerson.setText(currencyFormat.format(perPersonBill));
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        Log.d(TAG, "inside of onItemSelected method");
        numOfPeople = Integer.parseInt(adapterView.getItemAtPosition(position).toString());
        Log.d(TAG, "Number of people paying is " + numOfPeople);
        txtNumOfPeople.setText("Total People: " + numOfPeople);
        calculate();
        Log.d(TAG, "end of onItemSelected method");
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) { }

    public void onRadioButtonClicked(View view) {
        Log.d(TAG, "inside of onRadioButtonClicked method");

        roundOptionChosen = ((RadioButton)view).isChecked();
        roundOption = ((RadioButton)view).getText().toString();

        Log.d(TAG, "Rounding Option: "+ roundOption);
        Log.d(TAG, "Rounding option chosen: "+ roundOptionChosen);
        calculate();

        Log.d(TAG, "end of onRadioButtonClicked method");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG, "inside of onCreateOptionsMenu method");

        getMenuInflater().inflate(R.menu.menu_main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Log.d(TAG, "inside of onOptionsItemSelected method");

        switch(item.getItemId()){
            case R.id.action_share:
                if(billAmount == 0){
                    Toast.makeText(this, "Nothing to send", Toast.LENGTH_SHORT).show();
                }
                else{
                    sendText();
                }
                return true;
            case R.id.action_info:
                Toast.makeText(this, "The Spinner is used to split the total bill among friends", Toast.LENGTH_SHORT).show();
                return true;
            default:
                Log.d("TAG", "I don't know what was selected from the menu");
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void sendText() {
        Log.d(TAG, "inside of sendText method");
        String textToSend = "Bill:" + txtBill.getText().toString() + " Tip:" + txtTipAmount.getText().toString() + " Total:" + txtTotalAmount.getText().toString() + " Per Person:" + txtTotalAmountPerPerson.getText().toString();
        String mimeType = "text/plain";
        ShareCompat.IntentBuilder
                .from(this)
                .setType(mimeType)
                .setChooserTitle("Pick an app")
                .setText(textToSend)
                .startChooser();
        Log.d(TAG, "end of sendText method");
    }
}
package com.example.mp1tipcalculator;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.NumberFormat;


public class MainActivity extends AppCompatActivity implements TextWatcher {

    public static final String TAG = "MainActivity";

    private EditText editTxtBill;
    private TextView txtBill;

    private TextView txtPercentage;
    private SeekBar seekBar;

    private TextView txtTipAmount;
    private TextView txtTotalAmount;

    private double billAmount = 0;
    private double percent = 0;

    private static final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
    private static final NumberFormat percentFormat = NumberFormat.getPercentInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "Inside oncreate method");

        editTxtBill = (EditText)findViewById(R.id.editTxt_Bill);
        editTxtBill.addTextChangedListener((TextWatcher)this);
        txtBill = (TextView)findViewById(R.id.txt_Bill);

        txtPercentage = (TextView)findViewById(R.id.txt_Percentage);
        seekBar = (SeekBar)findViewById(R.id.seek_bar);

        txtTipAmount = (TextView)findViewById(R.id.txt_TipAmount);
        txtTotalAmount = (TextView)findViewById(R.id.txt_TotalAmount);

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

    private void calculate() {
        Log.d(TAG, "Inside the calculate method");

        txtPercentage.setText(percentFormat.format(percent));

        double tip = billAmount * percent;
        double billTotal = billAmount + tip;

        txtTipAmount.setText(currencyFormat.format(tip));
        txtTotalAmount.setText(currencyFormat.format(billTotal));

        Log.d(TAG, "End of calculate method");
    }


}
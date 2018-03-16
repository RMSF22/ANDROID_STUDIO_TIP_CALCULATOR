package com.example.ramonsantos.tipcalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity implements TextWatcher,
        SeekBar.OnSeekBarChangeListener{

    //declare your variables for the widgets
    private EditText editTextBillAmount;
    private TextView textViewBillAmount;
    private SeekBar percentSeekBar;

    //declare the variables for the calculations
    private double billAmount = 0.0;
    private double percent = .15;
    private TextView percentTextView;
    private TextView tipTextView;
    private TextView totalTextView;

    //set the number formats to be used for the $ amounts , and % amounts
    private static final NumberFormat currencyFormat =
            NumberFormat.getCurrencyInstance();
    private static final NumberFormat percentFormat =
            NumberFormat.getPercentInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        percentTextView = (TextView) findViewById(R.id.percent_Label);
        tipTextView = (TextView) findViewById(R.id.tip_Amount);
        totalTextView = (TextView) findViewById(R.id.total_Amount);
        tipTextView.setText(currencyFormat.format(0));
        totalTextView.setText(currencyFormat.format(0));

        //add Listeners to Widgets
        //-----------------------------------------------------------------------------------
        editTextBillAmount = (EditText)findViewById(R.id.editText_BillAmount);//uncomment this line
        editTextBillAmount.addTextChangedListener((TextWatcher) this);//uncomment this line

        textViewBillAmount = (TextView)findViewById(R.id.textView_BillAmount);
        editTextBillAmount.addTextChangedListener((TextWatcher)this);
        //-----------------------------------------------------------------------------------

        percentSeekBar =(SeekBar) findViewById(R.id.seek_Bar);
        percentTextView = (TextView) findViewById(R.id.percent_Label);
        percentSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                percentTextView.setText(String.valueOf(progress));
                percent = progress / 100.0; //calculate percent based on seeker value
                calculate();


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });



    }


    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    /*
    Note:   int i, int i1, and int i2
            represent start, before, count respectively
            The charSequence is converted to a String and parsed to a double for you
     */
    @Override
    public void onTextChanged(CharSequence charSequence, int Start, int before, int count) {
        Log.d("MainActivity", "inside onTextChanged method: charSequence= "+charSequence);
        //surround risky calculations with try catch (what if billAmount is 0 ?
        //charSequence is converted to a String and parsed to a double for you
        try {
            billAmount = Double.parseDouble(charSequence.toString()) / 100.00;
            Log.d("MainActivity", "Bill Amount = " + billAmount);
            //setText on the textView
            textViewBillAmount.setText(currencyFormat.format(billAmount));
            //perform tip and total calculation and update UI by calling calculate
            calculate();//uncomment this line
        }catch (NumberFormatException e){
            editTextBillAmount.setText("Something Went Wrong My Guy!");
            billAmount = 0.0;

        }
        calculate();
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        //percent = progress / 100.0; //calculate percent based on seeker value
        //calculate();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    // calculate and display tip and total amounts
    private void calculate() {
        Log.d("MainActivity", "inside calculate method");

        // format percent and display in percentTextView
       percentTextView.setText(percentFormat.format(percent));

        // calculate the tip and total
        double tip = billAmount * percent;
        double total = billAmount + tip;


        // display tip and total formatted as currency
        //user currencyFormat instead of percentFormat to set the textViewTip
         tipTextView.setText(currencyFormat.format(tip));
         totalTextView.setText(currencyFormat.format(total));
        //use the tip example to do the same for the Total

    }}



package com.david.crossfit.view.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.david.crossfit.R;
import com.david.crossfit.view.util.ConverterPreference;
import com.david.crossfit.view.util.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by TechnoA on 09.06.2017.
 */

public class Converter extends AppCompatActivity implements View.OnClickListener{

    @BindView(R.id.valueTop)
    TextView valueTop;

    @BindView(R.id.valueBottom)
    TextView valueBottom;

    @BindView(R.id.btnOne)
    Button btnOne;

    @BindView(R.id.btnTwo)
    Button btnTwo;

    @BindView(R.id.btnThree)
    Button btnThree;

    @BindView(R.id.btnFour)
    Button btnFour;

    @BindView(R.id.btnFive)
    Button btnFive;

    @BindView(R.id.btnSix)
    Button btnSix;

    @BindView(R.id.btnSeven)
    Button btnSeven;

    @BindView(R.id.btnEight)
    Button btnEight;

    @BindView(R.id.btnNine)
    Button btnNine;

    @BindView(R.id.btnZero)
    Button btnZero;

    @BindView(R.id.btnDot)
    Button btnDot;

    @BindView(R.id.btnBackSpace)
    ImageButton btnBackspace;

    @BindView(R.id.convert)
    ImageButton convert;


    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.converter_kg_lb);
        ButterKnife.bind(this);
//        defineAllViews();
        setListener();

        toolbar = (Toolbar)this.findViewById(R.id.toolbar);
        toolbar.setClickable(true);
        toolbar.setNavigationIcon(getResIdFromAttribute(this, com.lb.material_preferences_library.R.attr.homeAsUpIndicator));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Utils.logInfo("setNavigationOnClickListener");
                finish();
                overridePendingTransition(R.anim.open_main, R.anim.close_next);
            }
        });
        toolbar.setTitle(R.string.converter);
    }

    private static int getResIdFromAttribute(Activity activity, int attr) {
        if(attr == 0) {
            return 0;
        } else {
            TypedValue typedValue = new TypedValue();
            activity.getTheme().resolveAttribute(attr, typedValue, true);
            return typedValue.resourceId;
        }
    }
//    private void defineAllViews(){
//
//        valueTop = (TextView) findViewById(R.id.valueTop);
//        valueBottom = (TextView) findViewById(R.id.valueBottom);
//
//        btnOne = (Button) findViewById(R.id.btnOne);
//        btnTwo = (Button) findViewById(R.id.btnTwo);
//        btnThree = (Button) findViewById(R.id.btnThree);
//        btnFour = (Button) findViewById(R.id.btnFour);
//        btnFive = (Button) findViewById(R.id.btnFive);
//        btnSix = (Button) findViewById(R.id.btnSix);
//        btnSeven = (Button) findViewById(R.id.btnSeven);
//        btnEight = (Button) findViewById(R.id.btnEight);
//        btnNine = (Button) findViewById(R.id.btnNine);
//        btnZero = (Button) findViewById(R.id.btnZero);
//        btnDot = (Button) findViewById(R.id.btnDot);
//
//        btnBackspace = (ImageButton) findViewById(R.id.btnBackSpace);
//        convert = (ImageButton) findViewById(R.id.convert);
//
//    }

    private void setListener(){

        btnOne.setOnClickListener(this);
        btnTwo.setOnClickListener(this);
        btnThree.setOnClickListener(this);
        btnFour.setOnClickListener(this);
        btnFive.setOnClickListener(this);
        btnSix.setOnClickListener(this);
        btnSeven.setOnClickListener(this);
        btnEight.setOnClickListener(this);
        btnNine.setOnClickListener(this);
        btnZero.setOnClickListener(this);
        btnDot.setOnClickListener(this);
        btnBackspace.setOnClickListener(this);
        convert.setOnClickListener(this);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.open_main, R.anim.close_next);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnOne:
                setValue("1");
                break;
            case R.id.btnTwo:
                setValue("2");
                break;
            case R.id.btnThree:
                setValue("3");
                break;
            case R.id.btnFour:
                setValue("4");
                break;
            case R.id.btnFive:
                setValue("5");
                break;
            case R.id.btnSix:
                setValue("6");
                break;
            case R.id.btnSeven:
                setValue("7");
                break;
            case R.id.btnEight:
                setValue("8");
                break;
            case R.id.btnNine:
                setValue("9");
                break;
            case R.id.btnZero:
                setValue("0");
                break;
            case R.id.btnBackSpace:
                onBackspace();
                break;
            case R.id.convert:
                setDirectionConvert();
                convertValues();
                break;
        }
    }

    private void convertValues() {

        ConverterPreference preference = new ConverterPreference(this);
        boolean directionConvert = preference.getConvertKgToLb();

        String valueStr = valueTop.getText().toString();
        valueStr = valueStr.substring(0, valueStr.length() - 3);

        if (directionConvert) {
            if (valueStr.equals("0")) {

                valueTop.setText("0 kg");
                valueBottom.setText("0 lb");
            }else{
                valueTop.setText(valueStr + " kg");
                valueBottom.setText( "" + Math.round(Long.valueOf(valueStr)/0.454) + " lb");
            }
        } else {
            if (valueStr.equals("0")) {

                valueTop.setText("0 lb");
                valueBottom.setText("0 kg");
            }else{
                valueTop.setText(valueStr + " lb");
                valueBottom.setText( "" + Math.round(Long.valueOf(valueStr)*0.454) + " kg");
            }
        }





    }

    private void setDirectionConvert(){

        ConverterPreference preference = new ConverterPreference(this);
        boolean directionConvert = preference.getConvertKgToLb();

        preference.setConvertKgToLb(!directionConvert);

    }

    private void onBackspace(){

        ConverterPreference preference = new ConverterPreference(this);
        boolean directionConvert = preference.getConvertKgToLb();

        String contentStr = valueTop.getText().toString();
        contentStr = contentStr.substring(0, contentStr.length() - 3);

        if(directionConvert) {
            if (contentStr.length() == 1) {
                valueTop.setText("0" + " kg");
                valueBottom.setText("0" + " lb");
            } else {
                contentStr = contentStr.substring(0, contentStr.length() - 1);
                valueTop.setText(contentStr + " kg");
                valueBottom.setText("" + Math.round(Long.valueOf(contentStr) / 0.454) + " lb");
            }
        }else{
            if (contentStr.length() == 1) {
                valueTop.setText("0" + " lb");
                valueBottom.setText("0" + " kg");
            } else {
                contentStr = contentStr.substring(0, contentStr.length() - 1);
                valueTop.setText(contentStr + " lb");
                valueBottom.setText("" + Math.round(Long.valueOf(contentStr) * 0.454) + " kg");
            }
        }

    }

    private void setValue(String value){

        ConverterPreference preference = new ConverterPreference(this);
        boolean directionConvert = preference.getConvertKgToLb();

        String contentStr = valueTop.getText().toString();

        if(contentStr.length()>9){
            return;
        }
        contentStr = contentStr.substring(0, contentStr.length() - 3);

        if(directionConvert) {
            if (contentStr.equals("0")) {
                valueTop.setText(value + " kg");
                valueBottom.setText("" + Math.round(Long.valueOf(value) / 0.454) + " lb");
            } else {
                long longValueTop = Long.valueOf(contentStr) * 10 + Long.valueOf(value);
                valueTop.setText(String.valueOf(longValueTop) + " kg");

                long longValueBottom = Math.round(longValueTop / 0.454);
                valueBottom.setText(String.valueOf(longValueBottom) + " lb");
            }
        }else{
            if (contentStr.equals("0")) {
                valueTop.setText(value + " lb");
                valueBottom.setText("" + Math.round(Long.valueOf(value) * 0.454) + " kg");
            } else {
                long longValueTop = Long.valueOf(contentStr) * 10 + Long.valueOf(value);
                valueTop.setText(String.valueOf(longValueTop) + " lb");

                long longValueBottom = Math.round(longValueTop * 0.454);
                valueBottom.setText(String.valueOf(longValueBottom) + " kg");
            }
        }

    }
}

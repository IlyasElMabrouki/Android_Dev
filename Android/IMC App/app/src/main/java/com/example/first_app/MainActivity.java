package com.example.first_app;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    Button b;
    EditText height;
    EditText width;
    ImageView resultImg;
    TextView resultVal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_first);
    }

    public void disable(View v){
        height = findViewById(R.id.textHeight);
        width = findViewById(R.id.textWidth);
        resultVal = findViewById(R.id.textView7);
        resultImg = (ImageView) findViewById(R.id.imageView2);
        Double result = calculerIMC(
                        Double.parseDouble(height.getText().toString()),
                        Double.parseDouble(width.getText().toString()));
        resultImg.setVisibility(View.VISIBLE);
        resultImg.setImageDrawable(getResult(result,this));
        resultVal.setText(result.toString(), TextView.BufferType.EDITABLE);
    }

    public double calculerIMC(double height, double width){
        double height_cm = height/100;
        double result = width / (height_cm * height_cm);
        String formattedValue = String.format("%.2f", result);
        return Double.parseDouble(formattedValue);
    }

    public Drawable getResult(double imc, Context context) {
        if (imc < 18.5) return ContextCompat.getDrawable(context, R.drawable.maigre);
        else if (imc >= 18.5 && imc <= 24.9) return ContextCompat.getDrawable(context, R.drawable.normal);
        else if (imc >= 30.0 && imc <= 34.9) return ContextCompat.getDrawable(context, R.drawable.obese);
        else if (imc >= 25.0 && imc <= 29.9) return ContextCompat.getDrawable(context, R.drawable.surpoids);
        else if (imc >= 35) return ContextCompat.getDrawable(context, R.drawable.t_obese);
        return null;
    }
}
package com.example.first_app;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    EditText number1Text;
    EditText number2Text;
    EditText resText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_first);
    }

    public void disable(View v){
        number1Text = findViewById(R.id.number1Text);
        number2Text = findViewById(R.id.number2Text);
        resText = findViewById(R.id.resText);

        Integer res = Integer.parseInt(number1Text.getText().toString()) + Integer.parseInt(number2Text.getText().toString());
        resText.setText(res.toString(), TextView.BufferType.EDITABLE);
    }

    public void reset(View v){
        number1Text.setText("");
        number2Text.setText("");
        resText.setText("");
        number1Text.requestFocus();
    }
}

package com.example.first_app;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    TextView resText;
    Button btn;

    String value1;
    String value2;
    char operator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_first);
    }

    public void addToView(View v){
        resText = findViewById(R.id.resText);
        btn = findViewById(v.getId());

        resText.setText(resText.getText().toString() + btn.getText().toString());
    }


    public void showResult(View v){
        boolean findOperator = false;
        for (int i=0;i<resText.getText().length();i++) {
            if (resText.getText().toString().charAt(i) != '+' && !findOperator){
                value1 = value1 + resText.getText().toString().charAt(i);
            }
            else if (resText.getText().toString().charAt(i) != '+' && findOperator){
                value1 = value1 + resText.getText().toString().charAt(i);
            }
            else {
                operator = resText.getText().toString().charAt(i);
                findOperator = true;
            }
        }
    }

    public Double calculate(String value1, String value2, char operator){
        if (operator == '+'){
            return Double.parseDouble(value1) + Double.parseDouble(value2);
        }
        else if (operator == '-'){
            return Double.parseDouble(value1) - Double.parseDouble(value2);
        }
        else if (operator == 'x'){
            return Double.parseDouble(value1) * Double.parseDouble(value2);
        }
        else {
            return Double.parseDouble(value1) / Double.parseDouble(value2);
        }
    }

    public void sup(View v){
        resText.setText(resText.getText().toString().substring(0,resText.length()-1));
    }

    public void reset(View v){
        resText.setText("");
        resText.requestFocus();
    }
}

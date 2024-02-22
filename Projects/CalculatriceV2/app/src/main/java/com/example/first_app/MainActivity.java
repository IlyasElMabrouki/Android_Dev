package com.example.first_app;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView tvResult;
    private StringBuilder expressionBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_first);

        // Initialize your views
        tvResult = findViewById(R.id.tvResult);
        expressionBuilder = new StringBuilder();
    }

    // Method to handle button clicks
    public void onButtonClick(View view) {
        Button button = (Button) view;
        String buttonText = button.getText().toString();

        // Append the clicked button text to the expression
        expressionBuilder.append(buttonText);
        updateDisplay();
    }

    public void evaluateExpression(View view) {
        String expression = expressionBuilder.toString();
        try {
            // Use the ExpressionEvaluator class to evaluate the expression
            double result = ExpressionEvaluator.evaluate(expression);

            // Display the result
            tvResult.setText(String.valueOf(result));
        } catch (ArithmeticException e) {
            // Handle arithmetic errors (e.g., division by zero)
            tvResult.setText("Error: " + e.getMessage());
        } catch (Exception e) {
            // Handle other exceptions
            tvResult.setText("Error");
            e.printStackTrace();
        }
    }

    // Method to update the display with the current expression
    private void updateDisplay() {
        tvResult.setText(expressionBuilder.toString());
    }

    // Method to clear the expression and result
    public void clear(View view) {
        expressionBuilder.setLength(0);
        updateDisplay();
    }

    // Method to delete the last character from the expression
    public void delete(View view) {
        if (expressionBuilder.length() > 0) {
            expressionBuilder.deleteCharAt(expressionBuilder.length() - 1);
            updateDisplay();
        }
    }
}
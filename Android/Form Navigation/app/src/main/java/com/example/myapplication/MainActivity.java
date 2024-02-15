package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void navigate(View v){
        TextView nameText = findViewById(R.id.nameText);
        String name = nameText.getText().toString().trim(); // Handle leading/trailing spaces

        Intent intent = new Intent(MainActivity.this, name.isEmpty() ? SecondActivity.class : ThirdActivity.class);

        if (!name.isEmpty()) {
            intent.putExtra("name", name);
        }

        try {
            nameText.setText("");
            startActivity(intent);
            //finish();
        } catch (ActivityNotFoundException e) {
            // Handle case where target activity is not found
            Toast.makeText(this, "Error: Activity not found!", Toast.LENGTH_SHORT).show();
        }
    }

}
package com.example.stockall;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

public class NiftyCompanies extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nifty_companies);

        Intent intent = getIntent();
        EditText result = findViewById(R.id.resultText);
        String[] value = intent.getStringArrayExtra("outputList");
        String output = "";
        for(String val : value)
        {
            output += val+"\n";
        }
        result.setText(output);
    }
}
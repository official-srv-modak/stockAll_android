package com.example.stockall;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class NiftyCompanies extends AppCompatActivity {

    public void init() {
        TableLayout ll = (TableLayout) findViewById(R.id.table);


        for (int i = 0; i < 2; i++) {

            TableRow row = new TableRow(this);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(lp);
            EditText e1 = new EditText(this);
            e1.setText("Name");
            View view1 = e1;

            EditText e2 = new EditText(this);
            e2.setText("Name");
            View view2 = e2;

            EditText e3 = new EditText(this);
            e3.setText("Name");
            View view3 = e3;

            row.addView(view1);
            row.addView(view2);
            row.addView(view3);
            ll.addView(row, i);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nifty_companies);

        init();
        /*Intent intent = getIntent();
        EditText result = findViewById(R.id.resultText);
        String[] value = intent.getStringArrayExtra("outputList");
        String output = "";
        for(String val : value)
        {
            output += val+"\n";
        }
        result.setText(output);*/
    }
}
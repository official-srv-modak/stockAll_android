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

    public void init(String [] value) {
        TableLayout ll = (TableLayout) findViewById(R.id.table);


        for (int i = 0; i < value.length; i++) {
            try {
                System.out.println(value[i]);
                TableRow row = new TableRow(this);
                TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                row.setLayoutParams(lp);
                EditText e1 = new EditText(this);
                String [] textStr = value[i].split(" SPACE ");
                e1.setText(textStr[0]);
                e1.setEnabled(false);
                View view1 = e1;

                EditText e2 = new EditText(this);
                e2.setText(textStr[1]);
                e2.setEnabled(false);
                View view2 = e2;

                EditText e3 = new EditText(this);
                e3.setText(textStr[2]);
                e3.setEnabled(false);
                View view3 = e3;

                row.addView(view1);
                row.addView(view2);
                row.addView(view3);
                ll.addView(row, i);
            }catch(ArrayIndexOutOfBoundsException e)
            {
                e.printStackTrace();
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nifty_companies);

        Intent intent = getIntent();
        String[] value = intent.getStringArrayExtra("outputList");
        init(value);
    }
}
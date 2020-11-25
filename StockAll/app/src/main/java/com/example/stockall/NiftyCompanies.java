package com.example.stockall;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;


public class NiftyCompanies extends AppCompatActivity {


    @SuppressLint("NewApi")
    public void init(String [] value) {
        TableLayout ll = (TableLayout) findViewById(R.id.table);

        for (int i = 0; i < value.length; i++) {
            try {
                System.out.println(value[i]);
                TableRow row = new TableRow(this);
                TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                row.setLayoutParams(lp);
                String [] textStr = value[i].split(" SPACE ");
                TextView view1 = new TextView(this);
                view1.setText(textStr[0]);
                view1.setForegroundGravity(Gravity.CENTER);

                View view2 = new View(this);
                if(i!=0)
                {
                    Button btn = new Button(this);
                    btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onTableRowClick(btn.getText().toString());
                        }
                    });

                    btn.setWidth(1100);
                    btn.setText(textStr[1]);
                    view2 = btn;
                }
                else{
                    TextView v = new TextView(this);
                    v.setText(textStr[1]);
                    v.setForegroundGravity(Gravity.CENTER);
                    v.setTypeface(null, Typeface.BOLD);
                    v.setGravity(Gravity.CENTER);
                    view2 = v;

                }

                TextView view3 = new TextView(this);
                view3.setText(textStr[3].split("SPACE")[0]);
                view3.setForegroundGravity(Gravity.CENTER);

                if(i==0)
                {
                    view1.setTypeface(null, Typeface.BOLD);

                    view3.setTypeface(null, Typeface.BOLD);

                    view1.setGravity(Gravity.CENTER);

                    view3.setGravity(Gravity.CENTER);
                }
                row.addView(view1);
                row.addView(view2);
                row.addView(view3);

                TableLayout.LayoutParams tableRowParams=
                        new TableLayout.LayoutParams
                                (TableLayout.LayoutParams.FILL_PARENT,TableLayout.LayoutParams.WRAP_CONTENT);

                int leftMargin=10;
                int topMargin=10;
                int rightMargin=15;
                int bottomMargin=10;

                tableRowParams.setMargins(leftMargin, topMargin, rightMargin, bottomMargin);

                row.setLayoutParams(tableRowParams);

                ll.addView(row);

            }catch(ArrayIndexOutOfBoundsException e)
            {
                e.printStackTrace();
            }
        }

    }

    public void getCompanies(String text)
    {
        Python py = Python.getInstance();
        final PyObject pyInput = py.getModule("search");
        PyObject pyOutput = pyInput.callAttr("do_search", text);

        String output [] = MainActivity.getOutputInList(pyOutput.toString());

        for(int i = 0; i<output.length; i++)
        {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(output[i]));
            startActivity(browserIntent);

        }
    }

    private void onTableRowClick(String text)
    {
        getCompanies(text);
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
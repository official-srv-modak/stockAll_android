package com.example.stockall;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

import java.io.File;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        String niftyCompanyFile = getApplicationContext().getFilesDir().getAbsolutePath() + "/nifty_companies.txt";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if(!Python.isStarted())
            Python.start(new AndroidPlatform((this)));

        Python py = Python.getInstance();
        final PyObject pyInput = py.getModule("search");

        EditText companyName = findViewById(R.id.companyNameText);


        Button companyNameButton = findViewById(R.id.goBtn);
        companyNameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCompanies("chrome", pyInput, companyName);

            }
        });
        Button niftyCompaniesButton = findViewById(R.id.niftyCompany);
        niftyCompaniesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayNiftyCompanies(pyInput, niftyCompanyFile);
            }
        });

    }
    public static String[] getOutputInList(String outputString)
    {
        String outputStr = outputString;
        String output [] = outputStr.split("NEXTLINE");

        for(int i = 0; i < output.length; i++)
            output[i] = output[i].trim();

        return output;
    }
    public void getCompanies(String appname, PyObject pyInput, EditText companyName)
    {
        PyObject pyOutput = pyInput.callAttr("do_search", companyName.getText());

        String output [] = getOutputInList(pyOutput.toString());



        for(int i = 0; i<output.length; i++)
        {
            /*String url = "http://google.com/";
            Intent intent = new Intent(Intent.ACTION_MAIN, null);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            intent.setComponent(new ComponentName("org.mozilla.firefox", "org.mozilla.firefox.App"));
            intent.setAction("org.mozilla.gecko.BOOKMARK");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("args", "--url=" + url);
            intent.setData(Uri.parse(url));
            startActivity(intent);*/
            /*try{
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("google.com"));
                intent.setComponent(new ComponentName("org.mozilla.firefox", "org.mozilla.firefox.App"));
                this.startActivity(intent);

            }catch(Exception e)
            {
                System.out.println(e.getMessage());
            }*/

            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(output[i]));
            startActivity(browserIntent);

        }
    }

    private boolean isPackageInstalled(String packageName, PackageManager packageManager) {
        try {
            packageManager.getPackageInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public static void writeToFile(String file_path, String[] contents_user)
    {
        String dateStr = "";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        dateStr = formatter.format(date);

        String firstIndex = "";
        for(String val : contents_user)
        {
            firstIndex += val + " NEXTLINE ";
        }
        List<String> contents = new ArrayList<String>(2);
        contents.add(dateStr);
        contents.add(firstIndex);

        File file = new File(file_path);
        if(!file.exists())
            try{
                file.createNewFile();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        String contentStr = "";
        contentStr = contents.get(0)+"\n"+contents.get(1);
        try{
            ObjectOutputStream objOut = new ObjectOutputStream(new FileOutputStream(file));
            objOut.writeObject(contentStr);
            objOut.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static List<String> readFromFile(String file_path)
    {
        File file = new File(file_path);
        List<String> output = new ArrayList<String>();

        if(!file.exists())
            try{
                file.createNewFile();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }

        try
        {
            ObjectInputStream objIn = new ObjectInputStream(new FileInputStream(file));
            String outputStr = (String)objIn.readObject();
            String [] cat = outputStr.split("\n");
            output = Arrays.asList(cat);
            objIn.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return output;
    }

    public void displayNiftyCompanies(PyObject pyInput, String niftyCompanyFile)
    {
        List<String> outputFromFile = readFromFile(niftyCompanyFile);
        if(outputFromFile.size()>0)
        {
            String dateStr1 = outputFromFile.get(0);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            String dateStr2 = formatter.format(date);
            if(dateStr1.equals(dateStr2))
            {
                try {
                    Date d1 = formatter.parse(dateStr1);
                    Date d2 = formatter.parse(dateStr2);
                    long difference_In_Time = d2.getTime() - d1.getTime();
                    long difference_In_Days = (difference_In_Time/ (1000 * 60 * 60 * 24))% 365;
                    if (difference_In_Days != 0)
                    {
                        PyObject pyOutput = pyInput.callAttr("get_nifty_companies");
                        String output [] = getOutputInList(pyOutput.toString());

                        writeToFile(niftyCompanyFile, output);
                        Intent myIntent = new Intent(this, NiftyCompanies.class);
                        myIntent.putExtra("outputList", output);
                        this.startActivity(myIntent);
                    }
                    else
                    {
                        String output [] = getOutputInList(outputFromFile.get(1));
                        Intent myIntent = new Intent(this, NiftyCompanies.class);
                        myIntent.putExtra("outputList", output);
                        this.startActivity(myIntent);
                    }
                }catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
        else
        {
            PyObject pyOutput = pyInput.callAttr("get_nifty_companies");
            String output [] = getOutputInList(pyOutput.toString());

            writeToFile(niftyCompanyFile, output);
            Intent myIntent = new Intent(this, NiftyCompanies.class);
            myIntent.putExtra("outputList", output);
            this.startActivity(myIntent);
        }
    }
}
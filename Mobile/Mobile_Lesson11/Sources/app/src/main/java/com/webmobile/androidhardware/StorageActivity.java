package com.webmobile.androidhardware;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class StorageActivity extends AppCompatActivity {
    EditText txt_content;
    EditText contenttoDisplay;
    String FILENAME = "MyAppStorage";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage);
        txt_content = (EditText) findViewById(R.id.id_txt_mycontent);
        contenttoDisplay = (EditText) findViewById(R.id.id_txt_display);
    }



    public void saveTofile(View v) throws IOException {

        // ICP Task4: Write the code to save the text
        FileOutputStream fos = openFileOutput(FILENAME, Context. MODE_APPEND);
        String msg = String.valueOf(txt_content.getText());
        fos.write(msg.getBytes());
        fos.close();
    }


    public void retrieveFromFile(View v) throws IOException {

        // ICP Task4: Write the code to display the above saved text
        FileInputStream fis = openFileInput(FILENAME);
        InputStreamReader inputStreamReader = new InputStreamReader(fis);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String receiveString= "";
        StringBuilder stringBuilder = new StringBuilder();
        while((receiveString = bufferedReader.readLine()) != null){
            stringBuilder.append(receiveString);
        }
        fis.close();
        String ret = stringBuilder.toString();
        contenttoDisplay.setText(ret);
        contenttoDisplay.setVisibility(View.VISIBLE);

    }

}
package com.webmobile.icp13;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    TextToSpeech textToSpeech;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pref = getSharedPreferences("preferences", 0);
        editor = pref.edit();

        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                textToSpeech.setLanguage(Locale.US);
                toSpeech("Hello");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100 && resultCode == RESULT_OK && null != data) {
            ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            ((TextView)findViewById(R.id.voiceInput)).setText(result.get(0));
            speechHandler(result.get(0).toLowerCase());
        }
    }

    public void startVoiceInput(View view) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hello, How can I help you?");
        try {
            startActivityForResult(intent, 100);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(this, "Unable to start voice input", Toast.LENGTH_SHORT);
        }
    }

    private void toSpeech(String phrase) {
        textToSpeech.speak(phrase, TextToSpeech.QUEUE_FLUSH, null);
    }

    private void speechHandler(String phrase) {
        if(phrase.contains("hello")) {
            toSpeech("what is your name");
        } else if(phrase.contains("my name is")) {
            String[] words = phrase.split(" ");
            String name = words[words.length - 1];
            editor.putString("NAME", name).apply();
            toSpeech("Hello, " + name);
        } else if(phrase.contains("i am not feeling good what should i do") ||
                  phrase.contains("i'm not feeling good what should i do")) {
            toSpeech("I can understand. Please tell your symptoms in short.");
        } else if(phrase.contains("thank you my medical assistant")) {
            toSpeech("Thank you too, " + pref.getString("NAME", null) + ". Take care");
        } else if(phrase.contains("what time is it")) {
            SimpleDateFormat sdfDate = new SimpleDateFormat("hh:mm:a");//dd/MM/yyyy
            Date now = new Date();
            String[] strDate = sdfDate.format(now).split(":");
            if(strDate[1].contains("00"))
                strDate[1] = "o'clock";
            Log.e("Time", "The time is " + TextUtils.join(" ", strDate));
            toSpeech("The time is " + TextUtils.join(" ", strDate));

        } else if(phrase.contains("what medicines should i take") ||
                  phrase.contains("what medicine should i take")) {
            toSpeech("I think you have a fever. Please take this medicine.");
        }
    }
}
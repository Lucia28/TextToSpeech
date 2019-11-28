package com.example.texttospeech;

import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextToSpeech tts;
    private EditText edit;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edit = (EditText) findViewById(R.id.edit);
        button = (Button) findViewById(R.id.button);

        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == tts.SUCCESS) {
                    int result = tts.setLanguage(Locale.CHINESE);
                    if (result != TextToSpeech.LANG_COUNTRY_AVAILABLE
                            && result != TextToSpeech.LANG_AVAILABLE) {
                        Toast.makeText(MainActivity.this, "TTS暫時不支持這種語音的朗讀！", Toast.LENGTH_SHORT).show();
                    }
                } else
                    Log.e("TTS", "Initilization Failed!");
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tts != null && !tts.isSpeaking()) {
                    tts.setPitch(1);    //語調(1為正常語調；0.5比正常語調低一倍；2比正常語調高一倍)
                    tts.setSpeechRate(1);    //速度(1為正常速度；0.5比正常速度慢一倍；2比正常速度快一倍)
                    tts.speak(edit.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);     //發音
                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        tts.stop();
        tts.shutdown();;
    }
}

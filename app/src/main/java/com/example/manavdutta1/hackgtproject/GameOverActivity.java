package com.example.manavdutta1.hackgtproject;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

public class GameOverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
//        AssetManager am = context.getApplicationContext().getAssets();
//
//        Typeface typeface = Typeface.createFromAsset(am,
//                String.format(Locale.US, "fonts/%s", "angrybirds-regular.ttf"));
//
//        setTypeface(typeface);
//        TextView headerValue = (TextView) findViewById(R.id.textView5);
//        headerValue.setText( "" + savedInstanceState.get("EXTRA_SCORE"));
        setContentView(R.layout.activity_game_over);

    }

    public void retry(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void quit(View v) {
        finish();
    }
}

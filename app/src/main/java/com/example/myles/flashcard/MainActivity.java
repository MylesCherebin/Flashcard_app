package com.example.myles.flashcard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.card_question).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findViewById(R.id.card_question).setVisibility(View.INVISIBLE);
                findViewById(R.id.card_answer).setVisibility(View.VISIBLE);
            }
        });

        findViewById(R.id.card_answer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findViewById(R.id.card_answer).setVisibility(View.INVISIBLE);
                findViewById(R.id.card_question).setVisibility(View.VISIBLE);
            }
        });
    }
}

package com.example.myles.flashcard;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

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

        findViewById(R.id.add_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddCardActivity.class);
                MainActivity.this.startActivityForResult(intent, 100);

            }
        });

        findViewById(R.id.edit_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddCardActivity.class);
                intent.putExtra("question",
                        ((TextView) findViewById(R.id.card_question)).getText());
                intent.putExtra("answer",
                        ((TextView) findViewById(R.id.card_answer)).getText());
                MainActivity.this.startActivityForResult(intent, 100);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == 100){
            if (data != null) {
                String new_q = data.getExtras().getString("question");
                String new_a = data.getExtras().getString("answer");

                ((TextView) findViewById(R.id.card_question)).setText(new_q);
                ((TextView) findViewById(R.id.card_answer)).setText(new_a);

                Snackbar.make(findViewById(R.id.card_question),
                        "New Flashcard Created", Snackbar.LENGTH_SHORT).show();
            }
        }



    }
}

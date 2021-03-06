package com.example.myles.flashcard;

import android.animation.Animator;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.example.myles.flashcard.FlashcardDatabase;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    FlashcardDatabase flashcardDatabase;
    List<Flashcard> allFlashcards;
    int currentCardDisplayedIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        flashcardDatabase = new FlashcardDatabase(getApplicationContext());
        allFlashcards = flashcardDatabase.getAllCards();

        if (allFlashcards != null && allFlashcards.size() > 0){
            ((TextView) findViewById(R.id.card_question)).setText(allFlashcards.get(0).getQuestion());
            ((TextView) findViewById(R.id.card_answer)).setText(allFlashcards.get(0).getAnswer());
        }

        findViewById(R.id.card_question).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View answerSideView = findViewById(R.id.card_answer);

// get the center for the clipping circle
                int cx = answerSideView.getWidth() / 2;
                int cy = answerSideView.getHeight() / 2;

// get the final radius for the clipping circle
                float finalRadius = (float) Math.hypot(cx, cy);

// create the animator for this view (the start radius is zero)
                Animator anim = ViewAnimationUtils.createCircularReveal(answerSideView, cx, cy, 0f, finalRadius);

// hide the question and show the answer to prepare for playing the animation!
                findViewById(R.id.card_question).setVisibility(View.INVISIBLE);
                answerSideView.setVisibility(View.VISIBLE);

                anim.setDuration(3000);
                anim.start();
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
                overridePendingTransition(R.anim.right_in, R.anim.left_out);

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


        findViewById(R.id.next_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Animation leftOutAnim = AnimationUtils.loadAnimation(view.getContext(), R.anim.left_out);
                final Animation rightInAnim = AnimationUtils.loadAnimation(view.getContext(), R.anim.right_in);

                leftOutAnim.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        // this method is called when the animation first starts
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        // this method is called when the animation is finished playing
                        ((TextView) findViewById(R.id.card_question)).setText(allFlashcards.get(currentCardDisplayedIndex).getQuestion());
                        ((TextView) findViewById(R.id.card_answer)).setText(allFlashcards.get(currentCardDisplayedIndex).getAnswer());
                        findViewById(R.id.card_question).startAnimation(rightInAnim);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                        // we don't need to worry about this method
                    }
                });

                currentCardDisplayedIndex++;

                if (currentCardDisplayedIndex >= allFlashcards.size()) {
                    currentCardDisplayedIndex = 0;

                }


                findViewById(R.id.card_answer).setVisibility(View.INVISIBLE);
                findViewById(R.id.card_question).setVisibility(View.VISIBLE);

                findViewById(R.id.card_question).startAnimation(leftOutAnim);


            }
        });
        findViewById(R.id.delete_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentCardDisplayedIndex == 0 && allFlashcards.size() == 1){
                    flashcardDatabase.deleteCard(((TextView) findViewById(R.id.card_question)).getText().toString());

                    allFlashcards = flashcardDatabase.getAllCards();

                    ((TextView) findViewById(R.id.card_question)).setText("No Flashcards, Please Add Some :)");
                    ((TextView) findViewById(R.id.card_answer)).setText("");
                    findViewById(R.id.card_answer).setVisibility(View.INVISIBLE);
                    findViewById(R.id.card_question).setVisibility(View.VISIBLE);


                }

                else if (currentCardDisplayedIndex == allFlashcards.size() - 1){
                    currentCardDisplayedIndex = currentCardDisplayedIndex - 1;

                    flashcardDatabase.deleteCard(((TextView) findViewById(R.id.card_question)).getText().toString());

                    allFlashcards = flashcardDatabase.getAllCards();

                    ((TextView) findViewById(R.id.card_question)).setText(allFlashcards.get(currentCardDisplayedIndex).getQuestion());
                    ((TextView) findViewById(R.id.card_answer)).setText(allFlashcards.get(currentCardDisplayedIndex).getAnswer());
                    findViewById(R.id.card_answer).setVisibility(View.INVISIBLE);
                    findViewById(R.id.card_question).setVisibility(View.VISIBLE);
                }
                else{
                    flashcardDatabase.deleteCard(((TextView) findViewById(R.id.card_question)).getText().toString());

                    allFlashcards = flashcardDatabase.getAllCards();

                     ((TextView) findViewById(R.id.card_question)).setText(allFlashcards.get(currentCardDisplayedIndex).getQuestion());
                    ((TextView) findViewById(R.id.card_answer)).setText(allFlashcards.get(currentCardDisplayedIndex).getAnswer());
                    findViewById(R.id.card_answer).setVisibility(View.INVISIBLE);
                    findViewById(R.id.card_question).setVisibility(View.VISIBLE);
                }


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

                flashcardDatabase.insertCard(new Flashcard(new_q, new_a));


            }
        }


    }
}

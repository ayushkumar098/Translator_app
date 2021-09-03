package com.example.miwok;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class PhrasesActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        final ArrayList<Word> words = new ArrayList<Word>();
        words.add(new Word("Are you coming" , "lutti",R.raw.phrase_are_you_coming));
        words.add(new Word("Come here", "gdshjk",R.raw.phrase_come_here));
        words.add(new Word("How are you feeling", "yghb",R.raw.phrase_how_are_you_feeling));
        words.add(new Word("Im coming","tjyfc",R.raw.phrase_im_coming));
        words.add(new Word("Im feeling good","sdSDF",R.raw.phrase_im_feeling_good));
        words.add(new Word("Lets go","jhmcfg",R.raw.phrase_lets_go));
        words.add(new Word("My name is","awges",R.raw.phrase_my_name_is));
        words.add(new Word("What is your name","awges",R.raw.phrase_what_is_your_name));
        words.add(new Word("Where are you going","awges",R.raw.phrase_where_are_you_going));
        words.add(new Word("Yes im coming","awges",R.raw.phrase_yes_im_coming));

        WordAdapter adapter = new WordAdapter(this, words,R.color.category_phrases);

        ListView listView = findViewById(R.id.list);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Word word = words.get(position);
                releaseMediaPlayer();
                mediaPlayer = MediaPlayer.create(PhrasesActivity.this, word.getAudioResourceId());
                mediaPlayer.start();
                mediaPlayer.setOnCompletionListener(mCompletionListener);
            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }
    
    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mediaPlayer = null;
        }
    }
}
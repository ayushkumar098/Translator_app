package com.example.miwok;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class ColorActivity extends AppCompatActivity {

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
        words.add(new Word("Red" , "lutti",R.drawable.color_red,R.raw.color_red));
        words.add(new Word("Skin", "gdshjk",R.drawable.color_dusty_yellow,R.raw.color_mustard_yellow));
        words.add(new Word("Green", "yghb",R.drawable.color_green,R.raw.color_green));
        words.add(new Word("Grey","tjyfc",R.drawable.color_gray,R.raw.color_gray));
        words.add(new Word("Yellow","sdSDF",R.drawable.color_mustard_yellow,R.raw.color_dusty_yellow));
        words.add(new Word("Black","jhmcfg",R.drawable.color_black,R.raw.color_black));
        words.add(new Word("Brown","awges",R.drawable.color_brown,R.raw.color_brown));
        words.add(new Word("White","awges",R.drawable.color_white,R.raw.color_white));

        WordAdapter adapter = new WordAdapter(this, words, R.color.category_colors);

        ListView listView = findViewById(R.id.list);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Word word = words.get(position);
                releaseMediaPlayer();
                mediaPlayer = MediaPlayer.create(ColorActivity.this, word.getAudioResourceId());
                mediaPlayer.start();
                mediaPlayer.setOnCompletionListener(mCompletionListener);
            }
        });
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
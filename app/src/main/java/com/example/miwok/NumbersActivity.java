package com.example.miwok;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioFocusRequest;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class NumbersActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;

    private AudioManager mAudioManager;

    AudioAttributes playbackAttributes;

    final Object focusLock = new Object();

    boolean playbackDelayed = false;
    boolean playbackNowAuthorized = false;
    boolean resumeOnFocusGain;

    AudioManager.OnAudioFocusChangeListener audioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            switch (focusChange) {
                case AudioManager.AUDIOFOCUS_GAIN:
                    if (playbackDelayed || resumeOnFocusGain) {
                        synchronized (focusLock) {
                            playbackDelayed = false;
                            resumeOnFocusGain = false;
                        }

                        mediaPlayer.start();
                    }
                    break;
                case AudioManager.AUDIOFOCUS_LOSS:
                    synchronized (focusLock) {
                        resumeOnFocusGain = false;
                        playbackDelayed = false;
                    }
                    mediaPlayer.pause();
                    break;
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                    synchronized (focusLock) {
                        // only resume if playback is being interrupted
                        resumeOnFocusGain = mediaPlayer.isPlaying();
                        playbackDelayed = false;
                    }
                    mediaPlayer.pause();
                    break;
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                    // ... pausing or ducking depends on your app
                    break;
            }
        }
    };

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

        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        playbackAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build();
        AudioFocusRequest focusRequest = new AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN)
                .setAudioAttributes(playbackAttributes)
                .setAcceptsDelayedFocusGain(true)
                .setOnAudioFocusChangeListener(audioFocusChangeListener)
                .build();





        int res = mAudioManager.requestAudioFocus(focusRequest);


        final ArrayList<Word> words = new ArrayList<Word>();
        words.add(new Word("One" , "lutti",R.drawable.number_one,R.raw.number_one));
        words.add(new Word("Two", "gdshjk",R.drawable.number_two,R.raw.number_two));
        words.add(new Word("Three", "yghb",R.drawable.number_three,R.raw.number_three));
        words.add(new Word("Four","tjyfc",R.drawable.number_four,R.raw.number_four));
        words.add(new Word("Five","sdSDF",R.drawable.number_five,R.raw.number_five));
        words.add(new Word("Six","jhmcfg",R.drawable.number_six,R.raw.number_six));
        words.add(new Word("Seven","awges",R.drawable.number_seven,R.raw.number_seven));
        words.add(new Word("Eight","jhmv",R.drawable.number_eight,R.raw.number_eight));
        words.add(new Word("Nine","rseg",R.drawable.number_nine,R.raw.number_nine));
        words.add(new Word("Ten","zdvdb",R.drawable.number_ten,R.raw.number_ten));


        WordAdapter adapter = new WordAdapter(this, words,R.color.category_numbers);

        ListView listView = findViewById(R.id.list);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Word word = words.get(position);
                releaseMediaPlayer();

                synchronized(focusLock) {
                    if (res == AudioManager.AUDIOFOCUS_REQUEST_FAILED) {
                        playbackNowAuthorized = false;
                    } else if (res == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                        playbackNowAuthorized = true;
                        mediaPlayer = MediaPlayer.create(NumbersActivity.this, word.getAudioResourceId());
                        mediaPlayer.start();
                        mediaPlayer.setOnCompletionListener(mCompletionListener);
                    } else if (res == AudioManager.AUDIOFOCUS_REQUEST_DELAYED) {
                        playbackDelayed = true;
                        playbackNowAuthorized = false;
                    }
                }


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


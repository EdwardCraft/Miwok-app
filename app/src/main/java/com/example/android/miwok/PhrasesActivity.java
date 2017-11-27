/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class PhrasesActivity extends AppCompatActivity {
    private static final String TAG = PhrasesActivity.class.getName();

    private ArrayList<Word> phrases;
    private WordAdapter phrasesAdapter;
    private MediaPlayer audio;
    private AudioManager mAudioManager;

    private AudioManager.OnAudioFocusChangeListener mAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if(audio != null){
                if(focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK){
                    //The AUDIOFOCUS_LOSS_TRANSIENT case means we've lost audio focus for a
                    //short amount of time. The AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK case means that
                    //our app is allowed to continue playing sound but at lower volume. We'll treat
                    //both cases the same way because our app is playing short sound files.

                    //Pause playback and reset player to the star of the file. That way, we can
                    //play te word from the  beginning when we resume playback
                    audio.pause();
                    audio.seekTo(0);
                }else if(focusChange == AudioManager.AUDIOFOCUS_GAIN){
                    //The AUDIOFOCUS_GAIN case means  we have regained focus and can resume playback
                    audio.start();
                }else if(focusChange == AudioManager.AUDIOFOCUS_LOSS){
                    //The AUDIOFOCUS_LOSS case means we've lost audio focus and
                    //stop playback and clean up resources
                    audio = Utils.releaseMediaPlayer(audio);
                    mAudioManager = Utils.abandonFocus(mAudioManager, mAudioFocusChangeListener);
                }
            }
        }
    };


    private MediaPlayer.OnCompletionListener mOnCompletionListener = new MediaPlayer.OnCompletionListener(){
        @Override
        public void onCompletion(MediaPlayer mp) {
            //Now that  the sound  file has finished playing, release the media player  resources
            audio = Utils.releaseMediaPlayer(audio);
            mAudioManager = Utils.abandonFocus(mAudioManager, mAudioFocusChangeListener);

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phrases);
        mAudioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);

        setPhrases();
        setUpAdapter();
    }

    private void setPhrases(){
        phrases = new ArrayList<>();
        phrases.add(new Word("Where ara you going?","minto wuksus", Utils.NO_DATA,  R.raw.phrase_where_are_you_going));
        phrases.add(new Word("What is your name?","tinnә oyaase'nә", Utils.NO_DATA,  R.raw.phrase_what_is_your_name));
        phrases.add(new Word("My name is...","oyaaset...", Utils.NO_DATA, R.raw.phrase_my_name_is));
        phrases.add(new Word("How are you feeling?","michәksәs?", Utils.NO_DATA, R.raw.phrase_how_are_you_feeling));
        phrases.add(new Word("I’m feeling good.","kuchi achit", Utils.NO_DATA, R.raw.phrase_im_feeling_good));
        phrases.add(new Word("Are you coming?","әәnәs'aa?", Utils.NO_DATA, R.raw.phrase_are_you_coming));
        phrases.add(new Word("Yes, I’m coming.","hәә’ әәnәm", Utils.NO_DATA, R.raw.phrase_yes_im_coming));
        phrases.add(new Word("I’m coming.","әәnәm", Utils.NO_DATA, R.raw.phrase_im_coming));
        phrases.add(new Word("Come here.","әnni'nem", Utils.NO_DATA,  R.raw.phrase_come_here));

    }

    private void setUpAdapter(){
        phrasesAdapter = new WordAdapter(this, phrases);
        ListView listView = (ListView)findViewById(R.id.phrases_list);
        if(listView != null){
            listView.setAdapter(phrasesAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    audio = Utils.releaseMediaPlayer(audio);
                    mAudioManager = Utils.abandonFocus(mAudioManager, mAudioFocusChangeListener);
                    int result = mAudioManager.requestAudioFocus(mAudioFocusChangeListener,
                            AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                    if(result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED){
                        // We have audio focus  now.
                        //Create and setuo the MediaPlayer for the audio  resource associated
                        //with the current word
                        audio = MediaPlayer.create(PhrasesActivity.this, phrases.get(position).getAudioId());
                        audio.start();
                        audio.setOnCompletionListener(mOnCompletionListener);
                    }
                }
            });
        }
    }


    @Override
    protected void onStart(){
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        audio =  Utils.releaseMediaPlayer(audio);
        mAudioManager = Utils.abandonFocus(mAudioManager, mAudioFocusChangeListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

}

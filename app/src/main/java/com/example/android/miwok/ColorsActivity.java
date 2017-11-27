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

public class ColorsActivity extends AppCompatActivity {


    private ArrayList<Word> colorList;
    private WordAdapter colorsAdapter;


    private MediaPlayer audio;

    //Handles audio focus when playing a sound file
    private AudioManager mAudioManager;


    /*
    * This listener gets triggered whenever the audio focus changes
    * (i.e we gain or lose audio focus because of another app or device)
    * */

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
                    //mAudioManager = Utils.abandonFocus(mAudioManager, mAudioFocusChangeListener);
                }
            }
        }
    };

    /*
    * This listener gets triggered when the MediaPlayer hsa completed
    * playing the audio file
    * */

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
        setContentView(R.layout.activity_colors);

        mAudioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);

        setColor();
        setUpAdapter();


    }

    private void setColor(){
        colorList = new ArrayList<>();
        colorList.add(new Word("red", "wetetti", R.drawable.color_red, R.raw.color_red));
        colorList.add(new Word("green", "chokokki", R.drawable.color_green, R.raw.color_green));
        colorList.add(new Word("brown", "takaakki", R.drawable.color_brown, R.raw.color_brown));
        colorList.add(new Word("gray", "topoppi", R.drawable.color_gray, R.raw.color_gray));
        colorList.add(new Word("black", "kululli", R.drawable.color_black, R.raw.color_black));
        colorList.add(new Word("white", "kelelli", R.drawable.color_white, R.raw.color_white));
        colorList.add(new Word("dusty yellow", "topisa", R.drawable.color_dusty_yellow, R.raw.color_dusty_yellow));
        colorList.add(new Word("mustard yellow", "chiwiita", R.drawable.color_mustard_yellow, R.raw.color_mustard_yellow));



    }

    private void setUpAdapter(){
        colorsAdapter = new WordAdapter(this, colorList);
        ListView listView =  (ListView) findViewById(R.id.color_list);

        if(listView != null){
            listView.setAdapter(colorsAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    //Release the media player if it currently exits because we are about to
                    //play a different sound file
                    audio = Utils.releaseMediaPlayer(audio);
                    mAudioManager = Utils.abandonFocus(mAudioManager, mAudioFocusChangeListener);
                    //Request audio focus so in order to play the audio  file. The app need to play a
                    //short audio file, so we will request audio focus with a short  amount of time
                    //with AUDIOFOCUS_GAIN_TRANSIENT
                    int result = mAudioManager.requestAudioFocus(mAudioFocusChangeListener,
                            AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                    if(result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED){
                        // We have audio focus  now.
                        //Create and setuo the MediaPlayer for the audio  resource associated
                        //with the current word
                        audio = MediaPlayer.create(ColorsActivity.this, colorList.get(position).getAudioId());
                        audio.start();
                        audio.setOnCompletionListener(mOnCompletionListener);
                    }
                }
            });
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        audio = Utils.releaseMediaPlayer(audio);
        mAudioManager = Utils.abandonFocus(mAudioManager, mAudioFocusChangeListener);
    }









}

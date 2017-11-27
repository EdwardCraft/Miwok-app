package com.example.android.miwok;

import android.media.AudioManager;
import android.media.MediaPlayer;

/**
 * Created by PEDRO on 19/09/2017.
 */
public class Utils {

    public static final int NO_DATA = -1;
    public static final String AUDIO_END_MESSAGE = "audio finish!";

    //Fragments
    public static final int NUMBERS_OF_FRAGMENTS = 4;

    public static final int NUMBERS_FRAGMENT = 0;
    public static final int FAMILY_FRAGMENT = 1;
    public static final int COLORS_FRAGMENT = 2;
    public static final int PHRASES_FRAGMENT = 3;

    public static final String NUMBER_FRAGMENT_NAME = "NumbersFragment";
    public static final String COLORS_FRAGMENT_NAME = "ColorsFragment";
    public static final String FAMILY_FRAGMENT_NAME = "FamilyFragment";
    public static final String PHRASES_FRAGMENT_NAME = "PhrasesFragment";



    public static MediaPlayer releaseMediaPlayer(MediaPlayer audio){
        //if the media player is not null  , then is currently playing a sound
        if(audio != null){
            //Regardless if the current state of the media player , release its resources
            //because we no longer nee it.
            audio.release();
            // set the media  player back to null. For our code, we've decided that
            //setting the media player to null is an easy way to tell that the media player
            //is not configured to play an audio file at the moment.
            audio = null;

        }

        return audio;
    }

    public static AudioManager abandonFocus(AudioManager audioManager,
                                            AudioManager.OnAudioFocusChangeListener focusListener){
        //Regardless of whether or not we ware granted audio focus, abandon it. This also
        //unregisters the AudioFocusChangeListener so we don't take anymore callbacks
        if(audioManager != null && focusListener != null)audioManager.abandonAudioFocus(focusListener);

        return audioManager;

    }


}

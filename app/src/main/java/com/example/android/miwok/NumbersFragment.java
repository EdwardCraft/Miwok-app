package com.example.android.miwok;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class NumbersFragment extends Fragment {
    public static final String TAG = NumbersFragment.class.getSimpleName();

    private WordAdapter numbersAdapter;
    private ArrayList<Word> numbers;
    private MediaPlayer audio;
    private AudioManager mAudioManager;

    private AudioManager.OnAudioFocusChangeListener mAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if(audio != null){
                if(focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT
                        || focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK){
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




    public NumbersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.word_list, container, false);
        mAudioManager = (AudioManager)getActivity().getSystemService(Context.AUDIO_SERVICE);
        setUpNumbers();
        setUpArrayAdapter(rootView);


        return rootView;
    }




    private void setUpArrayAdapter(View rootView){
         /*
        * The adapter knows how to create layouts for each item in the list,
        * using the simple_list_create_item_1.xml layout resource defined in
        * the Android framework.
        *
        * This list item  layout contains  a simple (@Link TexView), which the
        * adapter will set to display a single word.
        * */

        numbersAdapter = new WordAdapter(getActivity(), numbers, Utils.NUMBER_FRAGMENT_NAME);
        ListView listView =  (ListView) rootView.findViewById(R.id.list);

        /*
        * Make the ListView use the ArrayAdapter  we created above, so that the
        * ListView will display list item for each word in the list on words.
        * To this by calling the setAdapter method on the ListView with the variable
        * name itemAdapter.
        * */

        if(listView != null){
            listView.setAdapter(numbersAdapter);
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
                        audio = MediaPlayer.create(getActivity(), numbers.get(position).getAudioId());
                        audio.start();
                        audio.setOnCompletionListener(mOnCompletionListener);
                    }
                }
            });


        }

    }


    private void setUpNumbers(){
        numbers = new ArrayList<>();
        numbers.add(new Word("one", "lutti",R.drawable.number_one, R.raw.number_one));
        numbers.add(new Word("two", "otiiko", R.drawable.number_two, R.raw.number_two));
        numbers.add(new Word("three", "tolookosu", R.drawable.number_three, R.raw.number_three));
        numbers.add(new Word("four", "oyyisa", R.drawable.number_four, R.raw.number_four));
        numbers.add(new Word("five", "massokka",R.drawable.number_five,R.raw.number_five));
        numbers.add(new Word("six", "temmokka", R.drawable.number_six, R.raw.number_six));
        numbers.add(new Word("seven", "kenekaku", R.drawable.number_seven, R.raw.number_seven));
        numbers.add(new Word("eight", "kawinta", R.drawable.number_eight, R.raw.number_eight));
        numbers.add(new Word("nine", "wo'e", R.drawable.number_nine, R.raw.number_nine));
        numbers.add(new Word("ten","na aacha", R.drawable.number_ten, R.raw.number_ten));


    }


    @Override
    public void onStop() {
        super.onStop();
        audio = Utils.releaseMediaPlayer(audio);
        mAudioManager = Utils.abandonFocus(mAudioManager, mAudioFocusChangeListener);
    }



}

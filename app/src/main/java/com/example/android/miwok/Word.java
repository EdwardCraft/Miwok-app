package com.example.android.miwok;

/**
 * Created by PEDRO on 14/09/2017.
 */
public class Word {
    private static final int NO_DATA = -1;


    //Translation for the word
    private String mDefaultTranslation;

    //Miwok translation for the word
    private String mMiwokTranslation;

    private int mImageResourceId;

    private int mAudioId;

    public Word(String mDefaultTranslation, String mMiwokTranslation){
        this.mDefaultTranslation = mDefaultTranslation;
        this.mMiwokTranslation = mMiwokTranslation;
        this.mImageResourceId = NO_DATA;
        this.mAudioId = NO_DATA;
    }

    public Word(String mDefaultTranslation, String mMiwokTranslation, int mImageResourceId){
        this.mDefaultTranslation = mDefaultTranslation;
        this.mMiwokTranslation = mMiwokTranslation;
        this.mImageResourceId = mImageResourceId;
        this.mAudioId = NO_DATA;
    }


    public Word(String mDefaultTranslation, String mMiwokTranslation, int mImageResourceId, int mAudioId){
        this.mDefaultTranslation = mDefaultTranslation;
        this.mMiwokTranslation = mMiwokTranslation;
        this.mImageResourceId = mImageResourceId;
        this.mAudioId = mAudioId;
    }

    /*
    * Get the default translation of the word
    * */
    public String getDefaultTranslation(){
        return mDefaultTranslation;
    }

    /*
    * Miwok translation of the word
    * */
    public String getMiwokTranslation(){
        return mMiwokTranslation;
    }


    public int getImageResourceId(){return mImageResourceId;}

    public int getAudioId(){return mAudioId;}

    public boolean hasImage(){
        return mImageResourceId != NO_DATA;
    }

    public boolean hasAudio(){return mAudioId != NO_DATA;}




}

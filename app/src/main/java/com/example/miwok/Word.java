package com.example.miwok;

public class Word {

    private String mDefTran;
    private String mMiwokTran;
    private int mImageResourceId = flag;
    private static final int flag = -1;

    private int mAudioResId;


    public Word(String defTran, String miwokTran, int audioResId){
        mDefTran = defTran;
        mMiwokTran = miwokTran;
        mAudioResId = audioResId;
    }

    public Word(String defTran, String miwokTran,int imageResourceId, int audioResId){
        mDefTran = defTran;
        mMiwokTran = miwokTran;
        mImageResourceId = imageResourceId;
        mAudioResId = audioResId;
    }

    public String getDefaultTranslation() {
        return mDefTran;
    }

    public String getMiwokTranslation() {
        return mMiwokTran;
    }

    public int getImageResourceId(){
        return mImageResourceId;
    }

    public boolean check(){
        return mImageResourceId != flag;
    }

    public int getAudioResourceId(){
        return mAudioResId;
    }
}

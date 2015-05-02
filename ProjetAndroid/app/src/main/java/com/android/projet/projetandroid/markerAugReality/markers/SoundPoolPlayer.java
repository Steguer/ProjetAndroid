package com.android.projet.projetandroid.markerAugReality.markers;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import com.android.projet.projetandroid.R;

import java.util.HashMap;

/**
 * Created by Adrien on 02/05/2015.
 */
public class SoundPoolPlayer {
    private SoundPool mShortPlayer= null;
    private HashMap mSounds = new HashMap();

    public SoundPoolPlayer(Context pContext)
    {
        // setup Soundpool
        this.mShortPlayer = new SoundPool(4, AudioManager.STREAM_MUSIC, 0);


        mSounds.put(R.raw.coin, this.mShortPlayer.load(pContext, R.raw.coin, 1));
        mSounds.put(R.raw.highjump, this.mShortPlayer.load(pContext, R.raw.highjump, 1));
    }

    public void playShortResource(int piResource) {
        int iSoundId = (Integer) mSounds.get(piResource);
        this.mShortPlayer.play(iSoundId, 0.99f, 0.99f, 0, 0, 1);
    }

    // Cleanup
    public void release() {
        // Cleanup
        this.mShortPlayer.release();
        this.mShortPlayer = null;
    }
}

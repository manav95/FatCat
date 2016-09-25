package com.example.manavdutta1.hackgtproject;

import android.media.AudioManager;
import android.media.SoundPool;

/**
 * Created by manavdutta1 on 9/25/16.
 */
public class SoundStuff {
    SoundPool sp = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
    /** soundId for Later handling of sound pool **/
    //Title sound
    //int titleId = sp.load(this.context, R.raw.title_cat_meow, 2);
    // Background music
    //int backgroundId = sp.load(this., R.raw.background_game_music, 3);
    // Garbage
    //int garbageId = sp.load(this.context, R.raw.sad_cat_meow, 1);
    // Food
    //int foodId1 = sp.load(this.context, R.raw.food_get_meow, 1);
    //int foodId2 = sp.load(this.context, R.raw.food_get_meow2, 1);
    // Loss
    //int lossId = sp.load(this.context, R.raw.cat_lost_hiss, 2);

    // Loop title
    //sp.play(titleId, 1, 1, 0, 1, 1);

    // Loop Background music
    //sp.play(backgroundId, 1, 1, 0, 1, 1);
    // Instance sounds
   // sp.play(garbageId, 1, 1, 0, 0, 1);
    //sp.play(foodId1, 1, 1, 0, 0, 1);
    //sp.play(foodId2, 1, 1, 0, 0, 1);
    //sp.play(lossId, 1, 1, 0, 0, 1);
}

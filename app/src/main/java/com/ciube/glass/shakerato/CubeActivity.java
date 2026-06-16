package com.ciube.glass.grape;

import android.app.Activity;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.WindowManager;

import com.google.android.glass.media.Sounds;

/**
 * CubeActivity
 *
 * Full-screen immersive activity for Google Glass XE24.
 * Displays a spinning isometric cube; no voice triggers are used.
 *
 * Swipe down to exit — plays the Glass system dismiss sound.
 */
public class CubeActivity extends Activity {

    private CubeView mCubeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Keep screen on — Glass dims after ~10 s by default
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        // Build the cube view programmatically — zero layout inflation
        mCubeView = new CubeView(this);

        setContentView(mCubeView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCubeView.invalidate();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    // -----------------------------------------------------------------------
    // Glass swipe-down → dismiss sound → finish
    // -----------------------------------------------------------------------

    @Override
    public void onBackPressed() {
        AudioManager audio = (AudioManager) getSystemService(AUDIO_SERVICE);
        audio.playSoundEffect(Sounds.DISMISSED);
        finish();
    }
}
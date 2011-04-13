package com.jmr.constGameSpeedTest;

import com.badlogic.gdx.backends.android.AndroidApplication;

import android.os.Bundle;

public class androidStarter extends AndroidApplication {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize(new constGameSpeedTest(), false);
    }
}
package com.mythqian.pluginstanderlib;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;



public interface CommonInterfaceActivity {
    void attach(Activity proxyActivity);

    public void onCreate(Bundle savedInstanceState);
    void onStart();
    void onResume();
    void onPause();
    void onStop();
    void onDestroy();
    void onSaveInstanceState(Bundle outState);
    boolean onTouchEvent(MotionEvent event);
    void onBackPressed();
}

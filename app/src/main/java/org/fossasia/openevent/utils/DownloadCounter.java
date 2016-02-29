package org.fossasia.openevent.utils;

import android.util.Log;

/**
 * Created by MananWason on 8/5/2015.
 */
public class DownloadCounter {
    private int counter = 0;

    public void incrementValue() {
        counter++;
        Log.d("Counter increment val", counter + "");

    }

    public int getValue() {
        return counter;
    }
}

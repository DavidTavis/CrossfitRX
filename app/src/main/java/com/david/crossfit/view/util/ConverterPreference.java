package com.david.crossfit.view.util;

import android.app.Activity;
import android.content.SharedPreferences;

/**
 * Created by TechnoA on 18.05.2017.
 */

public class ConverterPreference {
    SharedPreferences preferences;

    public ConverterPreference(Activity activity) {
        preferences = activity.getPreferences(Activity.MODE_PRIVATE);
    }

    public boolean getConvertKgToLb(){

        return preferences.getBoolean("convertKgToLb",true);

    }

    public void setConvertKgToLb(Boolean bool){

        preferences.edit().putBoolean("convertKgToLb", bool).commit();

    }

}

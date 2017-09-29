package com.david.crossfit.view.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.david.crossfit.R;
import com.lb.material_preferences_library.PreferenceActivity;
import com.lb.material_preferences_library.custom_preferences.Preference;


public class ActivityAbout extends PreferenceActivity
        implements Preference.OnPreferenceClickListener {

    @Override
    protected void onCreate(final Bundle savedInstanceState)
    {

        setTheme(R.style.AppTheme_Dark);
        super.onCreate(savedInstanceState);

        Preference prefConverterKey      = (Preference) findPreference(getString(R.string.pref_converter_key));
        Preference prefChartKey      = (Preference) findPreference(getString(R.string.pref_chart_key));
        Preference prefShareKey      = (Preference) findPreference(getString(R.string.pref_share_key));
        Preference prefRateReviewKey = (Preference) findPreference(getString(R.string.pref_rate_review_key));

        prefConverterKey.setOnPreferenceClickListener(this);
        prefChartKey.setOnPreferenceClickListener(this);
        prefShareKey.setOnPreferenceClickListener(this);
        prefRateReviewKey.setOnPreferenceClickListener(this);
    }

    @Override
    protected int getPreferencesXmlId()
    {
        return R.xml.pref_about;
    }

    @Override
    public boolean onPreferenceClick(android.preference.Preference preference) {
        if(preference.getKey().equals(getString(R.string.pref_share_key))) {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT,
                    getString(R.string.subject));
            shareIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.message) +
                    " " + getString(R.string.googleplay_url));
            startActivity(Intent.createChooser(shareIntent, getString(R.string.share_to)));
        }else if(preference.getKey().equals(getString(R.string.pref_rate_review_key))) {
            Intent rateReviewIntent = new Intent(Intent.ACTION_VIEW);
            rateReviewIntent.setData(Uri.parse(
                    getString(R.string.googleplay_url)));
            startActivity(rateReviewIntent);
        }else if(preference.getKey().equals(getString(R.string.pref_converter_key))) {
            Intent converterIntent = new Intent(this, Converter.class);
            startActivity(converterIntent);
        }else if(preference.getKey().equals(getString(R.string.pref_chart_key))) {
            Intent chartIntent = new Intent(this, RadarChartActivity.class);
            startActivity(chartIntent);
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.open_main, R.anim.close_next);
    }
}
package com.dmham.liftstrong;

import android.annotation.TargetApi;
import android.app.Activity;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass. Activities that
 * contain this fragment must implement the
 * {@link StrongLiftsSettingsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events. Use the
 * {@link StrongLiftsSettingsFragment#newInstance} factory method to create an
 * instance of this fragment.
 * 
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class StrongLiftsSettingsFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.pref_workout_plan);
    }
}

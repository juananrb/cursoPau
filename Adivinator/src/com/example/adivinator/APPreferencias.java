package com.example.adivinator;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class APPreferencias extends PreferenceActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.pref_settings);
	}

}

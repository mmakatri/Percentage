package xyz.protps.percentage.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import xyz.protps.percentage.R;


public class SettingsActivity extends AppCompatActivity {
    // Variable to manage the number of news
    private static Preference yemenMobilePercentage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the layout for this activity
        setContentView(R.layout.activity_settings);

        // Set the title
        setTitle(R.string.activity_title_settings);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    // Fragment preference to hold the setting layout
    public static class SettingsFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            // Load the settings layout from xml file
            addPreferencesFromResource(R.xml.settings_main);

            // Get the actual values of the (order by) preference
            //Preference orderBy = findPreference(getString(R.string.settings_order_by_key));
            // Show this value as summary
            //bindPreferenceSummaryToValue(orderBy);

            // Get the actual values of the (number of news per page) preference
            yemenMobilePercentage =
                    findPreference(getString(R.string.settings_yemen_mobile_percentage_value_key));
            // Show this value as summary
            bindPreferenceSummaryToValue(yemenMobilePercentage);
        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            // We check the value , it should be between 1 to 50 depend on the
            // Guardian API
            if (preference == yemenMobilePercentage) {
                int percentageValue = Integer.parseInt(newValue.toString());
                if (percentageValue > 100 || percentageValue < 0) {
                    Toast.makeText(getActivity(),
                            getString(R.string.settings_out_of_limits), Toast.LENGTH_LONG).show();
                    return false;
                }
            }

            // Set the new value to the current preference
            preference.setSummary(newValue.toString());
            return true;
        }

        private void bindPreferenceSummaryToValue(Preference preference) {
            // Set on change listener for the current preference
            preference.setOnPreferenceChangeListener(this);

            // Get a reference to the local shared preference
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(preference.getContext());
            // Get the value from the current preference
            String preferenceString = preferences.getString(preference.getKey(), "");
            // Call this method to handle the new value and apply it
            onPreferenceChange(preference, preferenceString);
        }
    }
//Test
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if(id == android.R.id.home)
        {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

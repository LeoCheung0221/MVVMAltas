package com.tufusi.networklib.env;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import android.app.Application;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.tufusi.networklib.R;

public class EnvironmentActivity extends AppCompatActivity {

    private static final String NETWORK_ENVIRONMENT_PREF_KEY = "network_environment_type";
    private static String sCurrentNetworkEnv = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_environment);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, new MyPreferenceFragment())
                .commit();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        sCurrentNetworkEnv = prefs.getString(EnvironmentActivity.NETWORK_ENVIRONMENT_PREF_KEY, "1");
    }

    public static class MyPreferenceFragment extends PreferenceFragmentCompat implements Preference.OnPreferenceChangeListener {

        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            if (!sCurrentNetworkEnv.equalsIgnoreCase(String.valueOf(newValue))) {
                Toast.makeText(getContext(), "您已经更改了网络环境，再您退出当前页面的时候APP将会重启切换环境！", Toast.LENGTH_SHORT).show();
            }
            return true;
        }

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            addPreferencesFromResource(R.xml.env_preference);
            Preference preference = findPreference(NETWORK_ENVIRONMENT_PREF_KEY);
            if (preference != null) {
                preference.setOnPreferenceChangeListener(this);
            }
        }
    }

    @Override
    public void onBackPressed() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String newValue = prefs.getString(NETWORK_ENVIRONMENT_PREF_KEY, "1");
        if (!sCurrentNetworkEnv.equalsIgnoreCase(newValue)) {
            android.os.Process.killProcess(android.os.Process.myPid());
        } else {
            finish();
        }
    }

    /**
     * 判断是否是正式环境
     */
    public static boolean isReleaseEnvironment(Application application) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(application);
        String env = prefs.getString(NETWORK_ENVIRONMENT_PREF_KEY, "1");
        return "1".equalsIgnoreCase(env);
    }
}
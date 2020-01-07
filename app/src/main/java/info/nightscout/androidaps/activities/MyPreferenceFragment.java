package info.nightscout.androidaps.activities;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.preference.PreferenceFragmentCompat;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasAndroidInjector;
import dagger.android.support.AndroidSupportInjection;
import info.nightscout.androidaps.Config;
import info.nightscout.androidaps.MainApp;
import info.nightscout.androidaps.R;
import info.nightscout.androidaps.interfaces.PluginBase;
import info.nightscout.androidaps.interfaces.PluginType;
import info.nightscout.androidaps.plugins.aps.loop.LoopPlugin;
import info.nightscout.androidaps.plugins.aps.openAPSAMA.OpenAPSAMAPlugin;
import info.nightscout.androidaps.plugins.aps.openAPSMA.OpenAPSMAPlugin;
import info.nightscout.androidaps.plugins.aps.openAPSSMB.OpenAPSSMBPlugin;
import info.nightscout.androidaps.plugins.constraints.safety.SafetyPlugin;
import info.nightscout.androidaps.plugins.general.automation.AutomationPlugin;
import info.nightscout.androidaps.plugins.general.careportal.CareportalPlugin;
import info.nightscout.androidaps.plugins.general.nsclient.NSClientPlugin;
import info.nightscout.androidaps.plugins.general.smsCommunicator.SmsCommunicatorPlugin;
import info.nightscout.androidaps.plugins.general.tidepool.TidepoolPlugin;
import info.nightscout.androidaps.plugins.general.wear.WearPlugin;
import info.nightscout.androidaps.plugins.general.xdripStatusline.StatusLinePlugin;
import info.nightscout.androidaps.plugins.insulin.InsulinOrefFreePeakPlugin;
import info.nightscout.androidaps.plugins.pump.combo.ComboPlugin;
import info.nightscout.androidaps.plugins.pump.danaR.DanaRPlugin;
import info.nightscout.androidaps.plugins.pump.danaRKorean.DanaRKoreanPlugin;
import info.nightscout.androidaps.plugins.pump.danaRS.DanaRSPlugin;
import info.nightscout.androidaps.plugins.pump.danaRv2.DanaRv2Plugin;
import info.nightscout.androidaps.plugins.pump.insight.LocalInsightPlugin;
import info.nightscout.androidaps.plugins.pump.medtronic.MedtronicPumpPlugin;
import info.nightscout.androidaps.plugins.pump.virtual.VirtualPumpPlugin;
import info.nightscout.androidaps.plugins.sensitivity.SensitivityAAPSPlugin;
import info.nightscout.androidaps.plugins.sensitivity.SensitivityOref0Plugin;
import info.nightscout.androidaps.plugins.sensitivity.SensitivityOref1Plugin;
import info.nightscout.androidaps.plugins.sensitivity.SensitivityWeightedAveragePlugin;
import info.nightscout.androidaps.plugins.source.DexcomPlugin;
import info.nightscout.androidaps.plugins.source.EversensePlugin;
import info.nightscout.androidaps.plugins.source.GlimpPlugin;
import info.nightscout.androidaps.plugins.source.PoctechPlugin;
import info.nightscout.androidaps.plugins.source.TomatoPlugin;

/**
 * Created by adrian on 2019-12-23.
 */
public class MyPreferenceFragment extends PreferenceFragmentCompat implements HasAndroidInjector {
    private Integer id;

    @Inject AutomationPlugin automationPlugin;
    @Inject DanaRPlugin danaRPlugin;
    @Inject DanaRKoreanPlugin danaRKoreanPlugin;
    @Inject DanaRv2Plugin danaRv2Plugin;
    @Inject DanaRSPlugin danaRSPlugin;
    @Inject CareportalPlugin careportalPlugin;
    @Inject InsulinOrefFreePeakPlugin insulinOrefFreePeakPlugin;
    @Inject LoopPlugin loopPlugin;
    @Inject OpenAPSAMAPlugin openAPSAMAPlugin;
    @Inject OpenAPSMAPlugin openAPSMAPlugin;
    @Inject OpenAPSSMBPlugin openAPSSMBPlugin;
    @Inject SafetyPlugin safetyPlugin;
    @Inject DexcomPlugin dexcomPlugin;
    @Inject EversensePlugin eversensePlugin;
    @Inject GlimpPlugin glimpPlugin;
    @Inject PoctechPlugin poctechPlugin;
    @Inject TomatoPlugin tomatoPlugin;
    @Inject SmsCommunicatorPlugin smsCommunicatorPlugin;
    @Inject StatusLinePlugin statusLinePlugin;
    @Inject TidepoolPlugin tidepoolPlugin;
    @Inject VirtualPumpPlugin virtualPumpPlugin;
    @Inject WearPlugin wearPlugin;

    @Inject DispatchingAndroidInjector<Object> androidInjector;

    @Override
    public AndroidInjector<Object> androidInjector() {
        return androidInjector;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        id = args.getInt("id");
    }

    private void addPreferencesFromResourceIfEnabled(PluginBase p, PluginType type) {
        if (p.isEnabled(type) && p.getPreferencesId() != -1)
            addPreferencesFromResource(p.getPreferencesId());
    }

    @Override public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        //setPreferencesFromResource(R.xml.mainfile, rootKey);
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null && savedInstanceState.containsKey("id")) {
            id = savedInstanceState.getInt("id");
        }

        if (id != -1) {
            addPreferencesFromResource(id);
        } else {

            if (!Config.NSCLIENT) {
                addPreferencesFromResource(R.xml.pref_password);
            }
            addPreferencesFromResource(R.xml.pref_general);
            addPreferencesFromResource(R.xml.pref_age);

            addPreferencesFromResource(R.xml.pref_overview);

            addPreferencesFromResourceIfEnabled(eversensePlugin, PluginType.BGSOURCE);
            addPreferencesFromResourceIfEnabled(dexcomPlugin, PluginType.BGSOURCE);
            addPreferencesFromResourceIfEnabled(tomatoPlugin, PluginType.BGSOURCE);
            addPreferencesFromResourceIfEnabled(poctechPlugin, PluginType.BGSOURCE);
            addPreferencesFromResourceIfEnabled(glimpPlugin, PluginType.BGSOURCE);
            addPreferencesFromResourceIfEnabled(careportalPlugin, PluginType.GENERAL);
            addPreferencesFromResourceIfEnabled(safetyPlugin, PluginType.CONSTRAINTS);
            if (Config.APS) {
                addPreferencesFromResourceIfEnabled(loopPlugin, PluginType.LOOP);
                addPreferencesFromResourceIfEnabled(openAPSMAPlugin, PluginType.APS);
                addPreferencesFromResourceIfEnabled(openAPSAMAPlugin, PluginType.APS);
                addPreferencesFromResourceIfEnabled(openAPSSMBPlugin, PluginType.APS);
            }

            addPreferencesFromResourceIfEnabled(SensitivityAAPSPlugin.getPlugin(), PluginType.SENSITIVITY);
            addPreferencesFromResourceIfEnabled(SensitivityWeightedAveragePlugin.getPlugin(), PluginType.SENSITIVITY);
            addPreferencesFromResourceIfEnabled(SensitivityOref0Plugin.getPlugin(), PluginType.SENSITIVITY);
            addPreferencesFromResourceIfEnabled(SensitivityOref1Plugin.getPlugin(), PluginType.SENSITIVITY);

            if (Config.PUMPDRIVERS) {
                addPreferencesFromResourceIfEnabled(danaRPlugin, PluginType.PUMP);
                addPreferencesFromResourceIfEnabled(danaRKoreanPlugin, PluginType.PUMP);
                addPreferencesFromResourceIfEnabled(danaRv2Plugin, PluginType.PUMP);
                addPreferencesFromResourceIfEnabled(danaRSPlugin, PluginType.PUMP);
                addPreferencesFromResourceIfEnabled(LocalInsightPlugin.getPlugin(), PluginType.PUMP);
                addPreferencesFromResourceIfEnabled(ComboPlugin.getPlugin(), PluginType.PUMP);
                addPreferencesFromResourceIfEnabled(MedtronicPumpPlugin.getPlugin(), PluginType.PUMP);
            }

            if (!Config.NSCLIENT) {
                addPreferencesFromResourceIfEnabled(virtualPumpPlugin, PluginType.PUMP);
            }

            addPreferencesFromResourceIfEnabled(insulinOrefFreePeakPlugin, PluginType.INSULIN);

            addPreferencesFromResourceIfEnabled(NSClientPlugin.getPlugin(), PluginType.GENERAL);
            addPreferencesFromResourceIfEnabled(tidepoolPlugin, PluginType.GENERAL);
            addPreferencesFromResourceIfEnabled(smsCommunicatorPlugin, PluginType.GENERAL);
            addPreferencesFromResourceIfEnabled(automationPlugin, PluginType.GENERAL);

            addPreferencesFromResource(R.xml.pref_others);
            addPreferencesFromResource(R.xml.pref_datachoices);

            addPreferencesFromResourceIfEnabled(wearPlugin, PluginType.GENERAL);
            addPreferencesFromResourceIfEnabled(statusLinePlugin, PluginType.GENERAL);
        }

        PreferencesActivity.initSummary(getPreferenceScreen());

        for (PluginBase plugin : MainApp.getPluginsList()) {
            plugin.preprocessPreferences(this);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("id", id);
    }
}

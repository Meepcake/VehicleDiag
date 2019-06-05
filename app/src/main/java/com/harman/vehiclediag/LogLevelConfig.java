package com.harman.vehiclediag;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.harman.vehiclediag.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import static android.support.constraint.Constraints.TAG;

public class LogLevelConfig extends Fragment {

    private static final String TAG = "LogConfig_Fragment";
    private HashSet<String> verboseFunctions = new HashSet<String>();

    // The onCreateView method is called when Fragment should create its View object hierarchy,
    // either dynamically or via XML layout inflation. 
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.activity_log_config, parent, false);
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        final Switch enableMediaPlayback, enableCarplayLog, enableAndroidAutoLog, enablePowerModingLog,
                enableIAP2PlaybackLog, enableTunerLog, enableHFPLog, enableTODLog, enableNavLog,
                enablePathologyLog, enableAUXLog, enableSoftwareUpdateLog, enableRVCLog, enableSWCLog,
                enableSecureTimeLog, enableCarLifeLog;

        enableMediaPlayback = (Switch) view.findViewById(R.id.enableMediaPlaybackLogSwitch);
        enableCarplayLog = (Switch) view.findViewById(R.id.enableCarplayLogSwitch);
        enableAndroidAutoLog = (Switch) view.findViewById(R.id.enableAndroidAutoLogSwitch);
        enablePowerModingLog = (Switch) view.findViewById(R.id.enablePowerModingLogSwitch);
        enableIAP2PlaybackLog = (Switch) view.findViewById(R.id.enableIAP2LogSwitch);
        enableTunerLog = (Switch) view.findViewById(R.id.enableTunerSwitch);
        enableHFPLog = (Switch) view.findViewById(R.id.enableHFPLogSwitch);
        enableTODLog = (Switch) view.findViewById(R.id.enableTODLogSwitch);
        enableNavLog = (Switch) view.findViewById(R.id.enableNavigationLogSwitch);
        enablePathologyLog = (Switch) view.findViewById(R.id.enablePathologyLogSwitch);
        enableAUXLog = (Switch) view.findViewById(R.id.enableAuxLogSwitch);
        enableSoftwareUpdateLog= (Switch) view.findViewById(R.id.enableSoftwareUpdateLogSwitch);
        enableRVCLog= (Switch) view.findViewById(R.id.enableRVCLogSwitch);
        enableSWCLog = (Switch) view.findViewById(R.id.enableSWCLogSwitch);
        enableSecureTimeLog = (Switch) view.findViewById(R.id.enableSecureTimeLogSwitch);
        enableCarLifeLog = (Switch) view.findViewById(R.id.enableCarLifeLogSwitch);

        //Tags listed under each function
        ArrayList<String> androidAutoTags = new ArrayList<>(Arrays.asList(
                "HarmanVehicleHAL", "NAVD", "HarmanUsbHandler", "VIT", "DCSM", "AapManager"));

        ArrayList<String> auxTags = new ArrayList<>(Arrays.asList(
                "ipcclient", "ipcserver"));

        ArrayList<String> carlifeTags = new ArrayList<>(Arrays.asList(
                "Carlife"));

        ArrayList<String> carplayTags = new ArrayList<>(Arrays.asList(
                "HarmanVehicleHAL", "NAVD", "HarmanUsbHandler", "VIT", "iAP2_HAL", "ACP_HAL", "iAP2_HIDL", "ACP_HIDL", "Carplay", "DCSM", "ACPService", "iAP2Service"));

        ArrayList<String> hfpTags = new ArrayList<>(Arrays.asList(
                "AudioModeService", "HarmanAudioControl", "HarmanAudioControl.Plugin", "HarmanAudioEffect", "HarmanBeepAlert", "HarmanAudioHAL"));

        ArrayList<String> iapTags = new ArrayList<>(Arrays.asList(
                "iAP2_HAL", "ACP_HAL", "iAP2_HIDL", "ACP_HIDL", "ACPService", "iAP2Service"));

        ArrayList<String> mediaPlaybackTags = new ArrayList<>(Arrays.asList(
                "HarmanMediaCommon", "HarmanMediaFramework", "MediaCoreService", "HarmanUsbHandler", "AudioModeService", "HarmanAudioControl",
                "HarmanAudioControl.Plugin", "HarmanAudioEffect", "HarmanBeepAlert", "HarmanAudioHAL"));

        ArrayList<String> navTags= new ArrayList<>(Arrays.asList(
                "NAVD"));

        ArrayList<String> pathologyTags= new ArrayList<>(Arrays.asList(
                "pathology"));

        ArrayList<String> powermodingTags= new ArrayList<>(Arrays.asList(
                "HarmanVehicleHAL", "VPS", "ipcclient", "ipcserver", "VIT"));

        ArrayList<String> rvcTags = new ArrayList<>(Arrays.asList(
                "EVSApp", "EarlyEVS"));

        ArrayList<String> secureTimeTags = new ArrayList<>(Arrays.asList(
                "SecureTimeHAL", "SecureTimeService"));

        ArrayList<String> softwareUpdateTags = new ArrayList<>(Arrays.asList(
                "swupdate"));

        ArrayList<String> swcTags = new ArrayList<>(Arrays.asList(
                "HarmanVehicleHAL", "VIT"));

        ArrayList<String> todTags = new ArrayList<>(Arrays.asList(
                "NAVD", "ipcclient", "ipcserver"));

        ArrayList<String> tunerTags = new ArrayList<>(Arrays.asList(
                "AudioModeService", "HarmanAudioControl", "HarmanAudioControl.Plugin", "HarmanAudioEffect", "HarmanBeepAlert", "HarmanAudioHAL"));

        enableMediaPlayback.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                changeLogOutputLevel("MediaPlayback", isChecked);
            }
        });

        enableCarplayLog.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                changeLogOutputLevel("Carplay", isChecked);
            }
        });

        enableAndroidAutoLog.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                changeLogOutputLevel("AndroidAuto", isChecked);
            }
        });

        enablePowerModingLog.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                changeLogOutputLevel("PowerModing", isChecked);
            }
        });

        enableIAP2PlaybackLog.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                changeLogOutputLevel("iAP2Playback", isChecked);
            }
        });

        enableTunerLog.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                changeLogOutputLevel("Tuner", isChecked);
            }
        });

        enableHFPLog.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                changeLogOutputLevel("HFP", isChecked);
            }
        });

        enableTODLog.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                changeLogOutputLevel("TOD", isChecked);
            }
        });

        enableNavLog.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                changeLogOutputLevel("Navigation", isChecked);
            }
        });

        enablePathologyLog.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                changeLogOutputLevel("Pathology", isChecked);
            }
        });

        enableAUXLog.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                changeLogOutputLevel("AUX", isChecked);
            }
        });

        enableSoftwareUpdateLog.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                changeLogOutputLevel("SoftwareUpdate", isChecked);
            }
        });

        enableRVCLog.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                changeLogOutputLevel("RVC", isChecked);
            }
        });

        enableSWCLog.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                changeLogOutputLevel("SWC", isChecked);
            }
        });

        enableSecureTimeLog.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                changeLogOutputLevel("SecureTime", isChecked);
            }
        });

        enableCarLifeLog.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                changeLogOutputLevel("CarLife", isChecked);
            }
        });

        //initialize switch states
        try{
            Process process = Runtime.getRuntime().exec("/system/bin/sh -");
            OutputStream stdin = process.getOutputStream();
            InputStream stdout = process.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(stdout));

            if(isVerbose(br, process, stdin, stdout, androidAutoTags))
                verboseFunctions.add("AndroidAuto");
            if(isVerbose(br, process, stdin, stdout, auxTags))
                verboseFunctions.add("AUX");
            if(isVerbose(br, process, stdin, stdout, carlifeTags))
                verboseFunctions.add("CarLife");
            if(isVerbose(br, process, stdin, stdout, carplayTags))
                verboseFunctions.add("Carplay");
            if(isVerbose(br, process, stdin, stdout, hfpTags))
                verboseFunctions.add("HFP");
            if(isVerbose(br, process, stdin, stdout, iapTags))
                verboseFunctions.add("iAP2Playback");
            if(isVerbose(br, process, stdin, stdout, mediaPlaybackTags))
                verboseFunctions.add("MediaPlayback");
            if(isVerbose(br, process, stdin, stdout, navTags))
                verboseFunctions.add("Navigation");
            if(isVerbose(br, process, stdin, stdout, pathologyTags))
                verboseFunctions.add("Pathology");
            if(isVerbose(br, process, stdin, stdout, powermodingTags))
                verboseFunctions.add("PowerModing");
            if(isVerbose(br, process, stdin, stdout, rvcTags))
                verboseFunctions.add("RVC");
            if(isVerbose(br, process, stdin, stdout, secureTimeTags))
                verboseFunctions.add("SecureTime");
            if(isVerbose(br, process, stdin, stdout, softwareUpdateTags))
                verboseFunctions.add("SoftwareUpdate");
            if(isVerbose(br, process, stdin, stdout, swcTags))
                verboseFunctions.add("SWC");
            if(isVerbose(br, process, stdin, stdout, todTags))
                verboseFunctions.add("TOD");
            if(isVerbose(br, process, stdin, stdout, tunerTags))
                verboseFunctions.add("Tuner");

            Log.d(TAG, "Done with isVerbose");

            if(!verboseFunctions.contains("AndroidAuto"))
                enableAndroidAutoLog.setChecked(false);
            if(!verboseFunctions.contains("AUX"))
                enableAUXLog.setChecked(false);
            if(!verboseFunctions.contains("CarLife"))
                enableCarLifeLog.setChecked(false);
            if(!verboseFunctions.contains("Carplay"))
                enableCarplayLog.setChecked(false);
            if(!verboseFunctions.contains("HFP"))
                enableHFPLog.setChecked(false);
            if(!verboseFunctions.contains("iAP2Playback"))
                enableIAP2PlaybackLog.setChecked(false);
            if(!verboseFunctions.contains("MediaPlayback"))
                enableMediaPlayback.setChecked(false);
            if(!verboseFunctions.contains("Navigation"))
                enableNavLog.setChecked(false);
            if(!verboseFunctions.contains("Pathology"))
                enablePathologyLog.setChecked(false);
            if(!verboseFunctions.contains("PowerModing"))
                enablePowerModingLog.setChecked(false);
            if(!verboseFunctions.contains("RVC"))
                enableRVCLog.setChecked(false);
            if(!verboseFunctions.contains("SecureTime"))
                enableSecureTimeLog.setChecked(false);
            if(!verboseFunctions.contains("SoftwareUpdate"))
                enableSoftwareUpdateLog.setChecked(false);
            if(!verboseFunctions.contains("SWC"))
                enableSWCLog.setChecked(false);
            if(!verboseFunctions.contains("TOD"))
                enableTODLog.setChecked(false);
            if(!verboseFunctions.contains("Tuner"))
                enableTunerLog.setChecked(false);

            stdin.write("exit\n".getBytes());
            stdin.flush();
            stdin.close();
        } catch (IOException e) {
            Log.e(TAG, "IOException of command e : " + e);
        }
    }

    private void changeLogOutputLevel(String function, boolean isChecked){
        try {
            Process process = Runtime.getRuntime().exec("/system/bin/sh -");
            OutputStream stdin = process.getOutputStream();

            if(isChecked) {
                Log.d(TAG, function + " switch changed");
                stdin.write(("hlogutil -f " + function + " -l VERBOSE\n").getBytes());
                verboseFunctions.add(function);
            }
            else{
                stdin.write(("hlogutil -f " + function + " -l INFO\n").getBytes());
                verboseFunctions.remove(function);
                for(String tmpStr : verboseFunctions){
                    Log.d(TAG, function + " switch turned off");
                    stdin.write(("hlogutil -f " + tmpStr + " -l VERBOSE\n").getBytes());
                }
            }
            stdin.write("exit\n".getBytes());
            stdin.flush();
            stdin.close();
        } catch (IOException e) {
            Log.e(TAG, "IOException of command e : " + e);
        }
    }

    private boolean isVerbose(BufferedReader br, Process process, OutputStream stdin,
                              InputStream stdout, ArrayList<String> tags) throws IOException {
        Log.d(TAG, "In isVerbose");
        for (int i = 0; i < tags.size(); ++i) {
            String property = "log.tag." + tags.get(i);
            Log.d(TAG, "property: " + property);
            stdin.write(("getprop " + property + "\n").getBytes());
            stdin.flush();
            String line = br.readLine();
            Log.d(TAG, line);
            if(!line.equals("VERBOSE")) return false;
        }
        return true;
    }

}
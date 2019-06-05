package com.harman.vehiclediag;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.SystemClock;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    /*Init some stuff :)*/
    private static final String TAG = "MainActivity";
    TextView textViewBuild, textViewABL, textViewIOC;
    PowerManager pm;
    FragmentTransaction ft;
    private static final String SNAPSHOT_INTENT = "com.fca.action.LAUNCH_SYSTEM_SNAPSHOT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);

        ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_manager, new MainPage_Fragment());
        ft.addToBackStack(null);
        ft.commit();

        textViewBuild = (TextView) headerView.findViewById(R.id.textViewBuild);
        textViewABL = (TextView) headerView.findViewById(R.id.textViewABL);
        textViewIOC = (TextView) headerView.findViewById(R.id.textViewIOC);
        pm = (PowerManager) getSystemService(Context.POWER_SERVICE);

        textViewBuild.setText("SoC: " + Build.DISPLAY.substring(40,Build.DISPLAY.length()));
        textViewABL.setText("ABL: " + Build.BOOTLOADER.substring(0,(Build.BOOTLOADER.length()-20)));
        textViewIOC.setText("IOC: 9001");
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_gohome) {
            ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_manager, new MainPage_Fragment());
            ft.addToBackStack(null);
            ft.commit();
        } else if (id == R.id.nav_setlogs) {

            ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_manager, new LogLevelConfig());
            ft.addToBackStack(null);
            ft.commit();
        } else if (id == R.id.nav_viewIPClogging) {
            ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_manager, new IPCLogging_Fragment());
            ft.addToBackStack(null);
            ft.commit();
        } else if (id == R.id.nav_domainDebug) {

        } else if (id == R.id.nav_tools) {
            Toast.makeText(MainActivity.this, "TOOLS", Toast.LENGTH_LONG).show();
        } else if (id == R.id.nav_snapshot) {
            Toast.makeText(MainActivity.this, "Taking Insight Snapshot", Toast.LENGTH_LONG).show();
            Intent shapshotintent = new Intent();
            shapshotintent.setAction(SNAPSHOT_INTENT);
            sendBroadcast(shapshotintent);

        } else if (id == R.id.nav_clearinsight) {
            Toast.makeText(MainActivity.this, "Deleting Data and Rebooting...", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_reset) {
            Toast.makeText(MainActivity.this, "Reset Requested. Please wait.", Toast.LENGTH_LONG).show();
            SystemClock.sleep(1000);
            //runCommand("reboot");
            //pm.reboot("USER REQUESTED");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void runCommand(String command){
        try{
            Process process = Runtime.getRuntime().exec(command);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            Log.e(TAG, "runCommand: " + command);

            int read;
            char[] buffer = new char[4096];
            StringBuffer output = new StringBuffer();
            while ((read = reader.read(buffer)) > 0) {
                output.append(buffer, 0, read);
            }
            reader.close();

            // Waits for the command to finish.
            process.waitFor(10, TimeUnit.SECONDS);

            Log.e(TAG,output.toString());
        } catch (IOException e) {
            Log.e(TAG, "IOException during runCommand: " + e);
        } catch (InterruptedException e) {
            Log.e(TAG, "Interrupted Exception during runCommand: " + e);
        }
    }
}

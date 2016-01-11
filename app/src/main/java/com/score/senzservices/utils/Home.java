package com.score.senzservices.utils;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.score.senzservices.R;

import java.util.ArrayList;
import java.util.Random;

public class Home extends AppCompatActivity {
    ArrayList<ApplicationInfo> apps = new ArrayList<>();
    Random random; //to be deleted (for testing purposes only)
    ListView app_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initUi();
    }

    private void initUi() {
        random = new Random();
        app_list = (ListView) findViewById(R.id.applications);
        addApps();
        AppListAdapter adapter = new AppListAdapter(this, R.layout.app_row, apps);
        app_list.setAdapter(adapter);
    }

    //to be deleted (for testing purposes only)
    public void addApps() {
        //locationz
        apps.add(new ApplicationInfo(getBaseContext(), "LocationZ", isPackageInstalled("com.score.senz"), 5,
                "Location sharing app","com.score.senz", BitmapFactory.decodeResource(getResources(), R.mipmap.ic_locationz)));

        apps.add(new ApplicationInfo(getBaseContext(), "SenZors", isPackageInstalled("com.score.senzors"), 5,
                "Shares your location to your friend/kid and their location safely", "com.score.senzors", BitmapFactory.decodeResource(getResources(), R.mipmap.ic_senzors)));

        apps.add(new ApplicationInfo(getBaseContext(), "Pi", isPackageInstalled("org.scorelab.pi"), -1,
                "Smart Home Controlling Application", "org.scorelab.pi", BitmapFactory.decodeResource(getResources(), R.mipmap.ic_pi)));
    }

    private void filterAppList() {
        //to be implemented with real data
    }

    private boolean isPackageInstalled(String packagename) {
        PackageManager pm = getBaseContext().getPackageManager();
        try {
            pm.getPackageInfo(packagename, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    protected void onRestart() {
        super.onRestart();
        finish();
        Intent intent = new Intent(Home.this, Home.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
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
        if (id == R.id.action_filter) {
            filterAppList();
        }


        return super.onOptionsItemSelected(item);
    }
}


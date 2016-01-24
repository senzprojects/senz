package com.score.senz.ui;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import com.score.senz.R;
import com.score.senz.pojos.AppInfo;

import java.util.ArrayList;
import java.util.Random;

public class HomeActivity extends AppCompatActivity {
    ArrayList<AppInfo> apps = new ArrayList<>();
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
        AppInfoListAdapter adapter = new AppInfoListAdapter(this, R.layout.app_row, apps);
        app_list.setAdapter(adapter);
    }

    //to be deleted (for testing purposes only)
    public void addApps() {
        //locationz
        apps.add(new AppInfo(getBaseContext(), "LocationZ", isPackageInstalled("com.score.senz"), 5,
                "Location sharing app", "com.score.senz", BitmapFactory.decodeResource(getResources(), R.mipmap.ic_locationz)));

        apps.add(new AppInfo(getBaseContext(), "HomeZ", isPackageInstalled("org.scorelab.pi"), -1,
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
        Intent intent = new Intent(HomeActivity.this, HomeActivity.class);
        startActivity(intent);
    }

}


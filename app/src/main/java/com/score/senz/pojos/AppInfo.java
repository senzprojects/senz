package com.score.senz.pojos;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;

/**
 * Created by Anesu on 1/10/2016.
 */
public class AppInfo {
    private String name;
    private String packageName;
    private boolean isInstalled;
    private Double rating;
    private Bitmap icon;
    private String description;
    Context context;
    public AppInfo(Context context, String name, boolean isInstalled, double rating, String description, String packageName, Bitmap icon)
    {
        this.name = name;
        this.isInstalled = isInstalled;
        this.rating = rating;
        this.icon = icon;
        this.description = description;
        this.context = context;
        this.packageName = packageName;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isInstalled() {
        PackageManager pm = context.getPackageManager();
        try {
            pm.getPackageInfo(this.getPackageName(), PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public void setIsInstalled(boolean isInstalled) {
        this.isInstalled = isInstalled;
    }

    public Bitmap getIcon(){
        return icon;
    }

    public void setIcon(Bitmap icon){
        this.icon = icon;
    }

    public String getDescription(){
        return description;
    }

    public void setDesciption(String description){
        this.description = description;
    }

    public String getPackageName(){
        return packageName;
    }

    public void setPackageName(String packageName){
        this.packageName = packageName;
    }

}


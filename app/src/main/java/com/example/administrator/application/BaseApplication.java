package com.example.administrator.application;

import android.app.Activity;
import android.app.Application;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2015/11/15.
 */
public class BaseApplication extends Application {

    private List<Activity> activityList = new LinkedList<Activity>();

    public void addActivity(Activity activity) {
        if (activity != null) {
            activityList.add(activity);
        }
    }

    public void exit() {
        for (Activity activity : activityList) {
            activity.finish();
        }
    }
}

package com.kkj.cvoting.plugin.util;

import android.app.Activity;
import android.os.AsyncTask;

import org.json.JSONObject;

public abstract class BasePlugin extends AsyncTask<JSONObject, Void, Void> {
    private Activity activity;
    public CompleteListener listener;

    public Activity getActivity(){
        return activity;
    }

    public void setPlugin(Activity activity, CompleteListener listener){
        this.activity = activity;
        this.listener = listener;
    }

    @Override
    protected Void doInBackground(JSONObject... jsonObjects) {
        executePlugin(jsonObjects[0]);
        return null;
    }

    protected abstract void executePlugin(JSONObject data);
}

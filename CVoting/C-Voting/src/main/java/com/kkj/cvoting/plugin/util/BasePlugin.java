package com.kkj.cvoting.plugin.util;

import android.os.AsyncTask;

import org.json.JSONObject;

import androidx.fragment.app.FragmentActivity;

public abstract class BasePlugin extends AsyncTask<JSONObject, Void, Void> {
    private FragmentActivity activity;
    public CompleteListener listener;

    public FragmentActivity getActivity() {
        return activity;
    }

    public void setPlugin(FragmentActivity activity, CompleteListener listener) {
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

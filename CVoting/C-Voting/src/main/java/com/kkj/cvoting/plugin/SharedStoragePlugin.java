package com.kkj.cvoting.plugin;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.kkj.cvoting.plugin.util.BasePlugin;
import com.kkj.cvoting.plugin.util.CallID;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class SharedStoragePlugin extends BasePlugin {
    private SharedPreferences pref;

    private String id;
    private JSONObject param;

    private String callback = "";
    private String storageName = "default";
    private String listName = "";

    private JSONObject result;

    @Override
    protected void executePlugin(JSONObject data) {
        try {
            id = data.getString("id");
            param = data.getJSONObject("param");

            if (param.has("callback")) {
                callback = param.getString("callback");
            }
            if (param.has("storage_name")) {
                storageName = param.getString("storage_name");
            }
            if (param.has("list_name")) {
                listName = param.getString("list_name");
            }

            if(TextUtils.isEmpty(storageName)){
                storageName = "default";
            }

            pref = getActivity().getSharedPreferences(storageName, Context.MODE_PRIVATE);

            if (id.equals(CallID.SET_SHARED_STORAGE)) {
                if(TextUtils.isEmpty(listName)){
                    result = new JSONObject();
                    result.put("result", false);
                    result.put("err_message", "list_name not found");
                } else {
                    result = setSharedStorage(param);
                }
            } else if (id.equals(CallID.GET_SHARED_STORAGE)) {
                if(TextUtils.isEmpty(listName)){
                    result = new JSONObject();
                    result.put("result", false);
                    result.put("err_message", "list_name not found");
                } else {
                    result = getSharedStorage();
                }
            }

            listener.sendCallback(callback, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private JSONObject setSharedStorage(JSONObject param) throws JSONException {
        SharedPreferences.Editor editor = pref.edit();

        JSONArray data = param.getJSONArray("data");

        JSONObject baseData = new JSONObject(pref.getString("baseData", ""));
        baseData.put(listName, data);

        editor.putString("baseData", baseData.toString());

        editor.commit();

        JSONObject resultData = new JSONObject();
        resultData.put("result", true);

        return resultData;
    }

    private JSONObject getSharedStorage() throws JSONException {
        JSONObject resultData = new JSONObject();

        JSONObject baseData = new JSONObject(pref.getString("baseData", ""));

        if(baseData.has(listName)){
            resultData.put("result", true);
            resultData.put("stored_data", baseData.getJSONArray(listName));
        }

        return resultData;
    }

}

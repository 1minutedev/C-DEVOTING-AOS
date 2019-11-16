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

            if(TextUtils.isEmpty(storageName)){
                storageName = "default";
            }

            pref = getActivity().getSharedPreferences(storageName, Context.MODE_PRIVATE);

            if (id.equals(CallID.SET_SHARED_STORAGE)) {
                result = setSharedStorage(param);
            } else if (id.equals(CallID.GET_SHARED_STORAGE)) {
                result = getSharedStorage();
            }

            listener.sendCallback(callback, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private JSONObject setSharedStorage(JSONObject param) throws JSONException {
        SharedPreferences.Editor editor = pref.edit();

        JSONArray datas = param.getJSONArray("data");

        for (int i = 0; i < datas.length(); i++) {
            JSONObject data = datas.getJSONObject(i);
            String key = data.keys().next();
            String value = data.getString(key);

            editor.putString(key, value);
        }

        editor.commit();

        JSONObject resultData = new JSONObject();
        resultData.put("result", true);

        return resultData;
    }

    private JSONObject getSharedStorage() throws JSONException {
        HashMap<String, String> datas = (HashMap<String, String>) pref.getAll();

        JSONObject storedData = new JSONObject();
        for(String key : datas.keySet()){
            storedData.put(key, datas.get(key));
        }

        JSONObject resultData = new JSONObject();
        resultData.put("result", true);
        resultData.put("stored_data", storedData);
        return resultData;
    }

}

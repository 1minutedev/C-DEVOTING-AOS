package com.kkj.cvoting.plugin;

import com.kkj.cvoting.plugin.util.BasePlugin;

import org.json.JSONObject;

public class TestPlugin extends BasePlugin {
    private JSONObject param;
    private String callback = "";

    @Override
    protected void executePlugin(JSONObject data) {
        try {
            param = data.getJSONObject("param");

            if(param.has("callback")){
                callback = param.getString("callback");
            }

            listener.sendCallback(callback, param);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

package com.kkj.cvoting.plugin;

import com.kkj.cvoting.plugin.util.BasePlugin;
import com.kkj.cvoting.view.MainFragmentActivity;

import org.json.JSONObject;

import androidx.fragment.app.Fragment;

public class ApplicationFinishPlugin extends BasePlugin {
    private JSONObject param;

    private String callback = "";

    @Override
    protected void executePlugin(JSONObject data) {
        try {
            param = data.getJSONObject("param");

            if (param.has("callback")) {
                callback = param.getString("callback");
            }

            for (int i = MainFragmentActivity.getFragmentListSize() - 1; i >= 0; i--) {
                Fragment fragment = MainFragmentActivity.getFragmentList().get(i);
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .remove(fragment)
                        .commitAllowingStateLoss();
                MainFragmentActivity.removeFragment(fragment);
            }
            getActivity().finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

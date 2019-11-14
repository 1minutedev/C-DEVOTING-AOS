package com.kkj.cvoting.plugin;

import android.os.Bundle;

import com.kkj.cvoting.plugin.util.BasePlugin;
import com.kkj.cvoting.view.MainFragmentActivity;
import com.kkj.cvoting.view.fragment.DiscussionFragment;
import com.kkj.cvoting.view.fragment.MainFragment;

import org.json.JSONObject;

import java.util.Iterator;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

public class ShowDiscussionPlugin extends BasePlugin {
    private JSONObject param;
    private String callback = "";

    private JSONObject pageData;
    private JSONObject resultData;

    @Override
    protected void executePlugin(JSONObject data) {
        try {
            resultData = new JSONObject();

            param = data.getJSONObject("param");

            if(param.has("callback")){
                callback = param.getString("callback");
            }
            if(param.has("page_data")){
                pageData = param.getJSONObject("page_data");
            }

            DiscussionFragment mainFragment = new DiscussionFragment();

            Bundle bundle = new Bundle();
            Iterator<String> keys = pageData.keys();

            while(keys.hasNext()){
                String key = keys.next();

                if(pageData.get(key) instanceof Integer){
                    bundle.putInt(key, pageData.getInt(key));
                }else if(pageData.get(key) instanceof String){
                    bundle.putString(key, pageData.getString(key));
                }else if(pageData.get(key) instanceof Boolean){
                    bundle.putBoolean(key, pageData.getBoolean(key));
                }
            }
            mainFragment.setArguments(bundle);

            goToFragment(mainFragment);

            resultData.put("result", true);
            listener.sendCallback(callback, param);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void goToFragment(Fragment fragment) {
        ((FragmentActivity)getActivity()).getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(getActivity().getResources().getIdentifier("anim_slide_in_left", "anim", getActivity().getPackageName()), getActivity().getResources().getIdentifier("hold", "anim", getActivity().getPackageName()))
                .add(getActivity().getResources().getIdentifier("content_frame", "id", getActivity().getPackageName()), fragment, null)
                .commitAllowingStateLoss();
        MainFragmentActivity.addFragment(fragment);
    }
}

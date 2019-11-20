package com.kkj.cvoting.util.bridge;

import android.app.Activity;
import android.webkit.JavascriptInterface;

import com.kkj.cvoting.plugin.GetImagePick;
import com.kkj.cvoting.plugin.SharedStoragePlugin;
import com.kkj.cvoting.plugin.ShowDiscussionPlugin;
import com.kkj.cvoting.plugin.TestPlugin;
import com.kkj.cvoting.plugin.util.BasePlugin;
import com.kkj.cvoting.plugin.util.CallID;
import com.kkj.cvoting.plugin.util.CompleteListener;
import com.kkj.cvoting.view.webview.KKJWebView;

import org.json.JSONObject;

public class KKJBridge {
    private Activity activity;
    private KKJWebView webView;

    private CompleteListener completeListener = new CompleteListener() {
        @Override
        public void sendCallback(final String callback, final JSONObject resultData) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    webView.loadUrl("javascript:" + callback + "(" + resultData + ")");
                }
            });
        }
    };

    public KKJBridge(Activity activity, KKJWebView webView) {
        this.activity = activity;
        this.webView = webView;
    }

    @JavascriptInterface
    public void callPlugin(String paramString) {
        try {
            JSONObject data = new JSONObject(paramString);

            JSONObject param = null;
            String id = "";

            if(data.has("id")){
                id = data.getString("id");
            }
            if(data.has("param")){
                param = data.getJSONObject("param");
            }

            BasePlugin plugin = null;
            boolean canExecute = true;

            switch (id) {
                case CallID.TEST:
                    plugin = new TestPlugin();
                    break;
                case CallID.SHOW_DISCUSSION:
                    plugin = new ShowDiscussionPlugin();
                    break;
                case CallID.SET_SHARED_STORAGE:
                case CallID.GET_SHARED_STORAGE:
                    plugin = new SharedStoragePlugin();
                    break;
                case CallID.GET_IMAGE_PICK:
                    plugin = new GetImagePick();
                    break;
                default:
                    canExecute = false;
                    break;
            }

            plugin.setPlugin(activity, completeListener);

            if (canExecute) {
                plugin.execute(data);
            } else {
                JSONObject error = new JSONObject();
                error.put("result", false);
                error.put("errCode", 44444);
                error.put("errMessage", id + " plugin not found");

                String callback = "";

                if (param.has("callback")) {
                    callback = param.getString("callback");
                }

                completeListener.sendCallback(callback, error);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

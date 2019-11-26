package com.kkj.cvoting.view.webview;

import android.app.Activity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.kkj.cvoting.view.MainFragmentActivity;
import com.kkj.cvoting.view.fragment.MainFragment;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;

public class KKJWebViewClient extends WebViewClient {

    private Activity mActivity;

    public KKJWebViewClient() {

    }

    public KKJWebViewClient(Activity activity) {
        mActivity = activity;
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        ArrayList<Fragment> fragments = MainFragmentActivity.getFragmentList();
        Fragment fragment = fragments.get(fragments.size() - 1);

        if (fragment instanceof MainFragment) {
            ((MainFragment) fragment).onPagefinished(view, url);
        }

        super.onPageFinished(view, url);
    }
}

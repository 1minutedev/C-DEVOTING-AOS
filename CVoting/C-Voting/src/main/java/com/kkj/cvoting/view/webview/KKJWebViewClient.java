package com.kkj.cvoting.view.webview;

import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.fragment.app.Fragment;

import com.kkj.cvoting.view.MainFragmentActivity;
import com.kkj.cvoting.view.fragment.MainFragment;

import java.util.ArrayList;

public class KKJWebViewClient extends WebViewClient {
    @Override
    public void onPageFinished(WebView view, String url) {
        ArrayList<Fragment> fragments = MainFragmentActivity.getFragmentList();
        Fragment currentFragment = fragments.get(fragments.size() - 1);
        if(currentFragment instanceof MainFragment){
            ((MainFragment) currentFragment).onPageFinished(view, url);
        }
        super.onPageFinished(view, url);
    }
}

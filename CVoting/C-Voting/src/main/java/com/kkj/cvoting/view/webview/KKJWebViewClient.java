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
        super.onPageFinished(view, url);
    }
}

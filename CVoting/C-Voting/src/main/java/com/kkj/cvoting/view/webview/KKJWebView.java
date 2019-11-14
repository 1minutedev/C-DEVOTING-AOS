package com.kkj.cvoting.view.webview;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.webkit.ValueCallback;
import android.webkit.WebView;

public class KKJWebView extends WebView {
    public KKJWebView(Context context) {
        super(context);
    }

    public KKJWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public KKJWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void loadUrl(String url) {
        if(url.startsWith("javascript:")) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
                super.loadUrl(url);
            } else {
                evaluateJavascript(url, new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String result) {
                        Log.e("KKJWebView", "onReceiveValue result = " + result);
                    }
                });
            }
        } else {
            super.loadUrl(url);
        }
    }
}

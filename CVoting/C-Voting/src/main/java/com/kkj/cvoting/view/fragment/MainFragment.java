package com.kkj.cvoting.view.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;

import com.kkj.cvoting.R;
import com.kkj.cvoting.application.Init;
import com.kkj.cvoting.util.bridge.KKJBridge;
import com.kkj.cvoting.view.webview.KKJWebChromeClient;
import com.kkj.cvoting.view.webview.KKJWebView;
import com.kkj.cvoting.view.webview.KKJWebViewClient;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class MainFragment extends Fragment {
    private View wrapper;
    public KKJWebView webView = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        wrapper = inflater.inflate(getResources().getLayout(R.layout.fragment_main), null);
        return wrapper;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        wrapper.setClickable(true);

        initWebView();
        loadWebView();

        ImageView btnMain = wrapper.findViewById(R.id.btn_gohome);
        btnMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadWebView();
            }
        });
    }

    private void initWebView() {
        webView = wrapper.findViewById(R.id.wv_main);
        webView.setWebViewClient(new KKJWebViewClient(getActivity()));
        webView.setWebChromeClient(new KKJWebChromeClient());

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setGeolocationEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setBuiltInZoomControls(true);
        settings.setUseWideViewPort(true);
        settings.setAppCacheEnabled(false);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        settings.setRenderPriority(WebSettings.RenderPriority.HIGH);

        settings.setLoadWithOverviewMode(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        settings.setAllowUniversalAccessFromFileURLs(true);
        settings.setSupportZoom(true);
        settings.setTextZoom(100);
        settings.setDisplayZoomControls(false);

        webView.addJavascriptInterface(new KKJBridge(getActivity(), webView), "KKJBridge");
    }

    public void loadWebView() {
        String url = ((Init) getActivity().getApplication()).getStartPage();
        webView.loadUrl(url);
    }

    public void onPagefinished(WebView view, String url) {
    }
}

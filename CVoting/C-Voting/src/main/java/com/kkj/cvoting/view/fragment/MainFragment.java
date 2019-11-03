package com.kkj.cvoting.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;

import com.kkj.cvoting.R;
import com.kkj.cvoting.application.Init;
import com.kkj.cvoting.view.webview.KKJWebChromeClient;
import com.kkj.cvoting.view.webview.KKJWebView;
import com.kkj.cvoting.view.webview.KKJWebViewClient;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class MainFragment extends Fragment {
    private View wrapper;
    private KKJWebView webView = null;

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
    }

    private void initWebView(){
        webView = wrapper.findViewById(R.id.wv_main);
        webView.setWebViewClient(new KKJWebViewClient());
        webView.setWebChromeClient(new KKJWebChromeClient());

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
    }

    private void loadWebView(){
        String url = ((Init) getActivity().getApplication()).getStartPage();
        webView.loadUrl(url);
    }
}

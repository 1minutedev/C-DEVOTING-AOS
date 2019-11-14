package com.kkj.cvoting.view.webview;

import android.util.Log;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class KKJWebChromeClient extends WebChromeClient {

    @Override
    public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
        String currentTime = new SimpleDateFormat("MM-dd HH:mm:ss.SSS").format(Calendar.getInstance().getTime());

        String logLevel = "D";
        ConsoleMessage.MessageLevel messageLevel = consoleMessage.messageLevel();
        if (messageLevel == ConsoleMessage.MessageLevel.ERROR) {
            logLevel = "E";
        } else if (messageLevel == ConsoleMessage.MessageLevel.LOG) {
            logLevel = "L";
        } else if (messageLevel == ConsoleMessage.MessageLevel.TIP) {
            logLevel = "T";
        } else if (messageLevel == ConsoleMessage.MessageLevel.WARNING) {
            logLevel = "W";
        }

        Log.e("onConsoleMessage", currentTime + " " + logLevel + "/chromium: " + consoleMessage.message() + ", source: " + consoleMessage.sourceId() + ", line: (" + consoleMessage.lineNumber() + ")");

        return super.onConsoleMessage(consoleMessage);
    }
}

package com.kkj.cvoting.plugin.util;

import org.json.JSONObject;

public interface CompleteListener {
    void sendCallback(String callback, JSONObject resultData);
}

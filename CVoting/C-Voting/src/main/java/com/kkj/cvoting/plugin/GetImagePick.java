package com.kkj.cvoting.plugin;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.kkj.cvoting.plugin.util.BasePlugin;
import com.kkj.cvoting.plugin.util.CompleteListener;
import com.kkj.cvoting.util.ConfigVariable;

import org.json.JSONObject;

public class GetImagePick extends BasePlugin {
    private JSONObject param;
    private static String sCallback = "";

    private static CompleteListener sListener;

    @Override
    protected void executePlugin(JSONObject data) {
        try {
            sListener = listener;

            param = data.getJSONObject("param");

            if (param.has("callback")) {
                sCallback = param.getString("callback");
            }

            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
            getActivity().startActivityForResult(intent, ConfigVariable.REQUEST_CODE_GET_IMAGE_PICK);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void onActivityResult(Intent data, Activity activity) {
        JSONObject result = new JSONObject();

        try {
            Uri uri = data.getData();
            result.put("result", true);
            result.put("image_path", getRealpath(uri, activity));
        }catch(Exception e){
            e.printStackTrace();
        }

        sListener.sendCallback(sCallback, result);
    }

    public static String getRealpath(Uri uri, Activity activity) {
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor c = activity.getContentResolver().query(uri, proj, null, null, null);
        int index = c.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

        c.moveToFirst();
        String path = c.getString(index);

        return path;
    }
}

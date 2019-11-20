package com.kkj.cvoting.util;

public class ConfigVariable {
    public static boolean isRelease = false;

    public static int CONTENTS_MODE = 0;
    public static String CONTENTS_PATH = "";

    public static final int CONTENTS_MODE_ASSETS = 0;
    public static final int CONTENTS_MODE_EXTERNAL = 1;
    public static final int CONTENTS_MODE_ABSOLUTE = 2;

    public static final String CONTENTS_HTML = "contents/index.html";

    public static final int REQUEST_CODE_SETTINGVIEW = 40001;
    public static final int REQUEST_CODE_GET_IMAGE_PICK = 40002;
}

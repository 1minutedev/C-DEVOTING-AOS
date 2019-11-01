package com.kkj.cvoting.view.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.kkj.cvoting.R;

public class SettingActivity extends Activity {
    private static final String TAG = SettingActivity.class.toString();

    private Spinner spinnerMode;
    private ArrayAdapter<String> modeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        initLayout();
    }

    private void initLayout(){
        //contents 모드
        String[] modeList = getResources().getStringArray(R.array.mode);
        modeAdapter = new ArrayAdapter<String>(this, R.layout.activity_setting, modeList);

        spinnerMode = findViewById(R.id.spinner_mode);
        spinnerMode.setAdapter(modeAdapter);
    }

}

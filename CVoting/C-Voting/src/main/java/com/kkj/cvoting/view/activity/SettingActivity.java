package com.kkj.cvoting.view.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.kkj.cvoting.R;

public class SettingActivity extends Activity {
    private static final String TAG = SettingActivity.class.toString();

    private Spinner spinnerMode;
    private ArrayAdapter<CharSequence> modeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        initLayout();
    }

    private void initLayout(){
        //contents 모드
        modeAdapter = ArrayAdapter.createFromResource(this, R.array.mode, android.R.layout.simple_spinner_dropdown_item);

        spinnerMode = findViewById(R.id.spinner_mode);
        spinnerMode.setAdapter(modeAdapter);
    }

}

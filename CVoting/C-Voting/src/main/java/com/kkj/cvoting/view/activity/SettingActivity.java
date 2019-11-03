package com.kkj.cvoting.view.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.kkj.cvoting.util.ConfigVariable;
import com.kkj.cvoting.R;

public class SettingActivity extends Activity implements View.OnClickListener {
    private static final String TAG = SettingActivity.class.toString();

    /**
     * 저장된 설정 정보를 가져옴.
     */
    private SharedPreferences mPref = null;

    /**
     * 컨텐츠 모드를 설정할 수 있는 spinner
     */
    private Spinner spinnerMode;
    private ArrayAdapter<CharSequence> modeAdapter;
    private int mode = 0;

    /**
     * 컨텐츠 경로를 설정하는 edit text
     */
    private EditText etPath = null;
    private String path = "";

    /**
     * 설정 저장을 하는 버튼
     */
    private Button btnSave = null;

    private boolean isFirst = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        getData();
        initLayout();
    }

    private void initLayout(){
        modeAdapter = ArrayAdapter.createFromResource(this, R.array.mode, android.R.layout.simple_spinner_dropdown_item);

        spinnerMode = findViewById(R.id.spinner_mode);
        spinnerMode.setAdapter(modeAdapter);
        spinnerMode.setSelection(mode);

        etPath = findViewById(R.id.et_path);
        etPath.setText(path);

        btnSave = findViewById(R.id.btn_save);
        btnSave.setOnClickListener(this);
    }

    private void getData(){
        mPref = getSharedPreferences("setting", MODE_PRIVATE);

        mode = mPref.getInt("mode", ConfigVariable.CONTENTS_MODE);
        path = mPref.getString("path", ConfigVariable.CONTENTS_PATH);
    }

    private void saveData(){
        ConfigVariable.CONTENTS_MODE = spinnerMode.getSelectedItemPosition();
        ConfigVariable.CONTENTS_PATH = etPath.getText().toString();

        SharedPreferences.Editor editor = mPref.edit();

        editor.putInt("mode", ConfigVariable.CONTENTS_MODE);
        editor.putString("path", ConfigVariable.CONTENTS_PATH);

        editor.commit();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_save:
                if(isFirst) {
                    isFirst = false;
                    saveData();
                    finish();
                }
                break;
        }
    }
}

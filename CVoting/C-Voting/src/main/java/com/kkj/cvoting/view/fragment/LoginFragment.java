package com.kkj.cvoting.view.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.kkj.cvoting.R;
import com.kkj.cvoting.view.MainFragmentActivity;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class LoginFragment extends Fragment {
    private View wrapper;
    private Button btnCvoting;

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        wrapper = inflater.inflate(getResources().getLayout(R.layout.fragment_login), null);
        return wrapper;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        wrapper.setClickable(true);

        btnCvoting = wrapper.findViewById(R.id.btn_c_voting);
        btnCvoting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainFragment mainFragment = new MainFragment();

                //데이터 넘길게 있다면,
                Bundle bundle = new Bundle();
                mainFragment.setArguments(bundle);

                goToFragment(mainFragment);
            }
        });

        pref = getActivity().getSharedPreferences("default", Context.MODE_PRIVATE);
        editor = pref.edit();
        setData();
    }

    private void goToFragment(Fragment fragment) {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(getResources().getIdentifier("anim_slide_in_left", "anim", getActivity().getPackageName()), getResources().getIdentifier("hold", "anim", getActivity().getPackageName()))
                .add(getResources().getIdentifier("content_frame", "id", getActivity().getPackageName()), fragment, null)
                .commitAllowingStateLoss();
        MainFragmentActivity.addFragment(fragment);
    }

    private void setData() {
        try {
            //저장된 값이 있으면 리턴.
            if(!TextUtils.isEmpty(pref.getString("baseData", ""))){
                return;
            }

            InputStream is = getActivity().getAssets().open("data.json");
            String jsonStr = inputStreamToString(is);

            JSONObject json = new JSONObject(jsonStr);
            editor.putString("baseData", json.toString());
            editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String inputStreamToString(InputStream is) {
        StringBuffer out = new StringBuffer();
        byte[] b = new byte[4096];
        try {
            String str = "";
            for (int n; (n = is.read(b)) != -1; ) {
                str = new String(b, 0, n);
                if (!str.equals("\n") && !str.equals("\t") && !str.equals("\r"))
                    out.append(new String(b, 0, n));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out.toString();
    }
}

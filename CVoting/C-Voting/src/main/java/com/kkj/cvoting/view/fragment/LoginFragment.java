package com.kkj.cvoting.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.kkj.cvoting.R;
import com.kkj.cvoting.view.MainFragmentActivity;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class LoginFragment extends Fragment {
    private View wrapper;
    private Button btnCvoting;

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

//        btnCvoting = wrapper.findViewById(R.id.btn_cvoting);
//        btnCvoting.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                MainFragment mainFragment = new MainFragment();
//
//                //데이터 넘길게 있다면,
//                Bundle bundle = new Bundle();
//                mainFragment.setArguments(bundle);
//
//                goToFragment(mainFragment);
//            }
//        });
    }

    private void goToFragment(Fragment fragment) {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(getResources().getIdentifier("anim_slide_in_left", "anim", getActivity().getPackageName()), getResources().getIdentifier("hold", "anim", getActivity().getPackageName()))
                .add(getResources().getIdentifier("content_frame", "id", getActivity().getPackageName()), fragment, null)
                .commitAllowingStateLoss();
        MainFragmentActivity.addFragment(fragment);
    }
}

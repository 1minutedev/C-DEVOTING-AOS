package com.kkj.cvoting.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kkj.cvoting.R;
import com.kkj.cvoting.view.MainFragmentActivity;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SplashFragment extends Fragment {
    private View wrapper;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        wrapper = inflater.inflate(getResources().getLayout(R.layout.fragment_splash), null);
        return wrapper;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        wrapper.setClickable(true);

        LoginFragment loginFragment = new LoginFragment();

        //데이터 넘길게 있다면,
        Bundle bundle = new Bundle();
        loginFragment.setArguments(bundle);

        goToFragment(loginFragment);
    }

    /**
     * 프래그먼트 액티비티에 프래그먼트 추가
     *
     * @param fragment
     */
    private void goToFragment(Fragment fragment) {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(getResources().getIdentifier("anim_slide_in_left", "anim", getActivity().getPackageName()), getResources().getIdentifier("hold", "anim", getActivity().getPackageName()))
                .add(getResources().getIdentifier("content_frame", "id", getActivity().getPackageName()), fragment, null)
                .commitAllowingStateLoss();

        MainFragmentActivity.addFragment(fragment);
    }
}

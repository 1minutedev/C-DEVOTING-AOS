package com.kkj.cvoting.view;


import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.kkj.cvoting.R;
import com.kkj.cvoting.view.fragment.SplashFragment;

import java.util.ArrayList;

public class MainFragmentActivity extends FragmentActivity {
    /**
     * 스택 관리를 위한 ArrayList
     */
    private static ArrayList<Fragment> fragmentList;

    /**
     * 현재 쌓인 프래그먼트 리스트
     *
     * @return
     */
    public static ArrayList<Fragment> getFragmentList() {
        if (fragmentList == null) {
            fragmentList = new ArrayList<Fragment>();
        }
        return fragmentList;
    }

    /**
     * 현재 프래그먼트 리스트 사이즈
     *
     * @return
     */
    public static int getFragmentListSize() {
        return fragmentList.size();
    }

    /**
     * 리스트에서 프래그먼트 추가
     *
     * @param fragment
     */
    public static void addFragment(Fragment fragment) {
        getFragmentList().add(fragment);
        Log.e("MainFragmentActivity", "addFragment current Stack == " + getFragmentListSize());
    }

    /**
     * 리스트에서 프래그먼트 제거
     *
     * @param fragment
     */
    public static void removeFragment(Fragment fragment) {
        getFragmentList().remove(fragment);
        Log.e("MainFragmentActivity", "removeFragment current Stack == " + getFragmentListSize());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragmentactivity_main);

        /**
         * 최초 프래그먼트 추가
         */
        SplashFragment splashFragment = new SplashFragment();

        // 데이터 넘길게 있다면 Bundle로 넘김
        Bundle bundle = new Bundle();
        splashFragment.setArguments(bundle);

        goToFragment(splashFragment);
    }

    /**
     * 백 버튼 이벤트
     * <p>
     * 스택에 따라 현재 프래그먼트를 제거하면서,
     * 이전 프래그먼트를 보여주도록 처리
     */
    @Override
    public void onBackPressed() {
        if (getFragmentList().size() == 0) {
            finish();
            return;
        } else {
            if (getFragmentList().size() > 1) {
                final Fragment currentFragment = getFragmentList().get(getFragmentListSize() - 1);
                final Fragment showFragment = getFragmentList().get(getFragmentListSize() - 2);

                getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(getResources().getIdentifier("hold", "anim", getPackageName()), getResources().getIdentifier("anim_slide_out_right", "anim", getPackageName()))
                        .remove(currentFragment)
                        .show(showFragment)
                        .commitAllowingStateLoss();
                removeFragment(currentFragment);
            } else {
                finish();
                removeFragment(getFragmentList().get(getFragmentListSize() - 1));
            }
        }
    }

    /**
     * 프래그먼트 액티비티에 프래그먼트 추가
     *
     * @param fragment
     */
    private void goToFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .add(getResources().getIdentifier("content_frame", "id", getPackageName()), fragment, null)
                .commit();

        //스택 관리를 위해 list에 추가
        addFragment(fragment);
    }
}
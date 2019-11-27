package com.kkj.cvoting.view;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;

import com.kkj.cvoting.R;
import com.kkj.cvoting.plugin.GetImagePick;
import com.kkj.cvoting.util.ConfigVariable;
import com.kkj.cvoting.view.activity.SettingActivity;
import com.kkj.cvoting.view.fragment.DiscussionFragment;
import com.kkj.cvoting.view.fragment.LoginFragment;
import com.kkj.cvoting.view.fragment.MainFragment;
import com.kkj.cvoting.view.fragment.SplashFragment;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

public class MainFragmentActivity extends FragmentActivity {
    /**
     * 스택 관리를 위한 ArrayList
     */
    private static ArrayList<Fragment> fragmentList;
    private boolean touched = false;

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
    }

    /**
     * 리스트에서 프래그먼트 제거
     *
     * @param fragment
     */
    public static void removeFragment(Fragment fragment) {
        getFragmentList().remove(fragment);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragmentactivity_main);

        checkPermission();
    }

    /**
     * 최초 프래그먼트 추가
     */
    private void nextProcess() {
        SplashFragment splashFragment = new SplashFragment();

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
    private int term = 2000;
    private int finishCnt = 0;

    @Override
    public void onBackPressed() {
        if (getFragmentList().size() < 4) {
            boolean closeFragment = true;

            final Fragment currentFragment = getFragmentList().get(getFragmentListSize() - 1);
            if (currentFragment instanceof MainFragment) {
                if (((MainFragment) currentFragment).webView.canGoBack()) {
                    closeFragment = false;
                    ((MainFragment) currentFragment).webView.goBack();
                    return;
                }
            }

            if (closeFragment) {
                finishCnt++;
                if (finishCnt > 1) {
                    for (int i = getFragmentListSize() - 1; i >= 0; i--) {
                        Fragment fragment = getFragmentList().get(i);
                        getSupportFragmentManager()
                                .beginTransaction()
                                .remove(fragment)
                                .commitAllowingStateLoss();
                        removeFragment(fragment);
                    }
                    finish();
                } else {
                    Toast.makeText(this, "한 번 더 이전 버튼을 누르면 앱을 종료합니다.", Toast.LENGTH_SHORT).show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            finishCnt = 0;
                        }
                    }, term);
                }
                return;
            }
        } else {
            final Fragment currentFragment = getFragmentList().get(getFragmentListSize() - 1);
            final Fragment showFragment = getFragmentList().get(getFragmentListSize() - 2);

            if(currentFragment instanceof DiscussionFragment){
                if(DiscussionFragment.showingPopup){
                    ((DiscussionFragment)currentFragment).closePopup();
                    return;
                }
            } else if(showFragment instanceof LoginFragment){
                finishCnt++;
                if (finishCnt > 1) {
                    for (int i = getFragmentListSize() - 1; i >= 0; i--) {
                        Fragment fragment = getFragmentList().get(i);
                        getSupportFragmentManager()
                                .beginTransaction()
                                .remove(fragment)
                                .commitAllowingStateLoss();
                        removeFragment(fragment);
                    }
                    finish();
                } else {
                    Toast.makeText(this, "한 번 더 이전 버튼을 누르면 앱을 종료합니다.", Toast.LENGTH_SHORT).show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            finishCnt = 0;
                        }
                    }, term);
                }
                return;
            }

            getSupportFragmentManager()
                    .beginTransaction()
                    .remove(currentFragment)
                    .show(showFragment)
                    .commitAllowingStateLoss();
            removeFragment(currentFragment);

//            if(showFragment instanceof MainFragment){
//                ((MainFragment)showFragment).loadWebView();
//            }
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

        addFragment(fragment);
    }

    public void comeBackHome() {
        final Fragment currentFragment = getFragmentList().get(getFragmentListSize() - 1);
        final Fragment showFragment = getFragmentList().get(getFragmentListSize() - 2);

        getSupportFragmentManager()
                .beginTransaction()
                .remove(currentFragment)
                .show(showFragment)
                .commitAllowingStateLoss();
        removeFragment(currentFragment);

        ((MainFragment) showFragment).loadWebView();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        int touchCount = event.getPointerCount();

        //환경 설정 접근하기 위한 히든 제스쳐 2 : 3점식 터치
        if (touchCount == 3 && !touched) {
            if (!ConfigVariable.isRelease) {
                touched = true;
                Intent intent = new Intent(this, SettingActivity.class);
                startActivityForResult(intent, ConfigVariable.REQUEST_CODE_SETTINGVIEW);
            }
        }

        return super.dispatchTouchEvent(event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ConfigVariable.REQUEST_CODE_SETTINGVIEW) {
            touched = false;

            if (getFragmentList().get(getFragmentListSize() - 1) instanceof MainFragment) {
                ((MainFragment) getFragmentList().get(getFragmentListSize() - 1)).loadWebView();
            }
        } else if (requestCode == ConfigVariable.REQUEST_CODE_GET_IMAGE_PICK) {
            GetImagePick.onActivityResult(data, this);
        }
    }

    private void checkPermission() {
        String permissions[] = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            nextProcess();
        } else {
            int cnt = 0;

            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    || checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(permissions, 0);
            } else {
                nextProcess();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 0) {
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    nextProcess();
                } else {
                    Toast.makeText(getApplicationContext(), "앱에서 필요한 권한이 없습니다.", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        }
    }
}
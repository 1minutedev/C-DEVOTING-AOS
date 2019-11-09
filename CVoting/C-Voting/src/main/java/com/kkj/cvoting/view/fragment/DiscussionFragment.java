package com.kkj.cvoting.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kkj.cvoting.R;
import com.kkj.cvoting.util.SlidingUpPanelLayout;
import com.kkj.cvoting.view.MainFragmentActivity;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import me.relex.circleindicator.CircleIndicator;

public class DiscussionFragment extends Fragment {
    private View wrapper;

    private SlidingUpPanelLayout mLayout;

    FragmentPagerAdapter adapterViewPager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        wrapper = inflater.inflate(getResources().getLayout(R.layout.fragment_discussion), null);
        return wrapper;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        wrapper.setClickable(true);

        mLayout = (SlidingUpPanelLayout) wrapper.findViewById(R.id.sliding_layout);
        mLayout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
            }
            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
            }
        });
        mLayout.setFadeOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
            }
        });

        ViewPager vpPager = (ViewPager) wrapper.findViewById(R.id.vp_pager);
        adapterViewPager = new CustomPagerAdapter(getActivity().getSupportFragmentManager());
        vpPager.setAdapter(adapterViewPager);

        CircleIndicator indicator = (CircleIndicator) wrapper.findViewById(R.id.indicator);
        indicator.setViewPager(vpPager);
    }

    public static class CustomPagerAdapter extends FragmentPagerAdapter {
        private static int NUM_ITEMS = 2;

        public CustomPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        // Returns total number of pages
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return DiscussionBottomFirstFragment.newInstance(0, "Page # 1");
                case 1:
                    return DiscussionBottomSecondFragment.newInstance(1, "Page # 2");
                default:
                    return null;
            }
        }

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            return "Page " + position;
        }

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

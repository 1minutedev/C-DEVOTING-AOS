package com.kkj.cvoting.view.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kkj.cvoting.R;
import com.kkj.cvoting.util.CustomViewPager;
import com.kkj.cvoting.util.SlidingUpPanelLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import me.relex.circleindicator.CircleIndicator;

public class DiscussionFragment extends Fragment {
    private View wrapper;

    private SlidingUpPanelLayout mLayout;
    private FragmentPagerAdapter adapterViewPager;

    private TextView tvSubject;
    private TextView tvWriter;
    private TextView tvContents;
    private TextView tvRegDate;
    private TextView tvDday;
    private ImageView ivImage;
    private LinearLayout llChanPer;
    private LinearLayout llBanPer;
    private LinearLayout llGitaPer;
    private TextView tvChan;
    private TextView tvBan;
    private TextView tvGita;

    private JSONObject bottomFirstPageData;
    private JSONObject bottomSecondPageData;

    private String subject = "";
    private String writer = "";
    private String contents = "";
    private String regDate = "";
    private String imagePath = "";
    private String startDate = "";
    private String endDate = "";
    private String dDay = "";

    private int totalCnt;
    private int chanCnt;
    private int banCnt;
    private int gitaCnt;

    private String chanPer = "";
    private String banPer = "";
    private String gitaPer = "";

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

        getData();
        setLayout();

        ImageView btnMain = wrapper.findViewById(R.id.btn_gohome);
        btnMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
    }

    private void getData() {
        try {
            Bundle data = getArguments();
            JSONObject pageData = new JSONObject(data.getString("page_data"));
            JSONObject discussionPageData = null;

            SharedPreferences pref = getActivity().getSharedPreferences("default", Context.MODE_PRIVATE);
            JSONObject allData = new JSONObject(pref.getString("baseData", ""));
            JSONArray reviewList = allData.getJSONArray("ReviewList");

            int idx = pageData.getInt("idx");

            for (int i = 0; i < reviewList.length(); i++) {
                JSONObject reviewData = reviewList.getJSONObject(i);
                if (reviewData.getInt("idx") == idx) {
                    discussionPageData = reviewData;
                }
            }

            if (discussionPageData.has("subject")) {
                subject = discussionPageData.getString("subject");
            }
            if (discussionPageData.has("writer")) {
                writer = discussionPageData.getString("writer");
            }
            if (discussionPageData.has("regDate")) {
                regDate = discussionPageData.getString("regDate");
            }
            if (discussionPageData.has("startDate")) {
                startDate = discussionPageData.getString("startDate");
            }
            if (discussionPageData.has("endDate")) {
                endDate = discussionPageData.getString("endDate");
            }
            if (discussionPageData.has("img")) {
                imagePath = discussionPageData.getString("img");
            }
            if (discussionPageData.has("content")) {
                contents = discussionPageData.getString("content");
            }

            if (discussionPageData.has("totalPartiCnt")) {
                totalCnt = discussionPageData.getInt("totalPartiCnt");
            }

            if (discussionPageData.has("agreeCnt")) {
                chanCnt = discussionPageData.getInt("agreeCnt");
            }
            if (discussionPageData.has("oppCnt")) {
                banCnt = discussionPageData.getInt("oppCnt");
            }
            if (discussionPageData.has("neutCnt")) {
                gitaCnt = discussionPageData.getInt("neutCnt");
            }

            double chanPer = chanCnt * 100 / totalCnt;
            double banPer = banCnt * 100 / totalCnt;
            int gitaPer = 100 - (int) chanPer - (int) banPer;

            this.chanPer = String.valueOf((int) chanPer);
            this.banPer = String.valueOf((int) banPer);
            this.gitaPer = String.valueOf(gitaPer);

            bottomFirstPageData = new JSONObject();
            bottomFirstPageData.put("idx", idx);
            bottomFirstPageData.put("totalCnt", totalCnt);
            bottomFirstPageData.put("agreeCnt", chanCnt);
            bottomFirstPageData.put("oppCnt", banCnt);
            bottomFirstPageData.put("neutCnt", gitaCnt);

            bottomSecondPageData = new JSONObject();
            bottomSecondPageData.put("idx", idx);
            if (discussionPageData.has("cmtList")) {
                bottomSecondPageData.put("cmtList", discussionPageData.getJSONArray("cmtList"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setLayout() {
        // 커스텀 뷰(슬라이딩)
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

        // ViewPager
        CustomViewPager vpPager = (CustomViewPager) wrapper.findViewById(R.id.vp_pager);
        vpPager.setSlidingLayout(mLayout);

        vpPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    SlidingUpPanelLayout.isLock = true;
                } else {
                    SlidingUpPanelLayout.isLock = false;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        adapterViewPager = new CustomPagerAdapter(getChildFragmentManager());
        vpPager.setAdapter(adapterViewPager);
        vpPager.setSlidingLayout(mLayout);

        CircleIndicator indicator = (CircleIndicator) wrapper.findViewById(R.id.indicator);
        indicator.setViewPager(vpPager);

        //값 설정
        tvSubject = wrapper.findViewById(R.id.tv_subject);
        tvWriter = wrapper.findViewById(R.id.tv_writer);
        tvRegDate = wrapper.findViewById(R.id.tv_regdate);
        tvDday = wrapper.findViewById(R.id.tv_dday);
        ivImage = wrapper.findViewById(R.id.iv_image);
        tvContents = wrapper.findViewById(R.id.tv_contents);

        llChanPer = wrapper.findViewById(R.id.ll_chan_per);
        tvChan = wrapper.findViewById(R.id.tv_chan_per);

        llBanPer = wrapper.findViewById(R.id.ll_ban_per);
        tvBan = wrapper.findViewById(R.id.tv_ban_per);

        llGitaPer = wrapper.findViewById(R.id.ll_gita_per);
        tvGita = wrapper.findViewById(R.id.tv_gita_per);

        //제목
        tvSubject.setText(subject);
        //작성자
        tvWriter.setText(writer);

        //등록일자 현재 전달받고 있는 데이터 형식은 yyyyMMddHHmm 기획서상의 데이터 형식은 MM/dd HH:mm
        SimpleDateFormat curFormat = new SimpleDateFormat("yyyyMMddHHmm");
        SimpleDateFormat newFormat = new SimpleDateFormat("MM/dd HH:mm");

        try {
            Date curDate = curFormat.parse(regDate);
            tvRegDate.setText(newFormat.format(curDate));
        } catch (Exception e) {
            e.printStackTrace();
        }

        //D-Day 현재 startDate와 endDate를 전달받고 있는 상태이므로, 현재 시간과 endDate로 D-Day 를 구하도록함.
        SimpleDateFormat endDateFormat = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat dDayFormat = new SimpleDateFormat("dd");

        try {
            String cur = endDateFormat.format(Calendar.getInstance().getTime());

            Date endDate = endDateFormat.parse(this.endDate);
            Date curDate = endDateFormat.parse(cur);

            dDay = dDayFormat.format(endDate.getTime() - curDate.getTime());
//            dDay = dDayFormat.format(endDate.getTime() - curDate.getTime() - (60 * 60 * 24 * 1000));
            tvDday.setText(dDay);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //이미지 패스를 전달 받아야할 듯 함. 상의해야 할 것으로 보임.
        try {
            InputStream is = getActivity().getAssets().open("contents/assets/common/images/posts/" + imagePath);
            ivImage.setImageBitmap(BitmapFactory.decodeStream(is));
        } catch (Exception e) {
            e.printStackTrace();
        }

        //컨텐츠
        tvContents.setText(contents);

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int deviceWidth = displayMetrics.widthPixels;

        tvChan.setText(chanPer + "%");
        tvBan.setText(banPer + "%");
        tvGita.setText(gitaPer + "%");

        LinearLayout.LayoutParams chanPerParams = (LinearLayout.LayoutParams) llChanPer.getLayoutParams();
        chanPerParams.width = deviceWidth * Integer.parseInt(chanPer) / 100;
        llChanPer.setLayoutParams(chanPerParams);

        LinearLayout.LayoutParams banPerParams = (LinearLayout.LayoutParams) llBanPer.getLayoutParams();
        banPerParams.width = deviceWidth * Integer.parseInt(banPer) / 100;
        llBanPer.setLayoutParams(banPerParams);

        LinearLayout.LayoutParams gitaPerParams = (LinearLayout.LayoutParams) llGitaPer.getLayoutParams();
        gitaPerParams.width = deviceWidth - chanPerParams.width - banPerParams.width;
        llGitaPer.setLayoutParams(gitaPerParams);
    }

    public void refreshPercent(String chanPer, String banPer, String gitaPer){
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int deviceWidth = displayMetrics.widthPixels;

        tvChan.setText(chanPer + "%");
        tvBan.setText(banPer + "%");
        tvGita.setText(gitaPer + "%");

        LinearLayout.LayoutParams chanPerParams = (LinearLayout.LayoutParams) llChanPer.getLayoutParams();
        chanPerParams.width = deviceWidth * Integer.parseInt(chanPer) / 100;
        llChanPer.setLayoutParams(chanPerParams);

        LinearLayout.LayoutParams banPerParams = (LinearLayout.LayoutParams) llBanPer.getLayoutParams();
        banPerParams.width = deviceWidth * Integer.parseInt(banPer) / 100;
        llBanPer.setLayoutParams(banPerParams);

        LinearLayout.LayoutParams gitaPerParams = (LinearLayout.LayoutParams) llGitaPer.getLayoutParams();
        gitaPerParams.width = deviceWidth - chanPerParams.width - banPerParams.width;
        llGitaPer.setLayoutParams(gitaPerParams);
    }

    public class CustomPagerAdapter extends FragmentPagerAdapter {
        private int NUM_ITEMS = 2;

        public CustomPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return DiscussionBottomFirstFragment.newInstance(0, bottomFirstPageData.toString(), mLayout, DiscussionFragment.this);
                case 1:
                    return DiscussionBottomSecondFragment.newInstance(1, bottomSecondPageData.toString(), mLayout);
                default:
                    return null;
            }
        }
    }
}

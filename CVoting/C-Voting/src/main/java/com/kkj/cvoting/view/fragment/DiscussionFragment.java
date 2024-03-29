package com.kkj.cvoting.view.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.kkj.cvoting.R;
import com.kkj.cvoting.util.CustomViewPager;
import com.kkj.cvoting.util.SlidingUpPanelLayout;
import com.kkj.cvoting.view.MainFragmentActivity;
import com.kkj.cvoting.view.fragment.discussion.ReplyListAdapter;

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

public class DiscussionFragment extends Fragment implements View.OnClickListener {
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

    public static RelativeLayout popupLayout;
    public static LinearLayout llReplyList;
    public static ScrollView svReplyList;

    private TextView chan;
    private TextView ban;

    private EditText etContents;
    private ImageView enter;

    private String type = "gita";
    private int idx = 0;

    private static int cmtIdx = 0;
    public static boolean showingPopup = false;
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

        showingPopup = false;

        getData();
        setLayout();

        ImageView btnMain = wrapper.findViewById(R.id.btn_gohome);
        ImageView btnBack = wrapper.findViewById(R.id.btn_back);

        popupLayout = wrapper.findViewById(R.id.rl_popup);
        ImageView ivClose = wrapper.findViewById(R.id.iv_close);

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closePopup();
            }
        });

        llReplyList = wrapper.findViewById(R.id.ll_reply_list);
        svReplyList = wrapper.findViewById(R.id.sv_reply_list);

        chan = wrapper.findViewById(R.id.tv_chan);
        ban = wrapper.findViewById(R.id.tv_ban);
        enter = wrapper.findViewById(R.id.iv_enter);
        etContents = wrapper.findViewById(R.id.et_contents);

        chan.setOnClickListener(this);
        ban.setOnClickListener(this);
        enter.setOnClickListener(this);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainFragmentActivity) getActivity()).comeBackHome();
            }
        });
        btnMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainFragmentActivity) getActivity()).comeBackHome();
            }
        });

        SlidingUpPanelLayout.isLock = true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_chan:
                if (type.equals("chan")) {
                    type = "gita";
                    chan.setBackground(getActivity().getResources().getDrawable(R.drawable.circle_unselected));
                    chan.setTextColor(getActivity().getResources().getColor(R.color.discussion_bottom_text_default));
                } else {
                    type = "chan";
                    chan.setBackground(getActivity().getResources().getDrawable(R.drawable.circle_selected_1));
                    chan.setTextColor(getActivity().getResources().getColor(R.color.discussion_bottom_text_selected));
                    ban.setBackground(getActivity().getResources().getDrawable(R.drawable.circle_unselected));
                    ban.setTextColor(getActivity().getResources().getColor(R.color.discussion_bottom_text_default));
                }
                break;
            case R.id.tv_ban:
                if (type.equals("ban")) {
                    type = "gita";
                    ban.setBackground(getActivity().getResources().getDrawable(R.drawable.circle_unselected));
                    ban.setTextColor(getActivity().getResources().getColor(R.color.discussion_bottom_text_default));
                } else {
                    type = "ban";
                    chan.setBackground(getActivity().getResources().getDrawable(R.drawable.circle_unselected));
                    chan.setTextColor(getActivity().getResources().getColor(R.color.discussion_bottom_text_default));
                    ban.setBackground(getActivity().getResources().getDrawable(R.drawable.circle_selected_2));
                    ban.setTextColor(getActivity().getResources().getColor(R.color.discussion_bottom_text_selected));
                }
                break;
            case R.id.iv_enter:
                String content = etContents.getText().toString();
                if (TextUtils.isEmpty(content)) {
                    Toast.makeText(getActivity(), "입력한 내용이 없습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    addList(etContents.getText().toString());
                    etContents.setText("");

                    type = "gita";

                    ban.setBackground(getActivity().getResources().getDrawable(R.drawable.circle_unselected));
                    ban.setTextColor(getActivity().getResources().getColor(R.color.discussion_bottom_text_default));

                    chan.setBackground(getActivity().getResources().getDrawable(R.drawable.circle_unselected));
                    chan.setTextColor(getActivity().getResources().getColor(R.color.discussion_bottom_text_default));

                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(etContents.getWindowToken(), 0);
                }
                break;
            default:
                break;
        }
    }

    private void getData() {
        try {
            Bundle data = getArguments();
            JSONObject pageData = new JSONObject(data.getString("page_data"));
            JSONObject discussionPageData = null;

            SharedPreferences pref = getActivity().getSharedPreferences("default", Context.MODE_PRIVATE);
            JSONObject allData = new JSONObject(pref.getString("baseData", ""));
            JSONArray reviewList = allData.getJSONArray("ReviewList");

            idx = pageData.getInt("idx");

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

            if(totalCnt == 0){
                this.chanPer = "0";
                this.banPer = "0";
                this.gitaPer = "0";
            } else {
                double chanPer = chanCnt == 0 ? 0 : chanCnt * 100 / totalCnt;
                double banPer = banCnt == 0 ? 0 : banCnt * 100 / totalCnt;
                int gitaPer = gitaCnt == 0 ? 0 : 100 - (int) chanPer - (int) banPer;

                this.chanPer = String.valueOf((int) chanPer);
                this.banPer = String.valueOf((int) banPer);
                this.gitaPer = String.valueOf(gitaPer);
            }

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
            tvDday.setText(dDay.startsWith("0") ? (dDay.length() > 1 ? dDay.substring(1) : "0") : dDay);
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

        int cPer = Integer.parseInt(chanPer);
        int bPer = Integer.parseInt(banPer);
        int gPer = Integer.parseInt(gitaPer);

        if(cPer == 0 && bPer == 0 && gPer == 0){
            cPer = 33;
            bPer = 33;
        }

        LinearLayout.LayoutParams chanPerParams = (LinearLayout.LayoutParams) llChanPer.getLayoutParams();
        chanPerParams.width = deviceWidth * cPer / 100;
        llChanPer.setLayoutParams(chanPerParams);

        LinearLayout.LayoutParams banPerParams = (LinearLayout.LayoutParams) llBanPer.getLayoutParams();
        banPerParams.width = deviceWidth * bPer / 100;
        llBanPer.setLayoutParams(banPerParams);

        LinearLayout.LayoutParams gitaPerParams = (LinearLayout.LayoutParams) llGitaPer.getLayoutParams();
        gitaPerParams.width = deviceWidth - chanPerParams.width - banPerParams.width;
        llGitaPer.setLayoutParams(gitaPerParams);
    }

    public void refreshPercent(String chanPer, String banPer, String gitaPer) {
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

    public static JSONArray replyCmtList;

    public static void showPopup(JSONArray data, Activity activity, int cmtIdx) {
        showingPopup = true;

        try {
            replyCmtList = data;
            DiscussionFragment.cmtIdx = cmtIdx;

            if (popupLayout != null) {
                if (popupLayout.getVisibility() == View.GONE) {
                    popupLayout.setVisibility(View.VISIBLE);
                    popupLayout.setClickable(true);

                    llReplyList.removeAllViews();

                    int margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, activity.getResources().getDisplayMetrics());
                    int textSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 5, activity.getResources().getDisplayMetrics());
                    int lineSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, activity.getResources().getDisplayMetrics());

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    params.setMargins(margin, margin, 0, margin);
                    params.gravity = Gravity.CENTER_VERTICAL;

                    LinearLayout.LayoutParams lineParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, lineSize);

                    for (int i = 0; i < data.length(); i++) {
                        JSONObject reply = data.getJSONObject(i);
                        String content = reply.getString("content");
                        String type = reply.getString("type");

                        LinearLayout ll = new LinearLayout(activity);
                        ll.setOrientation(LinearLayout.HORIZONTAL);

                        TextView tv1 = new TextView(activity);
                        tv1.setText(type.equals("chan") ? "찬" : type.equals("ban") ? "반" : "기");
                        tv1.setTextColor(Color.parseColor(type.equals("chan") ? "#097c25" : type.equals("ban") ? "#da0505" : "#b7b7b7"));
                        tv1.setTextSize(textSize);

                        TextView tv2 = new TextView(activity);
                        tv2.setText("***(학부 재학생) : " + content);
                        tv2.setTextColor(Color.parseColor("#000000"));

                        ll.addView(tv1, params);
                        ll.addView(tv2, params);

                        llReplyList.addView(ll, params);

                        if (i != data.length() - 1) {
                            View line = new View(activity);
                            line.setBackgroundColor(Color.parseColor("#acacac"));

                            llReplyList.addView(line, lineParams);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void closePopup(){
        showingPopup = false;
        if (popupLayout != null) {
            if (popupLayout.getVisibility() == View.VISIBLE) {
                popupLayout.setVisibility(View.GONE);
                popupLayout.setClickable(false);
            }
        }
    }

    public void addList(final String content) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    SharedPreferences pref = getActivity().getSharedPreferences("default", Context.MODE_PRIVATE);
                    JSONObject allData = new JSONObject(pref.getString("baseData", ""));
                    JSONArray reviewList = allData.getJSONArray("ReviewList");
                    JSONObject review = reviewList.getJSONObject(idx);
                    JSONArray cmtList = review.getJSONArray("cmtList");
                    JSONObject cmt = cmtList.getJSONObject(cmtIdx);
                    JSONArray replyCmtList = cmt.getJSONArray("replyCmtList");

                    JSONObject replyCmt = new JSONObject();
                    replyCmt.put("cmt_idx", replyCmtList.length());
                    replyCmt.put("type", type);
                    replyCmt.put("content", content);

                    DiscussionFragment.replyCmtList.put(replyCmt);
                    replyCmtList.put(replyCmt);

                    ReplyListAdapter.setReplyCnt(DiscussionFragment.cmtIdx, DiscussionFragment.replyCmtList.length());

                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("baseData", allData.toString());
                    editor.commit();

                    int margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getActivity().getResources().getDisplayMetrics());
                    int textSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 5, getActivity().getResources().getDisplayMetrics());
                    int lineSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, getActivity().getResources().getDisplayMetrics());

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    params.setMargins(margin, margin, 0, margin);
                    params.gravity = Gravity.CENTER_VERTICAL;

                    LinearLayout ll = new LinearLayout(getActivity());
                    ll.setOrientation(LinearLayout.HORIZONTAL);

                    TextView tv1 = new TextView(getActivity());
                    tv1.setText(type.equals("chan") ? "찬" : type.equals("ban") ? "반" : "기");
                    tv1.setTextColor(Color.parseColor(type.equals("chan") ? "#097c25" : type.equals("ban") ? "#da0505" : "#b7b7b7"));
                    tv1.setTextSize(textSize);

                    TextView tv2 = new TextView(getActivity());
                    tv2.setText("***(학부 재학생) : " + content);
                    tv2.setTextColor(Color.parseColor("#000000"));

                    ll.addView(tv1, params);
                    ll.addView(tv2, params);

                    LinearLayout.LayoutParams lineParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, lineSize);

                    View line = new View(getActivity());
                    line.setBackgroundColor(Color.parseColor("#acacac"));

                    llReplyList.addView(line, lineParams);
                    llReplyList.addView(ll, params);

                    svReplyList.fullScroll(View.FOCUS_DOWN);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }
}

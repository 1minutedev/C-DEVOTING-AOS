package com.kkj.cvoting.view.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.kkj.cvoting.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.kkj.cvoting.util.SlidingUpPanelLayout;
import com.kkj.cvoting.view.fragment.discussion.ReplyListAdapter;

public class DiscussionBottomSecondFragment extends Fragment implements View.OnClickListener {
    private int page;
    private View wrapper;

    private TextView chan;
    private TextView ban;

    private EditText etContents;
    private ImageView enter;

    private ListView replyListView;
    private ReplyListAdapter adapter;

    private String type = "gita";

    private JSONObject pageData;
    private JSONArray cmtList;
    private int idx = 0;

    private static SlidingUpPanelLayout mLayout;
    private SharedPreferences pref;

    public static DiscussionBottomSecondFragment newInstance(int page, String pageData, SlidingUpPanelLayout view) {
        DiscussionBottomSecondFragment fragment = new DiscussionBottomSecondFragment();
        mLayout = view;

        Bundle args = new Bundle();
        args.putInt("page", page);
        args.putString("pageData", pageData);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        wrapper = inflater.inflate(R.layout.fragment_discussion_bottom2, container, false);
        return wrapper;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        pref = getActivity().getSharedPreferences("default", Context.MODE_PRIVATE);

        Bundle data = getArguments();
        try {
            pageData = new JSONObject(data.getString("pageData"));
            idx = pageData.getInt("idx");
            cmtList = pageData.getJSONArray("cmtList");

            init();

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        for (int i = 0; i < cmtList.length(); i++) {
                            JSONObject cmt = cmtList.getJSONObject(i);
                            boolean isGood = false;
                            if(cmt.has("isGood")){
                                isGood = cmt.getBoolean("isGood");
                            }
                            adapter.addItem(cmt.getInt("cmt_idx"), cmt.getString("type"), cmt.getString("writer"), cmt.getString("content"), cmt.getInt("goodCnt"), cmt.getJSONArray("replyCmtList").length(), isGood, cmt.getJSONArray("replyCmtList"));
                            adapter.notifyDataSetChanged();
                        }
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void init() {
        replyListView = wrapper.findViewById(R.id.lv_reply_list);

        adapter = new ReplyListAdapter(replyListView, getActivity(), idx);
        replyListView.setAdapter(adapter);

        mLayout.setScrollableView(replyListView);

        chan = wrapper.findViewById(R.id.tv_chan);
        ban = wrapper.findViewById(R.id.tv_ban);
        enter = wrapper.findViewById(R.id.iv_enter);
        etContents = wrapper.findViewById(R.id.et_contents);

        chan.setOnClickListener(this);
        ban.setOnClickListener(this);
        enter.setOnClickListener(this);
    }

    private void setReplyData(File file) {
        try {
            FileInputStream fis = new FileInputStream(file);
            String jsonStr = inputStreamToString(fis);
            JSONObject replyData = new JSONObject(jsonStr);
            Log.e("wonmin", "replyData : " + replyData);
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
                addReplyList();
                break;
            default:
                break;
        }
    }

    private void addReplyList() {
        final String contents = etContents.getText().toString();

        if (TextUtils.isEmpty(contents)) {
            Toast.makeText(getActivity(), "입력한 내용이 없습니다.", Toast.LENGTH_SHORT).show();
        } else {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String user = "***(학부 재학생)";
                    adapter.addItem(adapter.getCount(), type, user, contents, 0, 0, false, new JSONArray());
                    adapter.notifyDataSetChanged();

                    etContents.setText("");
                    replyListView.setSelection(adapter.getCount() - 1);

                    //댓글 저장
                    try {
                        JSONObject data = new JSONObject(pref.getString("baseData", ""));
                        JSONArray reviewList = data.getJSONArray("ReviewList");
                        JSONObject review = reviewList.getJSONObject(idx);
                        JSONArray cmtList;
                        if(review.has("cmtList")) {
                            cmtList = review.getJSONArray("cmtList");
                        } else {
                            cmtList = new JSONArray();
                        }

                        JSONObject cmt = new JSONObject();
                        cmt.put("cmt_idx", adapter.getCount());
                        cmt.put("type", type);
                        cmt.put("writer", user);
                        cmt.put("content", contents);
                        cmt.put("replyCmtList", new JSONArray());

                        cmtList.put(cmt);

                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("baseData", data.toString());

                        editor.commit();
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    private void saveData(){

    }

    private String inputStreamToString(InputStream is) {
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
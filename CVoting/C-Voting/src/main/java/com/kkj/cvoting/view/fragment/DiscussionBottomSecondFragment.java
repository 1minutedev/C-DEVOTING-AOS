package com.kkj.cvoting.view.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.kkj.cvoting.R;

import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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

    public static DiscussionBottomSecondFragment newInstance(int page) {
        DiscussionBottomSecondFragment fragment = new DiscussionBottomSecondFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("someInt", 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        wrapper = inflater.inflate(R.layout.fragment_discussion_bottom2, container, false);
        return wrapper;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        init();

        String replyDataPath = getActivity().getFilesDir().getPath() + "/json/reply.json";
        File replyFile = new File(replyDataPath);
        if(replyFile.exists()){
            // 데이터를 가져와 뿌려준다.
            setReplyData(replyFile);
        }
    }

    private void init(){
        adapter = new ReplyListAdapter();

        replyListView = wrapper.findViewById(R.id.lv_reply_list);
        replyListView.setAdapter(adapter);

        chan = wrapper.findViewById(R.id.tv_chan);
        ban = wrapper.findViewById(R.id.tv_ban);
        enter = wrapper.findViewById(R.id.iv_enter);
        etContents = wrapper.findViewById(R.id.et_contents);

        chan.setOnClickListener(this);
        ban.setOnClickListener(this);
        enter.setOnClickListener(this);
    }

    private void setReplyData(File file){
        try {
            FileInputStream fis = new FileInputStream(file);
            String jsonStr = inputStreamToString(fis);
            JSONObject replyData = new JSONObject(jsonStr);
            Log.e("wonmin", "replyData : " + replyData);
            fis.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_chan:
                if(type.equals("chan")){
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
                if(type.equals("ban")){
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

    private void addReplyList(){
        String contents = etContents.getText().toString();

        if(TextUtils.isEmpty(contents)){
            Toast.makeText(getActivity(), "입력한 내용이 없습니다.", Toast.LENGTH_SHORT).show();
        }else {
            adapter.addItem(adapter.getCount() + 1, type, "김민지(학부 재학생)", contents, 0, 0);
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    adapter.notifyDataSetChanged();

                    etContents.setText("");
                    replyListView.setSelection(adapter.getCount() - 1);
                }
            });
        }
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
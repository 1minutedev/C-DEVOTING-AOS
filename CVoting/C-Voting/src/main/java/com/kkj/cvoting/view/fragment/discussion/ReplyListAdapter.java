package com.kkj.cvoting.view.fragment.discussion;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kkj.cvoting.R;

import java.util.ArrayList;

public class ReplyListAdapter extends BaseAdapter {
    private ArrayList<ReplyItem> replyList = new ArrayList<ReplyItem>();

    private TextView userName = null;
    private TextView contents = null;
    private TextView goodCnt = null;
    private TextView replyCnt = null;

    public ReplyListAdapter() {

    }

    @Override
    public int getCount() {
        return replyList.size();
    }

    @Override
    public Object getItem(int position) {
        return replyList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        ReplyItem replyItem = replyList.get(position);
        String type = replyItem.getType();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            if (type.equals("chan")) {
                convertView = inflater.inflate(R.layout.fragment_discussion_bottom2_item1, parent, false);
                goodCnt = (TextView) convertView.findViewById(R.id.tv_good_cnt);
                goodCnt.setText(String.valueOf(replyItem.getGoodCnt()));
            } else if (type.equals("ban")) {
                convertView = inflater.inflate(R.layout.fragment_discussion_bottom2_item2, parent, false);
                goodCnt = (TextView) convertView.findViewById(R.id.tv_good_cnt);
                goodCnt.setText(String.valueOf(replyItem.getGoodCnt()));
            } else {
                convertView = inflater.inflate(R.layout.fragment_discussion_bottom2_item3, parent, false);
            }

            userName = (TextView) convertView.findViewById(R.id.tv_user_name);
            userName.setText(replyItem.getUser());
            contents = (TextView) convertView.findViewById(R.id.tv_contents);
            contents.setText(replyItem.getContents());
            replyCnt = (TextView) convertView.findViewById(R.id.tv_reply_cnt);
            replyCnt.setText(String.valueOf(replyItem.getReplyCnt()));
        }

        return convertView;
    }

    public void addItem(int num, String type, String user, String contents, int goodCnt, int replyCnt){
        ReplyItem item = new ReplyItem();

        item.setNum(num);
        item.setType(type);
        item.setUser(user);
        item.setContents(contents);
        item.setGoodCnt(goodCnt);
        item.setReplyCnt(replyCnt);

        replyList.add(item);
    }
}

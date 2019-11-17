package com.kkj.cvoting.view.fragment.discussion;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ScrollView;
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
        ReplyViewHolder holder;

        final int pos = position;
        final Context context = parent.getContext();

        ReplyItem replyItem = replyList.get(pos);
        String type = replyItem.getType();

//        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            holder = new ReplyViewHolder();

            if (type.equals("chan")) {
                convertView = inflater.inflate(R.layout.fragment_discussion_bottom2_item1, parent, false);
                holder.tvGoodCnt = (TextView) convertView.findViewById(R.id.tv_good_cnt);
            } else if (type.equals("ban")) {
                convertView = inflater.inflate(R.layout.fragment_discussion_bottom2_item2, parent, false);
                holder.tvGoodCnt = (TextView) convertView.findViewById(R.id.tv_good_cnt);
            } else {
                convertView = inflater.inflate(R.layout.fragment_discussion_bottom2_item3, parent, false);
            }

            holder.tvUser = (TextView) convertView.findViewById(R.id.tv_user_name);
            holder.tvContents = (TextView) convertView.findViewById(R.id.tv_contents);
            holder.tvReplyCnt = (TextView) convertView.findViewById(R.id.tv_reply_cnt);

            ScrollView scrollView = convertView.findViewById(R.id.sv_contents);
            scrollView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    return true;
                }
            });
            convertView.setTag(holder);
//        } else {
//            holder = (ReplyViewHolder) convertView.getTag();
//        }

        if (holder.tvGoodCnt != null) {
            holder.tvGoodCnt.setText(String.valueOf(replyItem.getGoodCnt()));
        }

        holder.tvReplyCnt.setText(String.valueOf(replyItem.getReplyCnt()));
        holder.tvUser.setText(replyItem.getUser());
        holder.tvContents.setText(replyItem.getContents());

        return convertView;
    }

    public void addItem(int num, String type, String user, String contents, int goodCnt, int replyCnt) {
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

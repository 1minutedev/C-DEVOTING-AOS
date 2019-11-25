package com.kkj.cvoting.view.fragment.discussion;

import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;

public class ReplyViewHolder {
    public int cmtIdx;
    public String type;
    public boolean isGood;
    public JSONArray replyList;

    public ImageView ivGood;
    public ImageView ivReply;
    public ImageView ivPiyong;

    public TextView tvUser;
    public TextView tvGoodCnt;
    public TextView tvContents;
    public TextView tvReplyCnt;
}

package com.kkj.cvoting.view.fragment.discussion;

import org.json.JSONArray;

public class ReplyItem {
    private int num;
    private String type;
    private String user;
    private String contents;
    private int goodCnt;
    private int replyCnt;
    private boolean isGood;
    private JSONArray replyList;

    public void setNum(int num) {
        this.num = num;
    }
    public int getNum() {
        return num;
    }

    public void setType(String type) {
        this.type = type;
    }
    public String getType() {
        return type;
    }

    public void setUser(String user) {
        this.user = user;
    }
    public String getUser() {
        return user;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }
    public String getContents() {
        return contents;
    }

    public void setGoodCnt(int goodCnt) {
        this.goodCnt = goodCnt;
    }
    public int getGoodCnt() {
        return goodCnt;
    }

    public void setReplyCnt(int replyCnt) {
        this.replyCnt = replyCnt;
    }
    public int getReplyCnt() {
        return replyCnt;
    }

    public void setGood(boolean good) {
        isGood = good;
    }
    public boolean getGood(){
        return isGood;
    }
    public void setReplyList(JSONArray replyList) {
        this.replyList = replyList;
    }
    public JSONArray getReplyList() {
        return replyList;
    }
}

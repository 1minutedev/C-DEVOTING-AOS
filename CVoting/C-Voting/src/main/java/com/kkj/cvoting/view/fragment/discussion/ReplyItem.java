package com.kkj.cvoting.view.fragment.discussion;

public class ReplyItem {
    private int num;
    private String type;
    private String user;
    private String contents;
    private int goodCnt;
    private int replyCnt;

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
}

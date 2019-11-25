package com.kkj.cvoting.view.fragment.discussion;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.kkj.cvoting.R;
import com.kkj.cvoting.view.fragment.DiscussionFragment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.appcompat.app.AlertDialog;

public class ReplyListAdapter extends BaseAdapter {
    private ArrayList<ReplyItem> replyList = new ArrayList<ReplyItem>();

    private TextView userName = null;
    private TextView contents = null;
    private TextView goodCnt = null;
    private TextView replyCnt = null;

    private ListView mListView = null;

    private Activity activity;

    private int idx;

    public ReplyListAdapter(ListView view, Activity activity, int idx) {
        mListView = view;
        this.activity = activity;
        this.idx = idx;
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
        View view = convertView;

        ReplyViewHolder holder;

        final int pos = position;
        final Context context = parent.getContext();

        ReplyItem replyItem = replyList.get(pos);
        String type = replyItem.getType();

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        holder = new ReplyViewHolder();

        holder.cmtIdx = replyItem.getNum();
        holder.type = type;
        holder.isGood = replyItem.getGood();
        holder.replyList = replyItem.getReplyList();

        if (type.equals("chan")) {
            view = inflater.inflate(R.layout.fragment_discussion_bottom2_item1, parent, false);
            holder.tvGoodCnt = (TextView) view.findViewById(R.id.tv_good_cnt);
            holder.ivGood = (ImageView) view.findViewById(R.id.iv_good);
        } else if (type.equals("ban")) {
            view = inflater.inflate(R.layout.fragment_discussion_bottom2_item2, parent, false);
            holder.tvGoodCnt = (TextView) view.findViewById(R.id.tv_good_cnt);
            holder.ivGood = (ImageView) view.findViewById(R.id.iv_good);
        } else {
            view = inflater.inflate(R.layout.fragment_discussion_bottom2_item3, parent, false);
        }

        holder.tvUser = (TextView) view.findViewById(R.id.tv_user_name);
        holder.tvContents = (TextView) view.findViewById(R.id.tv_contents);
        holder.tvReplyCnt = (TextView) view.findViewById(R.id.tv_reply_cnt);

        holder.ivReply = (ImageView) view.findViewById(R.id.iv_reply);
        holder.ivPiyong = (ImageView) view.findViewById(R.id.iv_piyong);

        if (holder.tvGoodCnt != null) {
            holder.tvGoodCnt.setText(String.valueOf(replyItem.getGoodCnt()));
        }
        if (holder.ivGood != null) {
            if (holder.isGood) {
                if (holder.type.equals("chan")) {
                    holder.ivGood.setImageDrawable(activity.getResources().getDrawable(R.drawable.chan_good_sel));
                } else {
                    holder.ivGood.setImageDrawable(activity.getResources().getDrawable(R.drawable.ban_good_sel));
                }
            } else {
                if (holder.type.equals("chan")) {
                    holder.ivGood.setImageDrawable(activity.getResources().getDrawable(R.drawable.chan_good));
                } else {
                    holder.ivGood.setImageDrawable(activity.getResources().getDrawable(R.drawable.ban_good));
                }
            }
        }

        holder.tvReplyCnt.setText(String.valueOf(replyItem.getReplyCnt()));
        holder.tvUser.setText(replyItem.getUser());
        holder.tvContents.setText(replyItem.getContents());

        final ReplyViewHolder fHolder = holder;

        if (holder.ivGood != null) {
            holder.ivGood.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String message = "";

                    if (fHolder.isGood) {
                        message = activity.getResources().getString(R.string.dialog_txt_good);
                    } else {
                        message = activity.getResources().getString(R.string.dialog_txt_good_cancel);
                    }

                    new AlertDialog.Builder(activity)
                            .setMessage(message)
                            .setCancelable(false)
                            .setPositiveButton(activity.getResources().getString(R.string.txt_ok), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    activity.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                SharedPreferences pref = activity.getSharedPreferences("default", Context.MODE_PRIVATE);
                                                JSONObject data = new JSONObject(pref.getString("baseData", ""));
                                                JSONArray reviewList = data.getJSONArray("ReviewList");
                                                JSONObject review = reviewList.getJSONObject(idx);
                                                JSONArray cmtList = review.getJSONArray("cmtList");
                                                JSONObject cmt = cmtList.getJSONObject(fHolder.cmtIdx);

                                                int goodCnt = 0;

                                                if (cmt.has("goodCnt")) {
                                                    goodCnt = cmt.getInt("goodCnt");
                                                }

                                                Drawable drawable;

                                                if (cmt.has("isGood")) {
                                                    boolean isGood = cmt.getBoolean("isGood");

                                                    if (!isGood) {
                                                        cmt.put("goodCnt", goodCnt + 1);
                                                        cmt.put("isGood", true);

                                                        if (fHolder.type.equals("chan")) {
                                                            drawable = activity.getResources().getDrawable(R.drawable.chan_good_sel);
                                                        } else {
                                                            drawable = activity.getResources().getDrawable(R.drawable.ban_good_sel);
                                                        }

                                                        fHolder.tvGoodCnt.setText(String.valueOf(goodCnt + 1));
                                                        fHolder.ivGood.setImageDrawable(drawable);
                                                    } else {
                                                        cmt.put("goodCnt", goodCnt - 1);
                                                        cmt.put("isGood", false);

                                                        if (fHolder.type.equals("chan")) {
                                                            drawable = activity.getResources().getDrawable(R.drawable.chan_good);
                                                        } else {
                                                            drawable = activity.getResources().getDrawable(R.drawable.ban_good);
                                                        }
                                                        fHolder.tvGoodCnt.setText(String.valueOf(goodCnt - 1));
                                                        fHolder.ivGood.setImageDrawable(drawable);
                                                    }
                                                } else {
                                                    cmt.put("goodCnt", goodCnt + 1);
                                                    cmt.put("isGood", true);

                                                    if (fHolder.type.equals("chan")) {
                                                        drawable = activity.getResources().getDrawable(R.drawable.chan_good_sel);
                                                    } else {
                                                        drawable = activity.getResources().getDrawable(R.drawable.ban_good_sel);
                                                    }

                                                    fHolder.tvGoodCnt.setText(String.valueOf(goodCnt + 1));
                                                    fHolder.ivGood.setImageDrawable(drawable);
                                                }

                                                SharedPreferences.Editor editor = pref.edit();
                                                editor.putString("baseData", data.toString());

                                                editor.commit();
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    });
                                }
                            })
                            .setNegativeButton(activity.getResources().getString(R.string.txt_cancel), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            }).create().show();
                }
            });
        }

        holder.ivReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DiscussionFragment.showPopup(fHolder.replyList, activity);
            }
        });

        holder.ivPiyong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(activity)
                        .setMessage(activity.getResources().getString(R.string.dialog_txt_piyong))
                        .setCancelable(false)
                        .setPositiveButton(activity.getResources().getString(R.string.txt_ok), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .setNegativeButton(activity.getResources().getString(R.string.txt_cancel), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).create().show();
            }
        });

        view.setTag(holder);

        return view;
    }

    public void addItem(int num, String type, String user, String contents, int goodCnt, int replyCnt, boolean isGood, JSONArray list) {
        ReplyItem item = new ReplyItem();

        item.setNum(num);
        item.setType(type);
        item.setUser(user);
        item.setContents(contents);
        item.setGoodCnt(goodCnt);
        item.setReplyCnt(replyCnt);
        item.setGood(isGood);
        item.setReplyList(list);

        replyList.add(item);
    }

}

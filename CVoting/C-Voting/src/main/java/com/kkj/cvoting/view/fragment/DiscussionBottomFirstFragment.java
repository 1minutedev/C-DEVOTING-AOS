package com.kkj.cvoting.view.fragment;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kkj.cvoting.R;
import com.kkj.cvoting.util.SlidingUpPanelLayout;

import org.json.JSONObject;


public class DiscussionBottomFirstFragment extends Fragment implements View.OnClickListener {
    private View wrapper;
    private static SlidingUpPanelLayout mLayout;
    private static DiscussionFragment mDiscussionFragment;

    public static DiscussionBottomFirstFragment newInstance(int page, String pageData, SlidingUpPanelLayout view, DiscussionFragment discussionFragment) {
        mDiscussionFragment = discussionFragment;

        DiscussionBottomFirstFragment fragment = new DiscussionBottomFirstFragment();
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
        wrapper = inflater.inflate(R.layout.fragment_discussion_bottom1, container, false);
        return wrapper;
    }

    private TextView tvTotalCnt;
    private TextView chan;
    private TextView ban;
    private TextView gita;

    private int idx = 0;
    private int totalCnt = 0;
    private int chanCnt = 0;
    private int banCnt = 0;
    private int gitaCnt = 0;
    private String type = "";

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle bun = getArguments();
        JSONObject pageData = null;

        try {
            pageData = new JSONObject(bun.getString("pageData"));
            if (pageData.has("idx")) {
                idx = pageData.getInt("totalCnt");
            }
            if (pageData.has("totalCnt")) {
                totalCnt = pageData.getInt("totalCnt");
            }
            if (pageData.has("agreeCnt")) {
                chanCnt = pageData.getInt("agreeCnt");
            }
            if (pageData.has("oppCnt")) {
                banCnt = pageData.getInt("oppCnt");
            }
            if (pageData.has("neutCnt")) {
                gitaCnt = pageData.getInt("neutCnt");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        StringBuffer totalText = getTotalText();

        tvTotalCnt = wrapper.findViewById(R.id.tv_total_cnt);
        tvTotalCnt.setText(totalText);

        chan = wrapper.findViewById(R.id.circle_chan);
        ban = wrapper.findViewById(R.id.circle_ban);
        gita = wrapper.findViewById(R.id.circle_gita);

        chan.setOnClickListener(this);
        ban.setOnClickListener(this);
        gita.setOnClickListener(this);
    }

    private StringBuffer getTotalText(){
        StringBuffer totalText = new StringBuffer();
        totalText.append(totalCnt);
        if (totalText.length() > 3) {
            totalText.insert(totalText.length() - 3, ",");
        }

        totalText.insert(0, "총 참여 ");
        totalText.append("명");

        return totalText;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.circle_chan:
                if (!type.equals("chan")) {
                    showDialog(view.getId());
                }
                break;
            case R.id.circle_ban:
                if (!type.equals("ban")) {
                    showDialog(view.getId());
                }
                break;
            case R.id.circle_gita:
                if (!type.equals("gita")) {
                    showDialog(view.getId());
                }
                break;
            default:
                break;
        }
    }

    private void showDialog(final int resId) {
        new AlertDialog.Builder(getActivity())
                .setMessage(getActivity().getResources().getString(R.string.dialog_txt_chan))
                .setCancelable(false)
                .setPositiveButton(getActivity().getResources().getString(R.string.txt_ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (resId) {
                            case R.id.circle_chan:
                                if (type.equals("ban")) {
                                    //의견을 "반대"에서 "찬성" 으로 변경.
                                    banCnt -= 1;
                                    chanCnt += 1;
                                } else if (type.equals("gita")) {
                                    //의견을 "기타"에서 "찬성" 으로 변경.
                                    gitaCnt -= 1;
                                    chanCnt += 1;
                                } else if (type.equals("")) {
                                    //처음 의견을 누름.
                                    chanCnt += 1;
                                    totalCnt += 1;
                                }

                                type = "chan";
                                chan.setBackground(getActivity().getResources().getDrawable(R.drawable.circle_selected_1));
                                chan.setTextColor(getActivity().getResources().getColor(R.color.discussion_bottom_text_selected));
                                ban.setBackground(getActivity().getResources().getDrawable(R.drawable.circle_unselected));
                                ban.setTextColor(getActivity().getResources().getColor(R.color.discussion_bottom_text_default));
                                gita.setBackground(getActivity().getResources().getDrawable(R.drawable.circle_unselected));
                                gita.setTextColor(getActivity().getResources().getColor(R.color.discussion_bottom_text_default));
                                break;
                            case R.id.circle_ban:
                                if (type.equals("chan")) {
                                    //의견을 "찬성"에서 "반대" 로 변경.
                                    chanCnt -= 1;
                                    banCnt += 1;
                                } else if (type.equals("gita")) {
                                    //의견을 "기타"에서 "반대" 로 변경.
                                    gitaCnt -= 1;
                                    banCnt += 1;
                                } else if (type.equals("")) {
                                    //처음 의견을 누름.
                                    banCnt += 1;
                                    totalCnt += 1;
                                }

                                type = "ban";
                                chan.setBackground(getActivity().getResources().getDrawable(R.drawable.circle_unselected));
                                chan.setTextColor(getActivity().getResources().getColor(R.color.discussion_bottom_text_default));
                                ban.setBackground(getActivity().getResources().getDrawable(R.drawable.circle_selected_2));
                                ban.setTextColor(getActivity().getResources().getColor(R.color.discussion_bottom_text_selected));
                                gita.setBackground(getActivity().getResources().getDrawable(R.drawable.circle_unselected));
                                gita.setTextColor(getActivity().getResources().getColor(R.color.discussion_bottom_text_default));
                                break;
                            case R.id.circle_gita:
                                if (type.equals("chan")) {
                                    //의견을 "찬성"에서 "기타" 로 변경.
                                    chanCnt -= 1;
                                    gitaCnt += 1;
                                } else if (type.equals("ban")) {
                                    //의견을 "반대"에서 "기타" 로 변경.
                                    banCnt -= 1;
                                    gitaCnt += 1;
                                } else if (type.equals("")) {
                                    //처음 의견을 누름.
                                    gitaCnt += 1;
                                    totalCnt += 1;
                                }

                                type = "gita";
                                chan.setBackground(getActivity().getResources().getDrawable(R.drawable.circle_unselected));
                                chan.setTextColor(getActivity().getResources().getColor(R.color.discussion_bottom_text_default));
                                ban.setBackground(getActivity().getResources().getDrawable(R.drawable.circle_unselected));
                                ban.setTextColor(getActivity().getResources().getColor(R.color.discussion_bottom_text_default));
                                gita.setBackground(getActivity().getResources().getDrawable(R.drawable.circle_selected_3));
                                gita.setTextColor(getActivity().getResources().getColor(R.color.discussion_bottom_text_selected));
                                break;
                            default:
                                break;
                        }

                        refreshData();
                    }
                })
                .setNegativeButton(getActivity().getResources().getString(R.string.txt_cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).create().show();
    }

    private void refreshData(){
        tvTotalCnt.setText(getTotalText());

        double chanPer = chanCnt * 100 / totalCnt;
        double banPer = banCnt * 100 / totalCnt;
        int gitaPer = 100 - (int) chanPer - (int) banPer;

        mDiscussionFragment.refreshPercent(String.valueOf((int) chanPer), String.valueOf((int) banPer), String.valueOf(gitaPer));
        //total, 등 의견 카운트 저장해야함 여기서
    }
}
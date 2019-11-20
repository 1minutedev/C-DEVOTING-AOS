package com.kkj.cvoting.view.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
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

    public static DiscussionBottomFirstFragment newInstance(int page, String pageData, SlidingUpPanelLayout view) {
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

    private int totalCnt = 0;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle bun = getArguments();

        JSONObject pageData = null;

        try {
            pageData = new JSONObject(bun.getString("pageData"));
            if(pageData.has("totalCnt")){
                totalCnt = pageData.getInt("totalCnt");
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        StringBuffer totalText = new StringBuffer();
        totalText.append(totalCnt);
        if(totalText.length() > 3) {
            totalText.insert(totalText.length() - 3, ",");
        }

        totalText.insert(0, "총 참여 ");
        totalText.append("명");

        tvTotalCnt = wrapper.findViewById(R.id.tv_total_cnt);
        tvTotalCnt.setText(totalText);


        chan = wrapper.findViewById(R.id.circle_chan);
        ban = wrapper.findViewById(R.id.circle_ban);
        gita = wrapper.findViewById(R.id.circle_gita);

        chan.setOnClickListener(this);
        ban.setOnClickListener(this);
        gita.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.circle_chan:
                chan.setBackground(getActivity().getResources().getDrawable(R.drawable.circle_selected_1));
                chan.setTextColor(getActivity().getResources().getColor(R.color.discussion_bottom_text_selected));
                ban.setBackground(getActivity().getResources().getDrawable(R.drawable.circle_unselected));
                ban.setTextColor(getActivity().getResources().getColor(R.color.discussion_bottom_text_default));
                gita.setBackground(getActivity().getResources().getDrawable(R.drawable.circle_unselected));
                gita.setTextColor(getActivity().getResources().getColor(R.color.discussion_bottom_text_default));
                break;
            case R.id.circle_ban:
                chan.setBackground(getActivity().getResources().getDrawable(R.drawable.circle_unselected));
                chan.setTextColor(getActivity().getResources().getColor(R.color.discussion_bottom_text_default));
                ban.setBackground(getActivity().getResources().getDrawable(R.drawable.circle_selected_2));
                ban.setTextColor(getActivity().getResources().getColor(R.color.discussion_bottom_text_selected));
                gita.setBackground(getActivity().getResources().getDrawable(R.drawable.circle_unselected));
                gita.setTextColor(getActivity().getResources().getColor(R.color.discussion_bottom_text_default));
                break;
            case R.id.circle_gita:
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
    }
}
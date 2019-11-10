package com.kkj.cvoting.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kkj.cvoting.R;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class DiscussionBottomSecondFragment extends Fragment implements View.OnClickListener {
    private int page;
    private View wrapper;

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

    private TextView chan;
    private TextView ban;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        chan = wrapper.findViewById(R.id.tv_chan);
        ban = wrapper.findViewById(R.id.tv_ban);

        chan.setOnClickListener(this);
        ban.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_chan:
                chan.setBackground(getActivity().getResources().getDrawable(R.drawable.circle_selected_1));
                chan.setTextColor(getActivity().getResources().getColor(R.color.discussion_bottom_text_selected));
                ban.setBackground(getActivity().getResources().getDrawable(R.drawable.circle_unselected));
                ban.setTextColor(getActivity().getResources().getColor(R.color.discussion_bottom_text_default));
                break;
            case R.id.tv_ban:
                chan.setBackground(getActivity().getResources().getDrawable(R.drawable.circle_unselected));
                chan.setTextColor(getActivity().getResources().getColor(R.color.discussion_bottom_text_default));
                ban.setBackground(getActivity().getResources().getDrawable(R.drawable.circle_selected_2));
                ban.setTextColor(getActivity().getResources().getColor(R.color.discussion_bottom_text_selected));
                break;
            default:
                break;
        }
    }
}
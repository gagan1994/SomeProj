package com.example.gagan.proj1.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gagan.proj1.R;
import com.example.gagan.proj1.dbhelper.DbHelper;
import com.example.gagan.proj1.pojo.User;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Gagan on 4/16/2018.
 */

public class BaseFragment extends Fragment {
    protected Unbinder unbinder;
    protected String Tag;

    public String getThisTag() {
        return Tag;
    }

    public BaseFragment() {
        Tag = "Default";
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = getView(inflater, container);
        unbinder = ButterKnife.bind(view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    public View getView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.empty_fragment, null);
    }

    public String getTitle() {
        return "Help";
    }

    public User getUserDetailsToDisplay() {
        return DbHelper.getDbHepler().getCurrentUserObj();
    }
}

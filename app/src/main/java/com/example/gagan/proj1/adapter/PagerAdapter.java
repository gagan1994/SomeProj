package com.example.gagan.proj1.adapter;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.gagan.proj1.fragments.AddUserFragment;
import com.example.gagan.proj1.fragments.BaseFragment;
import com.example.gagan.proj1.fragments.ChattFragment;

import java.util.List;

/**
 * Created by Gagan on 4/16/2018.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {

    private final List<BaseFragment> baseFragments;
    private int ChattFragmentId = -1;
    private int ProfileFragmentId = -1;
    private int AddUserFragmentId = -1;

    public PagerAdapter(FragmentManager fm, List<BaseFragment> baseFragments) {
        super(fm);
        this.baseFragments = baseFragments;

        for (int i = 0; i < baseFragments.size(); i++) {
            if (baseFragments.get(i) instanceof ChattFragment) {
                ChattFragmentId = i;
            }
            if (baseFragments.get(i) instanceof BaseFragment) {
                ProfileFragmentId = i;
            }
            if (baseFragments.get(i) instanceof AddUserFragment) {
                AddUserFragmentId = i;
            }
        }
    }

    @Override
    public BaseFragment getItem(int position) {
        return baseFragments.get(position);
    }

    @Override
    public int getCount() {
        return baseFragments.size();
    }

    public ChattFragment getChattFragment() {
        return (ChattFragment) getItem(ChattFragmentId);
    }

    public int getChattFragmentPosition() {
        return ChattFragmentId;
    }

    public int getProfilePos() {
        return ProfileFragmentId;
    }

    public int getAddUserId() {
        return AddUserFragmentId;
    }
}

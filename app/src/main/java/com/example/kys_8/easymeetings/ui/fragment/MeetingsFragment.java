package com.example.kys_8.easymeetings.ui.fragment;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.example.kys_8.easymeetings.R;
import com.example.kys_8.easymeetings.ui.adapter.MyPageAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kys-8 on 2018/4/16.
 */

public class MeetingsFragment extends BaseFragment {
    private FloatingActionButton fab;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    @Override
    protected int getlayoutId() {
        return R.layout.fragment_meetings;
    }

    @Override
    protected void initView(View view) {

        fab = view.findViewById(R.id.fab_meetings);

        mTabLayout = view.findViewById(R.id.tab_meetings);
        mViewPager = view.findViewById(R.id.vp_meetings);

        List<String> titles = new ArrayList<>();
        titles.add(getString(R.string.attend_meeting));
        titles.add(getString(R.string.my_meetings));
        titles.add(getString(R.string.find));
        mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(0)));
        mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(0)));
        mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(0)));

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new AttendFragment());
        fragments.add(new MyFragment());
        fragments.add(new FindFragment());

        mViewPager.setOffscreenPageLimit(2);
        MyPageAdapter pageAdapter = new MyPageAdapter(getActivity().getSupportFragmentManager(),fragments,titles);
        mViewPager.setAdapter(pageAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
//        mTabLayout.setTabsFromPagerAdapter(pageAdapter);

        mViewPager.addOnPageChangeListener(pageChangeListener);

    }

    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            if (position != 2) {
                fab.show();
            } else {
                fab.hide();
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}

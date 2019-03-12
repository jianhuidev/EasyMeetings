package com.example.kys_8.easymeetings.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by kys-8 on 2018/4/11.
 */

public abstract class BaseFragment extends Fragment {

    protected View mView;

//    protected T mPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(getlayoutId(), container, false);

        initView(mView);
        return mView;
    }

    protected abstract int getlayoutId();

    protected abstract void initView(View view);
}

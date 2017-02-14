package com.wcc.swen.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wcc.swen.R;
import com.wcc.swen.contract.AppleContract;
import com.wcc.swen.presenter.ApplePresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class AppleFragment extends Fragment {

    @BindView(R.id.tv_apple_fragment)
    TextView tv_apple_fragment;

    private AppleContract.Presenter mPresenter;

    private Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_apple, container, false);
        unbinder = ButterKnife.bind(this, view);

        mPresenter = new ApplePresenter();
        mPresenter.dailyApple(getActivity(), tv_apple_fragment);

        return view;
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }
}

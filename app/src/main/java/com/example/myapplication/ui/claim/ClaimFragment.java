package com.example.myapplication.ui.claim;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.View;

import com.example.myapplication.R;
import com.example.myapplication.model.Category;
import com.example.myapplication.ui.BaseFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class ClaimFragment extends BaseFragment {
    private static final String TAG = "ClaimFragment";

    private static final String ARG_PARAM1 = "param1";
    private String mParam1;


    public ClaimFragment() {
        // Required empty public constructor
    }

    public static ClaimFragment newInstance(String param1) {
        ClaimFragment fragment = new ClaimFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (mParam1 == null) return;

        showViewPager(mParam1);

    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_claim;
    }

    @Override
    protected Fragment getCurrentFragment(int position) {
        switch (position) {
            case 0:
                return HealthFacilityClaimFragment.newInstance("", "");
            case 1:
                return ReviewsFragment.newInstance("", "");
            default:
                return BatchRunsFragment.newInstance("", "");
        }
    }

    @Override
    protected int getPagerItemCount() {
        return mainViewModel.getCurrentTabTitles().size();
    }
}
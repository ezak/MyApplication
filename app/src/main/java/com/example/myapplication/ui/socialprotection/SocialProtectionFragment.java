package com.example.myapplication.ui.socialprotection;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;
import com.example.myapplication.model.Category;
import com.example.myapplication.ui.BaseFragment;
import com.example.myapplication.ui.DefaultFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


public class SocialProtectionFragment extends BaseFragment {
    private static final String TAG = "SocialProtectionFragment";
    private static final String ARG_PARAM1 = "param1";
    private String mParam1;

    public SocialProtectionFragment() {
        // Required empty public constructor
    }

    public static SocialProtectionFragment newInstance(String param1, String param2) {
        Log.e(TAG, "newInstance: ?? " + param1);
        SocialProtectionFragment fragment = new SocialProtectionFragment();
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

        Log.w(TAG, "onCreate: " + mParam1 );
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (mParam1 == null) return;

        showViewPager(mParam1);
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_social_protection;
    }

    @Override
    protected Fragment getCurrentFragment(int position) {
        return DefaultFragment.newInstance("", "");
    }

    @Override
    protected int getPagerItemCount() {
        return mainViewModel.getCurrentTabTitles().size();
    }
}
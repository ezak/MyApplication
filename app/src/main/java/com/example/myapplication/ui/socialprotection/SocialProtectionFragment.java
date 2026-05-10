package com.example.myapplication.ui.socialprotection;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.View;

import com.example.myapplication.R;
import com.example.myapplication.ui.BaseFragment;
import com.example.myapplication.ui.DefaultFragment;


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
        return databaseViewModel.getCurrentTabTitles().size();
    }
}
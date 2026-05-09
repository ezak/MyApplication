package com.example.myapplication.ui.claim;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;
import com.example.myapplication.ui.BaseFragment;

public class ClaimFragment extends BaseFragment {
    private static final String TAG = "ClaimFragment";

    public ClaimFragment() {
        // Required empty public constructor
    }

    public static ClaimFragment newInstance() {
        return new ClaimFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_claim;
    }
}
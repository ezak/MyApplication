package com.example.myapplication.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.model.Category;
import com.example.myapplication.ui.claim.HealthFacilityClaimFragment;
import com.example.myapplication.ui.claim.ReviewsFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends BaseFragment {
    private static final String TAG = "MainFragment";
    private DrawerLayout drawerLayout;

    public MainFragment() {
        // Required empty public constructor
    }

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mainViewModel.getAllCategoriesLive().observe(getViewLifecycleOwner(), categories -> {
            for(Category category : categories) {
                Log.e(TAG, "onChanged: " + category.getName() );
            }
        });
    }


    @Override
    protected int getLayout() {
        return R.layout.fragment_main;
    }


    @Override
    protected Fragment getCurrentFragment(int position) {
        switch (position) {
            case 0:
                return HealthFacilityClaimFragment.newInstance("", "");
            default:
                return ReviewsFragment.newInstance("", "");
        }
    }

    @Override
    protected int getPagerItemCount() {
        return mainViewModel.getSubCategories().size();
    }
}
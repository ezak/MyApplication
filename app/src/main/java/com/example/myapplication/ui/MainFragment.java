package com.example.myapplication.ui;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.ui.claim.ClaimFragment;
import com.example.myapplication.utils.ViewUtil;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

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

    private final OnBackPressedCallback drawerOnBackPressedCallback =
            new OnBackPressedCallback(/* enabled= */ false) {
                @Override
                public void handleOnBackPressed() {
                    drawerLayout.closeDrawers();
                }
            };

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

    }


    @Override
    protected int getLayout() {
        return R.layout.fragment_main;
    }
}
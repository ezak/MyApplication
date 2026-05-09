package com.example.myapplication.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.ui.claim.ClaimFragment;
import com.example.myapplication.utils.ViewUtil;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

public abstract class BaseFragment extends Fragment {
    private static final String TAG = "BaseFragment";
    private DrawerLayout drawerLayout;
    private MaterialToolbar toolbar;

    protected abstract int getLayout();

    private final OnBackPressedCallback drawerOnBackPressedCallback =
            new OnBackPressedCallback(/* enabled= */ false) {
                @Override
                public void handleOnBackPressed() {
                    drawerLayout.closeDrawers();
                }
            };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getLayout(), container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), drawerOnBackPressedCallback);

        toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.app_name));

        toolbar.inflateMenu(R.menu.default_appbar_menu);
        toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.settings) {
                Toast.makeText(requireActivity(), item.getTitle(), Toast.LENGTH_SHORT).show();
                return true;
            } else {
                return false;
            }
        });

        // We are accessing object from the Activity
        NavigationView navigationView = requireActivity().findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(item -> {
            // This highlights the item in the UI
            item.setChecked(true);

            // Handle your fragment navigation or logic here
            int id = item.getItemId();

            if (id == R.id.nav_social_protection) {
                Toast.makeText(requireActivity(), item.getTitle(), Toast.LENGTH_SHORT).show();
                toolbar.setSubtitle(R.string.social_protection);
            } else if (id == R.id.nav_insurees_policies) {
                Toast.makeText(requireActivity(), item.getTitle(), Toast.LENGTH_SHORT).show();
                toolbar.setSubtitle(R.string.insurees_and_policies);
            } else if (id == R.id.nav_claims) {
                Toast.makeText(requireActivity(), item.getTitle(), Toast.LENGTH_SHORT).show();
            } else if (id == R.id.nav_dashboards) {
                Toast.makeText(requireActivity(), item.getTitle(), Toast.LENGTH_SHORT).show();
            } else if (id == R.id.nav_tasks_management) {
                Toast.makeText(requireActivity(), item.getTitle(), Toast.LENGTH_SHORT).show();
            } else if (id == R.id.nav_legal_finance) {
                Toast.makeText(requireActivity(), item.getTitle(), Toast.LENGTH_SHORT).show();
            } else if (id == R.id.nav_grievance) {
                Toast.makeText(requireActivity(), item.getTitle(), Toast.LENGTH_SHORT).show();
            } else if (id == R.id.nav_administration) {
                Toast.makeText(requireActivity(), item.getTitle(), Toast.LENGTH_SHORT).show();
            } else if (id == R.id.nav_tools) {
                Toast.makeText(requireActivity(), item.getTitle(), Toast.LENGTH_SHORT).show();
            } else if (id == R.id.nav_profile) {
                Toast.makeText(requireActivity(), item.getTitle(), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(requireActivity(), "Unknown Action", Toast.LENGTH_SHORT).show();
            }
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });

        drawerLayout = requireActivity().findViewById(R.id.drawer);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                requireActivity(),
                drawerLayout,
                toolbar,
                R.string.app_name,
                R.string.app_name) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                drawerOnBackPressedCallback.setEnabled(true);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                drawerOnBackPressedCallback.setEnabled(false);
            }
        };
        drawerLayout.addDrawerListener(toggle);

    }
}
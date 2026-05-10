package com.example.myapplication.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.myapplication.R;
import com.example.myapplication.api.ApiServiceViewModel;
import com.example.myapplication.database.DatabaseViewModel;
import com.example.myapplication.database.model.Category;
import com.example.myapplication.ui.claim.ClaimFragment;
import com.example.myapplication.ui.insureesandpolicies.InsureesAndPoliciesFragment;
import com.example.myapplication.ui.socialprotection.SocialProtectionFragment;
import com.example.myapplication.utils.ViewUtil;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public abstract class BaseFragment extends Fragment {
    private static final String TAG = "BaseFragment";

    private View mView;
    private DrawerLayout drawerLayout;
    private MaterialToolbar toolbar;

    protected DatabaseViewModel databaseViewModel;
    protected ApiServiceViewModel apiServiceViewModel;

    protected abstract int getLayout();
    protected abstract Fragment getCurrentFragment(int position);
    protected abstract int getPagerItemCount();

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

        ViewModelFactory factory = new ViewModelFactory();

        factory.register(DatabaseViewModel.class, () -> new DatabaseViewModel(RepositoryHelper.getDatabaseRepository(requireActivity().getApplication())));
        factory.register(ApiServiceViewModel.class, () -> new ApiServiceViewModel(0));

        ViewModelProvider provider = new ViewModelProvider(this, factory);

        databaseViewModel = provider.get(DatabaseViewModel.class);
        apiServiceViewModel = provider.get(ApiServiceViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return mView = inflater.inflate(getLayout(), container, false);
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
                ViewUtil.startFragment(requireActivity(),
                        SocialProtectionFragment.newInstance(String.valueOf(item.getTitle()), ""),
                        R.id.main_activity_fragment_container,
                        "main_fragment", false);
            } else if (id == R.id.nav_insurees_policies) {
                Toast.makeText(requireActivity(), item.getTitle(), Toast.LENGTH_SHORT).show();
                toolbar.setSubtitle(R.string.insurees_and_policies);
                ViewUtil.startFragment(requireActivity(),
                        InsureesAndPoliciesFragment.newInstance(String.valueOf(item.getTitle()), ""),
                        R.id.main_activity_fragment_container,
                        "main_fragment", false);
            } else if (id == R.id.nav_claims) {
                ViewUtil.startFragment(requireActivity(),
                        ClaimFragment.newInstance(String.valueOf(item.getTitle())),
                        R.id.main_activity_fragment_container,
                        "main_fragment", false);
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

    @SuppressLint("NotifyDataSetChanged")
    public void showViewPager(String parent) {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this);
        ViewPager2 viewPager2 = mView.findViewById(R.id.pager);
        viewPager2.setAdapter(viewPagerAdapter);

        databaseViewModel.getAllSubCategoriesLive(parent).observe(getViewLifecycleOwner(), categories -> {
            if (categories == null) return;

            databaseViewModel.setSubCategories(categories);

            databaseViewModel.getCurrentTabTitles().clear();
            for (Category category : databaseViewModel.getSubCategories()) {
                databaseViewModel.addToCurrentTabTitles(category.getName());
            }

            TabLayout tabLayout = mView.findViewById(R.id.tab_layout);
            new TabLayoutMediator(tabLayout, viewPager2, (tab, pos) ->
                    tab.setText(databaseViewModel.getCurrentTabTitles().get(pos))
            ).attach();

            viewPagerAdapter.notifyDataSetChanged();
        });
    }

    public static class ViewPagerAdapter extends FragmentStateAdapter {
        private static final String TAG = "ViewPagerAdapter";
        private final BaseFragment baseFragment;

        public ViewPagerAdapter(@NonNull Fragment fragment) {
            super(fragment);
            this.baseFragment = (BaseFragment) fragment;
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return baseFragment.getCurrentFragment(position);
        }

        @Override
        public int getItemCount() {
            return baseFragment.getPagerItemCount();
        }
    }
}
package com.example.myapplication.ui.settings;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.work.WorkManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.api.ApiServiceViewModel;
import com.example.myapplication.database.DatabaseViewModel;
import com.example.myapplication.settings.SettingsManager;
import com.example.myapplication.settings.SettingsViewModel;
import com.example.myapplication.ui.RepositoryHelper;
import com.example.myapplication.ui.ViewModelFactory;
import com.example.myapplication.utils.ViewUtil;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment implements LocaleDialogFragment.Listener {
    private static final String TAG = "SettingsFragment";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    protected SettingsViewModel settingsViewModel;

    public SettingsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        ViewModelFactory factory = new ViewModelFactory();

        SettingsManager settingsManager = new SettingsManager(requireActivity());
        factory.register(SettingsViewModel.class, () -> new SettingsViewModel(RepositoryHelper.getSettingsRepository(settingsManager)));

        ViewModelProvider provider = new ViewModelProvider(this, factory);

        settingsViewModel = provider.get(SettingsViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        View languageContainer = view.findViewById(R.id.setting_language_container);
        TextView languageSummary = view.findViewById(R.id.setting_language_summary);

        languageContainer.setOnClickListener(v -> {
            Log.e(TAG, "onClick: " );
            LocaleDialogFragment fragment = LocaleDialogFragment.newInstance("", "");
            fragment.setListener(SettingsFragment.this);
            ViewUtil.showChildDialog(this, fragment, "settings_fragment");
        });

        settingsViewModel.getLocale();
        settingsViewModel.getLocaleLive().observe(getViewLifecycleOwner(), s -> {
            Log.e(TAG, "onChanged: " + s );
            settingsViewModel.currentLocale = s;
            languageSummary.setText(s);
        });
    }

    @Override
    public void onLanguageSelected(String languageCode) {
        Log.e(TAG, "onLanguageSelected: " + languageCode );

        settingsViewModel.updateLocale(languageCode);
    }
}
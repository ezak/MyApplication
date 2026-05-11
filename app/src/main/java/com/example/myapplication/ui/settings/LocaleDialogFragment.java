package com.example.myapplication.ui.settings;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.myapplication.R;
import com.example.myapplication.settings.SettingsManager;
import com.example.myapplication.settings.SettingsViewModel;
import com.example.myapplication.ui.RepositoryHelper;
import com.example.myapplication.ui.ViewModelFactory;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LocaleDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LocaleDialogFragment extends DialogFragment {

    private static final String TAG = "LocaleDialogFragment";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private SettingsViewModel settingsViewModel;
    private Listener listener;
    public LocaleDialogFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LocaleDialogFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LocaleDialogFragment newInstance(String param1, String param2) {
        LocaleDialogFragment fragment = new LocaleDialogFragment();
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

        ViewModelProvider provider = new ViewModelProvider(requireParentFragment(), factory);

        settingsViewModel = provider.get(SettingsViewModel.class);

    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        android.widget.FrameLayout container = new android.widget.FrameLayout(requireContext());
        View view = LayoutInflater.from(requireContext()).inflate(R.layout.fragment_locale_dialog, container, false);

        RadioGroup radioGroup = view.findViewById(R.id.language_radio_group);
        String[] locales = getResources().getStringArray(R.array.language_codes);

        for (String locale : locales) {
            RadioButton rb = new RadioButton(requireContext());
            rb.setText(locale);
            rb.setId(View.generateViewId());
            rb.setChecked(locale.equals(settingsViewModel.currentLocale));
            rb.setOnClickListener(v -> {
                RadioButton clickedButton = (RadioButton) v;
                String selectedValue = clickedButton.getText().toString();
                listener.onLanguageSelected(selectedValue);
            });

            // Match parent width so the whole row is clickable
            RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(
                    RadioGroup.LayoutParams.MATCH_PARENT,
                    RadioGroup.LayoutParams.WRAP_CONTENT
            );
            radioGroup.addView(rb, params);
        }

        return new MaterialAlertDialogBuilder(requireContext())
                .setView(view)
                .setTitle("Confirm Action")
                .setPositiveButton("Confirm", (dialog, which) -> {
                    // Handle confirmation
                })
                .setNegativeButton("Cancel", (dialog, which) -> {
                    // Handle cancellation
                })
                .create();
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public interface Listener {
        void onLanguageSelected(String languageCode);
    }
}
package com.example.myapplication.ui.claim;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.model.Stage;
import com.example.myapplication.model.Wizard;
import com.google.android.material.button.MaterialButton;

public class HealthFacilityClaimFragment extends Fragment {

    private static final String TAG = "HealthFacilityClaimFragment";
    private static final String ARG_PARAM1 = "param1";

    private String mParam1;

    private MaterialButton previous;
    private MaterialButton next;

    private Wizard wizard;

    public HealthFacilityClaimFragment() {
        // Required empty public constructor
    }

    public static HealthFacilityClaimFragment newInstance(String param1, String param2) {
        HealthFacilityClaimFragment fragment = new HealthFacilityClaimFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_health_facility_claim, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LinearLayout wizardHolder = view.findViewById(R.id.wizard_holder);
        previous = view.findViewById(R.id.previous);
        next = view.findViewById(R.id.next);

        wizard = new Wizard();
        // wizard.total = 2;
        wizard.current = 2;

        Stage stage = new Stage();
        stage.name = "First Stage";
        wizard.stages.add(stage);

        stage = new Stage();
        stage.name = "Second Stage";
        wizard.stages.add(stage)
        ;
        stage = new Stage();
        stage.name = "Third Stage";
        wizard.stages.add(stage);

        stage = new Stage();
        stage.name = "Fourth Stage";
        wizard.stages.add(stage);

        TextView textView = new TextView(requireContext());
        textView.setText(wizard.stages.get(wizard.current).name);
        wizardHolder.addView(textView);

        resetButtons();

        Log.e(TAG, "onClick: current " + wizard.current + " total " + wizard.stages.size() );

        previous.setOnClickListener(v -> {
            wizard.current -= 1;
            Log.e(TAG, "onClick: current " + wizard.current + " total " + wizard.stages.size());

            wizardHolder.removeAllViews();

            TextView textView2 = new TextView(requireContext());
            textView2.setText(wizard.stages.get(wizard.current).name);
            wizardHolder.addView(textView2);

            resetButtons();
        });

        next.setOnClickListener(v -> {
            wizard.current += 1;
            Log.e(TAG, "onClick: current " + wizard.current + " total " + wizard.stages.size() );

            wizardHolder.removeAllViews();

            TextView textView2 = new TextView(requireContext());
            textView2.setText(wizard.stages.get(wizard.current).name);
            wizardHolder.addView(textView2);


            resetButtons();
        });
    }


    private void resetButtons() {
        previous.setEnabled(!(wizard.current == 0));
        next.setEnabled(!(wizard.stages.size() - 1 == wizard.current));
    }
}
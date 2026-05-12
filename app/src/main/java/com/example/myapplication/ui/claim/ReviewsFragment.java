package com.example.myapplication.ui.claim;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.api.ApiServiceViewModel;
import com.example.myapplication.ui.MainViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReviewsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReviewsFragment extends Fragment implements RecyclerAdapter.Listener {

    private static final String TAG = "ReviewsFragment";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View view;
    private ApiServiceViewModel apiServiceViewModel;
    private MainViewModel mainViewModel;

    public ReviewsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ReviewsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ReviewsFragment newInstance(String param1, String param2) {
        ReviewsFragment fragment = new ReviewsFragment();
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

        mainViewModel = new ViewModelProvider(requireParentFragment()).get(MainViewModel.class);
        apiServiceViewModel = new ViewModelProvider(requireParentFragment()).get(ApiServiceViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return view = inflater.inflate(R.layout.fragment_reviews, container, false);
    }

    @SuppressLint({"SetTextI18n", "NotifyDataSetChanged"})
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireActivity());
        recyclerView.setLayoutManager(layoutManager);
        RecyclerAdapter adapter = new RecyclerAdapter(requireActivity(), this);
        recyclerView.setAdapter(adapter);
        recyclerView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                // Scroll position and offset tracking
                int position = layoutManager.findFirstVisibleItemPosition();
                View firstItemView = layoutManager.findViewByPosition(position);
                if (firstItemView != null) {
                    int offset = firstItemView.getTop() - recyclerView.getPaddingTop();
                    // Update ViewModel with the index and the offset
                    mainViewModel.recyclerPosition = position;
                    mainViewModel.recyclerOffset = offset;
                }

                // lazy loading
                /*int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

                // Check if we are not currently loading and have reached the threshold
                if (!apiServiceViewModel.isLoading()) {
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0) {
                        // Trigger next page fetch
                        apiServiceViewModel.fetchNextPage();
                    }
                }*/

            }
        });

        TextView status = view.findViewById(R.id.status);

        FloatingActionButton fab = view.findViewById(R.id.fab_add);

        apiServiceViewModel.fetchData();

        apiServiceViewModel.getDataLive().observe(getViewLifecycleOwner(), resource -> {
            switch (resource.status) {
                case LOADING:
                    status.setText("Loading data... ");
                    // if (progressBar != null) progressBar.setVisibility(View.VISIBLE);
                    break;

                case SUCCESS:
                    //status.setText("Data Loaded"  + resource.data);
                    status.setVisibility(View.GONE);
                    adapter.setDummyModelList(resource.data);
                    adapter.notifyDataSetChanged();
                    layoutManager.scrollToPositionWithOffset(mainViewModel.recyclerPosition,
                            mainViewModel.recyclerOffset);
                    // if (progressBar != null) progressBar.setVisibility(View.GONE);
                    // Toast.makeText(this, "Data Loaded Successfully!", Toast.LENGTH_SHORT).show();
                    // Update your UI with resource.data
                    break;

                case ERROR:
                    status.setText("Error loading data " + resource.message);
                    Snackbar.make(view, resource.message, Snackbar.LENGTH_SHORT).show();
                    // if (progressBar != null) progressBar.setVisibility(View.GONE);
                    // Toast.makeText(this, "Error: " + resource.message, Toast.LENGTH_LONG).show();
                    break;
            }
        });
    }

    @Override
    public void onItemClickedListener() {

    }
}
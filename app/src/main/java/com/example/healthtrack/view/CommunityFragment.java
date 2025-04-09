package com.example.healthtrack.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.healthtrack.R;
import com.example.healthtrack.model.Challenge;
import com.example.healthtrack.model.Workout;
import com.example.healthtrack.viewmodel.ChallengeViewModel;
import com.example.healthtrack.viewmodel.ChallengeViewModelFactory;
import com.example.healthtrack.viewmodel.WorkoutPlansViewModel;
import com.example.healthtrack.viewmodel.WorkoutPlansViewModelFactory;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CommunityFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CommunityFragment extends Fragment {
    private static final String TAG = "CommunityFragment";


    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private ListView challengeListView;
    private ChallengeAdapter adapter;
    private ChallengeViewModel viewModel;

    private String mParam1;
    private String mParam2;
    private Button createNewChallengeButton;
    private DatabaseReference challengeRef;


    public CommunityFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CommunityFragment.
     */
    public static CommunityFragment newInstance(String param1, String param2) {
        CommunityFragment fragment = new CommunityFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "!!!");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_community, container, false);
        challengeListView = view.findViewById(R.id.recyclerViewChallenges);
        createNewChallengeButton = view.findViewById(R.id.createNewChallengeButton);

        adapter = new ChallengeAdapter(getContext(), R.layout.challenge_item, new ArrayList<>(), challenge -> {
            Intent intent = new Intent(getActivity(), WorkoutPlanDetailActivity.class);
            intent.putExtra("challenge", challenge);//here see my modification in workout class:)
            startActivity(intent);
        });
        challengeListView.setAdapter(adapter); // Problem

        ChallengeViewModelFactory factory = new ChallengeViewModelFactory(getContext());
        viewModel = new ViewModelProvider(this, factory).get(ChallengeViewModel.class);

        // Initialize Firebase Database Reference
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        challengeRef = database.getReference("challenges");

        // Fetch workout plans and update UI
        viewModel.getChallenges().observe(getViewLifecycleOwner(), new Observer<List<Challenge>>() {
            @Override
            public void onChanged(List<Challenge> challenges) {
                Log.d(TAG, "Workout plans changed, size: " + challenges.size());
                adapter.clear();
                adapter.addAll(challenges);
                adapter.notifyDataSetChanged();
            }
        });
        createNewChallengeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateNewChallengeDialogFragment createNewChallengeFragment =
                        new CreateNewChallengeDialogFragment();
                createNewChallengeFragment.show(getParentFragmentManager(),
                        "CreateNewChallengeDialogFragment");
            }
        });
        return view;
    }
}
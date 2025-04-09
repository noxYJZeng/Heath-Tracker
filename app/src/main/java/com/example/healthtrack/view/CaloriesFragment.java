package com.example.healthtrack.view;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.healthtrack.R;
import com.example.healthtrack.model.User;
import com.example.healthtrack.model.Workout;
import com.example.healthtrack.viewmodel.CaloriesFragmentViewModel;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CaloriesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CaloriesFragment extends Fragment {

    private final User user = new User();

    public CaloriesFragment() {
        // Required empty public constructor
    }

    public static CaloriesFragment newInstance() {
        return new CaloriesFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calories, container, false);

        // set daily calorie / bmr text
        TextView dailyCalorieGoal = view.findViewById(R.id.dailyCalorieGoal);
        CaloriesFragmentViewModel viewModel = new ViewModelProvider(this)
                .get(CaloriesFragmentViewModel.class);
        viewModel.getDailyCalorieGoal();
        float dailyCalories = Workout.getTotalCalories();
        float bmr = (float) User.getCurrentUser().getBmr();
        dailyCalorieGoal.setText(
                String.format("%.0f/%.0f", dailyCalories, bmr)
        );

        // set pie chart
        PieChart chart = view.findViewById(R.id.calorieChart);

        List<Integer> colors = new ArrayList<>();
        colors.add(Color.BLACK);
        colors.add(Color.GRAY);


        // add data to chart
        List<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(dailyCalories, "Calories Burned"));
        entries.add(new PieEntry(bmr - dailyCalories, "Calories Left"));
        PieDataSet set = new PieDataSet(entries, "Calorie Goal");
        set.setColors(colors);
        PieData data = new PieData(set);
        chart.setData(data);

        // set options
        chart.setDrawEntryLabels(false);
        chart.setDrawHoleEnabled(false);
        chart.getData().setDrawValues(false);
        Description description = new Description();
        description.setText("");
        chart.setDescription(description);
        chart.getLegend().setEnabled(false);

        // refresh chart
        chart.invalidate();

        return view;
    }
}
package com.example.currencyapp.view.screens;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.currencyapp.R;

public class SettingFragment extends Fragment {

    private RadioButton radioButtonDays;
    private RadioButton radioButtonEnters;
    private ImageView imageViewMin;
    private ImageView imageViewMax;
    private TextView textViewCount;
    private TextView textViewDays;
    private TextView textViewEnters;
    private Button buttonAccept;
    private int count = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        radioButtonDays = view.findViewById(R.id.radioButtonDays);
        radioButtonEnters = view.findViewById(R.id.radioButtonEnters);
        imageViewMin = view.findViewById(R.id.imageViewButtonLeft);
        imageViewMax = view.findViewById(R.id.imageViewButtonRight);
        textViewCount = view.findViewById(R.id.textViewCount);
        textViewDays = view.findViewById(R.id.textViewDays);
        textViewEnters = view.findViewById(R.id.textViewEnters);
        buttonAccept = view.findViewById(R.id.buttonAcceptCountUpdate);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        radioButtonDays.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                textViewDays.setVisibility(View.VISIBLE);
                textViewEnters.setVisibility(View.INVISIBLE);
            }
        });
        radioButtonEnters.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                textViewEnters.setVisibility(View.VISIBLE);
                textViewDays.setVisibility(View.INVISIBLE);
            }
        });
        imageViewMin.setOnClickListener(view -> {
            if (count > 1) {
                count--;
                String countAsString = count + "";
                textViewCount.setText(countAsString);
            }
        });
        imageViewMax.setOnClickListener(view -> {
            if (count < 10) {
                count++;
                String countAsString = count + "";
                textViewCount.setText(countAsString);
            }
        });

        buttonAccept.setOnClickListener(view -> {
            boolean isFirstStart = false;
            boolean isDay = radioButtonDays.isChecked();
            long time= System.currentTimeMillis();
            int countEnters = 1;
            SharedPreferences.Editor editor = requireContext().getSharedPreferences("startData", MODE_PRIVATE).edit();
            editor.putBoolean("isFirstStart", isFirstStart);
            editor.putBoolean("isDay", isDay);
            if (isDay) {
                editor.putLong("time", time);
            }
            editor.putInt("count", count);
            editor.putInt("countEnters", countEnters);
            editor.apply();
            Navigation.findNavController(view).navigate(R.id.action_splashFragment_to_viewPagerFragment2);
        });
    }
}
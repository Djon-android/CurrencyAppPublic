package com.example.currencyapp.onboarding.screens;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.currencyapp.R;

public class ConverterFragment extends Fragment {

    private TextView textViewToCurrencyList;
    private ImageView imageViewToCurrencyList;
    private ViewPager2 viewPager2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_converter, container, false);
        textViewToCurrencyList = view.findViewById(R.id.textVIewToCurrencyList);
        imageViewToCurrencyList = view.findViewById(R.id.imageViewToCurrencyList);
        viewPager2 = requireActivity().findViewById(R.id.viewPager2);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        textViewToCurrencyList.setOnClickListener(view -> viewPager2.setCurrentItem(1));
        imageViewToCurrencyList.setOnClickListener(view -> viewPager2.setCurrentItem(1));
    }
}
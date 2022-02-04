package com.example.currencyapp.onboarding;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.currencyapp.R;
import com.example.currencyapp.onboarding.screens.ConverterFragment;
import com.example.currencyapp.onboarding.screens.CurrencyListFragment;

import java.util.ArrayList;
import java.util.Objects;

public class ViewPagerFragment extends Fragment {

    private ArrayList<Fragment> fragments;
    private ViewPager2 viewPager2;
    private ViewPagerViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(ViewPagerViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_pager, container, false);


        viewPager2 = view.findViewById(R.id.viewPager2);

        fragments = new ArrayList<>();
        fragments.add(new ConverterFragment());
        fragments.add(new CurrencyListFragment());

        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager(), getLifecycle(), fragments);
        viewPager2.setAdapter(adapter);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SharedPreferences prefs = requireContext().getSharedPreferences("startData", MODE_PRIVATE);
        boolean isFirstStart = prefs.getBoolean("isFirstStart", true);
        if (isFirstStart) {
            viewModel.loadData(true);
            Navigation.findNavController(view).navigate(R.id.action_viewPagerFragment2_to_splashFragment);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel.loadData(false);
    }
}

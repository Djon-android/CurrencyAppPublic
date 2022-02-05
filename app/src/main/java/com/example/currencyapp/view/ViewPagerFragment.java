package com.example.currencyapp.view;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.currencyapp.R;
import com.example.currencyapp.adapters.ViewPagerAdapter;
import com.example.currencyapp.view.screens.ConverterFragment;
import com.example.currencyapp.view.screens.CurrencyListFragment;

import java.util.ArrayList;

public class ViewPagerFragment extends Fragment {

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


        ViewPager2 viewPager2 = view.findViewById(R.id.viewPager2);

        ArrayList<Fragment> fragments = new ArrayList<>();
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

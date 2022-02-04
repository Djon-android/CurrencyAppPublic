package com.example.currencyapp.onboarding.screens;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.currencyapp.R;
import com.example.currencyapp.adapters.ValuteAdapter;
import com.example.currencyapp.onboarding.ViewPagerViewModel;


public class CurrencyListFragment extends Fragment {

    private ViewPagerViewModel viewModel;
    private RecyclerView recyclerView;
    private ValuteAdapter adapter;
    private ViewPager2 viewPager2;

    private ImageView imageViewSetting;
    private TextView textViewSetting;
    private ImageView imageViewUpdate;
    private TextView textViewUpdate;
    private ImageView imageViewToCalculator;
    private TextView textViewToCalculator;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(ViewPagerViewModel.class);
        adapter = new ValuteAdapter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_currency_list, container, false);
        recyclerView = view.findViewById(R.id.recyclerViewValuteList);
        textViewSetting = view.findViewById(R.id.textViewSetting);
        imageViewSetting = view.findViewById(R.id.imageViewSetting);
        imageViewUpdate = view.findViewById(R.id.imageViewUpdateRate);
        textViewUpdate = view.findViewById(R.id.textViewUpdateRate);
        imageViewToCalculator = view.findViewById(R.id.imageViewToCalculator);
        textViewToCalculator = view.findViewById(R.id.textViewToCalculator);
        viewPager2 = requireActivity().findViewById(R.id.viewPager2);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel.getValutesLiveData().observe(requireActivity(), valutes -> adapter.setValutes(valutes));
        textViewSetting.setOnClickListener(view -> Navigation.findNavController(view).navigate(R.id.action_viewPagerFragment2_to_splashFragment));
        imageViewSetting.setOnClickListener(view -> Navigation.findNavController(view).navigate(R.id.action_viewPagerFragment2_to_splashFragment));
        imageViewUpdate.setOnClickListener(view -> loadAndToast());
        textViewUpdate.setOnClickListener(view -> loadAndToast());
        imageViewToCalculator.setOnClickListener(view -> viewPager2.setCurrentItem(0));
        textViewToCalculator.setOnClickListener(view -> viewPager2.setCurrentItem(0));
    }

    private void loadAndToast() {
        viewModel.loadData(true);
        Toast.makeText(requireContext(), "Загрузка данных", Toast.LENGTH_SHORT).show();
    }
}
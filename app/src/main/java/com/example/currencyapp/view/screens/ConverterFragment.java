package com.example.currencyapp.view.screens;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.example.currencyapp.R;
import com.example.currencyapp.pojo.Valute;
import com.example.currencyapp.view.ViewPagerViewModel;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ConverterFragment extends Fragment {

    private TextView textViewToCurrencyList;
    private ImageView imageViewToCurrencyList;
    private EditText editTextSum;
    private Spinner spinnerCurrency;
    private TextView textViewStartCurrencySum;
    private TextView textViewResultCurrencySum;
    private ImageView imageViewArrow;
    private ViewPager2 viewPager2;

    private ViewPagerViewModel viewModel;

    private double sum;
    private double value;
    private String stringCode;
    private int nominal;
    private double result = 0;
    private List<Valute> valutes;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        valutes = new ArrayList<>();
        viewModel = new ViewModelProvider(requireActivity()).get(ViewPagerViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_converter, container, false);
        textViewToCurrencyList = view.findViewById(R.id.textVIewToCurrencyList);
        imageViewToCurrencyList = view.findViewById(R.id.imageViewToCurrencyList);
        editTextSum = view.findViewById(R.id.editTextSum);
        spinnerCurrency = view.findViewById(R.id.spinnerCurrency);
        textViewStartCurrencySum = view.findViewById(R.id.textViewStartCurrencySum);
        textViewResultCurrencySum = view.findViewById(R.id.textViewResultCurrencySum);
        imageViewArrow = view.findViewById(R.id.imageViewArrow);
        viewPager2 = requireActivity().findViewById(R.id.viewPager2);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        textViewToCurrencyList.setOnClickListener(view -> viewPager2.setCurrentItem(1));
        imageViewToCurrencyList.setOnClickListener(view -> viewPager2.setCurrentItem(1));
        editTextSum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().isEmpty()) {
                    sum = Double.parseDouble(charSequence.toString());
                } else {
                    sum = 0;
                }
                String sumStart = charSequence + " RUB";
                textViewStartCurrencySum.setText(sumStart);
                calculateCurrency();
                showResult();
            }

            @Override
            public void afterTextChanged(Editable editable) {


            }
        });
        viewModel.getValutesLiveData().observe(this, valutesLiveData -> {
            valutes.clear();
            valutes.addAll(valutesLiveData);
            List<String> currency = new ArrayList<>();
            for (Valute valute : valutesLiveData) {
                currency.add(valute.getName());
            }
            spinnerCurrency.setAdapter(new ArrayAdapter<>(requireContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, currency));
        });
        spinnerCurrency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                value = valutes.get(i).getValue();
                nominal = valutes.get(i).getNominal();
                stringCode = valutes.get(i).getCharCode();
                calculateCurrency();
                showResult();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void calculateCurrency() {
        if (sum >= 1) {
            result = sum * (nominal / value);
        } else {
            result = 0;
        }
    }

    private void showResult() {
        if (result > 0) {
            DecimalFormat decimalFormat = new DecimalFormat("#.###");
            String resultString = decimalFormat.format(result) + " " + stringCode;
            textViewResultCurrencySum.setText(resultString);
            imageViewArrow.setVisibility(View.VISIBLE);
            textViewResultCurrencySum.setVisibility(View.VISIBLE);
            textViewStartCurrencySum.setVisibility(View.VISIBLE);
        } else {
            imageViewArrow.setVisibility(View.INVISIBLE);
            textViewResultCurrencySum.setVisibility(View.INVISIBLE);
            textViewStartCurrencySum.setVisibility(View.INVISIBLE);
        }
    }
}
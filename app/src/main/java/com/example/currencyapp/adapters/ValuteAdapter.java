package com.example.currencyapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.currencyapp.R;
import com.example.currencyapp.pojo.Valute;

import java.util.ArrayList;
import java.util.List;

public class ValuteAdapter extends RecyclerView.Adapter<ValuteAdapter.ValuteViewHolder> {

    private List<Valute> valutes;

    public ValuteAdapter() {
        valutes = new ArrayList<>();
    }

    public void setValutes(List<Valute> valutes) {
        this.valutes = valutes;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ValuteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.valute_item, parent, false);
        return new ValuteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ValuteViewHolder holder, int position) {
        holder.textViewNameValute.setText(valutes.get(position).getName());
        holder.textViewCode.setText(valutes.get(position).getNumCode());
        holder.textViewStringCode.setText(valutes.get(position).getCharCode());
//        holder.textViewCountOfUnits.setText(valutes.get(position).getNominal());
        String value = valutes.get(position).getValue() + "";
        holder.textViewRate.setText(value);
    }

    @Override
    public int getItemCount() {
        return valutes.size();
    }

    class ValuteViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewNameValute;
        private TextView textViewCode;
        private TextView textViewStringCode;
        private TextView textViewCountOfUnits;
        private TextView textViewRate;

        public ValuteViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNameValute = itemView.findViewById(R.id.textViewNameValute);
            textViewCode = itemView.findViewById(R.id.textViewCode);
            textViewStringCode = itemView.findViewById(R.id.textViewStringCode);
            textViewCountOfUnits = itemView.findViewById(R.id.textViewCountOfUnits);
            textViewRate = itemView.findViewById(R.id.textViewRate);
        }
    }
}

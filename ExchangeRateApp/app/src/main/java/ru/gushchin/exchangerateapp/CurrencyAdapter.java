package ru.gushchin.exchangerateapp;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

class CurrencyAdapter extends RecyclerView.Adapter<CurrencyAdapter.CurrencyViewHolder>{

    private List<Currency> currencyList;

    CurrencyAdapter(List<Currency> currencyList) {
        this.currencyList = currencyList;
    }

    @NonNull
    @Override
    public CurrencyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.currency_item,parent,false);
        return new CurrencyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CurrencyViewHolder holder, int position) {
        holder.bind(currencyList.get(position));
    }


    @Override
    public int getItemCount() {
        return currencyList.size();
    }

    static final class CurrencyViewHolder extends RecyclerView.ViewHolder {

        private final TextView textViewName;
        private final TextView textViewNumber;
        private final TextView textViewCode;
        private final TextView textViewPrice;
        private final TextView textViewMoving;


        public CurrencyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewNumber = itemView.findViewById(R.id.textViewNumberEd);
            textViewPrice = itemView.findViewById(R.id.textViewPriceTitle);
            textViewMoving = itemView.findViewById(R.id.textViewMovingTitle);
            textViewCode = itemView.findViewById(R.id.textViewCode);
        }

        private void bind (@NonNull Currency currency){

            textViewName.setText(currency.name);
            textViewName.setTextColor(Color.parseColor("#000000"));

            textViewNumber.setText(currency.number);
            textViewNumber.setTextColor(Color.parseColor("#000000"));

            textViewMoving.setText(currency.moving);
            textViewMoving.setTextColor(Color.parseColor("#000000"));

            textViewPrice.setText(currency.price);
            textViewPrice.setTextColor(Color.parseColor("#000000"));

            textViewCode.setText(currency.code);
            textViewCode.setTextColor(Color.parseColor("#000000"));

            if (currency.moving.substring(0,1).equals("+")){
                textViewMoving.setTextColor(Color.parseColor("#08C501"));
            }else {
                textViewMoving.setTextColor(Color.parseColor("#F9401F"));
            }
        }

    }
}

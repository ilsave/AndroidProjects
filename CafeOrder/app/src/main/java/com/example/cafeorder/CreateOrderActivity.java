package com.example.cafeorder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

public class CreateOrderActivity extends AppCompatActivity {

    private TextView textViewHello;
    private TextView textViewAdditions;
    private CheckBox checkBoxMilk;
    private CheckBox checkBoxSugar;
    private CheckBox checkBoxLemon;
    private Spinner spinnerTea;
    private Spinner spinnerCoffee;
    private String drink;
    private String name;
    private String password;
    private StringBuilder builderAdditions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_order);
        textViewHello = findViewById(R.id.textViewHello);
        textViewAdditions = findViewById(R.id.textViewAddition);
        checkBoxLemon = findViewById(R.id.checkboxLemon);
        checkBoxMilk = findViewById(R.id.checkboxMilk);
        checkBoxSugar = findViewById(R.id.checkboxSugar);
        spinnerCoffee = findViewById(R.id.spinnerCoffe);
        Intent intent = getIntent();
        if (intent.hasExtra("name") && intent.hasExtra("password")){
            name = intent.getStringExtra("name");
            password = intent.getStringExtra("password");
        }
        else {
            name = getString(R.string.default_name);
            password = getString(R.string.default_password);
        }
        String hello = String.format(getString(R.string.hello_user), name);
        textViewHello.setText(hello);
        spinnerTea = findViewById(R.id.spinnerTea);
        drink = getString(R.string.tea);
        String additions = String.format(getString(R.string.additions), drink);
        textViewAdditions.setText(additions);
        builderAdditions = new StringBuilder();
    }

    public void onClickChangeDrink(View view) {
        RadioButton button = (RadioButton) view;
        int id = button.getId();
        if (id == R.id.radioButtonTea){
            drink = getString(R.string.tea);
            spinnerTea.setVisibility(View.VISIBLE);//vidim mu eto ili net
            spinnerCoffee.setVisibility(View.INVISIBLE);
            checkBoxLemon.setVisibility(View.VISIBLE);
        }else{
            drink = getString(R.string.coffee);
            spinnerCoffee.setVisibility(View.VISIBLE);
            spinnerTea.setVisibility(View.INVISIBLE);
            checkBoxLemon.setVisibility(View.INVISIBLE);
        }
        String additions = String.format(getString(R.string.additions), drink);    //vstavlyaem coffe or tea
        textViewAdditions.setText(additions);
    }

    public void onclickSendOrder(View view) {
        builderAdditions.setLength(0);
        if (checkBoxMilk.isChecked()){
            builderAdditions.append(getString((R.string.milk))).append(" ");
        }
        if (checkBoxSugar.isChecked()){
            builderAdditions.append(getString((R.string.sugar))).append(" ");
        }
        if (checkBoxLemon.isChecked() && drink.equals(getString(R.string.tea))){
            builderAdditions.append(getString((R.string.lemon))).append(" ");
        }
        String optionOfDrink = "";
        if (drink.equals(getString((R.string.tea)))){
            optionOfDrink = spinnerTea.getSelectedItem().toString();//polychenie znachenia iz spinnera coffe
        } else {
            optionOfDrink = spinnerCoffee.getSelectedItem().toString();
        }
        String order = String.format(getString(R.string.order),name,password,drink, optionOfDrink);
        String additions;
        if (builderAdditions.length() > 0){
            additions = getString(R.string.neeed_additions) + builderAdditions.toString();
        }else{
            additions = "";
        }
        String fullOrder = order + additions;
        Intent intent = new Intent(this,OrderDetailActivity.class);
        intent.putExtra("order", fullOrder);
        startActivity(intent);
    }
}
